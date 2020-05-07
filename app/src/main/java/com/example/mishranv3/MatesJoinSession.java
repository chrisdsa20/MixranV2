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
    DatabaseReference db, db2,newdb;
    BottomNavigationView bottomNavigationView;
    FirebaseAuth mAuth;
    String currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mates_join_session);

        input = findViewById(R.id.sessionInput);
        db = FirebaseDatabase.getInstance().getReference("MatesSession");
        db2 = FirebaseDatabase.getInstance().getReference().child("MatesSessionID");
        newdb = FirebaseDatabase.getInstance().getReference().child("Music");
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
        bottomNavigationView = findViewById(R.id.joinSession_nav);

        bottomNavigationView.setSelectedItemId(R.id.mates);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), userHome.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.party:
                        startActivity(new Intent(getApplicationContext(), Party.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.solo:
                        startActivity(new Intent(getApplicationContext(), Solo.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.mates:
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), userProfile.class));
                        overridePendingTransition(0, 0);
                        return true;

                }

                return false;
            }
        });


    }
    public void onClickHandler(final View view){
        final String code = input.getText().toString();
        final String permission = "false";
//Check if the code entered by the user is equal to anything in the MatesSessionID document on the database
        db2.orderByChild("id").equalTo(code).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    //Inform user that the data has been
                    Toast.makeText(MatesJoinSession.this, "Data Found", Toast.LENGTH_SHORT).show();
                    String currentUser = mAuth.getCurrentUser().getUid();
                    //Permission is set to false, and the code and permission are added to the MatesSession document on the database
                    MatesSessions matesSessions = new MatesSessions(code,permission);
                    db.child(currentUser).setValue(matesSessions);
                    newSearch();
                }
                else{
                    //If this value does not exist then an error message is thrown
                    Toast.makeText(MatesJoinSession.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }





            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void newSearch(){
        Intent myIntent = new Intent(this, MatesJoinHome.class);
        startActivity(myIntent);
    }
}
