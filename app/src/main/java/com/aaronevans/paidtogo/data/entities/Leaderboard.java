package com.aaronevans.paidtogo.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Infinix Android on 25/1/2017.
 */

public class Leaderboard implements Parcelable {
    public static final Creator<Leaderboard> CREATOR = new Creator<Leaderboard>() {
        @Override
        public Leaderboard createFromParcel(Parcel in) {
            return new Leaderboard(in);
        }

        @Override
        public Leaderboard[] newArray(int size) {
            return new Leaderboard[size];
        }
    };
    @Expose
    @SerializedName("user_id")
    private String userId = null;
    @Expose
    @SerializedName("first_name")
    private String firstName = null;
    @Expose
    @SerializedName("last_name")
    private String lastName = null;
    @Expose
    @SerializedName("profile_picture")
    private String profilePicture = null;
    @Expose
    @SerializedName("place")
    private String place = null;

    protected Leaderboard(Parcel in) {
        userId = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        profilePicture = in.readString();
        place = in.readString();
    }

    public Leaderboard() {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userId);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(profilePicture);
        parcel.writeString(place);
    }
}
