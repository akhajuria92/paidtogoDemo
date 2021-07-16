
package com.aaronevans.paidtogo.data.remote.response.offers_response;

import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("amount")
    private String mAmount;
    @SerializedName("coins")
    private String mCoins;
    @SerializedName("created_at")
    private Object mCreatedAt;
    @SerializedName("id")
    private Long mId;
    @SerializedName("month")
    private Long mMonth;
    @SerializedName("name")
    private String mName;
    @SerializedName("unit_remaining")
    private Long mUnitRemaining;
    @SerializedName("units")
    private Long mUnits;
    @SerializedName("updated_at")
    private String mUpdatedAt;

    public String getAmount() {
        return mAmount;
    }

    public void setAmount(String amount) {
        mAmount = amount;
    }

    public String getCoins() {
        return mCoins;
    }

    public void setCoins(String coins) {
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

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Long getUnitRemaining() {
        return mUnitRemaining;
    }

    public void setUnitRemaining(Long unitRemaining) {
        mUnitRemaining = unitRemaining;
    }

    public Long getUnits() {
        return mUnits;
    }

    public void setUnits(Long units) {
        mUnits = units;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

}
