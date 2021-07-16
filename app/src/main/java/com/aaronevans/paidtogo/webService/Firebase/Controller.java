package com.aaronevans.paidtogo.webService.Firebase;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Controller  {


    public final String BASE_URL = "https://www.paidtogo.com/api/v1/";
    public List<FcmPojo> changesList;
    Context context;

    public void start(FcmRequest fcmRequest, Context context)
    {
        this.context = context;
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Api gerritAPI = retrofit.create(Api.class);

        Call<FcmPojo> call = gerritAPI.getFCMUpdate(fcmRequest);
        call.enqueue(new Callback<FcmPojo>() {
            @Override
            public void onResponse(Call<FcmPojo> call, Response<FcmPojo> response) {
                Log.e("FCMRESPONSE+Response++","success");
                Log.e("FCMRESPONSE+Response++",response.code()+"");
                FcmPojo fcmResponse = response.body();
                Log.e("FCMRESPONSE+Response++","getCode++"+fcmResponse.getCode());
                Log.e("FCMRESPONSE+Response++","fullresponse++"+fcmResponse.toString());
            }

            @Override
            public void onFailure(Call<FcmPojo> call, Throwable t) {
                call.cancel();
                Log.e("FCMRESPONSE+Response++","onFailure");
            }
        });
    }
}