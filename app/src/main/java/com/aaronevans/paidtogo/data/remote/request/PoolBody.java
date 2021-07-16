package com.aaronevans.paidtogo.data.remote.request;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by evaristo on 01/02/17.
 */

public class PoolBody {

    @Expose
    @SerializedName("pool_type_id")
    String id;

    @Expose
    @SerializedName("location_lat")
    double locationLat;

    @Expose
    @SerializedName("location_lon")
    double locationLong;

    public PoolBody(String id) {
        this.id = id;
    }

    public PoolBody(String id, double locationLat, double locationLong) {
        this.id = id;
        this.locationLat = locationLat;
        this.locationLong = locationLong;
    }
}
