package com.aaronevans.paidtogo.data.remote.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.aaronevans.paidtogo.ui.start.login.LoginContract;

public class FacebookLoginResponse implements LoginContract {

        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("access_token")
        @Expose
        private String accessToken;
        @SerializedName("detail")
        @Expose
        private String detail;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("profile_picture")
        @Expose
        private String profilePicture;
        @SerializedName("user_type")
        @Expose
        private Integer userType;
        @SerializedName("is_facebook")
        @Expose
        private Boolean isFacebook;
        @SerializedName("code")
        @Expose
        private String code;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getProfilePicture() {
            return profilePicture;
        }

        public void setProfilePicture(String profilePicture) {
            this.profilePicture = profilePicture;
        }

        public Integer getUserType() {
            return userType;
        }

        public void setUserType(Integer userType) {
            this.userType = userType;
        }

        public Boolean getIsFacebook() {
            return isFacebook;
        }

        public void setIsFacebook(Boolean isFacebook) {
            this.isFacebook = isFacebook;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

    }
