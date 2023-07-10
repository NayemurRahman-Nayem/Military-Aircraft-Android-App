package com.example.militaryaircraft;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.militaryaircraft.Blogs.Post_Page;
import com.example.militaryaircraft.MAP.MapsActivity;
import com.example.militaryaircraft.ML.MLPage;
import com.example.militaryaircraft.RESTfulAPI.RESTfulAPI;
import com.example.militaryaircraft.Rating.RatingApp;
import com.example.militaryaircraft.SESSION.UserProfile_Page;
import com.example.militaryaircraft.YouTube.Youtube_Video_Embedded;


public class Home_Page extends AppCompatActivity {

    LinearLayout UserProfile , map , ML , Video , rating  , Post , restfulapi , graphql ;
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
        restfulapi = findViewById(R.id.RESTfulAPI) ;
        graphql = findViewById(R.id.graphql) ;



        // GraphQL
//        graphql.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });


        // RESTful API
        restfulapi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home_Page.this, RESTfulAPI.class));
            }
        });


        // Login Page
        UserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home_Page.this, UserProfile_Page.class));
            }
        });

        // Machine Learning Page

        ML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home_Page.this, MLPage.class));
            }
        });

        // Google MAP
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home_Page.this, MapsActivity.class));
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
                startActivity(new Intent(Home_Page.this, RatingApp.class));
            }
        });


        Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home_Page.this, Post_Page.class));
            }
        });

    }
}