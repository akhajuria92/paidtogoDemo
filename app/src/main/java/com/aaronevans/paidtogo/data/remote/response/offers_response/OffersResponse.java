
package com.aaronevans.paidtogo.data.remote.response.offers_response;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class OffersResponse {

    @SerializedName("result")
    private List<Result> mResult;



    @SerializedName("points")
    private Points mPoints;



    @SerializedName("spent_coins")
    Double spent_coins;


    public List<Result> getmResult() {
        return mResult;
    }

    public void setmResult(List<Result> mResult) {
        this.mResult = mResult;
    }

    public Double getSpent_coins() {
        return spent_coins;
    }

    public void setSpent_coins(Double spent_coins) {
        this.spent_coins = spent_coins;
    }

    public Points getmPoints() {
        return mPoints;
    }

    public void setmPoints(Points mPoints) {
        this.mPoints = mPoints;
    }

    public List<Result> getResult() {
        return mResult;
    }

    public void setResult(List<Result> result) {
        mResult = result;
    }

}
