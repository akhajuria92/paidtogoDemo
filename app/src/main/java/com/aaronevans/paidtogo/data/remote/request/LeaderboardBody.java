package com.aaronevans.paidtogo.data.remote.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Infinix Android on 25/1/2017.
 */

public class LeaderboardBody {
    @Expose
    @SerializedName("user_id")
    private String userId = null;
    @Expose
    @SerializedName("pool_id")
    private String poolId = null;

    public void setPoolId(String poolId) {
        this.poolId = poolId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
