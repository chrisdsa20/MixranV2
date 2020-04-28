package com.example.mishranv3;

//The Home page after the user has joined a session

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateSessionHome extends AppCompatActivity {


    DatabaseReference userdb, userCode;
    FirebaseAuth mAuth;
    ImageView shareButton;
    String currentUser;
    TextView sessionCode;
    RecyclerView recyclerView;
    MatesMusicAdapter adapter;
    String text;
    private Object FirebaseRecyclerOptions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_session_home);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
        userdb = FirebaseDatabase.getInstance().getReference("MatesSession").child(currentUser);
        text = userdb.toString();
        Log.i(TAG, "onCreate: "+ userCode);
        shareButton = findViewById(R.id.shareButton);
        sessionCode = findViewById(R.id.sessionCode);
        recyclerView = findViewById(R.id.RecyclerMates);
        userdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String code = dataSnapshot.child("id").getValue().toString();
                    Log.i(TAG, "onDataChange: "+ code);
                    sessionCode.setText(code);
                }else
                {
                    sessionCode.setText("Welcome");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        getSongs();

    }

    private void getSongs() {
        final DatabaseReference[] db = new DatabaseReference[1];
        userdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String value = dataSnapshot.child("id").getValue().toString();
                    Log.i(TAG, "onDataChange: "+ value);
                    db[0] = FirebaseDatabase.getInstance().getReference("MatesSessionID").child(value).child("Songs");
                    FirebaseRecyclerOptions<Music> options= new FirebaseRecyclerOptions.Builder<Music>()
                            .setQuery(db[0], Music.class)
                            .build();
                    adapter = new MatesMusicAdapter(options);
                    recyclerView.setAdapter(adapter);
                    adapter.startListening();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private static final String TAG="CreateSessionHome";


    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public void shareHandler(View view){
        String code = sessionCode.getText().toString();

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, code);
        startActivity(shareIntent.createChooser(shareIntent,"Share"));
    }

    public void newSearch(View view){
        Intent myIntent = new Intent(this, Mates_Search.class);
        startActivity(myIntent);
        finish();
    }

}