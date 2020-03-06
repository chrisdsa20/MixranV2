package com.example.mishranv3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MateHome extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mate_home);
        recyclerView = findViewById(R.id.matesRecycler);




        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    public void JoinSession(View view){
        Intent myIntent = new Intent(this, MatesJoinSession.class);
        startActivity(myIntent);
    }

    public void CreateSession(View view){
        Intent myIntent = new Intent(this,MatesCreateSession.class);
        startActivity(myIntent);
    }
}
