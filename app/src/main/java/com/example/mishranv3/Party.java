package com.example.mishranv3;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Party extends AppCompatActivity {

    DatabaseReference db;
    FirebaseAuth mAuth;
    String currentUser;
    TextView code, partyName;
    ImageView share;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
        db = FirebaseDatabase.getInstance().getReference().child("PartySession").child(currentUser);
        code = findViewById(R.id.latestPartyCode);
        partyName = findViewById(R.id.partyName);
        bottomNavigationView = findViewById(R.id.party_nav);
        share = findViewById(R.id.partyShare);
        bottomNavigationView.setSelectedItemId(R.id.party);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), userHome.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.party:
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
                        startActivity(new Intent(getApplicationContext(), userProfile.class));
                        overridePendingTransition(0,0);
                        return true;

                }

                return false;
            }
        });
//Allows the user to see the latest session, and the user is able to join by clicking on the code
        //User is also able to share this code
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("id").exists()) {
                    String value = dataSnapshot.child("id").getValue().toString();
                    code.setText(value);
                }else{
                    code.setVisibility(View.INVISIBLE);
                    share.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
    public void onClick(View view){
        Intent myIntent = new Intent(this, PartyCreate.class);
        startActivity(myIntent);
    }

    public void goToJoin(){
        Intent myIntent = new Intent(this,PartyJoinHome.class);
        startActivity(myIntent);
    }
    public void goToCreate(){
        Intent myIntent = new Intent(this, CreatePartyHome.class);
        startActivity(myIntent);
    }

    public void partyjoin(View view){
        Intent myIntent = new Intent(this, PartyJoin.class);
        startActivity(myIntent);
    }

    public void goToSession(final View view){
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("permission").getValue().equals("true")){
                    goToCreate();
                }else{
                    goToJoin();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void shareHandler(View view){
        String sessionCode = code.getText().toString();

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, sessionCode);
        startActivity(shareIntent.createChooser(shareIntent,"Share"));
    }

    private static final String TAG = "Party";

}
