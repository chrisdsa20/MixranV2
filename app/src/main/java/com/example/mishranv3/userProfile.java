package com.example.mishranv3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class userProfile extends AppCompatActivity {

    EditText userName, emailHold, genreHold,passCheck,newPass;
    String currentUser;
    FirebaseAuth mAuth;
    DatabaseReference db, db2;
    BottomNavigationView bottomNavigationView;
    Button passConfirm, changepass;
    FirebaseUser user;
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        userName = findViewById(R.id.userHolder);
        emailHold = findViewById(R.id.emailHolder);
        genreHold = findViewById(R.id.favGenre1);
        changepass = findViewById(R.id.button17);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
        db = FirebaseDatabase.getInstance().getReference().child("Member").child(currentUser);
        db2 = FirebaseDatabase.getInstance().getReference().child("Genre").child(currentUser);
        bottomNavigationView = findViewById(R.id.profilebottom_nav);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String username = dataSnapshot.child("username").getValue().toString();
                    String email = dataSnapshot.child("email").getValue().toString();

                    userName.setText(username);
                    emailHold.setText(email);
                }

                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        db2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String genre = dataSnapshot.child("favGenre").getValue().toString();
                    genreHold.setText(genre);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        passCheck = findViewById(R.id.confirmPassword);
        newPass = findViewById(R.id.newPassword2);
        passConfirm = findViewById(R.id.confirmPass);
        passCheck.setVisibility(View.INVISIBLE);
        newPass.setVisibility(View.INVISIBLE);
        passConfirm.setVisibility(View.INVISIBLE);



        bottomNavigationView.setSelectedItemId(R.id.profile);
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
                        startActivity(new Intent(getApplicationContext(), MateHome.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        return true;

                }

                return false;
            }
        });

    }

    public void onClick(View view){
        String username = userName.getText().toString();
        String email = emailHold.getText().toString();
        String genre = genreHold.getText().toString();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("username",username);
        hashMap.put("email", email);
        db.updateChildren(hashMap);

        HashMap<String, Object> hashMap1 = new HashMap<>();
        hashMap1.put("favGenre",genre);

        db2.updateChildren(hashMap1);

    }
//logs user out of application
    public void signOut(View view){
        FirebaseAuth.getInstance().signOut();
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }

    //Collapsible menu to change password, as it is unnecessary  to view the fields to change passwords
    public void changePassword(View view){
        if(flag){
            passCheck.setVisibility(View.VISIBLE);
            newPass.setVisibility(View.VISIBLE);
            passConfirm.setVisibility(View.VISIBLE);
            flag = false;
            changepass.setText("Collapse");
        }else{
            passCheck.setVisibility(View.INVISIBLE);
            newPass.setVisibility(View.INVISIBLE);
            passConfirm.setVisibility(View.INVISIBLE);
            flag=true;
            changepass.setText("Change Password");
        }

    }

    public void checkAndChangePassword(View view){
        user = FirebaseAuth.getInstance().getCurrentUser();
        String email = emailHold.getText().toString();
        final String password = passCheck.getText().toString();
        final String newPassword = newPass.getText().toString();

        if(password.isEmpty() && newPassword.isEmpty()){
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
        }else{//Checks if user has entered their password correct, and based on this password is updated or an error message is thrown.
            AuthCredential credentials = EmailAuthProvider.getCredential(email,password);
            user.reauthenticate(credentials)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task){
                            if(task.isSuccessful()){
                                user.updatePassword(newPassword);
                                Log.d(TAG, "User re-authenticated");
                                Toast.makeText(userProfile.this, "Password has been successfully changed", Toast.LENGTH_LONG).show();
                            }else{
                                String message = task.getException().getMessage();
                                Toast.makeText(userProfile.this, message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    private static final String TAG = "userProfile";
}
