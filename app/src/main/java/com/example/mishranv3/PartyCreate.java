package com.example.mishranv3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PartyCreate extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    EditText name, description;
    TextView date;
    String currentUser;
    FirebaseAuth mAuth;
    DatabaseReference db,db2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_create);
        name = findViewById(R.id.partyName);
        description = findViewById(R.id.description);
        date = findViewById(R.id.date);
        db = FirebaseDatabase.getInstance().getReference("PartyID");
        db2 = FirebaseDatabase.getInstance().getReference("PartySession");
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();


        bottomNavigationView = findViewById(R.id.partyCreate_nav);

        bottomNavigationView.setSelectedItemId(R.id.party);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), userHome.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.party:
                        return true;
                    case R.id.solo:
                        startActivity(new Intent(getApplicationContext(), Solo.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.mates:
                        startActivity(new Intent(getApplicationContext(), MateHome.class));
                        overridePendingTransition(0, 0);
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

    public void onDateClick(View view) {
        Calendar c;
        c = Calendar.getInstance();
        final int mDay = c.get(Calendar.DAY_OF_MONTH);
        final int mMonth = c.get(Calendar.MONTH);
        final int mYear = c.get(Calendar.YEAR);

        DatePickerDialog dpd = new DatePickerDialog(this){
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.setText(mDay + "/"+ mMonth  + "/"+ mYear);
            }
        };
        dpd.show();
    }

    public void createParty(View view){

        String id = db.push().getKey(); //Unique code for session
        String partyName = name.getText().toString();
        String partyDate= date.getText().toString();
        String partyDescription = description.getText().toString();
        String permission = "true";
        if(TextUtils.isEmpty(partyName)){
            Toast.makeText(this, "Please enter a name for your party", Toast.LENGTH_LONG).show();
        }
        if(partyDate == "mm/dd/yyyy"){
            Toast.makeText(this, "Please enter a date", Toast.LENGTH_SHORT).show();
        }
        else{
            PartyID partyID = new PartyID(partyName,partyDescription,id,partyDate);
            PartySessions partySessions = new PartySessions(id,permission);
            db.child(id).setValue(partyID);
            db2.child(currentUser).setValue(partySessions);
            Toast.makeText(this, "Party Created", Toast.LENGTH_SHORT).show();
            Intent myIntent = new Intent(this, CreatePartyHome.class);
            startActivity(myIntent);

        }
    }
}


