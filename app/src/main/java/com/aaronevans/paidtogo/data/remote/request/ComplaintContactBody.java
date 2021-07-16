package com.aaronevans.paidtogo.data.remote.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ComplaintContactBody {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("description")
    @Expose
    private String description;

//    @SerializedName("media")
//    @Expose
//    private String media;
//
    @SerializedName("email")
    @Expose
    private String email;

    @Expose
    @SerializedName("media")
    private String photoURL;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public String getMedia() {
//        return media;
//    }
//
//    public void setMedia(String media) {
//        this.media = media;
//    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPhotoUrl() {
        return photoURL;
    }

    public ComplaintContactBody setPhoto(String photoSend) {
        this.photoURL = photoSend;
        return this;
    }


}