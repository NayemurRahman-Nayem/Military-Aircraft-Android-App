package com.example.militaryaircraft;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.militaryaircraft.databinding.ActivityGsapSassBinding;

public class GSAP_SASS extends AppCompatActivity {

    WebView webView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gsap_sass);
        webView = findViewById(R.id.webView) ;

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        webView.getSettings().setJavaScriptEnabled(true); // Enable JavaScript (optional)

        // Load an HTML file from assets folder
        webView.loadUrl("file:///android_asset/gsap.html");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
                startApp();
            }
        });
        thread.start();
    }

    public void doWork(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void startApp(){

        //Toast.makeText(GSAP_SASS.this,"LOL",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(GSAP_SASS.this, Login_Page.class);
        startActivity(intent);
        finish();
    }
}