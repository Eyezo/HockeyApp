package com.example.eyezo.hockeyapp;

import android.app.Application;

import com.backendless.Backendless;

public class HockeyBack extends Application {

    private static final String APPLICATION_ID = "819AF79A-0F50-F1D7-FFA3-A13BC35F6200";
    private static final String API_KEY = "C5D68A53-04A0-4CDA-BA06-36C995C06AA1";
    private static final String SERVER_URL = "https://api.backendless.com";

    @Override
    public void onCreate() {
        super.onCreate();

        Backendless.setUrl(SERVER_URL);
        Backendless.initApp(getApplicationContext(),APPLICATION_ID,API_KEY);
    }
}
