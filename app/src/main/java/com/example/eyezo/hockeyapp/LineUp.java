package com.example.eyezo.hockeyapp;

import android.app.Dialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import dmax.dialog.SpotsDialog;

public class LineUp extends AppCompatActivity {

    Spinner sp1, sp2, sp3, sp4, sp5, sp6, sp7, sp8, sp9, sp10, sp11, sp12, sp13,sp14,sp15, sp16
            ,spVs, spYourTeam;
    EditText ed1, ed2, ed3, ed4, ed5, ed6, ed7, ed8,ed9,ed10, ed11, ed12, ed13, ed14, ed15, ed16;

    RatingBar rtb1,rtb2,rtb3, rtb4, rtb5, rtb6, rtb7, rtb8, rtb9, rtb10, rtb11, rtb12,rtb13, rtb14,
            rtb15, rtb16;

    List<TeamClass> teamClassList;
    List<TeamClass> opponentClassList;
    List<AddPlayer> playerList;
    List<MatchLineUpClass> lineUpList;
    Dialog progDialog;
    int numOfObj = 0;
    CountDownLatch latch1, latch2;
    String[] playersRating;
    String[] goals;
    String[] position;
    String[] players;
    String message = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_up);

        //spinners for players at every position and team vs opp
        spVs = findViewById(R.id.spLineUpOpp);
        spYourTeam = findViewById(R.id.spLineUpTeam);
        sp1 = findViewById(R.id.spLeftForward);
        sp2 = findViewById(R.id.spRightForward);
        sp3 = findViewById(R.id.spCenterForward);
        sp4 = findViewById(R.id.spLeftLink);
        sp5 = findViewById(R.id.spRightLink);
        sp6 = findViewById(R.id.spCenterLink);
        sp7 = findViewById(R.id.spLeftBack);
        sp8 = findViewById(R.id.spRightBack);
        sp9 = findViewById(R.id.spCenterBack);
        sp10 = findViewById(R.id.spSweeper);
        sp11 = findViewById(R.id.spGoalie);
        sp12 = findViewById(R.id.spBench12);
        sp13 = findViewById(R.id.spBench13);
        sp14 = findViewById(R.id.spBench14);
        sp15 = findViewById(R.id.spBench15);
        sp16 = findViewById(R.id.spBench16);
        // edit text for goals
        ed1 = findViewById(R.id.edLeftForward);
        ed2 = findViewById(R.id.edRightForward);
        ed3 = findViewById(R.id.edCenterForward);
        ed4 = findViewById(R.id.edLeftLink);
        ed5 = findViewById(R.id.edRightLink);
        ed6 = findViewById(R.id.edCenterLink);
        ed7 = findViewById(R.id.edLeftBack);
        ed8 = findViewById(R.id.edRightBack);
        ed9 = findViewById(R.id.edCenterBack);
        ed10 = findViewById(R.id.edSweeper);
        ed11 = findViewById(R.id.edGoalie);
        ed12 = findViewById(R.id.edBench12);
        ed13 = findViewById(R.id.edBench13);
        ed14 = findViewById(R.id.edBench14);
        ed15 = findViewById(R.id.edBench15);
        ed16 = findViewById(R.id.edBench16);

        // the rating star
        rtb1 = findViewById(R.id.rtbLeftForward);
        rtb2 = findViewById(R.id.rtbRightForward);
        rtb3 = findViewById(R.id.rtbCenterForward);
        rtb4 = findViewById(R.id.rtbLeftLink);
        rtb5 = findViewById(R.id.rtbRightLink);
        rtb6 = findViewById(R.id.rtbCenterLink);
        rtb7 = findViewById(R.id.rtbLeftBack);
        rtb8 = findViewById(R.id.rtbRightBack);
        rtb9 = findViewById(R.id.rtbCenterBack);
        rtb10 = findViewById(R.id.rtbSweeper);
        rtb11 = findViewById(R.id.rtbGoalie);
        rtb12 = findViewById(R.id.rtbBench12);
        rtb13 = findViewById(R.id.rtbBench13);
        rtb14 = findViewById(R.id.rtbBench14);
        rtb15 = findViewById(R.id.rtbBench15);
        rtb16 = findViewById(R.id.rtbBench16);

        teamClassList = new ArrayList<>();
        opponentClassList = new ArrayList<>();
        playerList = new ArrayList<>();
        lineUpList = new ArrayList<>();
        progDialog = new SpotsDialog.Builder().setContext(this).setTheme(R.style.Custom).build();

        getTeamsAndOpponent();



    }
    private class SaveLineUpData extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progDialog = new SpotsDialog.Builder().setContext(LineUp.this).setTheme(R.style.Custom).build();
            progDialog.show();
            saveMatch();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            /*
             * uses an AsyncTask to work in the background as for the list is too long for the compiler
             * uses latch1 to count the number of objects within the table
             * uses latch2 to save objects from where latch1 stopped count
             */

            int rc = 0;
            for (String player : players) {
                if (player.equals("Please select a player"))
                {
                    rc = 1;
                }
            }

            if(rc == 0)
            {
                latch1 = new CountDownLatch(1);
                Backendless.Data.of(MatchLineUpClass.class).getObjectCount(new AsyncCallback<Integer>() {
                    @Override
                    public void handleResponse(Integer response) {
                        numOfObj = response;
                        latch1.countDown();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {


                    }
                });
                try
                {
                    latch1.await();
                }catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

                for(int x = 0; x < players.length;x++)
                {

                    MatchLineUpClass lineUp = new MatchLineUpClass();
                    if(x < goals.length)
                    {
                        lineUp.setPlayerGoals(goals[x]);
                    }
                    lineUp.setPlayerName(players[x]);
                    lineUp.setPlayerPosition(position[x]);
                    lineUp.setPlayerRating(playersRating[x]);
                    lineUp.setTeam(spYourTeam.getSelectedItem().toString());
                    lineUp.setTeamAgainst(spVs.getSelectedItem().toString());
                    lineUp.setCoach(getIntent().getStringExtra("coachName"));
                    int val = numOfObj + (x+1);
                    lineUp.setNumberOfData(val);

                    latch2 = new CountDownLatch(1);

                    Backendless.Persistence.save(lineUp, new AsyncCallback<MatchLineUpClass>() {
                        @Override
                        public void handleResponse(MatchLineUpClass response) {
                            latch2.countDown();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            //showToast(fault.getMessage());
                        }
                    });
                    try
                    {
                        latch2.await();

                    }catch (InterruptedException e)
                    {

                    }
                }

            }
            else
            {
                message = "Please select the appropriate choices on the spinners ";

            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progDialog.dismiss();
            if(!message.equals(""))
            {
                showToast(message);
            }
            else
            {
                showToast("Successfully saved");
            }
            LineUp.this.finish();
        }
    }
    private void getTeamsAndOpponent()
    {
        /*
         * fetch all the opponent teams
         */

        progDialog.show();

        String opp = "teamOrOpp = 'Opponent'";
        DataQueryBuilder dq = DataQueryBuilder.create();
        dq.setWhereClause(opp);
        Backendless.Persistence.of(TeamClass.class).find(dq, new AsyncCallback<List<TeamClass>>() {
            @Override
            public void handleResponse(List<TeamClass> response) {
                opponentClassList = response;
                fetchPlayer();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                progDialog.dismiss();
                showToast(fault.getMessage());
            }
        });


    }
    private void fetchPlayer()
    {
        /*
         * fetch all the players under the current logged in coach
         */
        String teamWhereClause = "coachName = '" + getIntent().getStringExtra("coachName") + "'";
        DataQueryBuilder dq2 = DataQueryBuilder.create();
        dq2.setWhereClause(teamWhereClause);

        Backendless.Persistence.of(AddPlayer.class).find(dq2, new AsyncCallback<List<AddPlayer>>() {
            @Override
            public void handleResponse(List<AddPlayer> response) {
                playerList = response;
                fillTheSpinners();
                fetchCoach();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                progDialog.dismiss();
                showToast(fault.getMessage());
            }
        });

    }
    private void fillTheSpinners()
    {
        /*
         * fills the spinners with all available players under the logged in coach
         */
        String[] theTeams = new String[playerList.size() + 1];
        theTeams[0] = "Please select a player";
        for(int x = 0; x < playerList.size();x++)
        {
            theTeams[x +1] = playerList.get(x).getName()+ " "+playerList.get(x).getSurname();
        }
        ArrayAdapter<String> playerAdapt = new ArrayAdapter<>(this, R.layout.spinner_item,theTeams);
        sp1.setAdapter(playerAdapt);
        sp2.setAdapter(playerAdapt);
        sp3.setAdapter(playerAdapt);
        sp4.setAdapter(playerAdapt);
        sp5.setAdapter(playerAdapt);
        sp6.setAdapter(playerAdapt);
        sp7.setAdapter(playerAdapt);
        sp8.setAdapter(playerAdapt);
        sp9.setAdapter(playerAdapt);
        sp10.setAdapter(playerAdapt);
        sp11.setAdapter(playerAdapt);
        sp12.setAdapter(playerAdapt);
        sp13.setAdapter(playerAdapt);
        sp14.setAdapter(playerAdapt);
        sp15.setAdapter(playerAdapt);
        sp16.setAdapter(playerAdapt);

    }
    private void fetchCoach()
    {
        /*
         * fetch all the the teams under the currently logged in coach
         */

        String teamWhereClause = "coachName = '" + getIntent().getStringExtra("coachName") + "'";
        DataQueryBuilder dq2 = DataQueryBuilder.create();
        dq2.setWhereClause(teamWhereClause);

        Backendless.Persistence.of(TeamClass.class).find(dq2, new AsyncCallback<List<TeamClass>>() {
            @Override
            public void handleResponse(List<TeamClass> response) {
                teamClassList = response;
                fillSpinner();
                progDialog.dismiss();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                progDialog.dismiss();
                showToast(fault.getMessage());
            }
        });
    }
    private void fillSpinner()
    {
        /*
         * fill appropriate spinners by letting you choose an opponent team against one of your teams
         */
        String[] spOpp = new String[opponentClassList.size()+1];
        String[] spTeams = new String[teamClassList.size()+1];
        spOpp[0] = "Select opponents";
        spTeams[0] = "Select teams";
        for (int i = 0; i < opponentClassList.size(); i++)
        {
            if(opponentClassList.get(i).getTeamName() != null)
                spOpp[i +1] = opponentClassList.get(i).getTeamName();
        }
        for (int x = 0; x < teamClassList.size(); x++)
        {
            spTeams[x +1] = teamClassList.get(x).getTeamName();
        }

        ArrayAdapter<String> adpat1 = new ArrayAdapter<>(this, R.layout.spinner_item,spOpp);
        ArrayAdapter<String> adpat2 = new ArrayAdapter<>(this, R.layout.spinner_item,spTeams);


        spVs.setAdapter(adpat1);
        spYourTeam.setAdapter(adpat2);

        spYourTeam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).toString().equals("Select teams"))
                {
                    //do nothing
                }
                else
                {
                    String the_team = parent.getItemAtPosition(position).toString();
                    fetchTeamSelected(the_team);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
    private void fetchTeamSelected(String team)
    {
        /*
         * assigns selected players to their field positions
         */

        String[] theTeams = new String[playerList.size() + 1];
        theTeams[0] = "Please select a player";
        List<String> playerNames = new ArrayList<>();
        playerNames.add("Please select a player");
        for(int x = 0; x < playerList.size();x++)
        {
            if(playerList.get(x).getTeamName().equals(team))
                playerNames.add(playerList.get(x).getName()+ " "+playerList.get(x).getSurname());

        }
        ArrayAdapter<String> playerAdapt = new ArrayAdapter<>(this, R.layout.spinner_item,playerNames);
        sp1.setAdapter(playerAdapt);
        sp2.setAdapter(playerAdapt);
        sp3.setAdapter(playerAdapt);
        sp4.setAdapter(playerAdapt);
        sp5.setAdapter(playerAdapt);
        sp6.setAdapter(playerAdapt);
        sp7.setAdapter(playerAdapt);
        sp8.setAdapter(playerAdapt);
        sp9.setAdapter(playerAdapt);
        sp10.setAdapter(playerAdapt);
        sp11.setAdapter(playerAdapt);
        sp12.setAdapter(playerAdapt);
        sp13.setAdapter(playerAdapt);
        sp14.setAdapter(playerAdapt);
        sp15.setAdapter(playerAdapt);
        sp16.setAdapter(playerAdapt);

    }


    private void saveMatch()
    {
        /*
         * saves the match will all relevant data when called
         */
        String leftForwardGoal = ed1.getText().toString();
        String rightForwardGoal = ed2.getText().toString();
        String centerForwardGoal = ed3.getText().toString();
        String leftLinkGoal = ed4.getText().toString();
        String rightLinkGoal = ed5.getText().toString();
        String centerLinkGoal = ed6.getText().toString();
        String leftBackGoal = ed7.getText().toString();
        String rightBackGoal = ed8.getText().toString();
        String centerBackGoal = ed9.getText().toString();
        String sweeperGoal = ed10.getText().toString();
        String goalieGoal = ed11.getText().toString();

        goals = new String[]{leftForwardGoal, rightForwardGoal, centerForwardGoal, leftLinkGoal, rightLinkGoal, centerLinkGoal, leftBackGoal,
                rightBackGoal, centerBackGoal, sweeperGoal, goalieGoal};

        String leftForward = sp1.getSelectedItem().toString();
        String rightForward = sp2.getSelectedItem().toString();
        String centerForward = sp3.getSelectedItem().toString();
        String leftLink = sp4.getSelectedItem().toString();
        String rightLink = sp5.getSelectedItem().toString();
        String centerLink = sp6.getSelectedItem().toString();
        String leftBack = sp7.getSelectedItem().toString();
        String rightBack = sp8.getSelectedItem().toString();
        String centerBack = sp9.getSelectedItem().toString();
        String sweeper = sp10.getSelectedItem().toString();
        String goalie = sp11.getSelectedItem().toString();
        String bench12 = sp12.getSelectedItem().toString();
        String bench13 = sp13.getSelectedItem().toString();
        String bench14 = sp14.getSelectedItem().toString();
        String bench15 = sp15.getSelectedItem().toString();
        String bench16 = sp16.getSelectedItem().toString();

        players = new String[]{leftForward, rightForward, centerForward, leftLink, rightLink, centerLink, leftBack,
                rightBack, centerBack, sweeper, goalie, bench12, bench13, bench14, bench15, bench16};

        String rtLeftForward = (String.valueOf( rtb1.getRating()));
        String rtbRightForward =(String.valueOf( rtb2.getRating()));
        String rtbCenterForward = (String.valueOf( rtb3.getRating()));
        String rtbLeftLink = (String.valueOf( rtb4.getRating()));
        String rtbRightLink = (String.valueOf( rtb5.getRating()));
        String rtbCenterLink = (String.valueOf( rtb6.getRating()));
        String rtbLeftBack = (String.valueOf( rtb7.getRating()));
        String rtbRightBack = (String.valueOf( rtb8.getRating()));
        String rtbCenterBack =(String.valueOf( rtb9.getRating()));
        String rtbSweeper = (String.valueOf( rtb10.getRating()));
        String rtbGoalie = (String.valueOf( rtb11.getRating()));
        String rtbBench12 = (String.valueOf( rtb12.getRating()));
        String rtbBench13 = (String.valueOf( rtb13.getRating()));
        String rtbBench14 = (String.valueOf( rtb14.getRating()));
        String rtbBench15 = (String.valueOf( rtb15.getRating()));
        String rtbBench16 = (String.valueOf( rtb16.getRating()));

        playersRating = new String[] {rtLeftForward,rtbRightForward,rtbCenterForward,rtbLeftLink,rtbRightLink,rtbCenterLink,rtbLeftBack,
                rtbRightBack,rtbCenterBack,rtbSweeper,rtbGoalie,rtbBench12,rtbBench13,rtbBench14,rtbBench15,rtbBench16};

        position = new String[]{"leftForward", "rightForward", "centerForward", "leftLink", "rightLink", "centerLink", "leftBack",
                "rightBack", "centerBack", "sweeper", "goalie", "bench12", "bench13", "bench14", "bench15", "bench16"};

    }





    private void showToast(String m)
    {
        Toast.makeText(this, m, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*
         * Inflate the menu, this adds items to the action bar if it is present.
         */
        getMenuInflater().inflate(R.menu.line_up_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
         * Handle action bar item clicks here. The action bar will
         * automatically handle clicks on the Home/Up button, so long
         * as you specify a parent activity in AndroidManifest.xml.
         */

        int id = item.getItemId();

        if(id == R.id.menu_save)
        {
            String opp = spVs.getSelectedItem().toString();
            String t = spYourTeam.getSelectedItem().toString();
            //  new SaveLineUpData().execute();
            if(opp.equals("Select opponents") || t.equals("Select teams"))
            {
                Toast.makeText(this, "Please select your team and opponent", Toast.LENGTH_SHORT).show();
            }
            else
            {

                SaveLineUpData s = new SaveLineUpData();
                s.execute();
            }


            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
