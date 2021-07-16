package com.aaronevans.paidtogo.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Infinix Android on 25/1/2017.
 */

public class PointRoute implements Parcelable {
    public static final Creator<PointRoute> CREATOR = new Creator<PointRoute>() {
        @Override
        public PointRoute createFromParcel(Parcel in) {
            return new PointRoute(in);
        }

        @Override
        public PointRoute[] newArray(int size) {
            return new PointRoute[size];
        }
    };
    @Expose
    @SerializedName("latitude")
    private String latitude = null;
    @Expose
    @SerializedName("longitude")
    private String longitude = null;
    @Expose
    @SerializedName("invisible")
    private String invisible = "0";
    private Date dateRegister = null;

    protected PointRoute(Parcel in) {
        latitude = in.readString();
        longitude = in.readString();
        invisible = in.readString();
    }

    public PointRoute() {

    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Date getDateRegister() {
        return dateRegister;
    }

    public void setDateRegister(Date dateRegister) {
        this.dateRegister = dateRegister;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(latitude);
        parcel.writeString(longitude);
        parcel.writeString(invisible);
    }
}
