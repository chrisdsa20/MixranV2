package com.example.mishranv3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MatesJoinSession extends AppCompatActivity {

    private static final String TAG="MatesJoinSession";
    EditText input;
    Button submit;
    MatesMusicAdapter adapter;
    DatabaseReference db, db2, database, newdb;
    BottomNavigationView bottomNavigationView;
    FirebaseAuth mAuth;
    String currentUser;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mates_join_session);

        input = findViewById(R.id.sessionInput);
        String code = input.getText().toString();
        submit = findViewById(R.id.submitCode);
        db = FirebaseDatabase.getInstance().getReference("MatesSession");
        db2 = FirebaseDatabase.getInstance().getReference().child("MatesSessionID");
        newdb = FirebaseDatabase.getInstance().getReference().child("Music");
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
        bottomNavigationView = findViewById(R.id.joinSession_nav);
        recyclerView = findViewById(R.id.joinAdapter);

    }
    public void onClickHandler(final View view){
        final String code = input.getText().toString();

        db2.orderByChild("id").equalTo(code).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    //Inform user that the data has been
                    Toast.makeText(MatesJoinSession.this, "Data Found", Toast.LENGTH_SHORT).show();
                    String currentUser = mAuth.getCurrentUser().getUid();
                    MatesSessions matesSessions = new MatesSessions(code);
                    db.child(currentUser).setValue(matesSessions);
                    newSearch();





                }
                else{
                    Toast.makeText(MatesJoinSession.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }





            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void searchHandler(View view){
        Intent searchIntent = new Intent(this, solo_search.class);
        startActivity(searchIntent);
    }
    private void newSearch(){
        Intent myIntent = new Intent(this, CreateSessionHome.class);
        startActivity(myIntent);
    }
}
