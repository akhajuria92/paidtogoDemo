package com.aaronevans.paidtogo.data.remote.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by evaristo on 26/12/16.
 */

public class FacebookBody {

    @Expose
    @SerializedName("email")
    String email;
    @Expose
    @SerializedName("first_name")
    String first_name;
    @Expose
    @SerializedName("last_name")
    String last_name;
    @Expose
    @SerializedName("fb_id")
    String fb_id;
    @Expose
    @SerializedName("image")
    String image;

    public FacebookBody(String email, String first_name, String last_name, String fb_id, String image) {
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.fb_id = fb_id;
        this.image = image;
    }
}
