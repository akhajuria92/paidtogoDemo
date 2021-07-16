package com.aaronevans.paidtogo.ui.earn_coins;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.aaronevans.paidtogo.PaidToGoApp;
import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.local.UserPreferences;
import com.aaronevans.paidtogo.data.remote.PaidToGoService;
import com.aaronevans.paidtogo.ui.BaseFragment;
import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyAdOptions;
import com.adcolony.sdk.AdColonyAppOptions;
import com.adcolony.sdk.AdColonyInterstitial;
import com.adcolony.sdk.AdColonyInterstitialListener;
import com.adcolony.sdk.AdColonyUserMetadata;
import com.adcolony.sdk.AdColonyZone;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class EarnCoinsFragment extends BaseFragment {
    private AdColonyInterstitial add;
    private AdColonyInterstitialListener listener;
    private AdColonyAdOptions adOptions;


    private TextView textViewWatchAdd;
    private LinearLayout linearLayoutWatchAdd;

    ProgressDialog mProgressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offer_earncoins, container, false);
        adSetup();
        findViews(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (add == null || add.isExpired()) {
            AdColony.requestInterstitial(PaidToGoApp.ADS_ZONE_ID, listener, adOptions);
        }
    }


    private void findViews(View view) {
        textViewWatchAdd=view.findViewById(R.id.textViewWatchAdd);
        linearLayoutWatchAdd=view.findViewById(R.id.linearLayoutWatchAdd);
        textViewWatchAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add.show();
            }
        });
    }

    void adSetup() {
        AdColonyAppOptions appOptions = new AdColonyAppOptions()
                .setUserID("unique_user_id")
                .setKeepScreenOn(true);

        // Configure AdColony in your launching Activity's onCreate() method so that cached ads can
        // be available as soon as possible.
        AdColony.configure(getActivity(), appOptions, PaidToGoApp.ADS_APP_ID, PaidToGoApp.ADS_ZONE_ID);

        // Optional user metadata sent with the ad options in each request
        AdColonyUserMetadata metadata = new AdColonyUserMetadata()
                .setUserAge(26)
                .setUserEducation(AdColonyUserMetadata.USER_EDUCATION_BACHELORS_DEGREE)
                .setUserGender(AdColonyUserMetadata.USER_MALE);

        // Ad specific options to be sent with request
        adOptions = new AdColonyAdOptions().setUserMetadata(metadata);

        // Set up listener for interstitial ad callbacks. You only need to implement the callbacks
        // that you care about. The only required callback is onRequestFilled, as this is the only
        // way to get an ad object.
        listener = new AdColonyInterstitialListener() {
            @Override
            public void onRequestFilled(AdColonyInterstitial ad) {
                linearLayoutWatchAdd.setVisibility(View.VISIBLE);
                add = ad;
            }

            @Override
            public void onRequestNotFilled(AdColonyZone zone) {
                // Ad request was not filled
                // progress.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onOpened(AdColonyInterstitial ad) {
                // Ad opened, reset UI to reflect state change
                //showButton.setEnabled(false);
                ///progress.setVisibility(View.VISIBLE);

            }

            @Override
            public void onExpiring(AdColonyInterstitial ad) {
                // Request a new ad if ad is expiring
                //showButton.setEnabled(false);
                //progress.setVisibility(View.VISIBLE);
                AdColony.requestInterstitial(PaidToGoApp.ADS_ZONE_ID, this, adOptions);

            }


            @Override
            public void onClosed(AdColonyInterstitial ad) {
                updatePoint();
                //super.onClosed(ad);
            }

        };


        if (add == null || add.isExpired()) {
            AdColony.requestInterstitial(PaidToGoApp.ADS_ZONE_ID, listener, adOptions);
        }
    }


    public void showProgressDialog() {
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setTitle(getResources().getString(R.string.app_name));
        mProgressDialog.show();
    }


    public void hideProgressDialog() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }




    void updatePoint() {
        showProgressDialog();
        PaidToGoService
                .getServiceClient()
                .updatePoints(UserPreferences.getUser(getActivity()).getId(),"1")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        hideProgressDialog();
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        hideProgressDialog();
                        onError(t);
                    }
                });
    }


    public void showErrorAlert(String msg) {
        new AlertDialog.Builder(getContext())
                .setTitle(getResources().getString(R.string.app_name))
                .setMessage(msg)
                .setPositiveButton(R.string.alert_accept, null)
                .create()
                .show();
    }

    public void onError(Throwable throwable) {
        if (throwable instanceof HttpException) {
            showErrorAlert(PaidToGoService.attendError((HttpException) throwable).getDetail());
        } else {
            showErrorAlert(getContext().getString(R.string.connection_problem));
        }
    }

}
