package com.example.loginapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class RegsiterMe extends AppCompatActivity {

    private EditText name, pwd, pwd2, email, phone;
    private ProgressBar LoadingProgress;
    private Button btn;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regsiter_me);

        //init views

        name =(EditText) findViewById(R.id.NameID);
        pwd =(EditText)findViewById(R.id.pwdID);
        pwd2 =(EditText) findViewById(R.id.ConfpwdID);
        email =(EditText)findViewById(R.id.EmailID);
        phone =(EditText)findViewById(R.id.PhoneID);
        LoadingProgress = (ProgressBar) findViewById(R.id.progressBarID);
        LoadingProgress.setVisibility(View.INVISIBLE);
        btn = (Button)findViewById(R.id.buttonID);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        databaseUser= FirebaseDatabase.getInstance().getReference();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn.setVisibility(View.INVISIBLE);
                LoadingProgress.setVisibility(View.VISIBLE);
                final String AdrEmail = email.getText().toString();
                final String password = pwd.getText().toString();
                final String passwood2 = pwd2.getText().toString();
                final String phoneNumber = phone.getText().toString();
                String userName = name.getText().toString() ;

                //testing fields
                if (AdrEmail.isEmpty() || password.isEmpty() || userName.isEmpty()|| !password.equals(passwood2) || phoneNumber.isEmpty()) {
                    showMessage("please verify all fields");
                    btn.setVisibility(View.VISIBLE);
                    LoadingProgress.setVisibility(View.INVISIBLE);
                        }
                else {
                    //nothing wrong with fields
                    //create user account
                    //try to create the user if the email is valid
                    CreateUserAccount(AdrEmail,userName, password,phoneNumber);

                        }
                    }
        });

    }

    private void CreateUserAccount(String email,final String name, String password ,final String tel) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //user account created succesfully
                            showMessage("Account created");
                            String id = mAuth.getUid();
                            UserInfo user = new UserInfo(name,id,tel);
                            databaseUser.child(id).setValue(user);

                        } else {
                            //account creation failed
                            showMessage("Account creation Failed");
                            btn.setVisibility(View.VISIBLE);
                            LoadingProgress.setVisibility(View.INVISIBLE);
                        }

                    }
                });
    }
    private void showMessage(String msg) {

        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        }
}

