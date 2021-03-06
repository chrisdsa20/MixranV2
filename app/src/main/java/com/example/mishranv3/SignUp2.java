package com.example.mishranv3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp2 extends AppCompatActivity {
    EditText favgenre1, favgenre2, favgenre3, userid;
    FirebaseDatabase db = FirebaseDatabase.getInstance();

    DatabaseReference DatabaseReff;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        favgenre1 = findViewById(R.id.favGenreinput1);
        favgenre2 = findViewById(R.id.favGenreinput2);
        favgenre3 = findViewById(R.id.favGenreinput3);
        userid = findViewById(R.id.userInsert);

        DatabaseReff = db.getInstance().getReference("Genre");
        mAuth = FirebaseAuth.getInstance();
    }
}