package com.example.militaryaircraft.Blogs;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView ;
import com.bumptech.glide.Glide ;
import com.example.militaryaircraft.R ;
import java.util.ArrayList ;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {
    Context context ;
    ArrayList<Comment> mData ;
    public CommentAdapter(Context context, ArrayList<Comment> mData) {
        this.context = context ;
        this.mData = mData ;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(context).inflate(R.layout.comment_listview,parent,false) ;
        return new MyViewHolder(row) ;
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.Content.setText(mData.get(position).getVal()) ;
        holder.username.setText(mData.get(position).getUserRealname()) ;
        //Toast.makeText(this.context,mData.get(position).getUserRealname(),Toast.LENGTH_LONG).show() ;
        holder.datetime.setText(mData.get(position).getDate() + "  " + mData.get(position).getTime() ) ;
        if(mData.get(position).getUserprofileUrl()!="NULL") {
            Glide
                    .with(holder.userImage.getContext())
                    .load(mData.get(position).getUserprofileUrl())
                    .circleCrop()
                    .into(holder.userImage);
        }
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView  Content , username , datetime ;
        ImageView userImage ;
        public MyViewHolder(View itemView) {
            super(itemView);
            Content = itemView.findViewById(R.id.postContent) ;
            username = itemView.findViewById(R.id.userName) ;
            datetime = itemView.findViewById(R.id.dateTime) ;
            userImage = itemView.findViewById(R.id.showusericon)  ;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent postDetailActivity = new Intent(context,PostDetails_Page.class);
//                    int position = getAdapterPosition();
//                    postDetailActivity.putExtra("description",mData.get(position).getDescription());
//                    postDetailActivity.putExtra("postID",mData.get(position).getPostKey());
//                    postDetailActivity.putExtra("userID",mData.get(position).getUserId()) ;
//                    //postDetailActivity.putExtra("postKey",mData.get(position).getPostKey());
//                    // will fix this later i forgot to add user name to post object
//                    //postDetailActivity.putExtra("userName",mData.get(position).getUsername);
//                    context.startActivity(postDetailActivity);
                }
            });
        }
    }
}
