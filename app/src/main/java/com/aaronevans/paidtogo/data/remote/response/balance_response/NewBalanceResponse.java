
package com.aaronevans.paidtogo.data.remote.response.balance_response;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class NewBalanceResponse {

    @SerializedName("data")
    private Data mData;



    @SerializedName("pendingPaymentsSum")
    private  double pendingPaymentsSum;

    @SerializedName("past_payments")
    private List<Payment> mPastPayments;
    @SerializedName("pending_payments")
    private List<Payment> mPendingPayments;

    public Data getData() {
        return mData;
    }

    public void setData(Data data) {
        mData = data;
    }

    public List<Payment> getPastPayments() {
        return mPastPayments;
    }

    public void setPastPayments(List<Payment> pastPayments) {
        mPastPayments = pastPayments;
    }

    public List<Payment> getPendingPayments() {
        return mPendingPayments;
    }

    public void setPendingPayments(List<Payment> pendingPayments) {
        mPendingPayments = pendingPayments;
    }

    public double getPendingPaymentsSum() {
        return pendingPaymentsSum;
    }

    public void setPendingPaymentsSum(double pendingPaymentsSum) {
        this.pendingPaymentsSum = pendingPaymentsSum;
    }
}
