package com.example.mishranv3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PartyJoin extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    EditText input;
    RecyclerView recyclerView;
    DatabaseReference db, db2;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_join);
        db = FirebaseDatabase.getInstance().getReference().child("PartyID");
        db2 = FirebaseDatabase.getInstance().getReference().child("PartySession");
        input = findViewById(R.id.joinParty);
        mAuth = FirebaseAuth.getInstance();

        bottomNavigationView = findViewById(R.id.partyJoin_nav);

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
    }

    public void onClickHandler(final View view){
        final String code = input.getText().toString();
        final String permission = "false";

        db.orderByChild("id").equalTo(code).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    //Inform user that the data has been
                    Toast.makeText(PartyJoin.this, "Data Found", Toast.LENGTH_SHORT).show();
                    String currentUser = mAuth.getCurrentUser().getUid();
                    PartySessions partySessions = new PartySessions(code,permission);
                    db2.child(currentUser).setValue(partySessions);
                    sessionData();
                }
                else{
                    Toast.makeText(PartyJoin.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }





            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void sessionData(){
        Intent myIntent = new Intent(this, CreatePartyHome.class);
        startActivity(myIntent);
    }
}
