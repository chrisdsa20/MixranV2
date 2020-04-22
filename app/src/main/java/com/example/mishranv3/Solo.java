package com.example.mishranv3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Solo extends AppCompatActivity {

    RecyclerView recyclerView;
    SoloMusicAdapter adapter;
    DatabaseReference db;
    BottomNavigationView bottomNavigationView;
    FirebaseAuth mAuth;
    String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solo);

        recyclerView = findViewById(R.id.recyclerView);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
        db = FirebaseDatabase.getInstance().getReference().child("UserMusic").child(currentUser);
        bottomNavigationView = findViewById(R.id.solo_nav);
        bottomNavigationView.setSelectedItemId(R.id.solo);
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
                        startActivity(new Intent(getApplication(), MateHome.class));
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), userProfile.class));
                        overridePendingTransition(0,0);
                        return true;

                }

                return false;
            }
        });


        FirebaseRecyclerOptions<Music> options =
                new FirebaseRecyclerOptions.Builder<Music>()
                        .setQuery(db, Music.class)
                        .build();

        adapter = new SoloMusicAdapter(options);
        recyclerView.setAdapter(adapter);
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

    public void onClick(View view){
        Intent myIntent = new Intent(this,solo_search.class);
        startActivity(myIntent);
    }
}
