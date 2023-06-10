package com.example.militaryaircraft;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviderGetKt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SplashScreen extends AppCompatActivity {


    ProgressBar progressBar ;
    private FirebaseAuth mAuth;
    SharedPreferences sharedPreferences ;
    int progress = 0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        progressBar = (ProgressBar) findViewById(R.id.progressbar)  ;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                doWork() ;
//                mAuth = FirebaseAuth.getInstance() ;
//                FirebaseUser user = mAuth.getCurrentUser();
//                if(user != null && user.isEmailVerified()){
//                    startActivity(new Intent(getApplicationContext(), Home_Page.class));
//                }else {
//                    startActivity(new Intent(SplashScreen.this,MainActivity.class));
//                }


                sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)  ;
                boolean isLoggedin = sharedPreferences.getBoolean("isLoggedin",false) ;
                if(isLoggedin) {
                    //Toast.makeText(SplashScreen.this,"true",Toast.LENGTH_SHORT).show(); ;
                    startActivity(new Intent(SplashScreen.this,Home_Page.class));
                }
                else {
                    startActivity(new Intent(SplashScreen.this,Login_Page.class)) ;
                }

            }
        });
        thread.start();
    }

    public void  doWork() {
        for(progress=1;progress<=10;progress++) {
            try {
                Thread.sleep(100) ;
                progressBar.setProgress(progress);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}