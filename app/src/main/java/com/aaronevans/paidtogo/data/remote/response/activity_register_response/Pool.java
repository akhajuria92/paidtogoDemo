
package com.aaronevans.paidtogo.data.remote.response.activity_register_response;


import com.google.gson.annotations.SerializedName;

public class Pool {

    @SerializedName("earned_money")
    private Long mEarnedMoney;
    @SerializedName("pool_name")
    private String mPoolName;

    public Long getEarnedMoney() {
        return mEarnedMoney;
    }

    public void setEarnedMoney(Long earnedMoney) {
        mEarnedMoney = earnedMoney;
    }

    public String getPoolName() {
        return mPoolName;
    }

    public void setPoolName(String poolName) {
        mPoolName = poolName;
    }

}
