package com.example.mishranv3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        db = FirebaseDatabase.getInstance().getReference().child("Member").child(currentUser);
        Toast.makeText(this, currentUser, Toast.LENGTH_SHORT).show();

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String username = dataSnapshot.child("username").getValue().toString();

                    userID.setText("Welcome"+ username);
                }else
                {
                    userID.setText("Welcome");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

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
