package com.example.militaryaircraft.Blogs;
import android.media.Image;
import android.net.Uri;
import com.google.firebase.database.ServerValue;

public class  Post {

    private String postKey;
    private Integer like ;
    private  Integer dislike ;
    private String description , pictureURL ;
    private String userId;
    private String username ;
    private  String userprofilephoto ;
    private  String userRealname ;
    private  String postDate , postTime ;
    public Post( String description, String userId , Integer like , Integer dislike , String username , String userRealname , String postDate , String postTime , String userprofilephoto ) {
        this.description = description;
        this.userId = userId;
        this.like = like ;
        this.username = username ;
        this.dislike = dislike ;
        this.userprofilephoto = userprofilephoto ;
        this.userRealname = userRealname ;
        this.postDate = postDate ;
        this.postTime = postTime ;
    }
    public void setUserprofilephoto(String userprofilephoto) {
        this.userprofilephoto = userprofilephoto;
    }
    public String getUserprofilephoto() {
        return userprofilephoto;
    }
    // make sure to have an empty constructor inside ur model class
    public Post() {
    }
    public String getUsername() {
        return username;
    }
    public String getPostKey() {
        return postKey;
    }
    public Integer getDislike() {
        return dislike;
    }
    public Integer getLike() {
        return like;
    }
    public String getUserRealname() {
        return userRealname;
    }
    public void setUserRealname(String userRealname) {
        this.userRealname = userRealname;
    }
    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }
    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }
    public String getPostDate() {
        return postDate;
    }
    public String getPostTime() {
        return postTime;
    }
    public void setPostKey(String postKey) {
        this.postKey = postKey;
    }
    public String getDescription() {
        return description;
    }
    public String getUserId() {
        return userId;
    }
    // public Object getTimeStamp() {return timeStamp;}
    public void setDescription(String description) {
        this.description = description;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    //public void setTimeStamp(Object timeStamp) {this.timeStamp = timeStamp;}
    public void setUsername(String username) {
        this.username = username;
    }
    public void setDislike(Integer dislike) {
        this.dislike = dislike;
    }
    public void setLike(Integer like) {
        this.like = like;
    }
}
