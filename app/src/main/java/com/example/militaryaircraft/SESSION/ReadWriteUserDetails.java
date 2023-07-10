package com.example.militaryaircraft.SESSION;


import android.graphics.Bitmap;

public class ReadWriteUserDetails {


    public String fullName,email,doB,gender,mobile,username,division,district;
    Float ratingApp ;
    Bitmap bitmap ;



    public ReadWriteUserDetails() {

    };

    public ReadWriteUserDetails(String texFullname,String email , String textDoB, String textGender, String textMobile , String username , String division , String district , Float ratingApp) {

        this.fullName = texFullname ;
        this.doB = textDoB ;
        this.gender = textGender;
        this.mobile = textMobile;
        this.email = email ;
        this.username = username ;
        this.ratingApp = ratingApp ;
        this.division = division ;
        this.district = district ;

    }

    public String getDoB() {
        return doB;
    }
    public String getEmail() {
        return email;
    }
    public String getFullName() {
        return fullName;
    }
    public Float getRatingApp() { return ratingApp ; }
    public String getGender() {
        return gender;
    }
    public String getDivision() { return division ; }
    public String getDistrict() { return district ; }
    public String getMobile() {
        return mobile;
    }
    public String getUsername() { return username ; }
    public void setDoB(String doB) {
        this.doB = doB;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public void setUsername(String username) { this.username = username  ; }
    public void setDivision(String division ) { this.division = division ; }
    public void setRatingApp(Float ratingApp) { this.ratingApp = ratingApp ; }
    public void setDistrict(String district) { this.district = district ; }



}
