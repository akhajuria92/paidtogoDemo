package com.aaronevans.paidtogo.ui.main.home;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;

import com.aaronevans.paidtogo.ui.earn_coins.EarnCoinsActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.gson.Gson;
import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.components.GPSTracker;
import com.aaronevans.paidtogo.data.entities.Activity;
import com.aaronevans.paidtogo.data.entities.UserEarning;
import com.aaronevans.paidtogo.data.local.UserPreferences;
import com.aaronevans.paidtogo.data.mHandler;
import com.aaronevans.paidtogo.data.remote.PaidToGoService;
import com.aaronevans.paidtogo.data.remote.request.ActivityRegister;
import com.aaronevans.paidtogo.data.remote.request.get_coins_value.GetCoinsValue;
import com.aaronevans.paidtogo.data.remote.response.PoolResponse;
import com.aaronevans.paidtogo.databinding.FragmentMainHomeBinding;
import com.aaronevans.paidtogo.ui.BaseFragment;
import com.aaronevans.paidtogo.ui.UpgradeToPro.UpgradeToProActivity;
import com.aaronevans.paidtogo.ui.Utils.Constants;
import com.aaronevans.paidtogo.ui.main.MainActivity;
import com.aaronevans.paidtogo.ui.main.activity_type.ActivityType_;
import com.aaronevans.paidtogo.ui.main.home.components.ExercisePager;
import com.aaronevans.paidtogo.ui.main.stats.UserStatsFragment;
import com.aaronevans.paidtogo.webService.Firebase.Controller;
import com.aaronevans.paidtogo.webService.Firebase.FcmRequest;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.BindingObject;
import org.androidannotations.annotations.DataBound;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.BindingAdapter;
import androidx.viewpager.widget.ViewPager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

import static android.content.Context.LOCATION_SERVICE;
import static com.aaronevans.paidtogo.ui.main.MainActivity.toolbar_image;
import static com.aaronevans.paidtogo.ui.main.MainActivity.toolbar_title;

/**
 * Created by leandro on 2/11/17.
 */

@DataBound
@EFragment(R.layout.fragment_main_home)
public class HomeFragment extends BaseFragment implements HomeContract.View, LocationListener {
    private static final int GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 1;
    private static final int REQUEST_CHECK_SETTINGS = 2;
    private static final int REQUEST_LOCATION = 3;
    ExercisePager mAdapter = null;
    private LocationCallback mLocationCallback;

    public static final float PARAMETER_CONVERT_MILES = 0.000621371f;
    @BindingObject
    FragmentMainHomeBinding mBinding;
    LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationClient;
    private GPSTracker gps;

    private ProgressDialog mProgressDialog = null;
    private ArrayList<PoolResponse> listActivities = new ArrayList<>();
    private HomeContract.Presenter mHomePresenter;
    private LocationManager mLocationManager;
    public static AppCompatActivity baseContext;
    Controller controller;
    FcmRequest fcmRequest;
    String settingValue = "0";


    ArrayList<ActivityRegister> activity1;
    //    LinearLayout ll_leaderboard,ll_stats,llbalance;
    private View contentView;
    private long syncTime;


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        contentView = super.onCreateView(inflater, container, savedInstanceState);
//        if (contentView == null) {
//            contentView = inflater.inflate(R.layout.fragment_main_home, container, false);
//        }
//
//        ll_leaderboard=contentView.findViewById(R.id.ll_leaderboard);
//        ll_stats=contentView.findViewById(R.id.ll_stats);
//        llbalance=contentView.findViewById(R.id.llbalance);
//
//        return contentView;
//    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        baseContext = (MainActivity) context;

        activity1 = new ArrayList<>();
//        getCoinsValue();
    }

    public static void openNextActivity() {
        Intent intent = new Intent(baseContext, UpgradeToProActivity.class);
        baseContext.startActivityForResult(intent, 201);
    }


    @BindingAdapter("scrollListener")
    public static void setOnLastItemListener(ViewPager pager, HomeContract.View.OnPageScrollListener listener) {
        pager.clearOnPageChangeListeners();
        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (listener != null) listener.onShowingItem(position);
                if (position == 1) {
                    if (UserPreferences.getUser(baseContext).getSubscription_id() == 1) {
                        showProAlert();
                    }

                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (listener != null) listener.onNewScroll(position, positionOffset);
            }
        });
    }

    public static void showProAlert() {
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(baseContext);

        // Setting Dialog Title
        alertDialog.setTitle("Upgrade To Pro");

        // Setting Dialog Message
        alertDialog
                .setMessage("Upgrade to Pro to earn 2x more Coins per mile and 3x more Coins per month. Use Coins to purchase Paypal payouts. You can also remove ads and enable background tracking with Pro. Try it free for 7 days!");

        // On pressing Settings button
        alertDialog.setPositiveButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        // on pressing cancel button
        alertDialog.setNegativeButton("Upgrade",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        openNextActivity();

                    }
                });

        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        /*    ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                    2);*/
            requestPermissions(
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.ACCESS_FINE_LOCATION},
                    2);
            return;
        }
        boolean gps_enabled = false;

        try {

            LocationManager locationManager = (LocationManager) getContext()
                    .getSystemService(LOCATION_SERVICE);
            // getting GPS status
            gps_enabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }
        if (!gps_enabled) {
            displayLocationSettingsRequest(getContext());
        } else {
            gps = new GPSTracker(getContext());
            if (gps.canGetLocation()) {

                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }

                mLocationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

                //postActivity();
            }
        }

        sendTokenFCMServer();

        //getCoinsValue();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.e("DialogPermission", "Ho! Ho! Ho!");  // Log not printed
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 2) {

            boolean gps_enabled = false;

            try {

                LocationManager locationManager = (LocationManager) getContext()
                        .getSystemService(LOCATION_SERVICE);
                // getting GPS status
                gps_enabled = locationManager
                        .isProviderEnabled(LocationManager.GPS_PROVIDER);
            } catch (Exception ex) {
            }
            if (!gps_enabled) {
                displayLocationSettingsRequest(getContext());
            } else {
                gps = new GPSTracker(getContext());
                if (gps.canGetLocation()) {

                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPporermissions for more details.
                        return;
                    }

                    mLocationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                    mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

                    //  postActivity();
                }
            }


        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == 1) {

                if (gps.canGetLocation()) {

                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    mLocationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                    mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

                    //  postActivity();
                }
            }
        }

    }

    private void stopLocationUpdates() {
        mLocationManager.removeUpdates(this);
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                mLocationCallback,
                null /* Looper */);
    }


    public void pagerSetup() {

        mAdapter = new ExercisePager(getContext(), this.listActivities, settingValue);
        mBinding.mPager.setAdapter(mAdapter);
        mBinding.startAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityType_.intent(HomeFragment.this).start();
            }
        });


        mBinding.llLeaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).openFragment(Constants.LEADERBOARD);
            }
        });


        mBinding.llStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).openFragment(Constants.STATS);
            }
        });
//

        mBinding.llbalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), EarnCoinsActivity.class));

                //((MainActivity) getActivity()).openFragment(Constants.BALANCE);
            }
        });

//
//        llbalance.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ((MainActivity)getActivity()).openFragment(Constants.BALANCE);
//            }
//        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mOpenMap:
                //  ActivityType_.intent(this).start();
                // Toast.makeText(getContext(), "Map is under Development", Toast.LENGTH_SHORT).show();
            /*    Intent i = new Intent(getContext(), MapActivity.class);
                startActivity(i);*/

                getFragmentManager().beginTransaction()
//                .setCustomAnimations(R.anim.fragment_slide_left_enter,
//                        R.anim.fragment_slide_left_exit,
//                        R.anim.fragment_slide_right_enter,
//                        R.anim.fragment_slide_right_exit)
                        .replace(R.id.mFragmentContainer, new UserStatsFragment())
                        .commit();
                break;

            case R.id.mSync:
                postData();
                //postActivity();
                break;
        }
        return true;
    }

  /*  private void showRegisterActivityDialog() {
        if (mAdapter.getCount() > 1) {
            ArrayList<String> pools = new ArrayList<>();
            for (PoolResponse activityItem : listActivities) {
                pools.add(activityItem.getName());
            }
            float a = UserPreferences.getTotalDistance(getContext());
            float b = UserPreferences.getTotalSteps(getContext());
            ArrayList<String> activityType = new ArrayList<>();
            activityType.add("Walk");
            activityType.add("Bike");
            activityType.add("Exercise");
            new SyncDialog(getContext())
                    .setActivePools(pools)
                    .setActivityTypes(activityType)
                    .setOnClickListener(new GeneralInterface.OnSyncListener() {
                        @Override
                        public void onSync(int poolPosition, int activityType) {

                            //postActivity(String.valueOf(listActivities.get(poolPosition).getId()), String.valueOf(activityType));
                        }
                    }).build().show();
        } else {
            Toast.makeText(getContext(), "Wait to get the pools list", Toast.LENGTH_SHORT).show();
        }
    }
*/

    private void postData() {
      /*  Location location = ((MainActivity) getActivity()).getLocation();
        if (location != null) {*/
        showProgressDialog();
        long currentTime = Calendar.getInstance().getTimeInMillis();

        ArrayList<ActivityRegister> temp = new ArrayList<>();
        ActivityRegister OnFeet = new ActivityRegister();
        OnFeet.setActivityType("1");
        OnFeet.setAccessToken(UserPreferences.getToken(getContext()));
        OnFeet.setStartDateTime(UserPreferences.getStartTimeOnFeet(getContext()));
        OnFeet.setEndLatitude(String.valueOf(mHandler.user_lat));
        OnFeet.setEndLongitude(String.valueOf(mHandler.user_lng));
        OnFeet.setMilesTraveled(String.valueOf(UserPreferences.getTotalDistance(getContext()) * PARAMETER_CONVERT_MILES));
        OnFeet.setStartLatitude(UserPreferences.getStartLatOnFeet(getContext()));
        OnFeet.setStartLongitude(UserPreferences.getStartLngOnFeet(getContext()));
        OnFeet.setTotalSteps(String.valueOf(UserPreferences.getTotalSteps(getContext())));
        OnFeet.setEndDateTime(formatTime(currentTime,
                "yyyy-MM-dd HH:mm:ss"));
        OnFeet.setCalories(String.valueOf(UserPreferences.getTotalDistance(getContext()) * PARAMETER_CONVERT_MILES * 100));
        OnFeet.setCreatedAt("");
        OnFeet.setUpdatedAt("");

        ActivityRegister OnBike = new ActivityRegister();
        OnBike.setActivityType("2");
        OnBike.setAccessToken(UserPreferences.getToken(getContext()));
        OnBike.setStartDateTime("");
        OnBike.setEndLatitude("");
        OnBike.setEndLongitude("");
        OnBike.setMilesTraveled(String.valueOf(UserPreferences.getTotalDistanceOnBike(getContext()) * PARAMETER_CONVERT_MILES));
        OnBike.setStartLatitude("");
        OnBike.setStartLongitude("");
        OnBike.setTotalSteps("");
        OnBike.setEndDateTime(formatTime(currentTime,
                "yyyy-MM-dd HH:mm:ss"));
        OnBike.setCalories(String.valueOf(UserPreferences.getTotalDistance(getContext()) * PARAMETER_CONVERT_MILES * 100));
        OnBike.setCreatedAt("");
        OnBike.setUpdatedAt("");
        temp.add(OnFeet);
        //   temp.add(OnBike);

        PaidToGoService.getServiceClient().registerActivity(temp)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::registerActivityResponse,
                        this::onError
                );
        //  }
    }

    private void registerActivityResponse(ResponseBody activity) {

       /* UserPreferences.saveAutoTrackStartTime("", getContext());
        UserPreferences.saveStartLatOnFeet("", getContext());
        UserPreferences.saveTotalSteps(0, getContext());
        UserPreferences.saveTotalDistance("0", getContext());
        UserPreferences.saveStartTimeOnFeet("", getContext());
        UserPreferences.saveTotalDistanceOnBike(0, getContext());*/
        //   Toast.makeText(getContext(), "Activity Registered", Toast.LENGTH_SHORT).show();
       /* ActivityRegisteredFragment fragment = new ActivityRegisteredFragment();
        Bundle bundle = new Bundle();
        bundle.putString("miles", String.valueOf(activity.getMilesTraveled()));
        bundle.putString("gas", String.valueOf(activity.getSaveTraffic()));
        bundle.putString("co2", String.valueOf(activity.getSavedCo2()));
        bundle.putString("cal", String.valueOf(activity.getSavedCalories()));
        bundle.putString("steps", String.valueOf(activity.getMilesTraveled()*2000));

        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction()
//                .setCustomAnimations(R.anim.fragment_slide_left_enter,
//                        R.anim.fragment_slide_left_exit,
//                        R.anim.fragment_slide_right_enter,
//                        R.anim.fragment_slide_right_exit)
                .replace(R.id.mFragmentContainerActivity, fragment)
                .commit();*/
        showErrorAlert("Activity Registered");
        //    Toast.makeText(getActivity(), "Activity Registered", Toast.LENGTH_SHORT).show();
        hideProgressDialog();
    }

    public void onError(Throwable throwable) {
        if (throwable instanceof HttpException) {
            showErrorAlert(PaidToGoService.attendError((HttpException) throwable).getDetail());
        } else {
            showErrorAlert(getContext().getString(R.string.connection_problem));
        }
    }

    /*private void postActivity(String poolId, String activityType) {
        Location location = ((MainActivity) getActivity()).getLocation();
        if (location != null) {
            long syncTime = UserPreferences.getSyncTime(getContext());
            long currentTime = Calendar.getInstance().getTimeInMillis();
            if (currentTime - syncTime >= 1000 * 60 * 60 * 24) {
                ((MainActivity) getActivity()).getGoogleFit().setListener(new GeneralInterface.OnActivityPost() {
                    @Override
                    public void onActivityPost(int totalSteps, float totalMiles) {
                        RegisterActivity activity = new RegisterActivity();
                        activity.setPoolId(poolId);
                        activity.setActivityType(activityType);
                        activity.setAccessToken(UserPreferences.getToken(getContext()));
                        activity.setStartDateTime(formatTime(syncTime, "yyyy-MM-dd HH:mm:ss"));

                        activity.setEndDateTime(formatTime(currentTime,
                                "yyyy-MM-dd HH:mm:ss"));
                        activity.setEndLatitude("30");
                        activity.setEndLongitude("71");
                        activity.setStartLatitude("30");
                        activity.setStartLongitude("71");

                        activity.setMilesTraveled(String.valueOf(totalSteps));
                        activity.setTotalSteps(String.valueOf(totalMiles));
                        mHomePresenter.registerActivity(activity);
                    }
                }).subscribeAPIs();
            } else {
                showErrorAlert("Data is already Synced");
            }
        }
    }*/
    @Override
    public void showProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setTitle(getResources().getString(R.string.app_name));
        mProgressDialog.show();
        mBinding.mPager.setVisibility(View.GONE);
    }

    @Override
    public void hideProgressDialog() {
        if (mProgressDialog.isShowing())
            mProgressDialog.dismiss();
        mBinding.mPager.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorAlert(String msg) {
        new AlertDialog.Builder(getContext())
                .setTitle(getResources().getString(R.string.app_name))
                .setMessage(msg)
                .setPositiveButton(R.string.alert_accept, null)
                .create()
                .show();
    }


    @UiThread(delay = 500)
    @AfterViews
    @Override
    public void startPresenter() {
        /*pagerSetup();
        mBinding.mIndicator.setupWithViewPager(mBinding.mPager, true);
        mHomePresenter = new HomePresenter().start(this);
        try {
            mHomePresenter.loadActivities(UserPreferences.getUser(getContext()).getId());
        } catch (Exception e) {
            e.printStackTrace();
        }*/
//        ll_leaderboard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ((MainActivity)getActivity()).openFragment(Constants.LEADERBOARD);
//            }
//        });
//
//        ll_stats.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                ((MainActivity)getActivity()).openFragment(Constants.STATS);
//            }
//        });
//
//        llbalance.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ((MainActivity)getActivity()).openFragment(Constants.BALANCE);
//            }
//        });

    }

    @Override
    public void onActivitiesResponse(ArrayList<PoolResponse> listActivities) {
        this.listActivities.clear();
        this.listActivities.addAll(listActivities);
        double dollar = 0.0;
        int points = 0;
        for (int i = 0; i < listActivities.size(); i++) {

            if (listActivities.get(i).getRewardType() == 1) {
                dollar = dollar + listActivities.get(i).getSummary().getEarnedMoney();
            } else {
                points = (int) (points + listActivities.get(i).getSummary().getEarnedPoints());
            }
        }
        UserEarning userEarning = new UserEarning();
        userEarning.setDollar(String.valueOf(dollar));
        userEarning.setPoint(String.valueOf(points));
        UserPreferences.saveEarning(getContext(), userEarning);
        mHandler.reg_pools = listActivities;
        pagerSetup();
    }

    @Override
    public void onRegisterActivity(Activity activity) {
        mHomePresenter.loadActivities(UserPreferences.getUser(getContext()).getId());
        UserPreferences.saveSynyTime(getContext(), Calendar.getInstance().getTimeInMillis());
    }


    public String formatTime(long time, String regex) {
        return DateFormat.format(regex, time).toString();
    }


    public void notifyUpdate() {

    }

    private void displayLocationSettingsRequest(Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        //    Log.i(TAG, "All location  settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        //  Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            //      Log.i(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        //   Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        com.aaronevans.paidtogo.data.mHandler.user_lat = location.getLatitude();
        com.aaronevans.paidtogo.data.mHandler.user_lng = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onResume() {
        super.onResume();
        toolbar_image.setVisibility(View.VISIBLE);
        toolbar_title.setVisibility(View.GONE);


        pagerSetup();
        mBinding.mIndicator.setupWithViewPager(mBinding.mPager, true);
        mHomePresenter = new HomePresenter().start(this);
        try {
            mHomePresenter.loadActivities(UserPreferences.getUser(getContext()).getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendTokenFCMServer() {
        try {
            controller = new Controller();
            fcmRequest = new FcmRequest();
            fcmRequest.device_type = "1";
            fcmRequest.device_token = UserPreferences.getFcmToken(baseContext);
            fcmRequest.user_id = UserPreferences.getUser(getContext()).getId();
            controller.start(fcmRequest, baseContext);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void postActivity() {
        syncTime = UserPreferences.getSyncTime(getContext());
        activity1.get(0).setActivityType("1");
        long currentTime = Calendar.getInstance().getTimeInMillis();
        activity1.get(0).setEndDateTime(formatTime(currentTime, "yyyy-MM-dd HH:mm:ss"));
        activity1.get(0).setEndLatitude(String.valueOf(mHandler.user_lat));
        activity1.get(0).setEndLongitude(String.valueOf(mHandler.user_lng));
        activity1.get(0).setAccessToken(String.valueOf(UserPreferences.getToken(getContext())));
        activity1.get(0).setCalories(UserPreferences.getCalories(getContext()));
        activity1.get(0).setCalories(UserPreferences.getCalories(getContext()));
        float distance = UserPreferences.getTotalDistance(getContext());

        activity1.get(0).setMilesTraveled(distance+"");

        activity1.get(0).setStartDateTime(String.valueOf(formatTime(syncTime, "yyyy-MM-dd HH:mm:ss")));
        activity1.get(0).setStartLatitude(String.valueOf(mHandler.user_lat));
        activity1.get(0).setStartLongitude(String.valueOf(mHandler.user_lng));
        activity1.get(0).setTotalSteps(UserPreferences.getTotalSteps(getContext())+"");
        activity1.get(0).setTotalSteps(UserPreferences.getTotalSteps(getContext())+"");

        ArrayList<ActivityRegister> temp = new ArrayList<>();
        activity1.get(0).setCreatedAt(formatTime(currentTime,
                "yyyy-MM-dd HH:mm:ss"));
        //   if(c1==true) {
        temp.addAll(activity1);
        //temp.addAll(activity1);


        //    if(c1==true||c2==true) {

        showProgressDialog();
        PaidToGoService.getServiceClient().registerActivity(temp)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::registerActivityResponse,
                        this::onError
                );
        //   }
        //  }
    }





}