package com.example.mishranv3;

//The Home page after the user has joined a session

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class CreateSessionHome extends AppCompatActivity {

    DatabaseReference matesdb, musicdb, matesdb2;
    FirebaseAuth mAuth;
    ImageView shareButton;
    String currentUser;
    EditText sessionCode, sessionName;
    RecyclerView recyclerView;
    MusicAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_session_home);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
        matesdb = FirebaseDatabase.getInstance().getReference().child("MatesSessionID");
        musicdb = FirebaseDatabase.getInstance().getReference().child("Music");
        matesdb2 = FirebaseDatabase.getInstance().getReference().child("MatesSession").child(currentUser);
        shareButton = findViewById(R.id.shareButton);
        sessionCode = findViewById(R.id.sessionCode);
        sessionName = findViewById(R.id.sessionInput);
        recyclerView = findViewById(R.id.RecyclerMates);

        FirebaseRecyclerOptions<Music> options =
                new FirebaseRecyclerOptions.Builder<Music>()
                        .setQuery(musicdb, Music.class)
                        .build();

        adapter = new MusicAdapter(options);
        recyclerView.setAdapter(adapter);

        matesdb2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String code = dataSnapshot.child("id").getValue().toString();
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


    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

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
}
