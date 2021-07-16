package com.aaronevans.paidtogo.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Farhan Arshad on 6/4/2018.
 */

public class PoolsActivities {

    @Expose
    @SerializedName("id")
    private String id;

    @Expose
    @SerializedName("pool_id")
    private String poolId;

    @Expose
    @SerializedName("type_id")
    private String typeId;

    @Expose
    @SerializedName("created_at")
    private String createdAt;

    @Expose
    @SerializedName("updated_at")
    private String updatedAt;
}
