package com.example.mishranv3;
//Create new Session

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class MatesCreateSession extends AppCompatActivity {

    EditText name;
    String currentUser;
    DatabaseReference db, db2;
    FirebaseAuth mAuth;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mates_create_session);

        db = FirebaseDatabase.getInstance().getReference("MatesSessionID");
        db2 = FirebaseDatabase.getInstance().getReference("MatesSession");
        name = findViewById(R.id.SessionName);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
        bottomNavigationView = findViewById(R.id.createSession_nav);
        bottomNavigationView.setSelectedItemId(R.id.mates);
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
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), userProfile.class));
                        overridePendingTransition(0,0);
                        return true;

                }

                return false;
            }
        });


    }


    public void onClickListener(View view){
        Intent myIntent = new Intent(this, CreateSessionHome.class);
        sessionData();
        startActivity(myIntent);
        finish();
    }

    private void sessionData(){
        String currentUser = mAuth.getCurrentUser().getUid();
        String id = db.push().getKey();

        MatesSessions matesSessions = new MatesSessions(currentUser,id);
        db.child(id).setValue(matesSessions);
        db2.child(currentUser).setValue(matesSessions);
    }


}
