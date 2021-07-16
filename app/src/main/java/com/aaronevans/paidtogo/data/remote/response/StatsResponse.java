package com.aaronevans.paidtogo.data.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Infinix Android on 30/1/2017.
 */

public class StatsResponse {

        @SerializedName("earned_money")
        @Expose
        private float earnedMoney;
        @SerializedName("miles_traveled")
        @Expose
        private float milesTraveled;
        @SerializedName("saved_co2")
        @Expose
        private float savedCo2;
        @SerializedName("save_traffic")
        @Expose
        private float saveTraffic;
        @SerializedName("saved_calories")
        @Expose
        private float savedCalories;
        @SerializedName("earned_points")
        @Expose
        private float earnedPoints;
        @SerializedName("total_steps")
        @Expose
        private float totalSteps;
        @SerializedName("bike_miles")
        @Expose
        private float bikeMiles;
        @SerializedName("walk_miles")
        @Expose
        private float walkMiles;
        @SerializedName("gym_checkins")
        @Expose
        private float gymCheckins;

        public float getEarnedMoney() {
            return earnedMoney;
        }

        public void setEarnedMoney(float earnedMoney) {
            this.earnedMoney = earnedMoney;
        }

        public float getMilesTraveled() {
            return milesTraveled;
        }

        public void setMilesTraveled(float milesTraveled) {
            this.milesTraveled = milesTraveled;
        }

        public float getSavedCo2() {
            return savedCo2;
        }

        public void setSavedCo2(float savedCo2) {
            this.savedCo2 = savedCo2;
        }

        public float getSaveTraffic() {
            return saveTraffic;
        }

        public void setSaveTraffic(float saveTraffic) {
            this.saveTraffic = saveTraffic;
        }

        public float getSavedCalories() {
            return savedCalories;
        }

        public void setSavedCalories(float savedCalories) {
            this.savedCalories = savedCalories;
        }

        public float getEarnedPoints() {
            return earnedPoints;
        }

        public void setEarnedPoints(float earnedPoints) {
            this.earnedPoints = earnedPoints;
        }

        public float getTotalSteps() {
            return totalSteps;
        }

        public void setTotalSteps(float totalSteps) {
            this.totalSteps = totalSteps;
        }

        public float getBikeMiles() {
            return bikeMiles;
        }

        public void setBikeMiles(float bikeMiles) {
            this.bikeMiles = bikeMiles;
        }

        public float getWalkMiles() {
            return walkMiles;
        }

        public void setWalkMiles(float walkMiles) {
            this.walkMiles = walkMiles;
        }

        public float getGymCheckins() {
            return gymCheckins;
        }

        public void setGymCheckins(float gymCheckins) {
            this.gymCheckins = gymCheckins;
        }

    }