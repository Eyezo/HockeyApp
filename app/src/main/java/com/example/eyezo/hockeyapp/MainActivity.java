package com.example.eyezo.hockeyapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.local.UserIdStorageFactory;

import java.util.List;
import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity {

    Dialog dialog;
    Button btnCancel, btnReset,txtForgPass, txtReg;

    EditText edEmail, edPassword, txtEmailAddressReset;

    AlertDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);

        progressDialog = new SpotsDialog.Builder().setContext(this).setTheme(R.style.Custom).build();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Login");
        actionBar.setIcon(R.mipmap.logo_2);

        showProgress(true);
        Backendless.UserService.isValidLogin(new AsyncCallback<Boolean>() {
            /*
             * validates the user as part of the stayLogged in option
             */
            @Override
            public void handleResponse(Boolean response) {
                if(response)
                {
                    String userObjId = UserIdStorageFactory.instance().getStorage().get();
                    Backendless.Data.of(BackendlessUser.class).findById(userObjId, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {
                            if(response.getProperty("role").equals("Admin"))
                            {
                                Intent intent = new Intent(MainActivity.this, AdminHome.class);
                                intent.putExtra("name", (String) response.getProperty("name"));
                                intent.putExtra("role", (String) response.getProperty("role"));
                                intent.putExtra("path", (String) response.getProperty("imagePath"));
                                startActivity(intent);
                                showProgress(false);
                                MainActivity.this.finish();
                            }
                            else if(response.getProperty("role").equals("Coach"))
                            {
                                Intent intent = new Intent(MainActivity.this, CoachHome.class);
                                intent.putExtra("name", (String) response.getProperty("name"));
                                intent.putExtra("role", (String) response.getProperty("role"));
                                intent.putExtra("path", (String) response.getProperty("imagePath"));
                                startActivity(intent);
                                showProgress(false);
                                MainActivity.this.finish();
                            }
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            showProgress(false);
                            Toast.makeText(MainActivity.this, "error "+ fault.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    showProgress(false);
                }
            }

            @Override
            public void handleFault(BackendlessFault fault) {

                Toast.makeText(MainActivity.this, "error "+ fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });




    }
    public void btnSubmit(View v)
    {
        /*
         * takes the email and password and logs in when all verifications are passed
         */
        String email = edEmail.getText().toString().trim();
        String password = edPassword.getText().toString().trim();

        if(email.isEmpty() || password.isEmpty())
        {

            UtilityHelper.customToast(this, "email and password are required to authenticate" );
        }
        else
        {
            if(connectionAvailable())
            {
                validateUser();



            }
            else
            {

                UtilityHelper.customToast(this, "No internet connection" );

            }


        }

    }
    public void btnResetPassword(View v)
    {
        /*
         * calls the dialog handling the password recoveries
         */
        dialogImage();
    }
    public void btnRegisterUser(View v)
    {
        /*
         * calls Register activity
         */
        startActivity(new Intent(this, RegisterUser.class));
    }

    private void dialogImage()
    {
        /*
         * sets a dialog to help with the password recoveries
         */
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.forgot_password);
        btnCancel = dialog.findViewById(R.id.btnForgotCancel);
        btnReset = dialog.findViewById(R.id.btnForgotReset);
        txtEmailAddressReset = dialog.findViewById(R.id.edForgotEmail);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 * goes to backendless API to restore the user password after email verification passes
                 */
                String em = txtEmailAddressReset.getText().toString().trim();

                if(em.isEmpty())
                {

                    UtilityHelper.customToast(MainActivity.this, "Please provide an email address" );
                }
                else
                {
                    if (connectionAvailable())
                    {
                        Backendless.UserService.restorePassword(em, new AsyncCallback<Void>() {
                            @Override
                            public void handleResponse(Void response) {
                                dialog.dismiss();

                                UtilityHelper.customToast(MainActivity.this, "email sent successfully" );
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                dialog.dismiss();
                                Toast.makeText(MainActivity.this, "error "+ fault.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                    else
                    {

                        UtilityHelper.customToast(MainActivity.this, "No internet" );
                    }

                }
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }
    private void showProgress(boolean show)
    {
        if (show) {
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }


    private void loginUser(String role)
    {
        /*
         * logs the user after every validation has passed
         */
        if(role.equals("none"))
        {
            showProgress(false);

            UtilityHelper.customToast(this, "Please contact your Admin to assign \n you a role before you can log in!");
        }
        else
        {
            Backendless.UserService.login(edEmail.getText().toString().trim(), edPassword.getText().toString().trim(), new AsyncCallback<BackendlessUser>() {
                @Override
                public void handleResponse(BackendlessUser response) {
                    if(response.getProperty("role").equals("Admin"))
                    {
                        Intent intent = new Intent(MainActivity.this, AdminHome.class);
                        intent.putExtra("name", (String) response.getProperty("name"));
                        intent.putExtra("role", (String) response.getProperty("role"));
                        intent.putExtra("path", (String) response.getProperty("imagePath"));
                        startActivity(intent);
                        showProgress(false);
                        MainActivity.this.finish();
                    }
                    else
                    {
                        Intent intent = new Intent(MainActivity.this, CoachHome.class);
                        intent.putExtra("name", (String) response.getProperty("name"));
                        intent.putExtra("role", (String) response.getProperty("role"));
                        intent.putExtra("path", (String) response.getProperty("imagePath"));
                        startActivity(intent);
                        showProgress(false);
                        MainActivity.this.finish();
                    }
                    MainActivity.this.finish();

                    UtilityHelper.customToast(MainActivity.this,"User successfully logged in" );
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    showProgress(false);
                    Toast.makeText(MainActivity.this, "error " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                }
            },true);
        }
    }
    private void validateUser()
    {
        /*
         * Checks if user trying to log in is registered and retrieves the role of the user.
         */

        showProgress(true);

        Backendless.Persistence.of(BackendlessUser.class).find(new AsyncCallback<List<BackendlessUser>>() {
            @Override
            public void handleResponse(List<BackendlessUser> response) {
                for(int x = 0; x < response.size();x++)
                {
                    if(response.get(x).getProperty("email").equals(edEmail.getText().toString().trim()))
                    {
                        loginUser((String) response.get(x).getProperty("role"));


                    }
                }
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(MainActivity.this, "error" + " " + fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean connectionAvailable() {
        /*
         * Checks if connection is available
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
