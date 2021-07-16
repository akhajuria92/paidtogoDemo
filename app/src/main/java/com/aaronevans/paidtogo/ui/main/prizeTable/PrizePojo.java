package com.aaronevans.paidtogo.ui.main.prizeTable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PrizePojo {

@SerializedName("id")
@Expose
public Integer id;
@SerializedName("banner")
@Expose
public String banner;
@SerializedName("destination_latitude")
@Expose
public Integer destinationLatitude;
@SerializedName("destination_longitude")
@Expose
public Integer destinationLongitude;
@SerializedName("earned_money_per_mile")
@Expose
public Double earnedMoneyPerMile;
@SerializedName("created_date_time")
@Expose
public String createdDateTime;
@SerializedName("end_date_time")
@Expose
public String endDateTime;
@SerializedName("icon_photo")
@Expose
public String iconPhoto;
@SerializedName("icon_photo_description")
@Expose
public String iconPhotoDescription;
@SerializedName("limit_per_day")
@Expose
public Integer limitPerDay;
@SerializedName("limit_per_month")
@Expose
public Integer limitPerMonth;
@SerializedName("money_available")
@Expose
public Integer moneyAvailable;
@SerializedName("name")
@Expose
public String name;
@SerializedName("open")
@Expose
public Integer open;
@SerializedName("quant_members_limit")
@Expose
public Integer quantMembersLimit;
@SerializedName("start_date")
@Expose
public String startDate;
@SerializedName("pool_type_id")
@Expose
public Integer poolTypeId;
@SerializedName("sponsor_id")
@Expose
public Integer sponsorId;
@SerializedName("terms_and_condition_id")
@Expose
public Integer termsAndConditionId;
@SerializedName("radius")
@Expose
public Integer radius;
@SerializedName("country")
@Expose
public String country;
@SerializedName("state")
@Expose
public String state;
@SerializedName("origin")
@Expose
public String origin;
@SerializedName("show_banner")
@Expose
public Integer showBanner;
@SerializedName("national")
@Expose
public Integer national;
@SerializedName("type")
@Expose
public String type;
@SerializedName("rate_per_mile")
@Expose
public Integer ratePerMile;
@SerializedName("user_id")
@Expose
public Integer userId;
@SerializedName("link")
@Expose
public String link;
@SerializedName("reward_type")
@Expose
public Integer rewardType;
@SerializedName("reward_calculate")
@Expose
public Integer rewardCalculate;
@SerializedName("set_monthly_goal")
@Expose
public String setMonthlyGoal;
@SerializedName("pool_lat")
@Expose
public Integer poolLat;
@SerializedName("pool_lng")
@Expose
public Integer poolLng;
@SerializedName("pool_radius")
@Expose
public String poolRadius;
@SerializedName("pool_address")
@Expose
public String poolAddress;
@SerializedName("city_location")
@Expose
public String cityLocation;
@SerializedName("in_house_payment")
@Expose
public Integer inHousePayment;
@SerializedName("funds_available")
@Expose
public Integer fundsAvailable;
@SerializedName("prize")
@Expose
public List<Prize> prize = null;



    public class Prize {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("amount")
        @Expose
        public Object amount;
        @SerializedName("rank")
        @Expose
        public Integer rank;
        @SerializedName("prize_date")
        @Expose
        public String prizeDate;
        @SerializedName("poolname")
        @Expose
        public String poolname;

    }
}