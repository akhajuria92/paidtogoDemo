package com.aaronevans.paidtogo.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.aaronevans.paidtogo.data.entities.contracts.UserContract;

/**
 * Created by evaristo on 20/12/16.
 */

public class User implements UserContract {


    @Expose(serialize = false)
    @SerializedName("code")
    private String code;


    @Expose(serialize = false)
    @SerializedName("message")
    private String message;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getPassword() {
        return password;
    }

    @Expose(serialize = false)
    @SerializedName("user_id")
    private String id;





    @Expose
    @SerializedName("profile_picture")
    private String photoURL;

    @Expose(serialize = false)
    @SerializedName("access_token")
    private String accessToken;
    @Expose
    private String email;
    @Expose
    @SerializedName("first_name")
    private String firstName;
    @Expose
    @SerializedName("last_name")
    private String lastName;
    @Expose
    private String password;
    @Expose
    private String bio;

    @SerializedName("app_type")
    @Expose
    private Integer appType;




    @SerializedName("subscription_id")
    @Expose
    private Integer subscription_id;

    public Integer getSubscription_id() {
        return subscription_id;
    }

    public void setSubscription_id(Integer subscription_id) {
        this.subscription_id = subscription_id;
    }

    private String age;
    private String dollar;
    private String point;


    public Integer getAppType() {
        return appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
    }

    public User withAppType(Integer appType) {
        this.appType = appType;
        return this;
    }



    public String getDollar() {
        return dollar;
    }

    public void setDollar(String dollar) {
        this.dollar = dollar;

    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    private String gender;

    private boolean isForExcercise;
    private boolean isWork;
    private boolean isChanging;

    private boolean isWalkRun;
    private boolean isBike;
    private boolean isBusTrain;

    public String getAge() {
        return age != null ? age : "";
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender != null ? gender : "";
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isForExcercise() {
        return isForExcercise;
    }

    public void setForExcercise(boolean forExcercise) {
        isForExcercise = forExcercise;
    }

    public boolean isWork() {
        return isWork;
    }

    public void setWork(boolean work) {
        isWork = work;
    }

    public boolean isChanging() {
        return isChanging;
    }

    public void setChanging(boolean changing) {
        isChanging = changing;
    }

    public boolean isWalkRun() {
        return isWalkRun;
    }

    public void setWalkRun(boolean walkRun) {
        isWalkRun = walkRun;
    }

    public boolean isBike() {
        return isBike;
    }

    public void setBike(boolean bike) {
        isBike = bike;
    }

    public boolean isBusTrain() {
        return isBusTrain;
    }

    public void setBusTrain(boolean busTrain) {
        isBusTrain = busTrain;
    }


    @Override
    public String getPhotoUrl() {
        return photoURL;
    }

    @Override
    public User setPhoto(String photoSend) {
        this.photoURL = photoSend;
        return this;
    }

    @Override
    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public User setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public User setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }


    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Override
    public String getBio() {
        return bio;
    }

    @Override
    public User setBio(String bio) {
        this.bio = bio;
        return this;
    }

    @Override
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
