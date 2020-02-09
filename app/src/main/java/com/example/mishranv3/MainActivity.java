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
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText email, pass;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.emailid);
        pass = findViewById(R.id.passid);

        mAuth = FirebaseAuth.getInstance();
    }

    public void loginActivity(View view){
        signIn();
    }

    public void signUp(View view){
        //Creating the event handler to navigate to Sign Up page
        Intent myIntent = new Intent(MainActivity.this, SignUp.class);
        startActivity(myIntent);

    }

    private void onClickHandler(){
        Intent myIntent = new Intent(MainActivity.this, Home.class);
        startActivity(myIntent);
    }
    private void signIn(){
        final String emailadd = email.getText().toString();
        final String password = pass.getText().toString();

        mAuth.signInWithEmailAndPassword(emailadd, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                                onClickHandler();
                        }if (emailadd.isEmpty()){
                            Toast.makeText(MainActivity.this, "Please Enter you Email", Toast.LENGTH_SHORT).show();
                        }if (password.isEmpty()) {
                            Toast.makeText(MainActivity.this, "Please Enter your Password", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            String message = task.getException().getMessage();
                            Toast.makeText(MainActivity.this, message,
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });
    }
}
