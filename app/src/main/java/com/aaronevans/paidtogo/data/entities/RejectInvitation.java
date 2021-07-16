package com.aaronevans.paidtogo.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Farhan Arshad on 5/10/2018.
 */

public class RejectInvitation {

    @Expose
    @SerializedName("user_id")
    private String userId;

    @Expose
    @SerializedName("pool_id")
    private String invitationId;

    public RejectInvitation(String userId, String invitationId) {
        this.userId = userId;
        this.invitationId = invitationId;
    }
}
