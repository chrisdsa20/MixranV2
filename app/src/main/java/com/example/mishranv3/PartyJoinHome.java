package com.example.mishranv3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PartyJoinHome extends AppCompatActivity {

    DatabaseReference userdb,db;
    RecyclerView recyclerView;
    matesClientAdapter clientAdapter;
    String currentUser;
    FirebaseAuth mAuth;
    TextView sessionCode;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_join_home);
        mAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.RecyclerJoinMates);
        currentUser = mAuth.getCurrentUser().getUid();
        sessionCode = findViewById(R.id.partyJoinCode);
        userdb = FirebaseDatabase.getInstance().getReference("PartySession").child(currentUser);
        userdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String code = dataSnapshot.child("id").getValue().toString();
                    Log.i(TAG, "onDataChange: " + code);
                    sessionCode.setText(code);
                } else {
                    sessionCode.setText("Welcome");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        clientSong();

        bottomNavigationView = findViewById(R.id.partyJoinbottom_nav);
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

    public void clientSong(){
        final DatabaseReference[] db = new DatabaseReference[1];
        userdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String value = dataSnapshot.child("id").getValue().toString();
                    Log.i(TAG, "onDataChange: "+ value);
                    db[0] = FirebaseDatabase.getInstance().getReference("PartyID").child(value).child("Songs");
                    FirebaseRecyclerOptions<Music> options= new FirebaseRecyclerOptions.Builder<Music>()
                            .setQuery(db[0], Music.class)
                            .build();
                    clientAdapter = new matesClientAdapter(options);
                    recyclerView.setAdapter(clientAdapter);
                    clientAdapter.startListening();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        clientAdapter.stopListening();
    }
    public void shareHandler(View view){
        String code = sessionCode.getText().toString();

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, code);
        startActivity(shareIntent.createChooser(shareIntent,"Share"));
    }

    public void newSearch(View view){
        Intent myIntent = new Intent(this, PartySearch.class);
        startActivity(myIntent);
    }

    private static final String TAG = "PartyJoinHome";
}
