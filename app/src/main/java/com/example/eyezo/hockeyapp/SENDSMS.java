package com.example.eyezo.hockeyapp;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SENDSMS extends AppCompatActivity {

    EditText ETnumber;
    EditText ETmessage;
    Button send;
    int MY_PERMISSIONS_REQUEST_SENT_SMS = 1;
    String SENT = "SMS_SENT";
    String DELIVERED = "SMS_DELIVERED";
    PendingIntent sentPI,deliveredPI;
    BroadcastReceiver smsSentReceiver,smsDeliveredReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendsms);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Send Sms");
        actionBar.setIcon(R.mipmap.logo_2);

        ETnumber = (EditText)findViewById(R.id.edtCellNo);
        ETmessage = (EditText)findViewById(R.id.edtMessage);
        send = (Button) findViewById(R.id.btnSend);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = ETmessage.getText().toString();
                String telNr = ETnumber.getText().toString();
                if(ContextCompat.checkSelfPermission(SENDSMS.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(SENDSMS.this,new String[]{Manifest.permission.SEND_SMS},MY_PERMISSIONS_REQUEST_SENT_SMS);
                }
                else
                {
                    SmsManager sms = SmsManager.getDefault();
                    sms.sendTextMessage(telNr,null,message,sentPI,deliveredPI);
                }
            }
        });
        sentPI = PendingIntent.getBroadcast(this,0,new Intent(SENT),0);
        deliveredPI = PendingIntent.getBroadcast(this,0,new Intent(DELIVERED),0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(smsSentReceiver);
        unregisterReceiver(smsDeliveredReceiver);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        smsSentReceiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                switch(getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(SENDSMS.this,"SMS Successfully Sent!",Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(SENDSMS.this,"Generic Failure",Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(SENDSMS.this,"No Service...Please make sure you have a working sim card inserted!",Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(SENDSMS.this,"Null Pdu",Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        };
        smsDeliveredReceiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(SENDSMS.this,"SmS Delivered",Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(SENDSMS.this,"You don't have enough funds to send SMS",Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        };
        registerReceiver(smsSentReceiver,new IntentFilter(SENT));
        registerReceiver(smsDeliveredReceiver,new IntentFilter(DELIVERED));
    }



}

