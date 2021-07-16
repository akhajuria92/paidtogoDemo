package com.aaronevans.paidtogo.webService.Firebase;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FcmRequest implements Serializable {

    @SerializedName("device_token")
    @Expose
    public String device_token;

    @SerializedName("user_id")
    @Expose
    public String user_id;

    @SerializedName("device_type")
    @Expose
    public String device_type;

}