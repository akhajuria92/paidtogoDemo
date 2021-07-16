package com.aaronevans.paidtogo.data.entities;

import com.google.gson.annotations.Expose;
import com.aaronevans.paidtogo.data.entities.contracts.ApiError;


public class Error implements ApiError {

    @Expose
    String detail;
    @Expose
    String code;

    public Error() {
    }

    public Error(String detail, String code) {
        this.detail = detail;
        this.code = code;
    }

    public String getDetail() {
        return detail;
    }

    @Override
    public String getCode() {
        return code;
    }

//    @Override
//    public boolean isDeauth() {
//        return getCode()==403;
//    }
}
