package com.example.mishranv3;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUp extends AppCompatActivity {

    EditText username, pass, email, confirm_email;
    FirebaseAuth mAuth;
    FirebaseDatabase db;


    DatabaseReference DatabaseReff;

    private static final String TAG = "SignUp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = findViewById(R.id.userInsert);
        pass = findViewById(R.id.passInsert);
        email = findViewById(R.id.emailInsert);
        confirm_email = findViewById(R.id.confirmEmail);


        mAuth = FirebaseAuth.getInstance();
        DatabaseReff = db.getInstance().getReference("Member");

    }

        @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser mAuthCurrentUser = mAuth.getCurrentUser();
        if(mAuthCurrentUser != null){
            newPage();
        }
    }

    public void onClickHandler(View view){
        createAccount();
    }


    private void createAccount() {
        String emailadd = email.getText().toString();
        String emailconfirm = confirm_email.getText().toString();
        String password = pass.getText().toString();

        if (!emailadd.equals(emailconfirm)) {
            Toast.makeText(this, "The emails do not match ", Toast.LENGTH_SHORT).show();
        }
        if (password.length() < 8) {
            Toast.makeText(this, "This password is less than 8 characters", Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.createUserWithEmailAndPassword(emailadd, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                sendUser();
                                newPage();

                            } else {
                                String message = task.getException().getMessage();
                                Toast.makeText(SignUp.this, message, Toast.LENGTH_SHORT).show();

                            }


                        }
                    });

        }


    }

    private void sendUser(){
        String currentUser = mAuth.getCurrentUser().getUid();
        String userID = username.getText().toString();
        String password = pass.getText().toString();
        String emailID = email.getText().toString();

        Member member = new Member(userID,password,emailID);

        DatabaseReff.child(currentUser).setValue(member);




    }

    private void newPage() {
        Intent newPage = new Intent(this, SignUp2.class);
        startActivity(newPage);
    }
}

