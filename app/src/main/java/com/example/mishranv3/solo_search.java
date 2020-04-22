package com.example.mishranv3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class solo_search extends AppCompatActivity {

    RecyclerView recyclerView;
    SearchAdapter adapter;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solo_search);
        db = FirebaseDatabase.getInstance().getReference().child("Music");
        recyclerView = findViewById(R.id.searchRecycler);

        FirebaseRecyclerOptions<Music> options =
                new FirebaseRecyclerOptions.Builder<Music>()
                        .setQuery(db, Music.class)
                        .build();

        adapter = new SearchAdapter(options);
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




}
