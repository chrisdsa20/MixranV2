package com.example.mishranv3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MatesCreateSession extends AppCompatActivity {

    EditText name;
    String id, currentUser;
    DatabaseReference db;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mates_create_session);

        db = FirebaseDatabase.getInstance().getReference("MatesSession");
        name = findViewById(R.id.SessionName);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();

    }


    public void onClickListener(View view){
        Intent myIntent = new Intent(this, MatesJoinSession.class);
        sessionData();
        startActivity(myIntent);
        finish();
    }

    private void sessionData(){
        String sessionName = name.getText().toString();
        String sessionId = db.push().getKey();

        MatesSession matesSession = new MatesSession(sessionName,sessionId);

        db.child(currentUser).setValue(matesSession);
    }

}
