package com.aaronevans.paidtogo.data.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.aaronevans.paidtogo.data.entities.PointRoute;

import java.util.List;

/**
 * Created by Infinix Android on 25/1/2017.
 */

public class RouteResponse {
    @Expose
    @SerializedName("route")
    private List<PointRoute> listPoints = null;

    public List<PointRoute> getListPoints() {
        return listPoints;
    }

    public void setListPoints(List<PointRoute> listPoints) {
        this.listPoints = listPoints;
    }
}
