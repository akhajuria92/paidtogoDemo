package com.aaronevans.paidtogo.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Farhan Arshad on 5/10/2018.
 */

public class Invitation {

    @Expose
    private String id;

    @Expose
    private int accepted;

    @Expose
    @SerializedName("date_time")
    private String dataTime;

    @Expose
    private String message;

    @Expose
    private String users;

    @Expose
    @SerializedName("pool_id")
    private String poolid;

    @Expose
    @SerializedName("created_at")
    private String createdAt;

    @Expose
    @SerializedName("updated_at")
    private String updatedAt;

    @Expose
    @SerializedName("user_id")
    private String userId;

    @Expose
    private User user;

    @Expose
    private Pool pool;

    public Pool getPool() {
        return pool;
    }

    public String getId() {
        return id;
    }
}
