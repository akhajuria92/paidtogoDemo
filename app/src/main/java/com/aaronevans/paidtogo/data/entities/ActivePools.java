package com.aaronevans.paidtogo.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Farhan Arshad on 6/4/2018.
 */

public class ActivePools {
    @Expose
    @SerializedName("id")
    private String id = null;

    @Expose
    @SerializedName("banner")
    private String banner = null;

    @Expose
    @SerializedName("destination_latitude")
    private String destinationLatitude = null;

    @Expose
    @SerializedName("destination_longitude")
    private String destinationLongitude = null;

    @Expose
    @SerializedName("earned_money_per_mile")
    private String earnedMoneyPerMile = null;

    @Expose
    @SerializedName("created_date_time")
    private String createdDateTime = null;

    @Expose
    @SerializedName("end_date_time")
    private String endDateTime = null;

    @Expose
    @SerializedName("icon_photo")
    private String iconphoto = null;

    @Expose
    @SerializedName("icon_photo_description")
    private String iconPhotoDescription = null;

    @Expose
    @SerializedName("limit_per_day")
    private String limitPerDay = null;

    @Expose
    @SerializedName("limit_per_month")
    private String limitPerMonth = null;

    @Expose
    @SerializedName("money_available")
    private String moneyAvailable = null;

    @Expose
    @SerializedName("name")
    private String name = null;

    @Expose
    @SerializedName("open")
    private String open = null;

    @Expose
    @SerializedName("quant_members_limit")
    private String quantMembersLimit = null;

    @Expose
    @SerializedName("start_date")
    private String startDate = null;

    @Expose
    @SerializedName("pool_type_id")
    private String poolTypeId = null;

    @Expose
    @SerializedName("sponsor_id")
    private String sponsorId = null;

    @Expose
    @SerializedName("terms_and_condition_id")
    private String termsAndConditionId = null;

    @Expose
    @SerializedName("radius")
    private String radius = null;

    @Expose
    @SerializedName("country")
    private String country = null;


    @Expose
    @SerializedName("state")
    private String state = null;


    @Expose
    @SerializedName("origin")
    private String origin = null;


    @Expose
    @SerializedName("show_banner")
    private String showBanner = null;


    @Expose
    @SerializedName("national")
    private String national = null;


    @Expose
    @SerializedName("type")
    private String type = null;


    @Expose
    @SerializedName("rate_per_mile")
    private String ratePerMile = null;


    @Expose
    @SerializedName("user_id")
    private String userId = null;


    @Expose
    @SerializedName("link")
    private String link = null;


    @Expose
    @SerializedName("reward_type")
    private String rewardType = null;


    @Expose
    @SerializedName("reward_calculate")
    private String rewardCalculate = null;


    @Expose
    @SerializedName("set_monthly_goal")
    private String setMonthlyGoal = null;


    @Expose
    @SerializedName("is_national")
    private int isNational;


    @Expose
    @SerializedName("statistics")
    private Statistics statistics = null;


    @Expose
    @SerializedName("activities")
    private ArrayList<Activity> activities = new ArrayList<>();

    @Expose
    @SerializedName("pool_allow_activities")
    private ArrayList<PoolsActivities> poolAllowActivities = new ArrayList<>();

    @Expose
    @SerializedName("pool_monthly_goals")
    private ArrayList<PoolsActivities> poolMonthlyGoals = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBanner() {
        return "https://www.paidtogo.com/images/pools/" + banner;// do need to remove static path
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getDestinationLatitude() {
        return destinationLatitude;
    }

    public void setDestinationLatitude(String destinationLatitude) {
        this.destinationLatitude = destinationLatitude;
    }

    public String getDestinationLongitude() {
        return destinationLongitude;
    }

    public void setDestinationLongitude(String destinationLongitude) {
        this.destinationLongitude = destinationLongitude;
    }

    public String getEarnedMoneyPerMile() {
        return earnedMoneyPerMile;
    }

    public void setEarnedMoneyPerMile(String earnedMoneyPerMile) {
        this.earnedMoneyPerMile = earnedMoneyPerMile;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getIconphoto() {
        return iconphoto;
    }

    public void setIconphoto(String iconphoto) {
        this.iconphoto = iconphoto;
    }

    public String getIconPhotoDescription() {
        return iconPhotoDescription;
    }

    public void setIconPhotoDescription(String iconPhotoDescription) {
        this.iconPhotoDescription = iconPhotoDescription;
    }

    public String getLimitPerDay() {
        return limitPerDay;
    }

    public void setLimitPerDay(String limitPerDay) {
        this.limitPerDay = limitPerDay;
    }

    public String getLimitPerMonth() {
        return limitPerMonth;
    }

    public void setLimitPerMonth(String limitPerMonth) {
        this.limitPerMonth = limitPerMonth;
    }

    public String getMoneyAvailable() {
        return moneyAvailable;
    }

    public void setMoneyAvailable(String moneyAvailable) {
        this.moneyAvailable = moneyAvailable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getQuantMembersLimit() {
        return quantMembersLimit;
    }

    public void setQuantMembersLimit(String quantMembersLimit) {
        this.quantMembersLimit = quantMembersLimit;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getPoolTypeId() {
        return poolTypeId;
    }

    public void setPoolTypeId(String poolTypeId) {
        this.poolTypeId = poolTypeId;
    }

    public String getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(String sponsorId) {
        this.sponsorId = sponsorId;
    }

    public String getTermsAndConditionId() {
        return termsAndConditionId;
    }

    public void setTermsAndConditionId(String termsAndConditionId) {
        this.termsAndConditionId = termsAndConditionId;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getShowBanner() {
        return showBanner;
    }

    public void setShowBanner(String showBanner) {
        this.showBanner = showBanner;
    }

    public String getNational() {
        return national;
    }

    public void setNational(int national) {
        isNational = national;
    }

    public void setNational(String national) {
        this.national = national;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRatePerMile() {
        return ratePerMile;
    }

    public void setRatePerMile(String ratePerMile) {
        this.ratePerMile = ratePerMile;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getRewardType() {
        return rewardType;
    }

    public void setRewardType(String rewardType) {
        this.rewardType = rewardType;
    }

    public String getRewardCalculate() {
        return rewardCalculate;
    }

    public void setRewardCalculate(String rewardCalculate) {
        this.rewardCalculate = rewardCalculate;
    }

    public String getSetMonthlyGoal() {
        return setMonthlyGoal;
    }

    public void setSetMonthlyGoal(String setMonthlyGoal) {
        this.setMonthlyGoal = setMonthlyGoal;
    }

    public int isNational() {
        return isNational;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    public ArrayList<Activity> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<Activity> activities) {
        this.activities = activities;
    }

    public ArrayList<PoolsActivities> getPoolAllowActivities() {
        return poolAllowActivities;
    }

    public void setPoolAllowActivities(ArrayList<PoolsActivities> poolAllowActivities) {
        this.poolAllowActivities = poolAllowActivities;
    }

    public ArrayList<PoolsActivities> getPoolMonthlyGoals() {
        return poolMonthlyGoals;
    }

    public void setPoolMonthlyGoals(ArrayList<PoolsActivities> poolMonthlyGoals) {
        this.poolMonthlyGoals = poolMonthlyGoals;
    }
}
