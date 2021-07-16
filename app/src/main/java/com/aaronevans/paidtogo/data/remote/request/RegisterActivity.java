package com.aaronevans.paidtogo.data.remote.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Farhan Arshad on 6/7/2018.
 */

public class RegisterActivity {

    @Expose
    @SerializedName("pool_id")
    public String poolId;

    @Expose
    @SerializedName("activity_type")
    public String activityType;

    @Expose
    @SerializedName("access_token")
    public String accessToken;

    @Expose
    @SerializedName("start_date_time")
    public String startDateTime;

    @Expose
    @SerializedName("end_latitude")
    public String endLatitude;

    @Expose
    @SerializedName("end_longitude")
    public String endLongitude;

    @Expose
    @SerializedName("miles_traveled")
    public String milesTraveled;

    @Expose
    @SerializedName("start_latitude")
    public String startLatitude;

    @Expose
    @SerializedName("start_longitude")
    public String startLongitude;

    @Expose
    @SerializedName("total_steps")
    public String totalSteps;

    @Expose
    @SerializedName("end_date_time")
    public String endDateTime;

    public String getPoolId() {
        return poolId;
    }

    public void setPoolId(String poolId) {
        this.poolId = poolId;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
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

    public String getTotalSteps() {
        return totalSteps;
    }

    public void setTotalSteps(String totalSteps) {
        this.totalSteps = totalSteps;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }
}
