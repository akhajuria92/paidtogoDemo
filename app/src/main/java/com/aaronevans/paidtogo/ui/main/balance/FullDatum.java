package com.aaronevans.paidtogo.ui.main.balance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FullDatum {

@SerializedName("id")
@Expose
public Integer id;
@SerializedName("winner_date")
@Expose
public String winnerDate;
@SerializedName("user_id")
@Expose
public Integer userId;
@SerializedName("rank")
@Expose
public Integer rank;
@SerializedName("prize_id")
@Expose
public Integer prizeId;
@SerializedName("description")
@Expose
public String description;
@SerializedName("is_paid_by_paypal")
@Expose
public Integer isPaidByPaypal;
@SerializedName("dateTime")
@Expose
public String dateTime;
@SerializedName("name")
@Expose
public String name;
@SerializedName("amount")
@Expose
public Object amount;
@SerializedName("slots")
@Expose
public Integer slots;
@SerializedName("prize_date")
@Expose
public String prizeDate;
@SerializedName("created_at")
@Expose
public String createdAt;
@SerializedName("updated_at")
@Expose
public String updatedAt;
@SerializedName("pool_id")
@Expose
public Integer poolId;
@SerializedName("showdate")
@Expose
public String showdate;

}