package com.aaronevans.paidtogo.data.remote.response;

import com.google.gson.annotations.Expose;
import com.aaronevans.paidtogo.data.entities.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Infinix Android on 20/1/2017.
 */

public class ActivityResponse {
    @Expose
    private List<Object> listActivities = null;

    private List<Activity> activities = null;

    public List<Object> getLisActivities() {
        return listActivities;
    }

    public void setLisActivities(List<Object> listActivities) {
        this.listActivities = listActivities;
    }

    public List<Activity> getActivities() {
        activities = new ArrayList<>();
        for (int i = 0; i < listActivities.size(); i++) {
            try {
                if (listActivities.get(i) instanceof Activity) {
                    activities.add((Activity) listActivities.get(i));
                }
            } catch (Exception exc) {

            }
        }
        return activities;
    }
}
