package com.example.eyezo.hockeyapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.files.BackendlessFile;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

public class RegisterUser extends AppCompatActivity {

    EditText edFirstName, edLastName, edEmail, edPassword, edRetypePass;
    AlertDialog dlg;
    ImageView profileImage;
    final int SELECT_REQ = 1;
    final int CAPTURE_REQ = 2;
    CircleImageView displayPic;
    Bitmap pic;
    String imagePath;

    private static final int UNIQUE_REQUEST_CODDE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        edFirstName = findViewById(R.id.firstName);
        edLastName = findViewById(R.id.lastName);
        edEmail = findViewById(R.id.emailReg);
        edPassword = findViewById(R.id.passReg);
        edRetypePass = findViewById(R.id.passRegRetype);
        displayPic = findViewById(R.id.dp);

        profileImage = findViewById(R.id.imgRegPP);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder picker = new AlertDialog.Builder(RegisterUser.this);
                picker.setNegativeButton("select photo", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectPhoto();
                    }
                });
                picker.setPositiveButton("take picture", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        capturePhoto();
                    }
                });
                picker.show();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.logo_1);
        actionBar.setTitle("Register");
    }
    public void btnRegister(View v)
    {
        saveImage();

    }
    private void registerUser()
    {
        BackendlessUser user = new BackendlessUser();
        user.setProperty("email", edEmail.getText().toString().trim());
        user.setProperty("name",edFirstName.getText().toString().trim() + " "+ edLastName.getText().toString().trim());
        user.setPassword(edPassword.getText().toString().trim());
        user.setProperty("role","none");
        user.setProperty("imagePath", imagePath);

        Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {


                UtilityHelper.customToast(RegisterUser.this, response.getProperty("name")+ "registered successfully");
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                dlg.dismiss();
                Toast.makeText(RegisterUser.this, "error"+ fault.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*
         * gets the results of an image either by picture selection or picture caption
         */
        if (requestCode == SELECT_REQ && resultCode == RESULT_OK && data != null)
        {
            Uri uri = data.getData();

            String[] proj = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(uri, proj, null, null,null);
            cursor.moveToFirst();

            int index = cursor.getColumnIndex(proj[0]);
            String path = cursor.getString(index);
            cursor.close();

            displayPic.setImageBitmap(BitmapFactory.decodeFile(path));
            pic = BitmapFactory.decodeFile(path);

        }
        else if(requestCode == CAPTURE_REQ && resultCode == RESULT_OK && data != null)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            displayPic.setImageBitmap(photo);
            pic = photo;


        }
    }
    private void saveImage()
    {
        String firstName = edFirstName.getText().toString().trim();
        String lastName = edLastName.getText().toString().trim();
        String email = edEmail.getText().toString().trim();
        String password = edPassword.getText().toString().trim();
        String passwordRetype = edRetypePass.getText().toString().trim();
        if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || passwordRetype.isEmpty())
        {


            UtilityHelper.customToast(this, "All fields are needed to register.");
        }
        else if(!password.equals(passwordRetype))
        {

            UtilityHelper.customToast(this, "Passwords must match");

        }
        else
        {
            if(connectionAvailable())
            {
                dlg = new SpotsDialog.Builder().setContext(this).setTheme(R.style.Custom).build();
                dlg.show();

                String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String photoName = "JPEG_" + time + "_.jpg";

                Backendless.Files.Android.upload(pic, Bitmap.CompressFormat.JPEG, 100, photoName,
                        "profilePics", new AsyncCallback<BackendlessFile>() {
                            @Override
                            public void handleResponse(BackendlessFile response) {
                                imagePath = response.getFileURL();
                                registerUser();
                                dlg.dismiss();
                                RegisterUser.this.finish();

                                UtilityHelper.customToast(RegisterUser.this, "image saved successfully");

                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {

                            }});

            }

        }

    }

    private void selectPhoto()
    {
        /*
         * grants you permissions to select a photo through the phones storage
         */
        if (ContextCompat.checkSelfPermission(RegisterUser.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(RegisterUser.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(RegisterUser.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    UNIQUE_REQUEST_CODDE);
        }
        else
        {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, SELECT_REQ);
        }

    }
    private void capturePhoto()
    {
        /*
         * grants you permissions to use the camera to capture photos
         */
        if (ContextCompat.checkSelfPermission(RegisterUser.this, Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(RegisterUser.this, new String[]{Manifest.permission.CAMERA},
                    UNIQUE_REQUEST_CODDE);
        }
        else
        {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAPTURE_REQ);
        }

    }
    private boolean connectionAvailable() {
        /*
         * checks if connection is available
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
