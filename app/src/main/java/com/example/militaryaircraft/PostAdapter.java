package com.example.militaryaircraft;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.bumptech.glide.Glide;
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
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {
    Context context ;
    ArrayList<Post> mData ;
    Boolean testClick ;
    String userName ;
    public PostAdapter(Context context, ArrayList<Post> mData) {
        this.context = context;
        this.mData = mData;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View row = LayoutInflater.from(context).inflate(R.layout.post_listview,parent,false);
        return new MyViewHolder(row);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String postID = mData.get(position).getPostKey() ;
        holder.Content.setText(mData.get(position).getDescription());
        holder.username.setText(mData.get(position).getUserRealname());
        holder.DateTime.setText(mData.get(position).getPostDate() + "   " + mData.get(position).getPostTime());

        if(mData.get(position).getUserprofilephoto()!="NULL"){
            Glide.with(holder.showUsericon.getContext())
                    .load(mData.get(position).getUserprofilephoto())
                    .circleCrop()
                    .into(holder.showUsericon);
        }

        // Already Liked ? #################################################################################################
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid() ;
        holder.getLikeButtonStatus(mData.get(position).getPostKey(),userID);
        // Creating Like in Realtime database ############################################################################
        DatabaseReference likeRef = FirebaseDatabase.getInstance().getReference("Likes");
        holder.showLikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                testClick = true;
                likeRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(testClick == true){

                            if(snapshot.child(postID).hasChild(userID)){
                                likeRef.child(postID).child(userID).removeValue();
                                testClick = false;
                            }
                            else{
                                likeRef.child(postID).child(userID).setValue(true);
                                testClick = false;
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
        // ###############################################################################################################


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Comments");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer cnt = 0 ;
                for (DataSnapshot postsnap: dataSnapshot.getChildren()) {
                    Comment comment1 = postsnap.getValue(Comment.class);
                    //Toast.makeText(PostDetails_Page.this,postID,Toast.LENGTH_SHORT).show();
                    if(Objects.equals(postID,comment1.getPostID())) {
                        cnt ++ ;
                    }
                }
                holder.commentcount.setText(Integer.toString(cnt));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        // ###############################################################################################################
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle , Content , username ;
        ImageView showUsericon , showLikeButton , showcomment ;
        TextView likecount , commentcount , DateTime ;
        public MyViewHolder(View itemView) {
            super(itemView);
            Content = itemView.findViewById(R.id.postContent) ;
            username = itemView.findViewById(R.id.userName) ;
            showUsericon = itemView.findViewById(R.id.showusericon) ;
            showLikeButton = itemView.findViewById(R.id.showlikebutton);
            likecount = itemView.findViewById(R.id.likecount) ;
            showcomment = itemView.findViewById(R.id.showcomment) ;
            commentcount = itemView.findViewById(R.id.commentcount) ;
            DateTime = itemView.findViewById(R.id.dateTime) ;
            showcomment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent postDetailActivity = new Intent(context,PostDetails_Page.class);
                    int position = getAdapterPosition();
                    postDetailActivity.putExtra("description",mData.get(position).getDescription());
                    postDetailActivity.putExtra("postID",mData.get(position).getPostKey());
                    postDetailActivity.putExtra("userID",mData.get(position).getUserId()) ;
                    postDetailActivity.putExtra("userRealname",mData.get(position).getUserRealname()) ;
                    context.startActivity(postDetailActivity);
                }
            });
        }
        public void getLikeButtonStatus(final String postkey, final String userid){
            DatabaseReference likeReference = FirebaseDatabase.getInstance().getReference("Likes");
            likeReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.child(postkey).hasChild(userid)){
                        int likecnt = (int)snapshot.child(postkey).getChildrenCount();
                        likecount.setText(String.valueOf(likecnt));
                        showLikeButton.setImageResource(R.drawable.afterlike);

                    }
                    else{
                        int likecnt = (int)snapshot.child(postkey).getChildrenCount();
                        likecount.setText(String.valueOf(likecnt));
                        showLikeButton.setImageResource(R.drawable.beforelike);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }
}
