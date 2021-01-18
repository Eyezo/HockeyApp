package com.example.eyezo.hockeyapp;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

public class CoachHome extends AppCompatActivity {
    Dialog dlg;
    long qeueId;
    DownloadManager dm;

    TextView names, role;
    CircleImageView userImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_home);

        names = findViewById(R.id.txtCoachNameProf);
        role = findViewById(R.id.txtCoachRoleProf);
        userImage = findViewById(R.id.imgCoachImage);

        names.setText(getIntent().getStringExtra("name"));
        role.setText(getIntent().getStringExtra("role"));

        BroadcastReceiver receiver = new BroadcastReceiver() {
            /*
             * populates the profile picture to the designated amage view even after the compiler has passed
             */
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();

                if(DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action))
                {
                    DownloadManager.Query dwnQuery = new DownloadManager.Query();
                    dwnQuery.setFilterById(qeueId);

                    Cursor c = dm.query(dwnQuery);

                    if(c.moveToFirst())
                    {
                        int columnIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);

                        if(DownloadManager.STATUS_SUCCESSFUL == c.getInt(columnIndex))
                        {
                            String uriString = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));

                            userImage.setImageURI(Uri.parse(uriString));
                        }
                    }

                }
            }
        };
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        downloadProfilePicture();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.logo_2);
        actionBar.setTitle("Coach Home");

    }
    private void downloadProfilePicture()
    {
        /*
         * downloads profile picture is the assigned path on registration
         */
        try
        {
            String path = getIntent().getStringExtra("path");
            dm = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(path));
            request.setVisibleInDownloadsUi(false);
            qeueId = dm.enqueue(request);


        }catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    public void txtAddPlayerCoach(View v)
    {
        /*
         * calls activity AddPlayer
         */
        Intent intent = new Intent(this, AddPlayerActivity.class);
        intent.putExtra("name", getIntent().getStringExtra("name"));
        startActivity(intent);
    }
    public void txtMyAccountCoach(View v)
    {
        startActivity(new Intent(this, ArticleActivity.class));
    }
    public void txtMatches(View v)
    {
        /*
         * calls activity Matches
         */
        Intent intent = new Intent(this, Matches.class);
        intent.putExtra("name", getIntent().getStringExtra("name"));
        startActivity(intent);
    }
    public void txtMyPlayersButtCoach(View v)
    {
        /*
         * calls activity with the list of players
         */
        Intent intent = new Intent(this, MyPlayersList.class);
        intent.putExtra("name", getIntent().getStringExtra("name"));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*
         * Inflate the menu; this adds items to the action bar if it is present.
         */
        getMenuInflater().inflate(R.menu.menu_coach, menu);
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
        if(id == R.id.menu_coach_logout)
        {
            dlg = new SpotsDialog.Builder().setTheme(R.style.Custom).setContext(this).build();
            dlg.show();

            Backendless.UserService.logout(new AsyncCallback<Void>() {
                @Override
                public void handleResponse(Void response) {
                    startActivity(new Intent(CoachHome.this, MainActivity.class));
                    CoachHome.this.finish();
                    dlg.dismiss();

                    UtilityHelper.customToast(CoachHome.this,"User Successfully logged out" );
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Toast.makeText(CoachHome.this, "error "+ fault.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
