package com.example.eyezo.hockeyapp;

import android.app.Dialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class MovePlayer extends AppCompatActivity {
    Spinner spPlayers, spTeams;
    TextView txtCurrent;
    Dialog dialog;
    List<AddPlayer> playersInTheTeam;
    List<TeamClass> teams;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_player);

        playersInTheTeam = new ArrayList<>();
        teams = new ArrayList<>();

        spPlayers = findViewById(R.id.spMovePlayerName);
        spTeams = findViewById(R.id.spTeamsToMoveTo);
        txtCurrent = findViewById(R.id.txtCurrentTeam);

        loadPlayersAndTeams();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.logo_2);
        actionBar.setTitle("Move Player");
    }
    private void selectedPlayer(int pos)
    {
        String currTeam = playersInTheTeam.get(pos).getTeamName();
        if(!currTeam.isEmpty() || !currTeam.equals(null))
            txtCurrent.setText("currently in the " + currTeam + " team");
    }
    private void loadPlayersAndTeams()
    {
        /*
         *get Players and teams data from the database
         */

        dialog = new SpotsDialog.Builder().setTheme(R.style.Custom).setContext(this).build();
        dialog.show();

        Backendless.Persistence.of(AddPlayer.class).find(new AsyncCallback<List<AddPlayer>>() {
            @Override
            public void handleResponse(List<AddPlayer> response) {
                playersInTheTeam = response;

                String whr = "teamOrOpp = 'Team'";
                DataQueryBuilder queryBuilder = DataQueryBuilder.create();
                queryBuilder.setWhereClause(whr);

                Backendless.Persistence.of(TeamClass.class).find(queryBuilder, new AsyncCallback<List<TeamClass>>() {
                    @Override
                    public void handleResponse(List<TeamClass> response) {
                        teams = response;
                        fillSpinner();
                        dialog.dismiss();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        dialog.dismiss();
                        Toast.makeText(MovePlayer.this, "error "+fault.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
            @Override
            public void handleFault(BackendlessFault fault) {
                dialog.dismiss();
                Toast.makeText(MovePlayer.this, "error "+fault.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }
    private void fillSpinner()
    {
        /*
         *fill the spinner with names of teams and players
         *
         */

        try
        {
            //Arrays to fill the spinner
            String[] team = new String[teams.size()];


            //A for loop to populate the arrays with names of players and teams
            for(int i = 0; i < teams.size();i++)
            {
                team[i] = teams.get(i).getTeamName();
            }

            String[] players = new String[playersInTheTeam.size()];
            for(int x = 0; x < playersInTheTeam.size();x++)
            {
                players[x] = playersInTheTeam.get(x).getName() + " " +
                        playersInTheTeam.get(x).getSurname();
            }
            ArrayAdapter<String> sp1 = new ArrayAdapter<>(this, R.layout.spinner_item,team);
            ArrayAdapter<String> sp2 = new ArrayAdapter<>(this, R.layout.spinner_item,players);

            spTeams.setAdapter(sp1);
            spPlayers.setAdapter(sp2);

            spPlayers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedPlayer(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            String val = playersInTheTeam.get(0).getTeamName();
            if (!val.equals(null))
                txtCurrent.setText("currently in the " + val + " team");

        }catch (IndexOutOfBoundsException e)
        {
            Toast.makeText(this, ""+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    public void btnMove_Player(View v)
    {


        //get position of a selected player from the spinner
        int playerPosition = spPlayers.getSelectedItemPosition();

        //use the position to get the player's id from the list
        String id = playersInTheTeam.get(playerPosition).getObjectId();


        dialog = new SpotsDialog.Builder().setContext(this).setTheme(R.style.Custom).build();
        dialog.show();

        //find the player by id in order to update the team in which he will move to
        Backendless.Persistence.of(AddPlayer.class).findById(id, new AsyncCallback<AddPlayer>() {
            @Override
            public void handleResponse(AddPlayer response) {

                String teamToMoveTo = spTeams.getSelectedItem().toString();
                response.setTeamName(teamToMoveTo);

                Backendless.Persistence.of(AddPlayer.class).save(response, new AsyncCallback<AddPlayer>() {
                    @Override
                    public void handleResponse(AddPlayer response) {
                        MovePlayer.this.finish();
                        dialog.dismiss();

                        UtilityHelper.customToast(MovePlayer.this, "Player Moved Successfully");
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        dialog.dismiss();
                        Toast.makeText(MovePlayer.this, "error "+ fault.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                dialog.dismiss();
                Toast.makeText(MovePlayer.this, "error "+ fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
