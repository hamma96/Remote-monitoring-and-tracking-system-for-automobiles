package com.example.loginapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class Account extends AppCompatActivity {

    private Button mLogOutBtn;
    String Text,no = "92683515";
    private final static int SEND_SMS_PERMISSION_REQ=1;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    Button btn;
    public Account() {
        Text = "Locate";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        btn=(Button)findViewById(R.id.button2);
        btn.setEnabled(false);
        FirebaseApp.initializeApp(this);
        mAuth=FirebaseAuth.getInstance();
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null){

                    Intent intent;
                    intent = new Intent(Account.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        mLogOutBtn=(Button) findViewById(R.id.LogoutBt);

        mLogOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent;
                intent = new Intent(Account.this,MainActivity.class);
                startActivity(intent);
            }
        });
        if(checkPermission(Manifest.permission.SEND_SMS))
        {
            btn.setEnabled(true);
        }
        else
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQ);
        }
        //this function send msg Locate to the GSM model to ask for the location
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1=Text.toString();



                if(checkPermission(Manifest.permission.SEND_SMS))
                {
                    SmsManager smsManager=SmsManager.getDefault();
                    smsManager.sendTextMessage(no,null,s1,null,null);

                }
                else {
                    Toast.makeText(Account.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private boolean checkPermission(String sendSms) {
        int checkpermission= ContextCompat.checkSelfPermission(this,sendSms);
        return checkpermission== PackageManager.PERMISSION_GRANTED;

    }


    public void onNotif(View v) {


        Intent intent = new Intent(Account.this, Notification.class);
        startActivity(intent);
    }
}
