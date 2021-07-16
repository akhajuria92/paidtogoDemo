package com.aaronevans.paidtogo.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Infinix Android on 17/1/2017.
 */

public class Activity implements Parcelable {

    public static final Creator<Activity> CREATOR = new Creator<Activity>() {
        @Override
        public Activity createFromParcel(Parcel in) {
            return new Activity(in);
        }

        @Override
        public Activity[] newArray(int size) {
            return new Activity[size];
        }
    };
    @Expose
    @SerializedName("id")
    private String id = null;
    @Expose
    @SerializedName("activity_id")
    private String activityId = null;
    @Expose
    @SerializedName("end_date_time")
    private String endDateTime = null;
    @Expose
    @SerializedName("end_latitude")
    private String endLatitude = null;
    @Expose
    @SerializedName("start_date_time")
    private String startDateTime = null;
    @Expose
    @SerializedName("start_latitude")
    private String startLatitude = null;
    @Expose
    @SerializedName("start_longitude")
    private String startLongitude = null;
    @Expose
    @SerializedName("pool_id")
    private String poolId = null;
    @Expose
    @SerializedName("user_id")
    private String userId = null;
    @Expose
    @SerializedName(value = "icon_photo", alternate = {"photo"})
    private String iconPhoto = null;
    @Expose
    @SerializedName("icon_photo_description")
    private String iconPhotoDescription = null;
    @Expose
    @SerializedName("name")
    private String name = null;
    @Expose
    @SerializedName("open")
    private String open = null;
    @Expose
    @SerializedName("earned_money")
    private String earnedMoney = null;
    @Expose
    @SerializedName("miles_traveled")
    private String milesTraveled = "0";
    @Expose
    @SerializedName("saved_co2")
    private String saveCo2 = "0";
    @Expose
    @SerializedName("saved_gas")
    private String saveGas = "0";
    @Expose
    @SerializedName("saved_calories")
    private String saveCalories = "0";
    @Expose
    @SerializedName("total_steps")
    private String totalSteps = "0";
    @Expose
    @SerializedName("activity_details")
    private ArrayList<ActivityDetail> activityDetail = new ArrayList<>();
    @Expose
    @SerializedName("pool")
    private Pool pool = null;
    @Expose
    @SerializedName("sum_of_step")
    private String sumOfStep = "0";
    private PoolType poolType = null;

    protected Activity(Parcel in) {
        id = in.readString();
        activityId = in.readString();
        endDateTime = in.readString();
        endLatitude = in.readString();
        startDateTime = in.readString();
        startLatitude = in.readString();
        startLongitude = in.readString();
        poolId = in.readString();
        userId = in.readString();
        iconPhoto = in.readString();
        iconPhotoDescription = in.readString();
        name = in.readString();
        open = in.readString();
        earnedMoney = in.readString();
        milesTraveled = in.readString();
        saveCo2 = in.readString();
        saveGas = in.readString();
        saveCalories = in.readString();
        totalSteps = in.readString();
        activityDetail = in.createTypedArrayList(ActivityDetail.CREATOR);
        pool = in.readParcelable(Pool.class.getClassLoader());
        sumOfStep = in.readString();
        poolType = in.readParcelable(PoolType.class.getClassLoader());
    }

    public static Creator<Activity> getCREATOR() {
        return CREATOR;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(activityId);
        dest.writeString(endDateTime);
        dest.writeString(endLatitude);
        dest.writeString(startDateTime);
        dest.writeString(startLatitude);
        dest.writeString(startLongitude);
        dest.writeString(poolId);
        dest.writeString(userId);
        dest.writeString(iconPhoto);
        dest.writeString(iconPhotoDescription);
        dest.writeString(name);
        dest.writeString(open);
        dest.writeString(earnedMoney);
        dest.writeString(milesTraveled);
        dest.writeString(saveCo2);
        dest.writeString(saveGas);
        dest.writeString(saveCalories);
        dest.writeString(totalSteps);
        dest.writeTypedList(activityDetail);
        dest.writeParcelable(pool, flags);
        dest.writeString(sumOfStep);
        dest.writeParcelable(poolType, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(String endLatitude) {
        this.endLatitude = endLatitude;
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

    public String getPoolId() {
        return poolId;
    }

    public void setPoolId(String poolId) {
        this.poolId = poolId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIconPhoto() {
        return iconPhoto;
    }

    public void setIconPhoto(String iconPhoto) {
        this.iconPhoto = iconPhoto;
    }

    public String getIconPhotoDescription() {
        return iconPhotoDescription;
    }

    public void setIconPhotoDescription(String iconPhotoDescription) {
        this.iconPhotoDescription = iconPhotoDescription;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getEarnedMoney() {
        return earnedMoney;
    }

    public void setEarnedMoney(String earnedMoney) {
        this.earnedMoney = earnedMoney;
    }

    public String getMilesTraveled() {
        return milesTraveled;
    }

    public void setMilesTraveled(String milesTraveled) {
        this.milesTraveled = milesTraveled;
    }

    public String getSaveCo2() {
        return saveCo2;
    }

    public void setSaveCo2(String saveCo2) {
        this.saveCo2 = saveCo2;
    }

    public String getSaveGas() {
        return saveGas;
    }

    public void setSaveGas(String saveGas) {
        this.saveGas = saveGas;
    }

    public String getSaveCalories() {
        return saveCalories;
    }

    public void setSaveCalories(String saveCalories) {
        this.saveCalories = saveCalories;
    }

    public String getTotalSteps() {
        return totalSteps;
    }

    public void setTotalSteps(String totalSteps) {
        this.totalSteps = totalSteps;
    }

    public ArrayList<ActivityDetail> getActivityDetail() {
        return activityDetail;
    }

    public void setActivityDetail(ArrayList<ActivityDetail> activityDetail) {
        this.activityDetail = activityDetail;
    }

    public Pool getPool() {
        return pool;
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    public String getSumOfStep() {
        return sumOfStep;
    }

    public void setSumOfStep(String sumOfStep) {
        this.sumOfStep = sumOfStep;
    }

    public PoolType getPoolType() {
        return poolType;
    }

    public void setPoolType(PoolType poolType) {
        this.poolType = poolType;
    }
}
