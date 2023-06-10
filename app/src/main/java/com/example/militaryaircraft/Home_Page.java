package com.example.militaryaircraft;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.tv.TvContract;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.auth.User;


public class Home_Page extends AppCompatActivity {

    LinearLayout UserProfile , map , ML , Video , rating  , Post ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        UserProfile  = findViewById(R.id.userProfile) ;
        map = findViewById(R.id.gotoMap) ;
        ML = findViewById(R.id.machineLearning) ;
        Video = findViewById(R.id.embeddedVideo) ;
        rating = findViewById(R.id.Rating) ;
        Post = findViewById(R.id.post) ;

        // Login Page
        UserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home_Page.this,UserProfile_Page.class));
            }
        });


        // Machine Learning Page

        ML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home_Page.this,MLPage.class));
            }
        });

        // Google MAP
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home_Page.this,MapsActivity.class));
            }
        });

        // Embedded Video of Youtube
        Video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home_Page.this, Youtube_Video_Embedded.class));
            }
        });


        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home_Page.this,RatingApp.class));
            }
        });


        Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home_Page.this,Post_Page.class));
            }
        });

    }
}