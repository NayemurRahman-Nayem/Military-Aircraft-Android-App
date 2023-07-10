package com.example.militaryaircraft.Blogs;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.militaryaircraft.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;
public class Post_Page extends AppCompatActivity {

    CircleImageView UploadPost ;
    ListView listView ;
    Integer pagenumber = 1 ;
    Integer startnumber = 1 , endnumber = 5 ;
    Button previousPage , nextPage  ;
    TextView pageNoView ;
    RecyclerView postRecyclerView ;
    PostAdapter postAdapter ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference ;
    ArrayList<Post> postList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_page);
        UploadPost = findViewById(R.id.uploadPost) ;
        previousPage = findViewById(R.id.previousButton) ;
        nextPage = findViewById(R.id.nextButton) ;
        pageNoView = findViewById(R.id.pageNo) ;
        postRecyclerView = findViewById(R.id.postList) ;
        postRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        pagenumber = Integer.parseInt(pageNoView.getText().toString()) ;
        // Upload user Post
        UploadPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Post_Page.this, Add_Post.class)) ;
            }
        });
        // for first time
        makeList();
        previousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pagenumber==1) {
                    Toast.makeText(Post_Page.this,"No previous Page Found !",Toast.LENGTH_SHORT).show();
                }
                else {
                    pagenumber-- ;
                    String s = Integer.toString(pagenumber) ;
                    pageNoView.setText(s);
                    endnumber = pagenumber*5 ;
                    startnumber = endnumber - 4 ;
                    makeList();
                }
            }
        });
        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pagenumber ++ ;
                String s = Integer.toString(pagenumber) ;
                endnumber = pagenumber*5 ;
                startnumber = endnumber - 4 ;
                pageNoView.setText(s);
                makeList() ;
            }
        });
    }
    void makeList() {
        Context context = this ;
        postRecyclerView.setHasFixedSize(true) ;
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Posts");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList = new ArrayList<Post>();
                ArrayList<Post> initial = new ArrayList<Post>() ;
                for (DataSnapshot postsnap: dataSnapshot.getChildren()) {
                    Post post = postsnap.getValue(Post.class);
                    initial.add(post);
                }
                Collections.reverse(initial);
                int cnt = 0 ;
                for (Post postsnap: initial) {
                    Post post = postsnap ;
                    if(cnt>=startnumber-1 && cnt<endnumber) {
                        postList.add(post) ;
                    }
                    cnt ++ ;
                }
                postAdapter = new PostAdapter(context,postList) ;
                postRecyclerView.setAdapter(postAdapter) ;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}