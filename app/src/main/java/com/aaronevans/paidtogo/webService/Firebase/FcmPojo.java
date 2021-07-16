package com.aaronevans.paidtogo.webService.Firebase;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FcmPojo implements Serializable
{

@SerializedName("code")
@Expose
private String code;
@SerializedName("data")
@Expose
private Data data;
private final static long serialVersionUID = 4119310614985954586L;

public String getCode() {
return code;
}

public void setCode(String code) {
this.code = code;
}

public FcmPojo withCode(String code) {
this.code = code;
return this;
}

public Data getData() {
return data;
}

public void setData(Data data) {
this.data = data;
}

public FcmPojo withData(Data data) {
this.data = data;
return this;
}


    public class Data implements Serializable
    {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("access_token")
        @Expose
        private String accessToken;
        @SerializedName("bio")
        @Expose
        private String bio;
        @SerializedName("company_name")
        @Expose
        private String companyName;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("fb_id")
        @Expose
        private String fbId;
        @SerializedName("apple_id")
        @Expose
        private String appleId;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("is_company")
        @Expose
        private Integer isCompany;
        @SerializedName("paypal_account")
        @Expose
        private String paypalAccount;
        @SerializedName("profile_picture")
        @Expose
        private String profilePicture;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("remember_token")
        @Expose
        private Object rememberToken;
        @SerializedName("user_type")
        @Expose
        private Integer userType;
        @SerializedName("frontweb_image")
        @Expose
        private Integer frontwebImage;
        @SerializedName("payment_token")
        @Expose
        private String paymentToken;
        @SerializedName("subscription_id")
        @Expose
        private Integer subscriptionId;
        @SerializedName("sub_start_date")
        @Expose
        private Object subStartDate;
        @SerializedName("sub_end_date")
        @Expose
        private Object subEndDate;
        @SerializedName("cancel_date")
        @Expose
        private Object cancelDate;
        @SerializedName("device_type")
        @Expose
        private String deviceType;
        @SerializedName("device_token")
        @Expose
        private String deviceToken;
        @SerializedName("first_login")
        @Expose
        private Integer firstLogin;
        @SerializedName("trial_date")
        @Expose
        private Object trialDate;
        @SerializedName("app_type")
        @Expose
        private Integer appType;
        @SerializedName("distance_unit_type")
        @Expose
        private Integer distanceUnitType;
        private final static long serialVersionUID = 7351304817505527775L;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Data withId(Integer id) {
            this.id = id;
            return this;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public Data withAccessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public Data withBio(String bio) {
            this.bio = bio;
            return this;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public Data withCompanyName(String companyName) {
            this.companyName = companyName;
            return this;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Data withEmail(String email) {
            this.email = email;
            return this;
        }

        public String getFbId() {
            return fbId;
        }

        public void setFbId(String fbId) {
            this.fbId = fbId;
        }

        public Data withFbId(String fbId) {
            this.fbId = fbId;
            return this;
        }

        public String getAppleId() {
            return appleId;
        }

        public void setAppleId(String appleId) {
            this.appleId = appleId;
        }

        public Data withAppleId(String appleId) {
            this.appleId = appleId;
            return this;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public Data withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public Data withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Integer getIsCompany() {
            return isCompany;
        }

        public void setIsCompany(Integer isCompany) {
            this.isCompany = isCompany;
        }

        public Data withIsCompany(Integer isCompany) {
            this.isCompany = isCompany;
            return this;
        }

        public String getPaypalAccount() {
            return paypalAccount;
        }

        public void setPaypalAccount(String paypalAccount) {
            this.paypalAccount = paypalAccount;
        }

        public Data withPaypalAccount(String paypalAccount) {
            this.paypalAccount = paypalAccount;
            return this;
        }

        public String getProfilePicture() {
            return profilePicture;
        }

        public void setProfilePicture(String profilePicture) {
            this.profilePicture = profilePicture;
        }

        public Data withProfilePicture(String profilePicture) {
            this.profilePicture = profilePicture;
            return this;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public Data withCreatedAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Data withUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Object getRememberToken() {
            return rememberToken;
        }

        public void setRememberToken(Object rememberToken) {
            this.rememberToken = rememberToken;
        }

        public Data withRememberToken(Object rememberToken) {
            this.rememberToken = rememberToken;
            return this;
        }

        public Integer getUserType() {
            return userType;
        }

        public void setUserType(Integer userType) {
            this.userType = userType;
        }

        public Data withUserType(Integer userType) {
            this.userType = userType;
            return this;
        }

        public Integer getFrontwebImage() {
            return frontwebImage;
        }

        public void setFrontwebImage(Integer frontwebImage) {
            this.frontwebImage = frontwebImage;
        }

        public Data withFrontwebImage(Integer frontwebImage) {
            this.frontwebImage = frontwebImage;
            return this;
        }

        public String getPaymentToken() {
            return paymentToken;
        }

        public void setPaymentToken(String paymentToken) {
            this.paymentToken = paymentToken;
        }

        public Data withPaymentToken(String paymentToken) {
            this.paymentToken = paymentToken;
            return this;
        }

        public Integer getSubscriptionId() {
            return subscriptionId;
        }

        public void setSubscriptionId(Integer subscriptionId) {
            this.subscriptionId = subscriptionId;
        }

        public Data withSubscriptionId(Integer subscriptionId) {
            this.subscriptionId = subscriptionId;
            return this;
        }

        public Object getSubStartDate() {
            return subStartDate;
        }

        public void setSubStartDate(Object subStartDate) {
            this.subStartDate = subStartDate;
        }

        public Data withSubStartDate(Object subStartDate) {
            this.subStartDate = subStartDate;
            return this;
        }

        public Object getSubEndDate() {
            return subEndDate;
        }

        public void setSubEndDate(Object subEndDate) {
            this.subEndDate = subEndDate;
        }

        public Data withSubEndDate(Object subEndDate) {
            this.subEndDate = subEndDate;
            return this;
        }

        public Object getCancelDate() {
            return cancelDate;
        }

        public void setCancelDate(Object cancelDate) {
            this.cancelDate = cancelDate;
        }

        public Data withCancelDate(Object cancelDate) {
            this.cancelDate = cancelDate;
            return this;
        }

        public String getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

        public Data withDeviceType(String deviceType) {
            this.deviceType = deviceType;
            return this;
        }

        public String getDeviceToken() {
            return deviceToken;
        }

        public void setDeviceToken(String deviceToken) {
            this.deviceToken = deviceToken;
        }

        public Data withDeviceToken(String deviceToken) {
            this.deviceToken = deviceToken;
            return this;
        }

        public Integer getFirstLogin() {
            return firstLogin;
        }

        public void setFirstLogin(Integer firstLogin) {
            this.firstLogin = firstLogin;
        }

        public Data withFirstLogin(Integer firstLogin) {
            this.firstLogin = firstLogin;
            return this;
        }

        public Object getTrialDate() {
            return trialDate;
        }

        public void setTrialDate(Object trialDate) {
            this.trialDate = trialDate;
        }

        public Data withTrialDate(Object trialDate) {
            this.trialDate = trialDate;
            return this;
        }

        public Integer getAppType() {
            return appType;
        }

        public void setAppType(Integer appType) {
            this.appType = appType;
        }

        public Data withAppType(Integer appType) {
            this.appType = appType;
            return this;
        }

        public Integer getDistanceUnitType() {
            return distanceUnitType;
        }

        public void setDistanceUnitType(Integer distanceUnitType) {
            this.distanceUnitType = distanceUnitType;
        }

        public Data withDistanceUnitType(Integer distanceUnitType) {
            this.distanceUnitType = distanceUnitType;
            return this;
        }

    }
}