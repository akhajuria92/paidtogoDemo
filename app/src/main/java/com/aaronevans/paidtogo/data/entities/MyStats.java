package com.aaronevans.paidtogo.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Infinix Android on 30/1/2017.
 */

public class MyStats {
    @Expose
    @SerializedName("balance")
    private String balance = null;
    @Expose
    @SerializedName("calculated_units")
    private List<CalculateUnit> calculatedUnits = null;

    public List<CalculateUnit> getCalculatedUnits() {
        return calculatedUnits;
    }

    public void setCalculatedUnits(List<CalculateUnit> calculatedUnits) {
        this.calculatedUnits = calculatedUnits;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }


}
