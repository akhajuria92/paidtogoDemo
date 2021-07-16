package com.aaronevans.paidtogo.data.remote.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActivityRegister {

    @SerializedName("activity_type")
    @Expose
    public String activityType;
    @SerializedName("access_token")
    @Expose
    public String accessToken;
    @SerializedName("start_date_time")
    @Expose
    public String startDateTime;
    @SerializedName("end_latitude")
    @Expose
    public String endLatitude;
    @SerializedName("end_longitude")
    @Expose
    public String endLongitude;
    @SerializedName("miles_traveled")
    @Expose
    public String milesTraveled;
    @SerializedName("start_latitude")
    @Expose
    public String startLatitude;
    @SerializedName("start_longitude")
    @Expose
    public String startLongitude;
    @SerializedName("total_steps")
    @Expose
    public String totalSteps;
    @SerializedName("end_date_time")
    @Expose
    public String endDateTime;
    @SerializedName("calories")
    @Expose
    public String calories;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;

    public ActivityRegister() {

    }

    public ActivityRegister(String activityType, String accessToken, String startDateTime, String endLatitude, String endLongitude, String milesTraveled, String startLatitude, String startLongitude, String totalSteps, String endDateTime, String calories, String createdAt, String updatedAt) {
        this.activityType = activityType;
        this.accessToken = accessToken;
        this.startDateTime = startDateTime;
        this.endLatitude = endLatitude;
        this.endLongitude = endLongitude;
        this.milesTraveled = milesTraveled;
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.totalSteps = totalSteps;
        this.endDateTime = endDateTime;
        this.calories = calories;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}