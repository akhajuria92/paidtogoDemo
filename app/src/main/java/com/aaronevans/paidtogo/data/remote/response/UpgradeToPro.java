
package com.aaronevans.paidtogo.data.remote.response;
import com.google.gson.annotations.SerializedName;

public class UpgradeToPro {

    @SerializedName("message")
    private String mMessage;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

}
