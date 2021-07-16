package com.aaronevans.paidtogo.ui.storeoffer;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.ViewPager;

import com.aaronevans.paidtogo.data.local.UserPreferences;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.remote.PaidToGoService;
import com.aaronevans.paidtogo.data.remote.response.offers_response.OffersResponse;
import com.aaronevans.paidtogo.ui.BaseFragment;
import com.aaronevans.paidtogo.ui.storeoffer.adapter.OfferPagerAdapter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

import static com.aaronevans.paidtogo.ui.main.MainActivity.toolbar_image;
import static com.aaronevans.paidtogo.ui.main.MainActivity.toolbar_title;

public class OfferFragment extends BaseFragment {


    private ViewPager pager;
    private TabLayout mIndicator;
    ProgressDialog mProgressDialog;

    @Override
    public void onResume() {
        super.onResume();
        toolbar_image.setVisibility(View.VISIBLE);
        toolbar_title.setVisibility(View.GONE);
        getOffer();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offer_fragment, container, false);
        findViews(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }




    private void findViews(View view) {
        pager = view.findViewById(R.id.mPager);
        mIndicator = view.findViewById(R.id.mIndicator);

    }

    public void onError(Throwable throwable) {
        hideProgressDialog();
        if (throwable instanceof HttpException) {
            showErrorAlert(PaidToGoService.attendError((HttpException) throwable).getDetail());
        } else {
            showErrorAlert(getContext().getString(R.string.connection_problem));
        }
    }



    private void setUpAdapter(OffersResponse response) {
        OfferPagerAdapter mAdapter = new OfferPagerAdapter(getActivity(),response);
        mIndicator.setupWithViewPager(pager);
        pager.setAdapter(mAdapter);

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


    private  void showErrorAlert(String message){
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setTitle(getResources().getString(R.string.app_name))
                .setPositiveButton(R.string.alert_accept, null)
                .create()
                .show();
    }

    void getOffer(){
        showProgressDialog();
        PaidToGoService
                .getServiceClient()
                .getOffers(UserPreferences.getUser(getContext()).getId())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String stringResponse=response.body().string().toString();
                            Gson gson=new Gson();
                            OffersResponse finalResponse = gson.fromJson(stringResponse, OffersResponse.class);
                            hideProgressDialog();
                            setUpAdapter(finalResponse);
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










}
