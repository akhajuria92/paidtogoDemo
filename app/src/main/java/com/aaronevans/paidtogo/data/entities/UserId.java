package com.aaronevans.paidtogo.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Farhan Arshad on 5/10/2018.
 */

public class UserId {

    @Expose
    @SerializedName("user_id")
    private String userId;

    public UserId(String userId) {
        this.userId= userId;
    }
}
