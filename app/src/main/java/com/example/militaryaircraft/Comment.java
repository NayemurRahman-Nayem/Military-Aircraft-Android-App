package com.example.militaryaircraft;

public class Comment {

    private String postID ;
    private String userRealname ;
    private String val ;
    private String date ;
    private  String time ;

    private String userprofileUrl ;

    public void setUserprofileUrl(String userprofileUrl) {
        this.userprofileUrl = userprofileUrl;
    }

    public Comment(String postID, String username, String val , String date , String time , String userprofileUrl ) {
        this.postID = postID;
        this.userRealname = username;
        this.val = val;
        this.time = time ;
        this.userprofileUrl = userprofileUrl ;
        this.date = date ;
    }

    public String getDate() {
        return date;
    }

    public String getUserprofileUrl() {
        return userprofileUrl;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setUserRealname(String userRealname) {
        this.userRealname = userRealname;
    }

    public String getUserRealname() {
        return userRealname;
    }

    public Comment() {

    }
    public String getPostID() {
        return postID;
    }


    public String getVal() {
        return val;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }



    public void setVal(String val) {
        this.val = val;
    }
}
