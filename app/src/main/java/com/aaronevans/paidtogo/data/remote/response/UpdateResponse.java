package com.aaronevans.paidtogo.data.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Infinix Android on 12/1/2017.
 */

public class UpdateResponse {

    @Expose
    @SerializedName("message")

    private String detail = null;
    @Expose
    @SerializedName("code")
    private String code = null;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Expose
    @SerializedName("new_password")
    private List<String> newPassword = null;

    public List<String> getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(List<String> newPassword) {
        this.newPassword = newPassword;
    }

    @SerializedName("error")
    @Expose
    private String error = null;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
