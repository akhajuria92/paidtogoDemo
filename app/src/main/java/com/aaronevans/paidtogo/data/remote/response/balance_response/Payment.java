
package com.aaronevans.paidtogo.data.remote.response.balance_response;


import com.google.gson.annotations.SerializedName;


public class Payment {

    @SerializedName("amount")
    private Long mAmount;
    @SerializedName("coins")
    private Long mCoins;
    @SerializedName("created_at")
    private Object mCreatedAt;
    @SerializedName("id")
    private Long mId;
    @SerializedName("month")
    private Long mMonth;
    @SerializedName("offer_id")
    private Long mOfferId;
    @SerializedName("points")
    private Long mPoints;
    @SerializedName("status")
    private Long mStatus;
    @SerializedName("updated_at")
    private String mUpdatedAt;
    @SerializedName("user_id")
    private Long mUserId;

    public Long getAmount() {
        return mAmount;
    }

    public void setAmount(Long amount) {
        mAmount = amount;
    }

    public Long getCoins() {
        return mCoins;
    }

    public void setCoins(Long coins) {
        mCoins = coins;
    }

    public Object getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(Object createdAt) {
        mCreatedAt = createdAt;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public Long getMonth() {
        return mMonth;
    }

    public void setMonth(Long month) {
        mMonth = month;
    }

    public Long getOfferId() {
        return mOfferId;
    }

    public void setOfferId(Long offerId) {
        mOfferId = offerId;
    }

    public Long getPoints() {
        return mPoints;
    }

    public void setPoints(Long points) {
        mPoints = points;
    }

    public Long getStatus() {
        return mStatus;
    }

    public void setStatus(Long status) {
        mStatus = status;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

    public Long getUserId() {
        return mUserId;
    }

    public void setUserId(Long userId) {
        mUserId = userId;
    }

}
