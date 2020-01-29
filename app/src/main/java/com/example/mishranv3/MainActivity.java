package com.example.mishranv3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickHandler(View view){
        //Creating the event handler to navigate to Sign Up page
        Intent myIntent = new Intent(MainActivity.this, SignUp.class);
        startActivity(myIntent);

    }

    public void onClickHandler1(View view){
        //Navigate to first page


    }
}
