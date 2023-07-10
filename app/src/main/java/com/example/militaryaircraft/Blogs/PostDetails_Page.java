package com.example.militaryaircraft.Blogs;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.militaryaircraft.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class PostDetails_Page extends AppCompatActivity {

    EditText commentValue;
    String comment_text = "Nayem" ;
    Button uploadComment ;
    String postID ;
    RecyclerView recyclerView ;
    FirebaseAuth firebaseAuth ;
    String userRealName ;
    DatabaseReference databaseReference ;
    FirebaseFirestore firebaseFirestore ;
    String userName ;
    CommentAdapter commentAdapter ;
    FirebaseUser currentUser ;
    String userid ;
    String commentDate , commentTime ;
    String userprofileurl ;
    ArrayList<Comment> commentsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details_page);
        recyclerView = findViewById(R.id.commentList) ;
        commentValue = findViewById(R.id.commentvalue) ;
        uploadComment = findViewById(R.id.uploadcomment) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        postID = getIntent().getStringExtra("postID") ;

        // Comment Part ###################################################################################################################################
        // Set Comments in the RecyclerView
        MakeCommentList() ;
        //comment
        uploadComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comment_text = commentValue.getText().toString() ;
                if(!comment_text.equals("")) {
                    CreateComment() ;
                }
                else {
                    Toast.makeText(PostDetails_Page.this,"Comment section is empty ! " , Toast.LENGTH_SHORT).show();
                }
            }
        });

        // get current user profile pic ;
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child("Image");
        databaseReference.child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot!=null) {
                    userprofileurl = snapshot.getValue().toString() ;
                }else{
                    userprofileurl = "NULL" ;
                    Toast.makeText(PostDetails_Page.this, "Null", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }) ;


        // Get the userRealName #############################################################################################
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        String userId = firebaseAuth.getCurrentUser().getUid() ;
        DocumentReference documentReference = firebaseFirestore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                userRealName = value.getString("fname") ;
            }
        });
        // ##################################################################################################################

    }

    // get all the comments for the post
    void MakeCommentList() {
        Context context = this ;
        recyclerView.setHasFixedSize(true) ;
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Comments");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commentsList = new ArrayList<Comment>()  ;
                for (DataSnapshot postsnap: dataSnapshot.getChildren()) {
                    Comment comment1 = postsnap.getValue(Comment.class);
                    //Toast.makeText(PostDetails_Page.this,postID,Toast.LENGTH_SHORT).show();
                    if(Objects.equals(postID,comment1.getPostID())) {
                        commentsList.add(comment1) ;
                    }
                }
                commentAdapter = new CommentAdapter(context,commentsList) ;
                recyclerView.setAdapter(commentAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    void CreateComment() {
        Calendar calendar = Calendar.getInstance() ;
        int year = calendar.get(Calendar.YEAR) ;
        int month = calendar.get(Calendar.MONTH) ;
        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols() ;
        String monthName = dateFormatSymbols.getMonths()[month] ;
        int day = calendar.get(Calendar.DAY_OF_MONTH) ;
        int hour = calendar.get(Calendar.HOUR_OF_DAY) ;
        int minute = calendar.get(Calendar.MINUTE) ;
        commentDate = monthName + " " + day + ", " +year ;
        commentTime = hour + ":" + minute ;
        Toast.makeText(PostDetails_Page.this, userRealName, Toast.LENGTH_SHORT).show();
        Comment comment1 = new Comment(postID,userRealName,comment_text,commentDate,commentTime , userprofileurl ) ;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Comments").push();
        myRef.setValue(comment1).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(PostDetails_Page.this, "Comment Uploaded ! ", Toast.LENGTH_SHORT).show();
             }
        });
    }
}