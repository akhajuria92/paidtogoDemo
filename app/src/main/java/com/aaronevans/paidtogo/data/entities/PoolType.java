package com.aaronevans.paidtogo.data.entities;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by Infinix Android on 26/1/2017.
 */

@Parcel
public class PoolType implements Parcelable {
    public static final Creator<PoolType> CREATOR = new Creator<PoolType>() {
        @Override
        public PoolType createFromParcel(android.os.Parcel in) {
            return new PoolType(in);
        }

        @Override
        public PoolType[] newArray(int size) {
            return new PoolType[size];
        }
    };
    @Expose
    @SerializedName("name")
    public String name = null;
    @Expose
    @SerializedName("id")
    public String id = null;
    @Expose
    @SerializedName("color")
    public String color = null;
    @Expose
    @SerializedName("background_picture")
    public String backgroundPicture = null;
    @Expose
    @SerializedName("gps_tracking_required")
    public String gpsTrackingRequired = null;
    @Expose
    @SerializedName("photo_verification_required")
    public String photoVerificationRequired = null;
    @Expose
    @SerializedName("max_speed")
    public String maxSpeed = null;
    @Expose
    @SerializedName("minSpeed")
    public String minSpeed = null;

    protected PoolType(android.os.Parcel in) {
        name = in.readString();
        id = in.readString();
        color = in.readString();
        backgroundPicture = in.readString();
        gpsTrackingRequired = in.readString();
        photoVerificationRequired = in.readString();
        maxSpeed = in.readString();
        minSpeed = in.readString();
    }

    public PoolType() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBackgroundPicture() {
        return backgroundPicture;
    }

    public void setBackgroundPicture(String backgroundPicture) {
        this.backgroundPicture = backgroundPicture;
    }

    public String getGpsTrackingRequired() {
        return gpsTrackingRequired;
    }

    public void setGpsTrackingRequired(String gpsTrackingRequired) {
        this.gpsTrackingRequired = gpsTrackingRequired;
    }

    public String getPhotoVerificationRequired() {
        return photoVerificationRequired;
    }

    public void setPhotoVerificationRequired(String photoVerificationRequired) {
        this.photoVerificationRequired = photoVerificationRequired;
    }

    public String getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(String maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public String getMinSpeed() {
        return minSpeed;
    }

    public void setMinSpeed(String minSpeed) {
        this.minSpeed = minSpeed;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(id);
        parcel.writeString(color);
        parcel.writeString(backgroundPicture);
        parcel.writeString(gpsTrackingRequired);
        parcel.writeString(photoVerificationRequired);
        parcel.writeString(maxSpeed);
        parcel.writeString(minSpeed);
    }
}
