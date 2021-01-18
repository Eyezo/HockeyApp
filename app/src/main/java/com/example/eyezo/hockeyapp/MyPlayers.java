package com.example.eyezo.hockeyapp;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import com.backendless.persistence.DataQueryBuilder;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class MyPlayers extends AppCompatActivity {

    Dialog dialog;

    TextView txtNameOfPlayer, txtMedAidName,txtMedAidPlan, txtMedAidNumb, txtAllergies, tvSms;
    String parent1, parent2,player_id;
    boolean playerDeleted = false;
    String deletedPlayerId;
    final int UNIQUE_REQUEST_CODE_2 = 5;
    List<MatchLineUpClass> playersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_players);

        tvSms = findViewById(R.id.tvSms);
        tvSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPlayers.this, SENDSMS.class);
                startActivity(intent);
            }
        });


        txtNameOfPlayer = findViewById(R.id.txtNamePlayerView);
        txtMedAidName = findViewById(R.id.txtMedAidNameView);
        txtMedAidPlan = findViewById(R.id.txtMedAidPlanView);
        txtMedAidNumb = findViewById(R.id.txtMedAidNumbView);
        txtAllergies = findViewById(R.id.txtAllergiesView);
        playersList = new ArrayList<>();

        fetchPlayer();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Player Information");
        actionBar.setIcon(R.mipmap.logo_1);
    }
    public void btnCallParentOne(View v)
    {
        /*
         * calls the first parent
         */
        makeCall(parent1);
    }
    public void btnCallParentTwo(View v)
    {
        /*
         * calls the second parent
         */
        makeCall(parent2);
    }
    private void makeCall(String cell)
    {
        /*
         * gets all the permissions necessary to use the call services
         */
        if(cell.equals(null))
        {

            UtilityHelper.customToast(this, "Numbers not found, please enter parents numbers");
        }
        else
        {
            if (ContextCompat.checkSelfPermission(MyPlayers.this, Manifest.permission.CALL_PHONE) !=
                    PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(MyPlayers.this, new String[]{Manifest.permission.CALL_PHONE},
                        UNIQUE_REQUEST_CODE_2);
            }
            else
            {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+cell));
                startActivity(intent);
            }
        }

    }
    private void fetchPlayer()
    {
        /*
         * fetches the list of players also available for matches
         */
        String id = getIntent().getStringExtra("playerId");

        dialog = new SpotsDialog.Builder().setContext(this).setTheme(R.style.Custom).build();
        dialog.show();

        Backendless.Persistence.of(AddPlayer.class).findById(id, new AsyncCallback<AddPlayer>() {
            @Override
            public void handleResponse(AddPlayer response) {
                player(response);
                DataQueryBuilder qB = DataQueryBuilder.create();
                qB.setWhereClause("team is not null");
                qB.setPageSize(100).setOffset(0);
                Backendless.Persistence.of(MatchLineUpClass.class).find(qB, new AsyncCallback<List<MatchLineUpClass>>() {
                    @Override
                    public void handleResponse(List<MatchLineUpClass> _playersList) {
                        playersList = _playersList;
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(MyPlayers.this, "error "+ fault.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.dismiss();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                dialog.dismiss();
                Toast.makeText(MyPlayers.this, "error "+ fault, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void player(AddPlayer player)
    {
        txtNameOfPlayer.setText(player.getName()+ "," +player.getSurname());
        txtAllergies.setText(player.getAllergies());
        txtMedAidNumb.setText(player.getMedAidNumber());
        txtMedAidName.setText(player.getMedAidName());
        txtMedAidPlan.setText(player.getMedAidPlan());

        parent1 = player.getFirstParentNum();
        parent2 = player.getSecondParentNum();

        player_id = player.getObjectId();
    }
    private void getDeletedPlayer()
    {

        if(playerDeleted)
        {
            /*
             * deletes the player and returns the id of the player list for removal
             */
            Intent intent = new Intent();
            intent.putExtra("id_deleted", deletedPlayerId );
            setResult(RESULT_OK, intent);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*
         * Inflate the menu; this adds items to the action bar if it is present.
         */
        getMenuInflater().inflate(R.menu.my_players_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
         * Handle action bar item clicks here. The action bar will
         * automatically handle clicks on the Home/Up button, so long
         * as you specify a parent activity in AndroidManifest.xml.
         */

        final int id = item.getItemId();

        if(id == R.id.edit_player)
        {
            /*
             * call MedInfoUpdate activity to update player med info
             */
            Intent intent = new Intent(this, MedInfoUpdate.class);
            intent.putExtra("id_player", player_id);
            intent.putExtra("medName", txtMedAidName.getText().toString());
            intent.putExtra("medPlan", txtMedAidPlan.getText().toString());
            intent.putExtra("medNum", txtMedAidNumb.getText().toString());
            intent.putExtra("allergy", txtAllergies.getText().toString());
            intent.putExtra("cell1",parent1);
            intent.putExtra("cell2",parent2);
            startActivity(intent);
            this.finish();
            return true;
        }
        if(id == R.id.delete_player)
        {
            /*
             * deletes player from backendless
             */
            dialog = new SpotsDialog.Builder().setContext(this).setTheme(R.style.Custom).build();

            AlertDialog.Builder dB = new AlertDialog.Builder(this);
            dB.setTitle("Delete Player");
            dB.setMessage("Are you sure want to delete this player?");
            dB.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dg, int which) {
                    dialog.show();

                    boolean gotPlayer = false;

                    for(int x = 0; x < playersList.size(); x++ )
                    {
                        if(player_id.equals(playersList.get(x).getObjectId()))
                        {
                            gotPlayer = true;
                        }
                    }
                    if(gotPlayer == false)
                    {
                        Backendless.Persistence.of(AddPlayer.class).findById(player_id, new AsyncCallback<AddPlayer>() {
                            @Override
                            public void handleResponse(AddPlayer responsePlayer) {
                                deletedPlayerId = responsePlayer.getObjectId();
                                Backendless.Persistence.of(AddPlayer.class).remove(responsePlayer, new AsyncCallback<Long>() {
                                    @Override
                                    public void handleResponse(Long response) {
                                        dialog.dismiss();
                                        playerDeleted = true;
                                        getDeletedPlayer();
                                        MyPlayers.this.finish();

                                        UtilityHelper.customToast(MyPlayers.this, "Deleted successfully");
                                    }

                                    @Override
                                    public void handleFault(BackendlessFault fault) {
                                        dialog.dismiss();
                                        Toast.makeText(MyPlayers.this, "error "+ fault, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                dialog.dismiss();
                                Toast.makeText(MyPlayers.this, "error "+ fault, Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                    else
                    {
                        UtilityHelper.customToast(MyPlayers.this, "Cannot delete player, Player has been assigned to a match");
                    }

                }
            });
            dB.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dg, int which) {

                }
            });
            dB.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
