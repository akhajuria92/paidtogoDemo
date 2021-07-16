package com.aaronevans.paidtogo.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Infinix Android on 19/1/2017.
 */



public class Balance {

    @SerializedName("earned_money")
    @Expose
    private Object earnedMoney;
    @SerializedName("earned_points")
    @Expose
    private double earnedPoints;

    public Object getEarnedMoney() {
        return earnedMoney;
    }

    public void setEarnedMoney(Object earnedMoney) {
        this.earnedMoney = earnedMoney;
    }

    public double getEarnedPoints() {
        return earnedPoints;
    }

    public void setEarnedPoints(double earnedPoints) {
        this.earnedPoints = earnedPoints;
    }

}
