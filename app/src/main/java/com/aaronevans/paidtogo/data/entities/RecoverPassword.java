package com.aaronevans.paidtogo.data.entities;

import com.google.gson.annotations.Expose;

/**
 * Created by evaristo on 23/12/16.
 */

public class RecoverPassword {

    @Expose
    String email;

    public RecoverPassword(String email) {
        this.email = email;
    }
}
