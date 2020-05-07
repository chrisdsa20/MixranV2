package com.example.mishranv3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import static android.widget.Toast.*;

public class MateHome extends AppCompatActivity {

    DatabaseReference db, userdb;
    FirebaseAuth mAuth;
    String currentUser;
    ImageView shareButton;
    BottomNavigationView bottomNavigationView;
    TextView code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mate_home);
        shareButton = findViewById(R.id.mateShare);
        code = findViewById(R.id.latestMatesCode);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
        db = FirebaseDatabase.getInstance().getReference().child("MatesSession").child(currentUser);
        userdb = FirebaseDatabase.getInstance().getReference();

        bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setSelectedItemId(R.id.mates);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), userHome.class));
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
//Finds the users favourite genre, and shows songs based on this
        userdb.child("MatesSession").child(currentUser).child("id").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String value = dataSnapshot.getValue().toString();
                    code.setText(value);

                }else{
                    code.setVisibility(View.INVISIBLE);
                    shareButton.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void onClickHandler(final View view){
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("permission").getValue().equals("true")){
                    goSessionHome();
                }else{
                    goJoinHome();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void JoinSession(View view){
        Intent myIntent = new Intent(this, MatesJoinSession.class);
        startActivity(myIntent);
    }

    public void CreateSession(View view){
        Intent myIntent = new Intent(this,MatesCreateSession.class);
        startActivity(myIntent);
    }

    public void shareHandler(View view){
        String sessionCode = code.getText().toString();

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, sessionCode);
        startActivity(shareIntent.createChooser(shareIntent,"Share"));
    }

    public void goSessionHome(){
        Intent myIntent = new Intent(this,CreateSessionHome.class);
        startActivity(myIntent);
    }

    public void goJoinHome(){
        Intent myIntent = new Intent(this,MatesJoinHome.class);
        startActivity(myIntent);
    }

}
