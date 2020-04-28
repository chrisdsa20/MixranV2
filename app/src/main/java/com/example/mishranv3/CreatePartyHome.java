package com.example.mishranv3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreatePartyHome extends AppCompatActivity {

    DatabaseReference userdb,db2;
    FirebaseAuth mAuth;
    TextView sessionCode;
    String currentUser;
    PartyPlaylistAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_party_home);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
        userdb = FirebaseDatabase.getInstance().getReference().child("PartySession");
        recyclerView = findViewById(R.id.partyPlaylistRecycler);
        sessionCode = findViewById(R.id.partyCode);
        userdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String code = dataSnapshot.child(currentUser).child("id").getValue().toString();
                sessionCode.setText(code);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                sessionCode.setText("Error");

            }
        });
    getPartySongs();

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

