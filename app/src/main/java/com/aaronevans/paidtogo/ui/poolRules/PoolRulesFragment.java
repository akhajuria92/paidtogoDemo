package com.aaronevans.paidtogo.ui.poolRules;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.remote.PaidToGoService;
import com.aaronevans.paidtogo.data.remote.request.get_coins_value.GetCoinsValue;
import com.aaronevans.paidtogo.ui.BaseFragment;
import com.aaronevans.paidtogo.ui.poolRules.adapter.PoolRulesAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

import static com.aaronevans.paidtogo.ui.main.MainActivity.toolbar_image;
import static com.aaronevans.paidtogo.ui.main.MainActivity.toolbar_title;

public class PoolRulesFragment  extends BaseFragment {
    private ViewPager pager;
    private TabLayout mIndicator;
    List<String> listOfSecond=new ArrayList<>();
    List<String> listOfFirst=new ArrayList<>();
    ProgressDialog mProgressDialog;

    String coinsValue;
    String pointsValue;

    @Override
    public void onResume() {
        super.onResume();
        toolbar_image.setVisibility(View.VISIBLE);
        toolbar_title.setVisibility(View.GONE);

        getCoinsValue();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pool_rules_fragment, container, false);
        findViews(view);
        return view;
    }


    private void findViews(View view) {
        pager = view.findViewById(R.id.mPager);
        mIndicator = view.findViewById(R.id.mIndicator);

    }

    private void setUpAdapter() {

        listOfFirst.clear();
        listOfSecond.clear();



        listOfSecond.add("1 Coin per mile");
        listOfSecond.add("Limit = "+pointsValue+" Coins per day");
        listOfSecond.add("Purchase Paypal Payouts from the Paidtogo Store");
       // listOfSecond.add("Coin Value = 2.5 to 5 cents per Coin");
        listOfSecond.add("Discounts for larger payouts");

        listOfFirst.add("2 Coins per mile");
        listOfFirst.add("Limit = "+coinsValue+" Coins per day");
        listOfFirst.add("Purchase Paypal Payouts from the Paidtogo Store");
        listOfFirst.add("Remove ads");
        listOfFirst.add("Win random cash prizes");
       // listOfFirst.add("Coin Value = 2.5 to 5 cents per Coin");
        listOfFirst.add("Discounts for larger payouts");


        PoolRulesAdapter mAdapter = new PoolRulesAdapter(getActivity(),listOfFirst,listOfSecond);
        mIndicator.setupWithViewPager(pager);
        pager.setAdapter(mAdapter);

        pager.setClipToPadding(false);
        // set padding manually, the more you set the padding the more you see of prev & next page
        pager.setPadding(60, 0, 60, 0);
        // sets a margin b/w individual pages to ensure that there is a gap b/w them
        pager.setPageMargin(40);

    }

    private  void showProgressDialog(){
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setTitle(getResources().getString(R.string.app_name));
        mProgressDialog.show();
    }

    private  void hideProgressDialog(){
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }



    void getCoinsValue() {
        showProgressDialog();
        PaidToGoService
                .getServiceClient()
                .getCoinsValue("paid_user_coin_limit")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String stringResponse=response.body().string().toString();
                            Gson gson=new Gson();
                            GetCoinsValue finalResponse = gson.fromJson(stringResponse, GetCoinsValue.class);
                            coinsValue=finalResponse.getResult().getSettingValue();
                            getPointsValue();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        hideProgressDialog();
                        onError(t);
                    }
                });
    }

    void getPointsValue() {
        PaidToGoService
                .getServiceClient()
                .getCoinsValue("free_user_coin_limit")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            hideProgressDialog();
                            String stringResponse=response.body().string().toString();
                            Gson gson=new Gson();
                            GetCoinsValue finalResponse = gson.fromJson(stringResponse, GetCoinsValue.class);
                            pointsValue=finalResponse.getResult().getSettingValue();
                            setUpAdapter();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        hideProgressDialog();
                        onError(t);
                    }
                });
    }


    public void onError(Throwable throwable) {
        hideProgressDialog();
        if (throwable instanceof HttpException) {
            showErrorAlert(PaidToGoService.attendError((HttpException) throwable).getDetail());
        } else {
            showErrorAlert(getContext().getString(R.string.connection_problem));
        }
    }

    private  void showErrorAlert(String message){
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setTitle(getResources().getString(R.string.app_name))
                .setPositiveButton(R.string.alert_accept, null)
                .create()
                .show();
    }
}
