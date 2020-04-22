package com.example.mishranv3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class userProfile extends AppCompatActivity {

    EditText userName, emailHold;
    String currentUser;
    FirebaseAuth mAuth;
    DatabaseReference db, db2;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        userName = findViewById(R.id.userHolder);
        emailHold = findViewById(R.id.emailHolder);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
        db = FirebaseDatabase.getInstance().getReference().child("Member").child(currentUser);
        db2 = FirebaseDatabase.getInstance().getReference().child("Genre").child(currentUser);
        bottomNavigationView = findViewById(R.id.bottomNavigationView2);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String username = dataSnapshot.child("username").getValue().toString();
                    String email = dataSnapshot.child("email").getValue().toString();

                    userName.setText(username);
                    emailHold.setText(email);
                }

                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(), Search.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.party:
                        startActivity(new Intent(getApplicationContext(), Party.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.solo:
                        startActivity(new Intent(getApplicationContext(), Solo.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.mates:
                        startActivity(new Intent(getApplicationContext(), MateHome.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        return true;

                }

                return false;
            }
        });

    }

    public void onClick(View view){
        String username = userName.getText().toString();
        String email = emailHold.getText().toString();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("username",username);
        hashMap.put("email", email);

        db.updateChildren(hashMap);

    }

    public void signOut(View view){
        FirebaseAuth.getInstance().signOut();
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }

}
