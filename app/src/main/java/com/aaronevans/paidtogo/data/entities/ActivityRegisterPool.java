package com.aaronevans.paidtogo.data.entities;


import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class ActivityRegisterPool {

    @SerializedName("pool_name")
    @Expose
    private String poolName;
    @SerializedName("earned_money")
    @Expose
    private Integer earnedMoney;

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

    public Integer getEarnedMoney() {
        return earnedMoney;
    }

    public void setEarnedMoney(Integer earnedMoney) {
        this.earnedMoney = earnedMoney;
    }

}
