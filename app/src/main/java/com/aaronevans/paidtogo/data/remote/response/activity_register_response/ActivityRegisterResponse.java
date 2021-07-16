package com.aaronevans.paidtogo.data.remote.response.activity_register_response;

import java.util.List;

import com.aaronevans.paidtogo.data.remote.response.activity_register_response.Pool;
import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class ActivityRegisterResponse {



    @SerializedName("error")
    @Expose
    private String error;


    @SerializedName("activity_type")
    private Long mActivityType;
    @SerializedName("code")
    private String mCode;
    @SerializedName("detail")
    private String mDetail;
    @SerializedName("earned_points")
    private Double mEarnedPoints;
    @SerializedName("fraudulent")
    private String mFraudulent;
    @SerializedName("miles_traveled")
    private Double mMilesTraveled;
    @SerializedName("pools")
    private List<Pool> mPools;
    @SerializedName("remaining_limit")
    private Double mRemainingLimit;
    @SerializedName("save_traffic")
    private Long mSaveTraffic;
    @SerializedName("saved_calories")
    private Double mSavedCalories;
    @SerializedName("saved_co2")
    private Double mSavedCo2;

    public Long getActivityType() {
        return mActivityType;
    }

    public void setActivityType(Long activityType) {
        mActivityType = activityType;
    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        mCode = code;
    }

    public String getDetail() {
        return mDetail;
    }

    public void setDetail(String detail) {
        mDetail = detail;
    }

    public Double getEarnedPoints() {
        return mEarnedPoints;
    }

    public void setEarnedPoints(Double earnedPoints) {
        mEarnedPoints = earnedPoints;
    }

    public String getFraudulent() {
        return mFraudulent;
    }

    public void setFraudulent(String fraudulent) {
        mFraudulent = fraudulent;
    }

    public Double getMilesTraveled() {
        return mMilesTraveled;
    }

    public void setMilesTraveled(Double milesTraveled) {
        mMilesTraveled = milesTraveled;
    }

    public List<Pool> getPools() {
        return mPools;
    }

    public void setPools(List<Pool> pools) {
        mPools = pools;
    }

    public Double getRemainingLimit() {
        return mRemainingLimit;
    }

    public void setRemainingLimit(Double remainingLimit) {
        mRemainingLimit = remainingLimit;
    }

    public Long getSaveTraffic() {
        return mSaveTraffic;
    }

    public void setSaveTraffic(Long saveTraffic) {
        mSaveTraffic = saveTraffic;
    }

    public Double getSavedCalories() {
        return mSavedCalories;
    }

    public void setSavedCalories(Double savedCalories) {
        mSavedCalories = savedCalories;
    }

    public Double getSavedCo2() {
        return mSavedCo2;
    }

    public void setSavedCo2(Double savedCo2) {
        mSavedCo2 = savedCo2;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}