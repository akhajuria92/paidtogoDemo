package com.aaronevans.paidtogo.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Infinix Android on 30/1/2017.
 */

public class CalculateUnit {
    @Expose
    @SerializedName("date_time")
    private String dateTime = null;

    @Expose
    @SerializedName("value")
    private String value;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
