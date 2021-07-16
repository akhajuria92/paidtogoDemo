package com.aaronevans.paidtogo.data.remote.request;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by evaristo on 08/02/17.
 */

@Parcel
public class ActivityBody implements Parcelable {

    public static final Creator<ActivityBody> CREATOR = new Creator<ActivityBody>() {
        @Override
        public ActivityBody createFromParcel(android.os.Parcel in) {
            return new ActivityBody(in);
        }

        @Override
        public ActivityBody[] newArray(int size) {
            return new ActivityBody[size];
        }
    };
    @Expose
    @SerializedName("pool_id")
    public String poolId;
    @Expose
    @SerializedName("access_token")
    public String accessToken;
    @Expose
    @SerializedName("start_date_time")
    public String startDateTime;
    @Expose
    @SerializedName("start_latitude")
    public String startLatitude;
    @Expose
    @SerializedName("start_longitude")
    public String startLongitude;
    @Expose
    @SerializedName("end_latitude")
    public String endLatitude;
    @Expose
    @SerializedName("end_longitude")
    public String endLongitude;
    @Expose
    @SerializedName("miles_traveled")
    public String milesTraveled;
    // public String photo;
    @Expose
    @SerializedName("activity_route")
    public String activityRoute;


    protected ActivityBody(android.os.Parcel in) {
        poolId = in.readString();
        accessToken = in.readString();
        startDateTime = in.readString();
        startLatitude = in.readString();
        startLongitude = in.readString();
        endLatitude = in.readString();
        endLongitude = in.readString();
        milesTraveled = in.readString();
        activityRoute = in.readString();
    }

    public ActivityBody() {

    }

    public String getActivityRoute() {
        return activityRoute;
    }

    public void setActivityRoute(String activityRoute) {
        this.activityRoute = activityRoute;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(String startLatitude) {
        this.startLatitude = startLatitude;
    }

    public String getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(String startLongitude) {
        this.startLongitude = startLongitude;
    }

    public String getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(String endLatitude) {
        this.endLatitude = endLatitude;
    }

    public String getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(String endLongitude) {
        this.endLongitude = endLongitude;
    }

    public String getMilesTraveled() {
        return milesTraveled;
    }

    public void setMilesTraveled(String milesTraveled) {
        this.milesTraveled = milesTraveled;
    }

    public String getPoolId() {
        return poolId;
    }

    public void setPoolId(String poolId) {
        this.poolId = poolId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(poolId);
        parcel.writeString(accessToken);
        parcel.writeString(startDateTime);
        parcel.writeString(startLatitude);
        parcel.writeString(startLongitude);
        parcel.writeString(endLatitude);
        parcel.writeString(endLongitude);
        parcel.writeString(milesTraveled);
        parcel.writeString(activityRoute);
    }
}
