package com.example.eyezo.hockeyapp;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

public class AdminHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Dialog dlg;
    long qeueId;
    DownloadManager dm;

    TextView names, role;
    CircleImageView userImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Admin_Home");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View v = navigationView.inflateHeaderView(R.layout.nav_header_admin_home);
        names = v.findViewById(R.id.namesOfUserId);
        role = v.findViewById(R.id.roleUserId);
        userImage = v.findViewById(R.id.drawerImageId);

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

    public void txtAddTeamOrOppButt(View v)
    {
        /*
         * calls activity AddTeamOrOpp
         */
        startActivity(new Intent(this, AddTeamOrOpp.class));
    }
    public void txtMovePlayerButt(View v)
    {
        /*
         * calls activity MovePlayer
         */
        startActivity(new Intent(this, MovePlayer.class));
    }
    public void txtRoleButt(View v)
    {
        /*
         * calls activity Roles
         */
        startActivity(new Intent(this, Roles.class));
    }
    public void txtMyPlayersButt(View v)
    {
        /*
         * calls activity with the list of players
         */
        Intent intent = new Intent(this, MyPlayersList.class);
        intent.putExtra("name", getIntent().getStringExtra("name"));
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        /*
         * overrides onBackPressed by closing the drawer layout when its opened
         */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*
         * Inflate the menu; this adds items to the action bar if it is present.
         */

        getMenuInflater().inflate(R.menu.admin_home, menu);
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


        if (id == R.id.menu_logout) {
            /*
             * Logs the user out
             */
            dlg = new SpotsDialog.Builder().setTheme(R.style.Custom).setContext(this).build();
            dlg.show();

            Backendless.UserService.logout(new AsyncCallback<Void>() {
                @Override
                public void handleResponse(Void response) {
                    startActivity(new Intent(AdminHome.this, MainActivity.class));
                    AdminHome.this.finish();
                    dlg.dismiss();

                    UtilityHelper.customToast(AdminHome.this, "User Successfully logged out");
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Toast.makeText(AdminHome.this, "error "+ fault.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        /*
         *  Handle navigation view item clicks here.
         */

        int id = item.getItemId();

        if (id == R.id.nav_add_player)
        {
            Intent intent = new Intent(this, AddPlayerActivity.class);
            intent.putExtra("name", getIntent().getStringExtra("name"));
            startActivity(intent);
        }
        else if (id == R.id.nav_matches)
        {
            Intent intent = new Intent(this, Matches.class);
            intent.putExtra("name", getIntent().getStringExtra("name"));
            startActivity(intent);
        }
        else if (id == R.id.nav_news)
        {
            startActivity(new Intent(this, ArticleActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

