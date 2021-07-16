package com.aaronevans.paidtogo.data.remote.response.offers_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Points {

    @SerializedName("326")
    Paid paid;

    @SerializedName("288")
    Free free;



    @SerializedName("spent_coins")
    String spend_coins;


    public String getSpend_coins() {
        return spend_coins;
    }

    public void setSpend_coins(String spend_coins) {
        this.spend_coins = spend_coins;
    }

    public Free getFree() {
        return free;
    }

    public void setFree(Free free) {
        this.free = free;
    }

    public Paid getPaid() {
        return paid;
    }

    public void setPaid(Paid paid) {
        this.paid = paid;
    }

}