
package com.aaronevans.paidtogo.data.remote.request.earned_points;

import android.content.Intent;

import com.google.gson.annotations.SerializedName;

public class EarnedPoints {

    @SerializedName("code")
    private String mCode;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("points")
    private int mPoints;

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        mCode = code;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Integer getPoints() {
        return mPoints;
    }

    public void setPoints(Integer points) {
        mPoints = points;
    }

}
