package com.example.mishranv3;
//Create new Session

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    public void onClick(View view){
        String sessionname= name.getText().toString();

//Check to see if user has filled out session name
        if(TextUtils.isEmpty(sessionname)){
            Toast.makeText(this,"Please enter a name...", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Data Sent", Toast.LENGTH_SHORT).show();
            Intent myIntent = new Intent(this, CreateSessionHome.class);
            startActivity(myIntent);
            sessionData();
        }

    }

    public void newSearch(View view){
        Intent myIntent = new Intent(this, solo_search.class);
        startActivity(myIntent);
        finish();
    }


    private void sessionData(){
        String sessionName = name.getText().toString();
        String currentUser = mAuth.getCurrentUser().getUid();
        String permission = "true"; //Set to true so tha the initiator of the session is able to play music
        String id = db.push().getKey(); //Generates random key that will be used as the session ID

        MatesSessions matesSessions = new MatesSessions(id,permission);
        MatesSession matesSession = new MatesSession(sessionName,id);
        db.child(id).setValue(matesSession);
        db2.child(currentUser).setValue(matesSessions);
    }



}