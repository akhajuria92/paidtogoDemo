
package com.aaronevans.paidtogo.data.remote.response.balance_response;


import com.google.gson.annotations.SerializedName;


public class Paid {

    @SerializedName("all_earned_points")
    private Double mAllEarnedPoints;
    @SerializedName("month_earned_points")
    private Double mMonthEarnedPoints;

    public Double getAllEarnedPoints() {
        return mAllEarnedPoints;
    }

    public void setAllEarnedPoints(Double allEarnedPoints) {
        mAllEarnedPoints = allEarnedPoints;
    }

    public Double getMonthEarnedPoints() {
        return mMonthEarnedPoints;
    }

    public void setMonthEarnedPoints(Double monthEarnedPoints) {
        mMonthEarnedPoints = monthEarnedPoints;
    }

}
