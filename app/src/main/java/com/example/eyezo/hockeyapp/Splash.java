package com.example.eyezo.hockeyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {

    ImageView hockey_splash;

    Intent intent;

    private static int SLEEP_TIME = 5500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        hockey_splash = findViewById(R.id.img_splash);

        Thread splashThread = new Thread()
        {
            @Override
            public void run() {
                try
                {
                    synchronized (this)
                    {
                        wait(SLEEP_TIME);
                    }
                }catch (InterruptedException ex)
                {
            }

            finish();

                Intent intent = new Intent();
                intent.setClass(Splash.this, MainActivity.class);
                startActivity(intent);

        }};
        splashThread.start();


    }
}
