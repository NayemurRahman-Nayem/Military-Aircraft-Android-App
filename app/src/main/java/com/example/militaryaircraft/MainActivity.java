package com.example.militaryaircraft;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.StartupTime;
import com.google.firebase.firestore.auth.User;


public class MainActivity extends AppCompatActivity {

    LinearLayout login , register , rating ;
    Button button1 , button2 , YoutubeVideo ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.userlogin) ;
        register = findViewById(R.id.userRegister) ;
        rating = findViewById(R.id.Rating) ;
        // Login

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Login_Page.class));
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Register_Page.class));
            }
        });



    }
}