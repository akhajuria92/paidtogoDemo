package com.aaronevans.paidtogo.ui.main.balance;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.aaronevans.paidtogo.PaidToGoApp;
import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.local.UserPreferences;
import com.aaronevans.paidtogo.data.remote.response.balance_response.Data;
import com.aaronevans.paidtogo.data.remote.response.balance_response.NewBalanceResponse;
import com.aaronevans.paidtogo.ui.BaseFragment;
import com.aaronevans.paidtogo.webService.Firebase.FcmPojo;
import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyAdOptions;
import com.adcolony.sdk.AdColonyAppOptions;
import com.adcolony.sdk.AdColonyInterstitial;
import com.adcolony.sdk.AdColonyInterstitialListener;
import com.adcolony.sdk.AdColonyUserMetadata;
import com.adcolony.sdk.AdColonyZone;

import static com.aaronevans.paidtogo.ui.main.MainActivity.toolbar_title;

public class BalanceFragment extends BaseFragment {
    TextView textViewTotalCoins, textViewCurrentCycleCoins, textViewTotalEarnerUsd,textViewCurrentCycleUsd;
    String title = null;


    private AdColonyInterstitial add;
    private AdColonyInterstitialListener listener;
    private AdColonyAdOptions adOptions;

    boolean isAddShow = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_balance, container, false);
        findViews(view);
        adSetup();
        if (add == null || add.isExpired()) {
            // Optionally update location info in the ad options for each request:
            // LocationManager locationManager =
            //     (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            // Location location =
            //     locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            // adOptions.setUserMetadata(adOptions.getUserMetadata().setUserLocation(location));
            AdColony.requestInterstitial(PaidToGoApp.ADS_ZONE_ID, listener, adOptions);
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadFragment(new FragmentBalancePayment());
    }


    void adSetup(){
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
                // Ad passed back in request filled callback, ad can now be shown
                add = ad;
                if(!isAddShow){
                    isAddShow=true;
                    add.show();
                }

                //showButton.setEnabled(true);
                // progress.setVisibility(View.INVISIBLE);

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
        };

        // Set up button to show an ad when clicked
     /*   showButton = findViewById(R.id.showbutton);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ad.show();
            }
        });*/
    }


    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            title = bundle.getString("title", "");
            toolbar_title.setText(title);
        }
    }

    public boolean loadFragment(Fragment fragment) {
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
        return true;
    }

    void findViews(View view){
        textViewTotalCoins = view.findViewById(R.id.textViewTotalCoins);
        textViewCurrentCycleCoins = view.findViewById(R.id.textViewCurrentCycleCoins);
        textViewTotalEarnerUsd = view.findViewById(R.id.textViewTotalEarnerUsd);
        textViewCurrentCycleUsd = view.findViewById(R.id.textViewCurrentCycleUsd);

    }



    public void setAmount(NewBalanceResponse response) {

       // Toast.makeText(getActivity(), response.getPendingPaymentsSum()+"", Toast.LENGTH_SHORT).show();


        double paidEarnedPoints=0.0;
        double freeEarnedPoints=0.0;
        double totalEarnedPoints=0.0;
        double currentEarnedPoints=0.0;
        double spentCoins=0.0;

        double coinsValue=Double.parseDouble(UserPreferences.getCoinsValue(getActivity()));

        if(response.getData().getPaid()!=null){
            paidEarnedPoints=response.getData().getPaid().getAllEarnedPoints();
        }

        if(response.getData().getFree()!=null){
            freeEarnedPoints=response.getData().getFree().getAllEarnedPoints();
        }
        if(response.getData().getSpent_coins()!=null){
            spentCoins=response.getData().getSpent_coins();
        }

        totalEarnedPoints=paidEarnedPoints+freeEarnedPoints;

        currentEarnedPoints=paidEarnedPoints+freeEarnedPoints-spentCoins;

        double finalTotalEarnedCoin = totalEarnedPoints / coinsValue;
        double finalCurrentEarnedCoin = currentEarnedPoints / coinsValue;


        textViewTotalCoins.setText("Total Coins Earned(all time): " + (int) finalTotalEarnedCoin);
        textViewCurrentCycleCoins.setText("Current Balance: " + (int) finalTotalEarnedCoin);
        String usdValue="";
        if (UserPreferences.getUser(getActivity()).getSubscription_id() == 2){
             usdValue = UserPreferences.getUSDPricePaid(getActivity());
        }else{
             usdValue = UserPreferences.getUSDPriceFree(getActivity());
        }
        double totalEarnedUsdYearly = totalEarnedPoints * Double.parseDouble(usdValue);
       // double totalEarnedUsdMonthly = currentEarnedPoints * Double.parseDouble(usdValue);


        textViewTotalEarnerUsd.setText("Potential USD Earnings*: $" + String.format("%.2f", totalEarnedUsdYearly));
        textViewCurrentCycleUsd.setText("Total Earned USD Value Current Cycle: $" + String.format("%.2f", response.getPendingPaymentsSum()));

      /*  if (UserPreferences.getUser(getActivity()).getSubscription_id() == 2){
             double coinsValue=Double.parseDouble(UserPreferences.getCoinsValue(getActivity()));
             int coinsyearly = (int)(data.getPaid().getAllEarnedPoints() / coinsValue);
             int coinsMonthly = (int)(data.getPaid().getMonthEarnedPoints() / coinsValue);

             textViewTotalCoins.setText("Total Coins Earned(all time): " + coinsyearly);
             textViewCurrentCycleCoins.setText("Current Cycle Coins Earned: " + coinsMonthly);

             String usdValue = UserPreferences.getUSDPricePaid(getActivity());
             double totalEarnedUsdYearly = coinsyearly * Double.parseDouble(usdValue);
             double totalEarnedUsdMonthly = coinsMonthly * Double.parseDouble(usdValue);




         }else{

             double coinsValue=Double.parseDouble(UserPreferences.getCoinsValue(getActivity()));
             int coinsyearly = (int)(data.getFree().getAllEarnedPoints() / coinsValue);
             int coinsMonthly = (int)(data.getFree().getMonthEarnedPoints() / coinsValue);
             textViewTotalCoins.setText("Total Coins Earned(all time): " + coinsyearly);
             textViewCurrentCycleCoins.setText("Current Cycle Coins Earned: " + coinsMonthly);

             String usdValue = UserPreferences.getUSDPriceFree(getActivity());
             double totalEarnedUsdYearly = coinsyearly * Double.parseDouble(usdValue);
             double totalEarnedUsdMonthly = coinsMonthly * Double.parseDouble(usdValue);



             textViewTotalEarnerUsd.setText("Total Earned USD Value: $" + String.format("%.2f", totalEarnedUsdYearly));
             textViewCurrentCycleUsd.setText("Total Pending Payment Amount: $" + String.format("%.2f", totalEarnedUsdMonthly));
         }*/
    }
}
