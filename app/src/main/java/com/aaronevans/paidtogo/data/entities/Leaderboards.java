package com.aaronevans.paidtogo.data.entities;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Leaderboards {

    @SerializedName("active_commute")
    @Expose
    private List<ActiveCommute> activeCommute = null;
    @SerializedName("exercise")
    @Expose
    private List<ActiveCommute> exercise = null;

    public List<ActiveCommute> getActiveCommute() {
        return activeCommute;
    }

    public void setActiveCommute(List<ActiveCommute> activeCommute) {
        this.activeCommute = activeCommute;
    }

    public List<ActiveCommute> getExercise() {
        return exercise;
    }

    public void setExercise(List<ActiveCommute> exercise) {
        this.exercise = exercise;
    }

}