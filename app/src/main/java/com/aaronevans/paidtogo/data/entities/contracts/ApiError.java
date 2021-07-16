package com.aaronevans.paidtogo.data.entities.contracts;


public interface ApiError {
    String ERROR = "ERROR";

    String getDetail();

    String getCode();
//    boolean isDeauth();
}
