package com.aaronevans.paidtogo.webService;

import com.aaronevans.paidtogo.data.remote.response.balance_response.NewBalanceResponse;
import com.aaronevans.paidtogo.ui.main.balance.BalancePojo;
import com.aaronevans.paidtogo.ui.main.balance.BalanceRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @POST("getUserAmount")
    Call<BalancePojo> getBalanceList(@Body BalanceRequest balanceRequest);

    @FormUrlEncoded
    @POST("get/payment/data")
    Call<NewBalanceResponse> getNewBalanceList(@Field("user_id") String user_id);

}