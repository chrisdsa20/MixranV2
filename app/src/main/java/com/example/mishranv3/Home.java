package com.example.mishranv3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Home extends AppCompatActivity {

    DatabaseReference db;
    String currentUser;
    TextView userID;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userID = findViewById(R.id.userWelcome);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
        db = FirebaseDatabase.getInstance().getReference().child("Member");


        db.addValueEventListener(new ValueEventListener() {
            private String TAG = "Home";

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                   String username;
                   username = dataSnapshot.child(currentUser).child("username").getValue().toString();

                    userID.setText("Welcome " + username);
                    Log.i(TAG, username);
                }else
                {
                    userID.setText(R.string.welcome);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
//OnClickHandlers to take users to different modes
    public void onClickParty(View view){
        Intent party = new Intent(this, Party.class);
        startActivity(party);
    }

    public void onClickMates(View view){
        Intent mates = new Intent(this, MateHome.class);
        startActivity(mates);
    }

    public void onClickSolo(View view){
        Intent solo = new Intent(this, Solo.class);
        startActivity(solo);
    }
}
