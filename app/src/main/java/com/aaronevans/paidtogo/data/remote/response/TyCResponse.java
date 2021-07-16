package com.aaronevans.paidtogo.data.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by evaristo on 23/12/16.
 */

public class TyCResponse extends Error {

    @Expose
    @SerializedName("tyc")
    String terms;

    public String getTerms() {
        return terms;
    }
}
