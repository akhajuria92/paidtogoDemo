package com.aaronevans.paidtogo.data.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActivitiesResponse {

    @SerializedName("earned_money")
    @Expose
    private Float earnedMoney;
    @SerializedName("miles_traveled")
    @Expose
    private Double milesTraveled;
    @SerializedName("saved_co2")
    @Expose
    private Double savedCo2;
    @SerializedName("save_traffic")
    @Expose
    private Double saveTraffic;
    @SerializedName("saved_calories")
    @Expose
    private Double savedCalories;
    @SerializedName("earned_points")
    @Expose
    private Double earnedPoints;
    @SerializedName("total_steps")
    @Expose
    private String totalSteps;
    @SerializedName("bike_miles")
    @Expose
    private Double bikeMiles;
    @SerializedName("walk_miles")
    @Expose
    private Double walkMiles;
    @SerializedName("gym_checkins")
    @Expose
    private Double gymCheckins;

    public Float getEarnedMoney() {
        return earnedMoney;
    }

    public void setEarnedMoney(Float earnedMoney) {
        this.earnedMoney = earnedMoney;
    }

    public Double getMilesTraveled() {
        return milesTraveled;
    }

    public void setMilesTraveled(Double milesTraveled) {
        this.milesTraveled = milesTraveled;
    }

    public Double getSavedCo2() {
        return savedCo2;
    }

    public void setSavedCo2(Double savedCo2) {
        this.savedCo2 = savedCo2;
    }

    public Double getSaveTraffic() {
        return saveTraffic;
    }

    public void setSaveTraffic(Double saveTraffic) {
        this.saveTraffic = saveTraffic;
    }

    public Double getSavedCalories() {
        return savedCalories;
    }

    public void setSavedCalories(Double savedCalories) {
        this.savedCalories = savedCalories;
    }

    public Double getEarnedPoints() {
        return earnedPoints;
    }

    public void setEarnedPoints(Double earnedPoints) {
        this.earnedPoints = earnedPoints;
    }

    public String getTotalSteps() {
        return totalSteps;
    }

    public void setTotalSteps(String totalSteps) {
        this.totalSteps = totalSteps;
    }

    public Double getBikeMiles() {
        return bikeMiles;
    }

    public void setBikeMiles(Double bikeMiles) {
        this.bikeMiles = bikeMiles;
    }

    public Double getWalkMiles() {
        return walkMiles;
    }

    public void setWalkMiles(Double walkMiles) {
        this.walkMiles = walkMiles;
    }

    public Double getGymCheckins() {
        return gymCheckins;
    }

    public void setGymCheckins(Double gymCheckins) {
        this.gymCheckins = gymCheckins;
    }

}
