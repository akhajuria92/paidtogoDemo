package com.aaronevans.paidtogo.ui.main.prizeTable;

import android.app.Activity;
import android.content.Context;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.aaronevans.paidtogo.ui.ComplaintContactActivity.MyProgressBar;
import com.aaronevans.paidtogo.ui.Utils.Functions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Controller implements Callback<List<PrizePojo>> {

    interface response {
        void passList(List<PrizePojo> changesList);
    }

    public final String BASE_URL = "https://www.paidtogo.com/api/v1/";
    public List<PrizePojo> changesList;
    public response response_listener;
    MyProgressBar myProgressBar;
    Context context;

    public void start(String month, String year, response response_listener, Context context) {
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

        Call<List<PrizePojo>> call = gerritAPI.getPrizeList(year, month);
        call.enqueue(this);

    }

    @Override
    public void onResponse(Call<List<PrizePojo>> call, Response<List<PrizePojo>> response) {
        Functions.hideProgressbar((Activity) context);

        if (response.isSuccessful()) {
            changesList=new ArrayList<>();
            changesList = response.body();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                changesList.forEach(change -> System.out.println("response+42" + change.cityLocation));
            }
            response_listener.passList(changesList);
            System.out.println("controller++success+55" + response.errorBody());

        } else {
            System.out.println("controller++errror+57" + response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<List<PrizePojo>> call, Throwable t) {
        t.printStackTrace();
        Functions.hideProgressbar((Activity) context);
    }
}