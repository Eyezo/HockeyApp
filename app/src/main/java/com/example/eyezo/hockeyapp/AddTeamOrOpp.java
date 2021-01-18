package com.example.eyezo.hockeyapp;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class AddTeamOrOpp extends AppCompatActivity {
    Spinner spTeam, spAgeGroup, spCoach;
    EditText edTeamName;

    Dialog dlg;

    //Arrays to fill the spinner

    String[] teamN = {"Select whether Team ro Opponent","Team", "Opponent"};
    String[] ageGroup = {"Please select age group","u/14", "u/15","u/16","1st"};

    List<BackendlessUser> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_team_or_opp);

        users = new ArrayList<>();
        loadData();
        spTeam = findViewById(R.id.spTeam);
        spAgeGroup = findViewById(R.id.spAgeGroup);
        spCoach = findViewById(R.id.spCoach);
        edTeamName = findViewById(R.id.edTeamName);

        spAgeGroup.setEnabled(false);
        spCoach.setEnabled(false);

        ArrayAdapter<String> choice = new ArrayAdapter<>(this, R.layout.spinner_item,teamN);
        spTeam.setAdapter(choice);
        ArrayAdapter<String> choice2 = new ArrayAdapter<>(this, R.layout.spinner_item,ageGroup);
        spAgeGroup.setAdapter(choice2);

        spTeam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                /*
                 * makes some values available for saving if team is selected instead of opponents
                 */
                String selectedItem = parent.getItemAtPosition(position).toString();

                if(selectedItem.equals("Team"))
                {
                    spAgeGroup.setEnabled(true);
                    spCoach.setEnabled(true);

                }
                else
                {
                    spAgeGroup.setEnabled(false);
                    spCoach.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.logo_2);
        actionBar.setTitle("Add Team / Opponent");

    }
    public void submitTeam(View v)
    {
        /*
         * saves team under a coach and opponents as a team with nno information of coach and division
         */
        String teamOrOpp = spTeam.getSelectedItem().toString();
        String age = spAgeGroup.getSelectedItem().toString();
        String coachName = spCoach.getSelectedItem().toString();
        String teamName = edTeamName.getText().toString();

        if(teamOrOpp.equals("Opponent") || teamOrOpp.equals("Team"))
        {
            if(teamName.isEmpty())
            {
                UtilityHelper.customToast(this, "Please give name fo team" );

            }
            else
            {
                TeamClass teamClass = new TeamClass();

                if(teamOrOpp.equals("Team"))
                {
                    teamClass.setAgeGroup(age);
                    teamClass.setCoachName(coachName);
                }
                teamClass.setTeamOrOpp(teamOrOpp);
                teamClass.setTeamName(teamName);

                dlg = new SpotsDialog.Builder().setContext(this)
                        .setTheme(R.style.Custom).build();
                dlg.show();

                Backendless.Persistence.save(teamClass, new AsyncCallback<TeamClass>() {
                    @Override
                    public void handleResponse(TeamClass response) {
                        AddTeamOrOpp.this.finish();
                        dlg.dismiss();

                        UtilityHelper.customToast(AddTeamOrOpp.this,"Successfully created" );

                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(AddTeamOrOpp.this, "error "+ fault.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }
        else
        {

            UtilityHelper.customToast(this, "Please select the appropriate choices on the spinners" );

        }

    }
    private void setSpinner(String[] name)
    {
        ArrayAdapter<String> choice3 = new ArrayAdapter<>(this, R.layout.spinner_item,name);
        spCoach.setAdapter(choice3);
    }
    private void loadData()
    {
        /*
         *In this Method, we retrieve users whose roles are either Admin
         * or Coach
         */
        if(users != null)
        {
            users.clear();
        }

        String whereClause = "role = 'Admin' OR role = 'Coach'";
        DataQueryBuilder qB = DataQueryBuilder.create();
        qB.setWhereClause(whereClause);

        dlg = new SpotsDialog.Builder().setTheme(R.style.Custom).setContext(this).build();
        dlg.show();
        Backendless.Persistence.of(BackendlessUser.class).find(qB, new AsyncCallback<List<BackendlessUser>>() {
            @Override
            public void handleResponse(List<BackendlessUser> response) {
                users = response;
                setSpinner(getBackendUsers());
                dlg.dismiss();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                dlg.dismiss();
                Toast.makeText(AddTeamOrOpp.this, "error "+ fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String[] getBackendUsers()
    {
        /*
         * assign name of users retrieved from method load to an array
         * that will fill a spinner
         */
        String[] userNames = new String[users.size()+1];
        userNames[0] = "Please select coach";
        for(int x = 0; x <users.size(); x++)
        {
            userNames[x +1] = (String)users.get(x).getProperty("name");
        }

        return userNames;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opponent,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.menu_opps)
        {
            startActivity(new Intent(this,OpponentList.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}