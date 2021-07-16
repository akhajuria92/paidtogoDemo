package com.aaronevans.paidtogo.ui.main.balance;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BalanceRequest implements Serializable {

    @SerializedName("year")
    @Expose
    public String year;
    @SerializedName("month")
    @Expose
    public String month;
    @SerializedName("user_id")
    @Expose
    public String userId;

}