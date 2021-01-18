package com.example.eyezo.hockeyapp;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class MyPlayersList extends AppCompatActivity implements PlayerAdapter.ItemClicked{


    List<AddPlayer> myPlayers;
    List<TeamClass> teamsPerCoachList;
    Spinner spTeamPerCoach;
    Dialog dialog;
    int count = 0;

    RecyclerView rcView;
    RecyclerView.Adapter myAdapter1;
    RecyclerView.LayoutManager layoutManager;


    final static int DELETE_REQUEST_CODE = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_players_list);


        rcView = findViewById(R.id.lstPlayers);
        rcView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        rcView.setLayoutManager(layoutManager);

        myPlayers = new ArrayList<>();
        teamsPerCoachList = new ArrayList<>();
        spTeamPerCoach = findViewById(R.id.spTeamArr);


        loadPlayerAndTeam();


        spTeamPerCoach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                count++;
                if(parent.getItemAtPosition(position).toString().equals("Please select a team"))
                {
                    //do nothing
                }
                else
                {
                    loadPlayersByTeam(teamsPerCoachList.get(position - 1).getTeamName());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.logo_2);
        actionBar.setTitle("Players");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*
         * removes the deleted player from the list using id_deleted from MyPlayers
         */
        if(resultCode == RESULT_OK && requestCode == DELETE_REQUEST_CODE)
        {
            String deletedPlayerId = data.getStringExtra("id_deleted");
            for(int x = 0; x < myPlayers.size(); x++)
            {
                if(myPlayers.get(x).getObjectId().equals(deletedPlayerId))
                {
                    myPlayers.remove(x);
                    myAdapter1.notifyDataSetChanged();
                }
            }
        }
    }

    private void loadPlayersByTeam(String team)
    {

        if(count >= 2)
        {
            dialog = new SpotsDialog.Builder().setContext(this).setTheme(R.style.Custom).build();
            dialog.show();

            String whereClause = "teamName = '" + team + "'";
            DataQueryBuilder queryBuilder = DataQueryBuilder.create();
            queryBuilder.setWhereClause(whereClause);

            Backendless.Persistence.of(AddPlayer.class).find(queryBuilder, new AsyncCallback<List<AddPlayer>>() {
                @Override
                public void handleResponse(List<AddPlayer> response) {
                    myPlayers.clear();
                    myPlayers = response;
                    updateList();
                    dialog.dismiss();
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    dialog.dismiss();
                    Toast.makeText(MyPlayersList.this, "error "+fault.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void loadPlayerAndTeam()
    {
        /*
         * loads players to the list together with their designated teams
         */
        dialog = new SpotsDialog.Builder().setContext(this).setTheme(R.style.Custom).build();
        dialog.show();

        String whereClause = "coachName = '" +getIntent().getStringExtra("name") + "'";
        DataQueryBuilder qb = DataQueryBuilder.create();
        qb.setWhereClause(whereClause);
        qb.setPageSize(100).setOffset(0);

        Backendless.Persistence.of(AddPlayer.class).find(qb, new AsyncCallback<List<AddPlayer>>() {
            @Override
            public void handleResponse(List<AddPlayer> response) {
                myPlayers = response;
                updateList();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(MyPlayersList.this, "error "+ fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        String clauseTwo = "coachName = '" + getIntent().getStringExtra("name")+ "'";
        DataQueryBuilder query = DataQueryBuilder.create();
        query.setWhereClause(clauseTwo);
        query.setPageSize(100).setOffset(0);

        Backendless.Persistence.of(TeamClass.class).find(query, new AsyncCallback<List<TeamClass>>() {
            @Override
            public void handleResponse(List<TeamClass> response) {
                teamsPerCoachList = response;
                fillSpinner();
                dialog.dismiss();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(MyPlayersList.this, "error "+ fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateList()
    {
        /*
         * updates the list
         */
        myAdapter1 = new PlayerAdapter(this, myPlayers);
        rcView.setAdapter(myAdapter1);
    }
    private void fillSpinner()
    {
        String[] teamNames = new String[teamsPerCoachList.size()+1];
        teamNames[0] = "Please select a team";
        for(int i = 0; i < teamsPerCoachList.size();i++)
        {
            teamNames[i +1] = teamsPerCoachList.get(i).getTeamName();
        }

        ArrayAdapter<String> sp = new ArrayAdapter<>(this, R.layout.spinner_item,teamNames);
        spTeamPerCoach.setAdapter(sp);
    }

    @Override
    public void onItemClicked(int index) {
        String playerId = myPlayers.get(index).getObjectId();
        Intent intent = new Intent(MyPlayersList.this,MyPlayers.class);
        intent.putExtra("playerId", playerId);
        startActivityForResult(intent, DELETE_REQUEST_CODE);
    }
}
