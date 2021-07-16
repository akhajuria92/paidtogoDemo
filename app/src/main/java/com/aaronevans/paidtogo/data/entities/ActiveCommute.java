package com.aaronevans.paidtogo.data.entities;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActiveCommute {

    @SerializedName("first_name")
    @Expose
    private String firstName="";

    @SerializedName("sum_prize")
    @Expose
    private String sum_prize;

    @SerializedName("potential_earning")
    @Expose
    private String potential_earning;

    @SerializedName("last_name")
    @Expose
    private String lastName="";
    @SerializedName("profile_picture")
    @Expose
    private String profilePicture;
    @SerializedName("earned_money")
    @Expose
    private Integer earnedMoney;
    @SerializedName("miles_traveled")
    @Expose
    private Double milesTraveled;
    @SerializedName("activities")
    @Expose
    private List<Object> activities = null;


    @SerializedName("id")
    @Expose
    private Integer id;






    @SerializedName("earned_points")
    @Expose
    private double earned_points;

    String position;


    public double getEarned_points() {
        return earned_points;
    }

    public void setEarned_points(double earned_points) {
        this.earned_points = earned_points;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Integer getEarnedMoney() {
        return earnedMoney;
    }

    public void setEarnedMoney(Integer earnedMoney) {
        this.earnedMoney = earnedMoney;
    }

    public Double getMilesTraveled() {
        return milesTraveled;
    }

    public void setMilesTraveled(Double milesTraveled) {
        this.milesTraveled = milesTraveled;
    }

    public List<Object> getActivities() {
        return activities;
    }

    public void setActivities(List<Object> activities) {
        this.activities = activities;
    }

    public String getPotential_earning() {
        return potential_earning;
    }

    public void setPotential_earning(String potential_earning) {
        this.potential_earning = potential_earning;
    }

    public String getSum_prize() {
        return sum_prize;
    }

    public void setSum_prize(String sum_prize) {
        this.sum_prize = sum_prize;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}