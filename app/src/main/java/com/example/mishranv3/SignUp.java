package com.example.mishranv3;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    FirebaseAuth mAuth;


    DatabaseReference DatabaseReff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = findViewById(R.id.userInsert);
        pass = findViewById(R.id.passInsert);
        email = findViewById(R.id.emailInsert);
        confirm_email = findViewById(R.id.confirmEmail);


        DatabaseReff = db.getReference("members");
        mAuth =  FirebaseAuth.getInstance();



    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser mAuthCurrentUser= mAuth.getCurrentUser();
        Toast.makeText(this, "You are already logged in", Toast.LENGTH_LONG).show();
    }

    public void createAccount(){
        String emailadd = email.getText().toString();
        String password = pass.getText().toString();
        mAuth.createUserWithEmailAndPassword(emailadd, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUp.this, "The email or password is incorrect",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });

    }




    public void sendData(){

        String userText = username.getText().toString();
        String passText = pass.getText().toString();
        String emailAdd = email.getText().toString();
        String emailConfirm = confirm_email.getText().toString();
        String id = DatabaseReff.push().getKey();

        Member member = new Member(userText,passText,emailAdd,emailConfirm,id);

        DatabaseReff.child(userText).setValue(member);

    }

    public void onClickHandlerSignUp(){
        Intent newPage = new Intent(this, SignUp2.class);
        startActivity(newPage);
    }

    public void checkEmail(View view){
        String emailAdd1 = email.getText().toString();
        String emailConfirm1 = confirm_email.getText().toString();
        String password = pass.getText().toString();


        if(!emailAdd1.equals(emailConfirm1)){
            Toast.makeText(this, "The emails do not match ", Toast.LENGTH_SHORT).show();
        }if(password.length() < 8){
            Toast.makeText(this, "This password is less than 8 characters", Toast.LENGTH_SHORT).show();
        }
        else{
//            Toast.makeText(this, "The emails do not match ", Toast.LENGTH_SHORT).show();
            sendData();
            createAccount();
            onClickHandlerSignUp();

        }
    }


    private class TAG {
    }
}

