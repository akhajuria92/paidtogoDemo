package com.aaronevans.paidtogo.webService.Firebase;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Api {

    @POST("update/device-token-android")
    Call<FcmPojo> getFCMUpdate(@Body FcmRequest fcmRequest);

}