package com.aaronevans.paidtogo.data.remote.response;

import com.google.gson.annotations.SerializedName;

public class SuccessfullUnitUpdatResponse {

    @SerializedName("code")
    private String mCode;
    @SerializedName("message")
    private String mMessage;

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

}
