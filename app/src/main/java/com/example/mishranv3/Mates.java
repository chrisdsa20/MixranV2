package com.example.mishranv3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Mates extends AppCompatActivity {

//The page will generate a page with a unique ID that other users can type in to find the music that is on that link and
    //to then be able to put their own music on that

    String id;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    MatesAdapter recyclerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mates);
        recyclerView = findViewById(R.id.matesRecycler);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        id = databaseReference.getKey();
        recyclerAdapter = new MatesAdapter();


    }
}
