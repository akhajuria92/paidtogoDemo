package com.aaronevans.paidtogo.data.remote.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Infinix Android on 10/1/2017.
 */

public class UpdateProfile {
    @Expose
    @SerializedName("access_token")
    String accessToken = "";
    @Expose
    @SerializedName("email")
    String email = "";
    @Expose
    @SerializedName("first_name")
    String firstName = "";
    @Expose
    @SerializedName("last_name")
    String lastName = "";
    @Expose
    @SerializedName("bio")
    String bio = "";
    @Expose
    @SerializedName("profile_picture")
    String profilePicture = "";
    @Expose
    @SerializedName("paypal_account")
    String paypalAccount = "";


    public UpdateProfile() {
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getPaypalAccount() {
        return paypalAccount;
    }

    public void setPaypalAccount(String paypalAccount) {
        this.paypalAccount = paypalAccount;
    }
}
