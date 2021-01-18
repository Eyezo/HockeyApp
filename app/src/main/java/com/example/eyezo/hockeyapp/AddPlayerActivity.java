package com.example.eyezo.hockeyapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class AddPlayerActivity extends AppCompatActivity {
    final static int RESULT_ACT= 1;

    EditText edPlayerName, edPlayerSurname;
    Spinner spPlayerTeam;

    List<TeamClass> dataOfTeams;
    Dialog dialog;
    AddPlayer myPlayer;
    Button btnSavePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);

        edPlayerName = findViewById(R.id.edPlayerName);
        edPlayerSurname = findViewById(R.id.edPlayerSurname);
        spPlayerTeam = findViewById(R.id.spTeamNames);
        myPlayer = new AddPlayer();
        dataOfTeams = new ArrayList<>();
        btnSavePlayer = findViewById(R.id.btnSaveNewRole);
        teams();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add Player");
        actionBar.setIcon(R.mipmap.logo_2);
    }
    public void btnMedInfo(View v)
    {
        /*
         * takes you to the medical information activity
         */
        startActivityForResult(new Intent(this, MedInformation.class), RESULT_ACT);
    }
    public void btnSavePlayer(View v)
    {
        /*
         * saves player under a team with a designated coach
         */

        String playerName = edPlayerName.getText().toString().trim();
        String playerSurname = edPlayerSurname.getText().toString().trim();
        String playerTeam = spPlayerTeam.getSelectedItem().toString();

        if(playerTeam.equals("Please select team names"))
        {


            UtilityHelper.customToast(this, "Please select a team for the player");
        }
        else
        {
            if(playerName.isEmpty() || playerSurname.isEmpty() || playerTeam.isEmpty())
            {

                UtilityHelper.customToast(this, "Please enter both fields");

            }
            else
            {
                if(connectionAvailable())
                {
                    dialog = new SpotsDialog.Builder().setContext(this).setTheme(R.style.Custom).build();
                    dialog.show();

                    myPlayer.setName(playerName);
                    myPlayer.setSurname(playerSurname);
                    myPlayer.setTeamName(playerTeam);
                    myPlayer.setCoachName(getIntent().getStringExtra("name"));

                    Backendless.Persistence.save(myPlayer, new AsyncCallback<AddPlayer>() {
                        @Override
                        public void handleResponse(AddPlayer response) {
                            AddPlayerActivity.this.finish();
                            dialog.dismiss();
                            UtilityHelper.customToast(AddPlayerActivity.this, "Player Saved Successfully" );

                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(AddPlayerActivity.this, "error "+ fault.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                }
                else
                {
                    Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        }


    }
    private void savePlayer(AddPlayer player)
    {
        myPlayer = player;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*
         * gets results from medical information class and saves them as part of
         * player info
         */
        if(requestCode == RESULT_ACT && resultCode== RESULT_OK)
        {
            AddPlayer player = new AddPlayer();
            player.setMedAidName(data.getStringExtra("medName"));
            player.setMedAidNumber(data.getStringExtra("medNumb"));
            player.setMedAidPlan(data.getStringExtra("medPlan"));
            player.setAllergies(data.getStringExtra("allergies"));
            player.setFirstParentNum(data.getStringExtra("parent1"));
            player.setSecondParentNum(data.getStringExtra("parent2"));

            savePlayer(player);

        }
    }

    private void teams()
    {
        /*
         * gets a list of teams where coach is equals to the currently logged in coach
         */
        String coachName = getIntent().getStringExtra("name");

        String whereCluase = "coachName = '" + coachName + "'";
        DataQueryBuilder qB = DataQueryBuilder.create();
        qB.setWhereClause(whereCluase);

        dialog = new SpotsDialog.Builder().setTheme(R.style.Custom).setContext(this).build();
        dialog.show();

        Backendless.Persistence.of(TeamClass.class).find(qB, new AsyncCallback<List<TeamClass>>() {
            @Override
            public void handleResponse(List<TeamClass> response) {
                dataOfTeams = response;
                teamNames();
                dialog.dismiss();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                dialog.dismiss();
                Toast.makeText(AddPlayerActivity.this, "error "+ fault, Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void teamNames()
    {
        /*
         * checks if the currently logged in coach has been assigned a team in order to save the player
         */

        if(dataOfTeams.size() == 0)
        {

            UtilityHelper.customToast(this, "No Team has been assigned to the current coach");

            AddPlayerActivity.this.finish();
        }
        else
        {
            String[] names = new String[dataOfTeams.size() +1];
            names[0] = "Please select team names";
            for(int x = 0; x < dataOfTeams.size();x++)
            {
                names[x +1] = dataOfTeams.get(x).getTeamName();
            }

            ArrayAdapter<String> arr = new ArrayAdapter<>(this, R.layout.spinner_item, names);
            spPlayerTeam.setAdapter(arr);
        }

    }
    private boolean connectionAvailable() {

        /*
         * checks for internet connection
         */
        boolean connected = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                connected = true;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                connected = true;
            }
        } else {
            connected = false;
        }
        return connected;
    }
}
