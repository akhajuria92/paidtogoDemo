
package com.aaronevans.paidtogo.data.remote.response.balance_response;


import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("288")
    private Free mFree;
    @SerializedName("326")
    private Paid mPaid;




    @SerializedName("spent_coins")
    Double spent_coins;




    public Double getSpent_coins() {
        return spent_coins;
    }

    public void setSpent_coins(Double spent_coins) {
        this.spent_coins = spent_coins;
    }

    public Free getFree() {
        return mFree;
    }

    public void setFree(Free free) {
        mFree = free;
    }

    public Paid getPaid() {
        return mPaid;
    }

    public void setPaid(Paid paid) {
        mPaid = paid;
    }

}
