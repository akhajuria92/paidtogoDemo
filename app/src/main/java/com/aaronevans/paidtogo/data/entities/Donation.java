package com.aaronevans.paidtogo.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Donation {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("ad_id")
    @Expose
    private Integer adId;
    @SerializedName("pool_id")
    @Expose
    private Integer poolId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("leader_rank")
    @Expose
    private Integer leaderRank;
    @SerializedName("commute_type")
    @Expose
    private Integer commuteType;
    @SerializedName("remaining_amount")
    @Expose
    private Double remainingAmount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAdId() {
        return adId;
    }

    public void setAdId(Integer adId) {
        this.adId = adId;
    }

    public Integer getPoolId() {
        return poolId;
    }

    public void setPoolId(Integer poolId) {
        this.poolId = poolId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getLeaderRank() {
        return leaderRank;
    }

    public void setLeaderRank(Integer leaderRank) {
        this.leaderRank = leaderRank;
    }

    public Integer getCommuteType() {
        return commuteType;
    }

    public void setCommuteType(Integer commuteType) {
        this.commuteType = commuteType;
    }

    public Double getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(Double remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

}