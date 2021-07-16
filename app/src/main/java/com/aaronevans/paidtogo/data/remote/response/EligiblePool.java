package com.aaronevans.paidtogo.data.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EligiblePool {

@SerializedName("id")
@Expose
private Double id;
@SerializedName("banner")
@Expose
private String banner;
@SerializedName("destination_latitude")
@Expose
private Object destinationLatitude;
@SerializedName("destination_longitude")
@Expose
private Object destinationLongitude;
@SerializedName("earned_money_per_mile")
@Expose
private Double earnedMoneyPerMile;
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
private Double limitPerDay;
@SerializedName("limit_per_month")
@Expose
private Double limitPerMonth;
@SerializedName("money_available")
@Expose
private Double moneyAvailable;
@SerializedName("name")
@Expose
private String name;
@SerializedName("open")
@Expose
private Double open;
@SerializedName("quant_members_limit")
@Expose
private Double quantMembersLimit;
@SerializedName("start_date")
@Expose
private String startDate;
@SerializedName("pool_type_id")
@Expose
private Double poolTypeId;
@SerializedName("sponsor_id")
@Expose
private Double sponsorId;
@SerializedName("terms_and_condition_id")
@Expose
private Double termsAndConditionId;
@SerializedName("radius")
@Expose
private Double radius;
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
private Double showBanner;
@SerializedName("national")
@Expose
private Double national;
@SerializedName("type")
@Expose
private String type;
@SerializedName("rate_per_mile")
@Expose
private Double ratePerMile;
@SerializedName("user_id")
@Expose
private Double userId;
@SerializedName("link")
@Expose
private String link;
@SerializedName("reward_type")
@Expose
private Double rewardType;
@SerializedName("reward_calculate")
@Expose
private Double rewardCalculate;
@SerializedName("set_monthly_goal")
@Expose
private Object setMonthlyGoal;
@SerializedName("pool_lat")
@Expose
private Double poolLat;
@SerializedName("pool_lng")
@Expose
private Double poolLng;
@SerializedName("pool_radius")
@Expose
private String poolRadius;
@SerializedName("pool_address")
@Expose
private Object poolAddress;
@SerializedName("city_location")
@Expose
private String cityLocation;
@SerializedName("in_house_payment")
@Expose
private Double inHousePayment;

public Double getId() {
return id;
}

public void setId(Double id) {
this.id = id;
}

public String getBanner() {
return banner;
}

public void setBanner(String banner) {
this.banner = banner;
}

public Object getDestinationLatitude() {
return destinationLatitude;
}

public void setDestinationLatitude(Object destinationLatitude) {
this.destinationLatitude = destinationLatitude;
}

public Object getDestinationLongitude() {
return destinationLongitude;
}

public void setDestinationLongitude(Object destinationLongitude) {
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

public Double getLimitPerDay() {
return limitPerDay;
}

public void setLimitPerDay(Double limitPerDay) {
this.limitPerDay = limitPerDay;
}

public Double getLimitPerMonth() {
return limitPerMonth;
}

public void setLimitPerMonth(Double limitPerMonth) {
this.limitPerMonth = limitPerMonth;
}

public Double getMoneyAvailable() {
return moneyAvailable;
}

public void setMoneyAvailable(Double moneyAvailable) {
this.moneyAvailable = moneyAvailable;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public Double getOpen() {
return open;
}

public void setOpen(Double open) {
this.open = open;
}

public Double getQuantMembersLimit() {
return quantMembersLimit;
}

public void setQuantMembersLimit(Double quantMembersLimit) {
this.quantMembersLimit = quantMembersLimit;
}

public String getStartDate() {
return startDate;
}

public void setStartDate(String startDate) {
this.startDate = startDate;
}

public Double getPoolTypeId() {
return poolTypeId;
}

public void setPoolTypeId(Double poolTypeId) {
this.poolTypeId = poolTypeId;
}

public Double getSponsorId() {
return sponsorId;
}

public void setSponsorId(Double sponsorId) {
this.sponsorId = sponsorId;
}

public Double getTermsAndConditionId() {
return termsAndConditionId;
}

public void setTermsAndConditionId(Double termsAndConditionId) {
this.termsAndConditionId = termsAndConditionId;
}

public Double getRadius() {
return radius;
}

public void setRadius(Double radius) {
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

public Double getShowBanner() {
return showBanner;
}

public void setShowBanner(Double showBanner) {
this.showBanner = showBanner;
}

public Double getNational() {
return national;
}

public void setNational(Double national) {
this.national = national;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public Double getRatePerMile() {
return ratePerMile;
}

public void setRatePerMile(Double ratePerMile) {
this.ratePerMile = ratePerMile;
}

public Double getUserId() {
return userId;
}

public void setUserId(Double userId) {
this.userId = userId;
}

public String getLink() {
return link;
}

public void setLink(String link) {
this.link = link;
}

public Double getRewardType() {
return rewardType;
}

public void setRewardType(Double rewardType) {
this.rewardType = rewardType;
}

public Double getRewardCalculate() {
return rewardCalculate;
}

public void setRewardCalculate(Double rewardCalculate) {
this.rewardCalculate = rewardCalculate;
}

public Object getSetMonthlyGoal() {
return setMonthlyGoal;
}

public void setSetMonthlyGoal(Object setMonthlyGoal) {
this.setMonthlyGoal = setMonthlyGoal;
}

public Double getPoolLat() {
return poolLat;
}

public void setPoolLat(Double poolLat) {
this.poolLat = poolLat;
}

public Double getPoolLng() {
return poolLng;
}

public void setPoolLng(Double poolLng) {
this.poolLng = poolLng;
}

public String getPoolRadius() {
return poolRadius;
}

public void setPoolRadius(String poolRadius) {
this.poolRadius = poolRadius;
}

public Object getPoolAddress() {
return poolAddress;
}

public void setPoolAddress(Object poolAddress) {
this.poolAddress = poolAddress;
}

public String getCityLocation() {
return cityLocation;
}

public void setCityLocation(String cityLocation) {
this.cityLocation = cityLocation;
}

public Double getInHousePayment() {
return inHousePayment;
}

public void setInHousePayment(Double inHousePayment) {
this.inHousePayment = inHousePayment;
}

}