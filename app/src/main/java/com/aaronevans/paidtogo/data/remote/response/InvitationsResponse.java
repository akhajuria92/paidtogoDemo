package com.aaronevans.paidtogo.data.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.aaronevans.paidtogo.data.entities.Invitation;

import java.util.ArrayList;

/**
 * Created by Farhan Arshad on 5/10/2018.
 */

public class InvitationsResponse {
    @Expose
    @SerializedName("code")
    private String code = null;

    @Expose
    @SerializedName("Invitations")
    private ArrayList<Invitation> invitations = new ArrayList<>();

    public ArrayList<Invitation> getInvitations() {
        return invitations;
    }
}
