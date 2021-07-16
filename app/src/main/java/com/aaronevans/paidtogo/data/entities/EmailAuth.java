package com.aaronevans.paidtogo.data.entities;

import com.google.gson.annotations.Expose;

/**
 * Created by evaristo on 22/12/16.
 */

public class EmailAuth {

    @Expose
    private String email;
    @Expose
    private String password;

    public EmailAuth(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
