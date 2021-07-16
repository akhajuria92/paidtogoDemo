package com.aaronevans.paidtogo.webService;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.aaronevans.paidtogo.data.local.UserPreferences;
import com.aaronevans.paidtogo.data.remote.response.balance_response.NewBalanceResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.aaronevans.paidtogo.ui.ComplaintContactActivity.MyProgressBar;
import com.aaronevans.paidtogo.ui.Utils.Functions;
import com.aaronevans.paidtogo.ui.main.balance.BalancePojo;
import com.aaronevans.paidtogo.ui.main.balance.BalanceRequest;
import com.aaronevans.paidtogo.ui.main.balance.FullDatum;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Controller  {

    public interface response {
        void passList(NewBalanceResponse resource);
    }

    public final String BASE_URL = "";
    public List<BalancePojo> changesList;
    public response response_listener;
    MyProgressBar myProgressBar;
    Context context;

    public void start(BalanceRequest balanceRequest, response response_listener, Context context) {
        this.response_listener = response_listener;
        this.context = context;
        Functions.showProgressbar((Activity) context);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Api gerritAPI = retrofit.create(Api.class);


        Call<NewBalanceResponse> call = gerritAPI.getNewBalanceList(UserPreferences.getUser(context).getId());
        call.enqueue(new Callback<NewBalanceResponse>() {
            @Override
            public void onResponse(Call<NewBalanceResponse> call, Response<NewBalanceResponse> response) {
                Log.e("BALANCECONTROLLER++55","success");
                Log.e("BALANCECONTROLLER++55",response.code()+"");
                String displayResponse = "";
               // BalancePojo resource = response.body();
                response_listener.passList(response.body());
                Functions.hideProgressbar((Activity)context);
            }

            @Override
            public void onFailure(Call<NewBalanceResponse> call, Throwable t) {
                call.cancel();
                Log.e("BALANCECONTROLLER++55","onFailure");
                Functions.hideProgressbar((Activity) context);
            }
        });

       /* Call<BalancePojo> call = gerritAPI.getBalanceList(balanceRequest);
        call.enqueue(new Callback<BalancePojo>() {
            @Override
            public void onResponse(Call<BalancePojo> call, Response<BalancePojo> response) {
                Log.e("BALANCECONTROLLER++55","success");
                Log.e("BALANCECONTROLLER++55",response.code()+"");
                String displayResponse = "";
                BalancePojo resource = response.body();
                response_listener.passList(resource.fullData,resource);
                Functions.hideProgressbar((Activity)context);
            }

            @Override
            public void onFailure(Call<BalancePojo> call, Throwable t) {
                call.cancel();
                Log.e("BALANCECONTROLLER++55","onFailure");
                Functions.hideProgressbar((Activity) context);
            }
        });*/
    }

}