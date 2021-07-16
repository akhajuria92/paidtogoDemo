
package com.aaronevans.paidtogo.ui.profile;

import com.google.gson.annotations.SerializedName;

public class ProfilePicmodel {

    @SerializedName("access_token")
    private String mAccessToken;
    @SerializedName("bio")
    private String mBio;
    @SerializedName("code")
    private String mCode;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("first_name")
    private String mFirstName;
    @SerializedName("is_company")
    private Long mIsCompany;
    @SerializedName("last_name")
    private String mLastName;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("payment_token")
    private String mPaymentToken;
    @SerializedName("paypal_account")
    private Object mPaypalAccount;
    @SerializedName("profile_picture")
    private String mProfilePicture;
    @SerializedName("subscription_id")
    private Long mSubscriptionId;
    @SerializedName("total_money")
    private Long mTotalMoney;
    @SerializedName("total_points")
    private Double mTotalPoints;
    @SerializedName("user_id")
    private Long mUserId;
    @SerializedName("user_type")
    private Long mUserType;

    public String getAccessToken() {
        return mAccessToken;
    }

    public void setAccessToken(String accessToken) {
        mAccessToken = accessToken;
    }

    public String getBio() {
        return mBio;
    }

    public void setBio(String bio) {
        mBio = bio;
    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        mCode = code;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public Long getIsCompany() {
        return mIsCompany;
    }

    public void setIsCompany(Long isCompany) {
        mIsCompany = isCompany;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getPaymentToken() {
        return mPaymentToken;
    }

    public void setPaymentToken(String paymentToken) {
        mPaymentToken = paymentToken;
    }

    public Object getPaypalAccount() {
        return mPaypalAccount;
    }

    public void setPaypalAccount(Object paypalAccount) {
        mPaypalAccount = paypalAccount;
    }

    public String getProfilePicture() {
        return mProfilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        mProfilePicture = profilePicture;
    }

    public Long getSubscriptionId() {
        return mSubscriptionId;
    }

    public void setSubscriptionId(Long subscriptionId) {
        mSubscriptionId = subscriptionId;
    }

    public Long getTotalMoney() {
        return mTotalMoney;
    }

    public void setTotalMoney(Long totalMoney) {
        mTotalMoney = totalMoney;
    }

    public Double getTotalPoints() {
        return mTotalPoints;
    }

    public void setTotalPoints(Double totalPoints) {
        mTotalPoints = totalPoints;
    }

    public Long getUserId() {
        return mUserId;
    }

    public void setUserId(Long userId) {
        mUserId = userId;
    }

    public Long getUserType() {
        return mUserType;
    }

    public void setUserType(Long userType) {
        mUserType = userType;
    }

}
