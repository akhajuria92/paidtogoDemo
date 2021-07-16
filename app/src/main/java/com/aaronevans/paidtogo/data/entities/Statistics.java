package com.aaronevans.paidtogo.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Farhan Arshad on 6/4/2018.
 */

public class Statistics {
    @Expose
    @SerializedName("earned_money")
    private long earnedMoney;

    @Expose
    @SerializedName("milesTraveled")
    private String milesTraveled = "0";

    @Expose
    @SerializedName("saved_calories")
    private String savedCalories;

    @Expose
    @SerializedName("saved_co2")
    private String savedCo2;

    @Expose
    @SerializedName("saved_gas")
    private String savedGas;

    @Expose
    @SerializedName("total_steps")
    private String totalSteps;

    public long getEarnedMoney() {
        return earnedMoney;
    }

    public void setEarnedMoney(long earnedMoney) {
        this.earnedMoney = earnedMoney;
    }

    public String getMilesTraveled() {
        return milesTraveled;
    }

    public void setMilesTraveled(String milesTraveled) {
        this.milesTraveled = milesTraveled;
    }

    public String getSavedCalories() {
        return savedCalories;
    }

    public void setSavedCalories(String savedCalories) {
        this.savedCalories = savedCalories;
    }

    public String getSavedCo2() {
        return savedCo2;
    }

    public void setSavedCo2(String savedCo2) {
        this.savedCo2 = savedCo2;
    }

    public String getSavedGas() {
        return savedGas;
    }

    public void setSavedGas(String savedGas) {
        this.savedGas = savedGas;
    }

    public String getTotalSteps() {
        return totalSteps;
    }

    public void setTotalSteps(String totalSteps) {
        this.totalSteps = totalSteps;
    }
}
