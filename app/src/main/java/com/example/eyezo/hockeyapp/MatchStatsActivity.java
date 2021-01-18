package com.example.eyezo.hockeyapp;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import dmax.dialog.SpotsDialog;

public class MatchStatsActivity extends AppCompatActivity implements Fragment_One.FragOneData, Fragment_Two.FragTwoData,
        Fragment_Three.FragThreeDataListner, Fragment_Four.FragFourDataListner{

    private ViewPager viewPager;
    Fragment fragment = null;
    ActionBar actionBar;
    MatchStatsClass matchStatsClass;
    Dialog dlgMatchStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_stats);

        viewPager = findViewById(R.id.container);
        actionBar = getSupportActionBar();
        matchStatsClass = new MatchStatsClass();


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                /*
                 * Handles the bottom navigation holding the fragments
                 */

                switch(item.getItemId())
                {
                    case R.id.menu_1:
                    {
                        fragment = new Fragment_One();
                        switchFragment(fragment);
                        return  true;
                    }
                    case R.id.menu_2:
                    {
                        fragment = new Fragment_Two();
                        switchFragment(fragment);
                        return  true;
                    }
                    case R.id.menu_3:
                    {
                        fragment = new Fragment_Three();
                        switchFragment(fragment);
                        return  true;
                    }
                    case R.id.menu_4:
                    {
                        fragment = new Fragment_Four();
                        switchFragment(fragment);
                        return  true;
                    }
                }

                return false;
            }
        });
        fragment = new Fragment_One();
        switchFragment(fragment);

    }
    private void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contentFl,fragment).commit();
    }

    private void showToast(String m)
    {
        Toast.makeText(this, m, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void getTurnOverData(String teamTurnOver1st, String teamTurnOver2nd, String oppTurnOver1st, String oppTurnOver2nd) {
        /*
         * calls values that were set in fragOneData
         */
        matchStatsClass.setTurnOver1stHalfTeam(teamTurnOver1st);
        matchStatsClass.setTurnOver2ndHalfTeam(teamTurnOver2nd);
        matchStatsClass.setTurnOver1stHalfAgainst(oppTurnOver1st);
        matchStatsClass.setTurnOver2ndHalfAgainst(oppTurnOver2nd);

    }

    @Override
    public void getGoalsData(String teamGoal1st, String teamGoal2nd, String oppGoal1st, String oppGoal2nd) {
        /*
         * calls values that were set in fragOneData and populates the sccore on the actionBar
         */
        int goalsFor = Integer.parseInt(teamGoal1st) + Integer.parseInt(teamGoal2nd);
        int against = Integer.parseInt(oppGoal1st) + Integer.parseInt(oppGoal2nd);
        actionBar.setLogo(R.mipmap.logo_1);
        actionBar.setTitle("Score: " + goalsFor + " - " + against);

        matchStatsClass.setGoal1stHalfTeam(teamGoal1st);
        matchStatsClass.setGoal2ndHalfTeam(teamGoal2nd);
        matchStatsClass.setGoal1stHalfAgainst(oppGoal1st);
        matchStatsClass.setGoal2ndHalfAgainst(oppGoal2nd);
    }

    @Override
    public void circlePenetration(String firstHalfPen, String secondHalfPen) {
        /*
         * calls values that were set in fragTwoData
         */
        matchStatsClass.setCirclePen1st(firstHalfPen);
        matchStatsClass.setCirclePen2nd(secondHalfPen);

    }

    @Override
    public void penaltyCorners(String firstHalfPenalty, String secondHalfPennalty) {
        /*
         * calls values that were set in fragThreeDataListener
         */
        matchStatsClass.setPenaltyCorners1st(firstHalfPenalty);
        matchStatsClass.setPenaltyConers2nd(secondHalfPennalty);
    }

    @Override
    public void shots(String firstHalfPenalty, String secondHalfPennalty) {
        /*
         * calls values that were set in fragFourDataListener
         */
        matchStatsClass.setShots1st(firstHalfPenalty);
        matchStatsClass.setShots2nd(secondHalfPennalty);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*
         * Inflate the menu; this adds items to the action bar if it is present.
         */
        getMenuInflater().inflate(R.menu.menu_frag, menu);
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


        if(id == R.id.menu_save_stats)
        {
            /*
             * saves match stats to backendless
             */
            String teamName;
            String opponentTeamName;

            String name = getIntent().getStringExtra("matchHeader");
            String[] names = name.split(" ");

            if(names[0].equals("1st"))
            {
                teamName = names[0] + " " + names[1];
                opponentTeamName = names[3];
            }
            else
            {
                teamName = names[0];
                opponentTeamName = names[2];
            }
            matchStatsClass.setTeamFor(teamName);
            matchStatsClass.setTeamAgainst(opponentTeamName);

            dlgMatchStats = new SpotsDialog.Builder().setContext(this).setTheme(R.style.Custom).build();
            dlgMatchStats.show();

            Backendless.Persistence.save(matchStatsClass, new AsyncCallback<MatchStatsClass>() {
                @Override
                public void handleResponse(MatchStatsClass response) {
                    dlgMatchStats.dismiss();
                    showToast("Successfully saved match stats");
                    MatchStatsActivity.this.finish();
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    dlgMatchStats.dismiss();
                    showToast(fault.getMessage());
                }
            });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
