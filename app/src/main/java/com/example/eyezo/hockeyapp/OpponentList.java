package com.example.eyezo.hockeyapp;

import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class OpponentList extends AppCompatActivity {

    ListView lstOpps;
    ListView lstTeams;
    List<TeamClass> opponentClassList;
    List<TeamClass> teamClassList;
    Dialog progDialog;
    OppsAdapter oppsAdapter;
    OppsAdapter teamsAdapter;
    Fragment frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opponent_list);

        lstOpps = findViewById(R.id.lstOpps);
        lstTeams = findViewById(R.id.lstTeams);
        teamClassList = new ArrayList<>();
        opponentClassList = new ArrayList<>();

        progDialog = new SpotsDialog.Builder().setContext(this).setTheme(R.style.Custom).build();
        getOpponent();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.logo_2);
        actionBar.setTitle("Opponents");

    }
    private void getOpponent()
    {
        /*
         * gets opponents from backendless
         */
        progDialog.show();
        String opp = "teamOrOpp = 'Opponent'";
        DataQueryBuilder dq = DataQueryBuilder.create();
        dq.setWhereClause(opp);
        Backendless.Persistence.of(TeamClass.class).find(dq, new AsyncCallback<List<TeamClass>>() {
            @Override
            public void handleResponse(List<TeamClass> response) {
                opponentClassList = response;
                oppsAdapter = new OppsAdapter(OpponentList.this, opponentClassList);
                lstOpps.setAdapter(oppsAdapter);
                getTeams();

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                progDialog.dismiss();
                Toast.makeText(OpponentList.this, "error "+ fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getTeams()
    {
        /*
         * gets teams from backendless
         */
        String opp = "teamOrOpp = 'Team'";
        DataQueryBuilder dq = DataQueryBuilder.create();
        dq.setWhereClause(opp);

        Backendless.Persistence.of(TeamClass.class).find(dq, new AsyncCallback<List<TeamClass>>() {
            @Override
            public void handleResponse(List<TeamClass> response) {
                teamClassList = response;
                teamsAdapter = new OppsAdapter(OpponentList.this, teamClassList);
                lstTeams.setAdapter(teamsAdapter);
                progDialog.dismiss();

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                progDialog.dismiss();
                Toast.makeText(OpponentList.this, "error "+ fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
