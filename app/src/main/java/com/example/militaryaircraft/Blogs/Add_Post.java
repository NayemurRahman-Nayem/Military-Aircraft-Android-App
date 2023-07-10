package com.example.militaryaircraft.Blogs;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.militaryaircraft.R;
import com.google.android.gms.tasks.OnFailureListener;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormatSymbols;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class Add_Post extends AppCompatActivity {

    Button popupAddBtn;
    TextView popupDescription;
    Uri pickedImgUri = null;
    private static final int REQUESCODE = 2 ;
    Dialog popAddPost ;
    FirebaseAuth mAuth;
    private static final int PReqCode = 2 ;
    FirebaseUser currentUser ;
    CircleImageView userphoto ;
    String userprofilephoto ;
    String userRealName ;
    DatabaseReference databaseReference ;
    String imageDownlaodLink ;
    String userName = "dust" ;
    String  postDate , postTime ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        // ----------------------------------------------------------
        popupDescription = findViewById(R.id.popup_description);
        popupAddBtn = findViewById(R.id.popup_add);
        userphoto = findViewById(R.id.userPhoto) ;

        // Add post click Listener
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        StorageReference dc = storage.getReference().child("users").child(firebaseAuth.getCurrentUser().getUid());
        dc.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Glide.with(Add_Post.this)
                        .load(uri) // the uri you got from Firebase
                        .circleCrop()
                        .override(600,600)
                        .into(userphoto); //Your imageView variable
                Toast.makeText(Add_Post.this, "Success", Toast.LENGTH_SHORT).show();
                String userid = firebaseAuth.getCurrentUser().getUid() ;
                DocumentReference documentReference = firebaseFirestore.collection("users").document(userid) ;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Add_Post.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        // Get the username from firebase Realtime database //
        String userId = firebaseAuth.getCurrentUser().getUid() ;
        DocumentReference documentReference = firebaseFirestore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                userName = value.getString("username");
                userRealName = value.getString("fname") ;
            }
        });
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child("Image");
        databaseReference.child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot!=null) {
                    userprofilephoto = snapshot.getValue().toString() ;
                }else{
                    userprofilephoto = "NULL" ;
                    Toast.makeText(Add_Post.this, "Null", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }) ;


        popupAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!popupDescription.getText().toString().isEmpty()) {
                            currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        Calendar calendar = Calendar.getInstance() ;
                        int year = calendar.get(Calendar.YEAR) ;
                        int month = calendar.get(Calendar.MONTH) ;
                        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols() ;
                        String monthName = dateFormatSymbols.getMonths()[month] ;
                        int day = calendar.get(Calendar.DAY_OF_MONTH) ;
                        int hour = calendar.get(Calendar.HOUR_OF_DAY) ;
                        int minute = calendar.get(Calendar.MINUTE) ;
                        postDate = monthName + " " + day + ", " +year ;
                        postTime = hour + ":" + minute ;
                            Post post = new Post(
                            popupDescription.getText().toString(),
                            currentUser.getUid(),0,0,userName,userRealName , postDate , postTime , userprofilephoto );
                             FirebaseDatabase database = FirebaseDatabase.getInstance();
                             DatabaseReference myRef = database.getReference("Posts").push();
                            // get post unique ID and upadte post key
                            String key = myRef.getKey();
                            post.setPostKey(key);
                            // add post data to firebase database
                            myRef.setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Add_Post.this, "Uploaded Successfully ! ", Toast.LENGTH_SHORT).show();
                                }
                            });
                            startActivity(new Intent(Add_Post.this, Post_Page.class));
                        }
                else {
                    Toast.makeText(Add_Post.this, "Try Again ! ", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}