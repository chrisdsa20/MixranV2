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

public class CreatePartyHome extends AppCompatActivity {

    DatabaseReference userdb;
    FirebaseAuth mAuth;
    TextView sessionCode;
    String currentUser;
    PartyPlaylistAdapter adapter;
    RecyclerView recyclerView;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_party_home);
        bottomNavigationView = findViewById(R.id.createPartybottom_nav);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
        userdb = FirebaseDatabase.getInstance().getReference().child("PartySession");
        recyclerView = findViewById(R.id.partyPlaylistRecycler);
        sessionCode = findViewById(R.id.partyCode);

        //Allows the user to see the code of the session
        userdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String code = dataSnapshot.child(currentUser).child("id").getValue().toString();
                    sessionCode.setText(code);
                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    getPartySongs();

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
                        startActivity(new Intent(getApplicationContext(), userProfile.class));
                        overridePendingTransition(0,0);
                        return true;

                }

                return false;
            }
        });

    }
    private static final String TAG = "CreatePartyHome";

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
//The userdb contains the code for the session under the currentUser id, however the application cannot access the code straight therefore the app gets the code from the Party Session
    //If the data exists then, this the application uses the code value to find the songs in the PartyID document
    public void getPartySongs(){
        final DatabaseReference[] db = new DatabaseReference[1];
        userdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String value = dataSnapshot.child(currentUser).child("id").getValue().toString();
                    Log.i(TAG, "onDataChange: " + value);
                    db[0] = FirebaseDatabase.getInstance().getReference("PartyID").child(value).child("Songs");
                    FirebaseRecyclerOptions<Music> options = new FirebaseRecyclerOptions.Builder<Music>()
                            .setQuery(db[0], Music.class)
                            .build();
                    adapter = new PartyPlaylistAdapter(options);
                    recyclerView.setAdapter(adapter);
                    adapter.startListening();

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
        adapter.stopListening();
    }
}

