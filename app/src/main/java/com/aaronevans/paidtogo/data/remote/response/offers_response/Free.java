package com.aaronevans.paidtogo.data.remote.response.offers_response;

import com.google.gson.annotations.SerializedName;

public class Free {

    @SerializedName("all_earned_points")
    private Double mAllEarnedPoints;


    public Double getAllEarnedPoints() {
        return mAllEarnedPoints;
    }

    public void setAllEarnedPoints(Double allEarnedPoints) {
        mAllEarnedPoints = allEarnedPoints;
    }
}



