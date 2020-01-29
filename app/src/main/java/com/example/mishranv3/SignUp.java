package com.example.mishranv3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    EditText username, pass, email, confirm_email;
    Button confirmbtn;
    FirebaseDatabase db = FirebaseDatabase.getInstance();

    DatabaseReference DatabaseReff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = findViewById(R.id.userInsert);
        pass = findViewById(R.id.passInsert);
        email = findViewById(R.id.emailInsert);
        confirm_email = findViewById(R.id.confirmEmail);
        confirmbtn = findViewById(R.id.button3);

        DatabaseReff = db.getReference("members");


    }

    public void sendData(){

        String userText = username.getText().toString();
        String passText = pass.getText().toString();
        String emailAdd = email.getText().toString();
        String emailConfirm = confirm_email.getText().toString();
        String id = DatabaseReff.push().getKey();

        Member member = new Member(userText,passText,emailAdd,emailConfirm,id);

        DatabaseReff.child(userText).setValue(member);

    }

    public void onClickHandlerSignUp(){
        Intent newPage = new Intent(this, SignUp2.class);
        startActivity(newPage);
    }

    public void checkEmail(View view){
        String emailAdd1 = email.getText().toString();
        String emailConfirm1 = confirm_email.getText().toString();


        if(emailAdd1.equals(emailConfirm1)){
            sendData();
            onClickHandlerSignUp();
        }else{
            Toast.makeText(this, "The emails do not match ", Toast.LENGTH_SHORT).show();

        }
    }

}

