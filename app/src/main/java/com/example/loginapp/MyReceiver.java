package com.example.loginapp;
import com.example.loginapp.Message;
import com.example.loginapp.UserInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MyReceiver extends BroadcastReceiver {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SmsBroadcastReceiver";
    String msg, phoneNo = "";
    private FirebaseAuth mAuth;

    private DatabaseReference MsgDB;

    String iddd;
    String alarmeMsg="Alarme is on";

    @Override
    public void onReceive(Context context, Intent intent) {
        FirebaseApp.initializeApp(context);
        mAuth=FirebaseAuth.getInstance();
        MsgDB =FirebaseDatabase.getInstance().getReference("Messages");
        //retrieves the general action to be performed and display on log
        Log.i(TAG, "Intent Received: " +intent.getAction());


        if (intent.getAction()==SMS_RECEIVED)
        {
            //retrieves a map of extended data from the intent
            Bundle dataBundle = intent.getExtras();
            if (dataBundle!=null)
            {
                //creating PDU(Protocol Data Unit) object which is a protocol for transferring message
                Object[] mypdu = (Object[])dataBundle.get("pdus");
                final SmsMessage[] message = new SmsMessage[mypdu.length];

                for (int i = 0; i<mypdu.length; i++)
                {
                    //for build versions >= API Level 23
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    {
                        String format = dataBundle.getString("format");
                        //From PDU we get all object and SmsMessage Object using following line of code
                        message[i] = SmsMessage.createFromPdu((byte[])mypdu[i], format);
                    }
                    else
                    {
                        //<API level 23
                        message[i] = SmsMessage.createFromPdu((byte[])mypdu[i]);
                    }
                    msg = message[i].getMessageBody();
                    phoneNo = message[i].getOriginatingAddress();
                }

                Toast.makeText(context, "Message: " +msg +"\nNumber: " +phoneNo, Toast.LENGTH_LONG).show();
                if (msg.equals(alarmeMsg)) {        //on test le message récu avec le msg qui annonce le declanchement d'alarme

                  //  Toast.makeText(context, "Message d'alarme recu", Toast.LENGTH_LONG).show();
                    iddd = mAuth.getUid();
                    Message msg2 = new Message(msg);
                    MsgDB.child(iddd).push().setValue(msg2);
                }
                else
                {
                    String arr[] = msg.split(" ", 2);

                    String firstWord = arr[0]; //la forme de msg envoyé par la carte qui annonce la position du véhicule est par example:GPS latitude:xxxxx longitude:xxxx
                    if (firstWord.equals("htt"))
                    {
                        //open the gps work
                        Intent inten2 = new Intent(Intent.ACTION_VIEW, Uri.parse(msg));
                        context.startActivity(inten2);
                       // Toast.makeText(context, "Message de gps", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }

    }





}












