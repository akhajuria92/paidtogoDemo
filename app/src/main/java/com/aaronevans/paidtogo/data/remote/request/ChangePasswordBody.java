package com.aaronevans.paidtogo.data.remote.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Infinix Android on 20/1/2017.
 */

public class ChangePasswordBody {
    @Expose
    @SerializedName("old_password")
    private String old_password = null;

    @Expose
    @SerializedName("new_password")
    private String new_password = null;

    @Expose
    @SerializedName("password_repeat")
    private String password_repeat = null;

    @Expose
    @SerializedName("access_token")
    private String access_token = null;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getOld_password() {
        return old_password;
    }

    public void setOld_password(String old_password) {
        this.old_password = old_password;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    public String getPassword_repeat() {
        return password_repeat;
    }

    public void setPassword_repeat(String password_repeat) {
        this.password_repeat = password_repeat;
    }
}
