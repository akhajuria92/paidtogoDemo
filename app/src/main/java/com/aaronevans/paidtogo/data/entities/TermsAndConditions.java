package com.aaronevans.paidtogo.data.entities;

import com.google.gson.annotations.Expose;

/**
 * Created by evaristo on 23/12/16.
 */

public class TermsAndConditions {

    @Expose
    String id;

    public TermsAndConditions(String id) {
        this.id = id;
    }
}
