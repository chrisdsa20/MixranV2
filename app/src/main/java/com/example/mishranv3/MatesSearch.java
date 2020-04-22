package com.example.mishranv3;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MatesSearch extends AppCompatActivity {

    DatabaseReference db;
    RecyclerView recyclerView;
    MusicAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solo_search);

        db = FirebaseDatabase.getInstance().getReference().child("Music");
        recyclerView = findViewById(R.id.searchRecycler);

        FirebaseRecyclerOptions<Music> options =
                new FirebaseRecyclerOptions.Builder<Music>()
                        .setQuery(db, Music.class)
                        .build();

        adapter = new MusicAdapter(options);
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
