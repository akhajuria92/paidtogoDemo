package com.aaronevans.paidtogo.data.remote.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.aaronevans.paidtogo.data.entities.Balance;
import com.aaronevans.paidtogo.data.entities.Pivot;
import com.aaronevans.paidtogo.data.entities.Sponsor;

public class BalanceResponse{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("banner")
    @Expose
    private String banner;
    @SerializedName("destination_latitude")
    @Expose
    private double destinationLatitude;
    @SerializedName("destination_longitude")
    @Expose
    private double destinationLongitude;
    @SerializedName("earned_money_per_mile")
    @Expose
    private double earnedMoneyPerMile;
    @SerializedName("created_date_time")
    @Expose
    private String createdDateTime;
    @SerializedName("end_date_time")
    @Expose
    private String endDateTime;
    @SerializedName("icon_photo")
    @Expose
    private String iconPhoto;
    @SerializedName("icon_photo_description")
    @Expose
    private String iconPhotoDescription;
    @SerializedName("limit_per_day")
    @Expose
    private Integer limitPerDay;
    @SerializedName("limit_per_month")
    @Expose
    private Integer limitPerMonth;
    @SerializedName("money_available")
    @Expose
    private Integer moneyAvailable;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("open")
    @Expose
    private Integer open;
    @SerializedName("quant_members_limit")
    @Expose
    private Integer quantMembersLimit;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("pool_type_id")
    @Expose
    private Integer poolTypeId;
    @SerializedName("sponsor_id")
    @Expose
    private Integer sponsorId;
    @SerializedName("terms_and_condition_id")
    @Expose
    private Integer termsAndConditionId;
    @SerializedName("radius")
    @Expose
    private Integer radius;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("origin")
    @Expose
    private String origin;
    @SerializedName("show_banner")
    @Expose
    private Integer showBanner;
    @SerializedName("national")
    @Expose
    private Integer national;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("rate_per_mile")
    @Expose
    private Integer ratePerMile;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("reward_type")
    @Expose
    private Integer rewardType;
    @SerializedName("reward_calculate")
    @Expose
    private Integer rewardCalculate;
    @SerializedName("set_monthly_goal")
    @Expose
    private Object setMonthlyGoal;
    @SerializedName("pool_lat")
    @Expose
    private Integer poolLat;
    @SerializedName("pool_lng")
    @Expose
    private Integer poolLng;
    @SerializedName("pool_radius")
    @Expose
    private String poolRadius;
    @SerializedName("pool_address")
    @Expose
    private String poolAddress;
    @SerializedName("city_location")
    @Expose
    private String cityLocation;
    @SerializedName("in_house_payment")
    @Expose
    private Integer inHousePayment;
    @SerializedName("balance")
    @Expose
    private Balance balance;
    @SerializedName("sponsors")
    @Expose
    private List<Sponsor> sponsors = null;
    @SerializedName("pivot")
    @Expose
    private Pivot pivot;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public double getDestinationLatitude() {
        return destinationLatitude;
    }

    public void setDestinationLatitude(double destinationLatitude) {
        this.destinationLatitude = destinationLatitude;
    }

    public double getDestinationLongitude() {
        return destinationLongitude;
    }

    public void setDestinationLongitude(double destinationLongitude) {
        this.destinationLongitude = destinationLongitude;
    }

    public Double getEarnedMoneyPerMile() {
        return earnedMoneyPerMile;
    }

    public void setEarnedMoneyPerMile(Double earnedMoneyPerMile) {
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

    public String getIconPhoto() {
        return iconPhoto;
    }

    public void setIconPhoto(String iconPhoto) {
        this.iconPhoto = iconPhoto;
    }

    public String getIconPhotoDescription() {
        return iconPhotoDescription;
    }

    public void setIconPhotoDescription(String iconPhotoDescription) {
        this.iconPhotoDescription = iconPhotoDescription;
    }

    public Integer getLimitPerDay() {
        return limitPerDay;
    }

    public void setLimitPerDay(Integer limitPerDay) {
        this.limitPerDay = limitPerDay;
    }

    public Integer getLimitPerMonth() {
        return limitPerMonth;
    }

    public void setLimitPerMonth(Integer limitPerMonth) {
        this.limitPerMonth = limitPerMonth;
    }

    public Integer getMoneyAvailable() {
        return moneyAvailable;
    }

    public void setMoneyAvailable(Integer moneyAvailable) {
        this.moneyAvailable = moneyAvailable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOpen() {
        return open;
    }

    public void setOpen(Integer open) {
        this.open = open;
    }

    public Integer getQuantMembersLimit() {
        return quantMembersLimit;
    }

    public void setQuantMembersLimit(Integer quantMembersLimit) {
        this.quantMembersLimit = quantMembersLimit;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Integer getPoolTypeId() {
        return poolTypeId;
    }

    public void setPoolTypeId(Integer poolTypeId) {
        this.poolTypeId = poolTypeId;
    }

    public Integer getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(Integer sponsorId) {
        this.sponsorId = sponsorId;
    }

    public Integer getTermsAndConditionId() {
        return termsAndConditionId;
    }

    public void setTermsAndConditionId(Integer termsAndConditionId) {
        this.termsAndConditionId = termsAndConditionId;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
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

    public Integer getShowBanner() {
        return showBanner;
    }

    public void setShowBanner(Integer showBanner) {
        this.showBanner = showBanner;
    }

    public Integer getNational() {
        return national;
    }

    public void setNational(Integer national) {
        this.national = national;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getRatePerMile() {
        return ratePerMile;
    }

    public void setRatePerMile(Integer ratePerMile) {
        this.ratePerMile = ratePerMile;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getRewardType() {
        return rewardType;
    }

    public void setRewardType(Integer rewardType) {
        this.rewardType = rewardType;
    }

    public Integer getRewardCalculate() {
        return rewardCalculate;
    }

    public void setRewardCalculate(Integer rewardCalculate) {
        this.rewardCalculate = rewardCalculate;
    }

    public Object getSetMonthlyGoal() {
        return setMonthlyGoal;
    }

    public void setSetMonthlyGoal(Object setMonthlyGoal) {
        this.setMonthlyGoal = setMonthlyGoal;
    }

    public Integer getPoolLat() {
        return poolLat;
    }

    public void setPoolLat(Integer poolLat) {
        this.poolLat = poolLat;
    }

    public Integer getPoolLng() {
        return poolLng;
    }

    public void setPoolLng(Integer poolLng) {
        this.poolLng = poolLng;
    }

    public String getPoolRadius() {
        return poolRadius;
    }

    public void setPoolRadius(String poolRadius) {
        this.poolRadius = poolRadius;
    }

    public String getPoolAddress() {
        return poolAddress;
    }

    public void setPoolAddress(String poolAddress) {
        this.poolAddress = poolAddress;
    }

    public String getCityLocation() {
        return cityLocation;
    }

    public void setCityLocation(String cityLocation) {
        this.cityLocation = cityLocation;
    }

    public Integer getInHousePayment() {
        return inHousePayment;
    }

    public void setInHousePayment(Integer inHousePayment) {
        this.inHousePayment = inHousePayment;
    }

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

    public List<Sponsor> getSponsors() {
        return sponsors;
    }

    public void setSponsors(List<Sponsor> sponsors) {
        this.sponsors = sponsors;
    }

    public Pivot getPivot() {
        return pivot;
    }

    public void setPivot(Pivot pivot) {
        this.pivot = pivot;
    }

}