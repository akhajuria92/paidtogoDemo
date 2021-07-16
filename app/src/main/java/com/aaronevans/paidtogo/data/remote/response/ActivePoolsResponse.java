package com.aaronevans.paidtogo.data.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.aaronevans.paidtogo.data.entities.ActivePools;

import java.util.ArrayList;

/**
 * Created by Farhan Arshad on 6/4/2018.
 */

public class ActivePoolsResponse {
    @Expose
    @SerializedName("sponsorPools")
    private ArrayList<ActivePools> sponsorPools;

    @Expose
    @SerializedName("nationalPool")
    private ActivePools nationalPool;

    public ArrayList<ActivePools> getSponsorPools() {
        return sponsorPools;
    }

    public ActivePools getNationalPool() {
        return nationalPool;
    }
}
