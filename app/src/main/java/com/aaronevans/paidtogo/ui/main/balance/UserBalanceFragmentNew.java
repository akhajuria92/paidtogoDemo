package com.aaronevans.paidtogo.ui.main.balance;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aaronevans.paidtogo.data.remote.response.balance_response.NewBalanceResponse;
import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyAdOptions;
import com.adcolony.sdk.AdColonyAppOptions;
import com.adcolony.sdk.AdColonyInterstitial;
import com.adcolony.sdk.AdColonyInterstitialListener;
import com.adcolony.sdk.AdColonyUserMetadata;
import com.adcolony.sdk.AdColonyZone;
import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.ui.BaseFragment;
import com.aaronevans.paidtogo.ui.UpgradeToPro.UpgradeToProActivity;
import com.aaronevans.paidtogo.webService.Controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import androidx.fragment.app.Fragment;

import static com.aaronevans.paidtogo.ui.main.MainActivity.mAppBar;
import static com.aaronevans.paidtogo.ui.main.MainActivity.toolbar_image;
import static com.aaronevans.paidtogo.ui.main.MainActivity.toolbar_title;

public class UserBalanceFragmentNew extends BaseFragment implements Controller.response {

    Context context;
    LinearLayout ll_payment, ll_date;
    TextView tv_date;

    private int mYear;
    private int mMonth;
    private int mDay;

    TextView tv_earned, tv_paid, tv_pending;

    Controller controller;
    BalanceRequest balanceRequest;


    final private String APP_ID = "appeabfb6fc9c9f49b597";
    final private String ZONE_ID = "vz0af1d8c6daac43a9bc";
    final private String TAG = "AdColonyDemo";


    private AdColonyInterstitial add;
    private AdColonyInterstitialListener listener;
    private AdColonyAdOptions adOptions;

    boolean isAddShow=false;


    public UserBalanceFragmentNew() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_balance_fragment_new, container, false);
        ll_payment = view.findViewById(R.id.ll_payment);
        tv_date = view.findViewById(R.id.tv_date);

        tv_earned = view.findViewById(R.id.tv_earned);
        tv_paid = view.findViewById(R.id.tv_paid);
        tv_pending = view.findViewById(R.id.tv_pending);

        callWebServiceBalance(getCurrentMonth(), getCurrentYear());

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        String month_name = month_date.format(cal.getTime());

        String date = month_name + "-" + year;
        tv_date.setText(date);

        showProAlert();

        ll_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new FragmentBalancePayment());
            }
        });

        ll_date = view.findViewById(R.id.ll_date);
        ll_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new FragmentDateBalance());
                openDateMonthPicker();
            }
        });

        loadFragment(new FragmentDateBalance());


        adSetup();

        if (add == null || add.isExpired()) {
            // Optionally update location info in the ad options for each request:
            // LocationManager locationManager =
            //     (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            // Location location =
            //     locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            // adOptions.setUserMetadata(adOptions.getUserMetadata().setUserLocation(location));
            AdColony.requestInterstitial(ZONE_ID, listener, adOptions);
        }
        return view;
    }

    void adSetup(){
        AdColonyAppOptions appOptions = new AdColonyAppOptions()
                .setUserID("unique_user_id")
                .setKeepScreenOn(true);

        // Configure AdColony in your launching Activity's onCreate() method so that cached ads can
        // be available as soon as possible.
        AdColony.configure(getActivity(), appOptions, APP_ID, ZONE_ID);

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
                Log.d(TAG, "onRequestFilled");
            }

            @Override
            public void onRequestNotFilled(AdColonyZone zone) {
                // Ad request was not filled
                // progress.setVisibility(View.INVISIBLE);
                Log.d(TAG, "onRequestNotFilled");
            }

            @Override
            public void onOpened(AdColonyInterstitial ad) {
                // Ad opened, reset UI to reflect state change
                //showButton.setEnabled(false);
                ///progress.setVisibility(View.VISIBLE);
                Log.d(TAG, "onOpened");
            }

            @Override
            public void onExpiring(AdColonyInterstitial ad) {
                // Request a new ad if ad is expiring
                //showButton.setEnabled(false);
                //progress.setVisibility(View.VISIBLE);
                AdColony.requestInterstitial(ZONE_ID, this, adOptions);
                Log.d(TAG, "onExpiring");
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

    private void callWebServiceBalance(String month, String year) {
        controller = new Controller();
        balanceRequest = new BalanceRequest();
        balanceRequest.month = month;
        balanceRequest.year = year;
        balanceRequest.userId = "270";
        controller.start(balanceRequest, (Controller.response) UserBalanceFragmentNew.this, context);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public boolean loadFragment(Fragment fragment) {
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbar_image.setVisibility(View.GONE);
        toolbar_title.setVisibility(View.VISIBLE);
        toolbar_title.setText("Balance");
        mAppBar.setBackgroundColor(context.getResources().getColor(R.color.balance_gray));
    }

    @Override
    public  void onStop(){
        super.onStop();
        mAppBar.setBackgroundColor(context.getResources().getColor(R.color.white));
    }

    public void showProAlert() {
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle("Paidtogo");

        // Setting Dialog Message
        alertDialog.setMessage("You are not subscribed to Pro");

        // On pressing Settings button
        alertDialog.setPositiveButton("Upgrade to Pro",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        moveNext();
                    }
                });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    private void moveNext() {
        Intent intent = new Intent(getActivity(), UpgradeToProActivity.class);
        startActivity(intent);
    }

    private void openDateMonthPicker() {
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear=monthOfYear+1;
                        callWebServiceBalance(String.valueOf(monthOfYear), String.valueOf(year));
                    }
                }, year, month, mDay);
        ((ViewGroup) datePickerDialog.getDatePicker()).findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE);
        datePickerDialog.show();
    }

    public void setAmount(String totalAmount, String totalPoints, String pending_payment) {
        myLog("userbalance", "setAmount", "success");
        try {
            totalPoints = String.format("%.2f", String.valueOf(totalPoints));
        } catch (Exception e) {
            e.printStackTrace();
        }
        tv_earned.setText("Total Points Earned : " + totalPoints);
        tv_paid.setText("Total Payments Paid : $" + totalAmount+".00");
        tv_pending.setText("Total Payments Pending : $" + pending_payment+".00");
    }

    @Override
    public void passList(NewBalanceResponse response) {
       /* myLog("103", "++passList++", fullDatumList.toString());
        String totalPoints = String.format("%.2f", resource.totalPoints);
        tv_earned.setText("Total Points Earned : " + totalPoints);
        tv_paid.setText("Total Payments Paid : $" + resource.totalAmount+".00");
        tv_pending.setText("Total Payments Pending : $" + resource.pendingPayment+".00");*/
    }

}