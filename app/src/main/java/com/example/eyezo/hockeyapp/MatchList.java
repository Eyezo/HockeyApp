package com.example.eyezo.hockeyapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import dmax.dialog.SpotsDialog;

public class MatchList extends AppCompatActivity implements MatchAdapter.ItemClicked {

    List<MatchLineUpClass> matchLineUpClassList;
    List<String> vsTeams;
    Dialog dlg;
    ListView lstMatches;
    int numObjCount =0;
    CountDownLatch latch1, latch2;
    String error;

    RecyclerView rcView;
    RecyclerView.Adapter myAdapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_list);

        rcView = findViewById(R.id.lstMatches);
        matchLineUpClassList = new ArrayList<>();
        vsTeams = new ArrayList<>();
        rcView.setHasFixedSize(true);
        rcView.setLayoutManager(new LinearLayoutManager(this));

        new Matches().execute();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Matches");
        actionBar.setIcon(R.mipmap.logo_1);

    }

    @Override
    public void onItemClicked(int index) {
        String name = vsTeams.get(index);
        Intent intent = new Intent(MatchList.this, MatchStatsActivity.class);
        intent.putExtra("matchHeader", name);
        startActivity(intent);
    }

    private class Matches extends AsyncTask<Void,Void,String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dlg = new SpotsDialog.Builder().setContext(MatchList.this).setTheme(R.style.Custom)
                    .build();
            dlg.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            /*
             * uses AsyncTask due to the list is too fast for the compiler
             * uses latch1 to get the number of objects in the table
             * uses latch2 to save its sixteen objects where latch1 stopped count
             */

            error = "";

            latch1 = new CountDownLatch(1);
            Backendless.Data.of(MatchLineUpClass.class).getObjectCount(new AsyncCallback<Integer>() {
                @Override
                public void handleResponse(Integer response) {
                    numObjCount = response;
                    latch1.countDown();
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    error = fault.getMessage();
                }
            });
            try
            {
                latch1.await();
            }catch (InterruptedException e)
            {
                error = e.getMessage();
            }

            DataQueryBuilder dataQueryBuilder = DataQueryBuilder.create();
            dataQueryBuilder.setWhereClause("team is not null");
            dataQueryBuilder.setPageSize(100).setOffset(0);
            dataQueryBuilder.setSortBy("numberOfData");

            latch2 = new CountDownLatch(1);
            Backendless.Persistence.of(MatchLineUpClass.class).find(dataQueryBuilder, new AsyncCallback<List<MatchLineUpClass>>() {
                @Override
                public void handleResponse(List<MatchLineUpClass> response) {
                    matchLineUpClassList = response;
                    latch2.countDown();
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    latch2.countDown();
                    error = fault.getMessage();
                }
            });

            try
            {
                latch2.await();
            }catch (InterruptedException e)
            {
                error = e.getMessage();
            }


            return error;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            assignMatches();
            toastMessage(s);
            dlg.dismiss();
        }
    }

    int n = 0;
    int counter = 0;
    public void assignMatches()
    {

        if(matchLineUpClassList.size() == 0)
        {
            lstMatches.setVisibility(View.GONE);
            //toastMessage("No match has been created for the currently logged in coach");
        }
        else
        {
            for(int x = 0; x < matchLineUpClassList.size();x++)
            {
                String coachOfTeam = getIntent().getStringExtra("name");
                if(!coachOfTeam.equals(matchLineUpClassList.get(x).getCoach()))
                {
                    matchLineUpClassList.remove(x);
                }
            }
            for(int x = 0; x < matchLineUpClassList.size();x++)
            {
                if(counter == n)
                {
                    String name  = matchLineUpClassList.get(x).getTeam() + " VS "+
                            matchLineUpClassList.get(x).getTeamAgainst();

                    vsTeams.add(name);
                    n = n + 16;
                }
                counter++;
            }



            myAdapter1 = new MatchAdapter(this, vsTeams);
            rcView.setAdapter(myAdapter1);
        }


    }
    private void toastMessage(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
