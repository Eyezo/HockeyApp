package com.example.eyezo.hockeyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Matches extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matches_select);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.logo_2);
        actionBar.setTitle("Matches");

    }
    public void btnLineUp(View v)
    {
        /*
         * calls LineUp activity
         */
        Intent intent = new Intent(this, LineUp.class);
        intent.putExtra("coachName", getIntent().getStringExtra("name"));
        startActivity(intent);
    }
    public void btnListMatches(View v)
    {
        /*
         * calls MatchList activity
         */
        Intent intent = new Intent(this, MatchList.class);
        intent.putExtra("name", getIntent().getStringExtra("name"));
        startActivity(intent);
    }
}
