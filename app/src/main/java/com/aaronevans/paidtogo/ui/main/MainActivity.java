package com.aaronevans.paidtogo.ui.main;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aaronevans.paidtogo.data.remote.PaidToGoService;
import com.aaronevans.paidtogo.data.remote.request.get_coins_value.GetCoinsValue;
import com.aaronevans.paidtogo.data.remote.response.BalanceResponse;
import com.aaronevans.paidtogo.data.remote.response.balance_response.NewBalanceResponse;
import com.aaronevans.paidtogo.ui.earn_coins.EarnCoinsActivity;
import com.aaronevans.paidtogo.ui.main.balance.BalanceFragment;
import com.aaronevans.paidtogo.ui.main.chart.ChartFragment;
import com.aaronevans.paidtogo.ui.main.winners.components.UsersAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.components.GPSTracker;
import com.aaronevans.paidtogo.data.entities.PoolType;
import com.aaronevans.paidtogo.data.entities.User;
import com.aaronevans.paidtogo.data.entities.UserEarning;
import com.aaronevans.paidtogo.data.local.UserPreferences;
import com.aaronevans.paidtogo.data.mHandler;
import com.aaronevans.paidtogo.ui.BaseActivity;
import com.aaronevans.paidtogo.ui.ComplaintContactActivity.ComplaintContactFragment;
import com.aaronevans.paidtogo.ui.TermsandConditionActivity.TermsandConditionActivity;
import com.aaronevans.paidtogo.ui.UpgradeToPro.UpgradeToProActivity;
import com.aaronevans.paidtogo.ui.Utils.Constants;
import com.aaronevans.paidtogo.ui.how_works.HowItWorks;
import com.aaronevans.paidtogo.ui.main.balance.FragmentBalancePayment;
import com.aaronevans.paidtogo.ui.main.activity_type.ActivityType_;
import com.aaronevans.paidtogo.ui.main.home.HomeFragment;
import com.aaronevans.paidtogo.ui.main.home.HomeFragment_;
import com.aaronevans.paidtogo.ui.main.leaderboard.LeaderBoardFragment_;
import com.aaronevans.paidtogo.ui.main.settings.SettingsFragment_;
import com.aaronevans.paidtogo.ui.main.stats.UserStatsFragment;
import com.aaronevans.paidtogo.ui.main.winners.WinnersFragment_;
import com.aaronevans.paidtogo.ui.map.MapActivity;
import com.aaronevans.paidtogo.ui.poolRules.PoolRulesFragment;
import com.aaronevans.paidtogo.ui.profile.ProfileActivity_;
import com.aaronevans.paidtogo.ui.start.LoginActivity_;
import com.aaronevans.paidtogo.ui.storeoffer.OfferFragment;
import com.google.gson.Gson;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity implements MainContract.View, FragmentBalancePayment.SetData {

    public static final String LABEL = "main_activity";
    private static final int REQUEST_CHECK_SETTINGS = 2;
    public static final int REQUEST_CODE_ACTIVITY = 201;
    public static String layout_status = "list";
    @ViewById
    public static Toolbar mToolbar;
    @ViewById
    ImageView nav_image;
    @ViewById
    public static ImageView toolbar_image;
    @ViewById
    public static ImageView toolbar_image_new;
    @ViewById
    public static TextView toolbar_title;
    @ViewById
    public static AppBarLayout mAppBar;
    @ViewById
    NavigationView mDrawerView;
    @ViewById
    DrawerLayout mDrawerLayout;
    @ViewById
    TabLayout mTabLayout;
    ImageView nav_user_image;
    ProgressDialog mProgressDialog;
    MainContract.Presenter mPresenter;

    ImageView mHeaderPicture;
    ActionBarDrawerToggle mDrawerToggle;
    private Location location;
    TextView nav_user_name;
    TextView textViewUserPoints;
    GPSTracker gps;
    private String coinsValue;

    private Menu menu;

//    private GoogleFit googleFit;

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GoogleFit.REQUEST_OAUTH_REQUEST_CODE) {
                googleFit.subscribeAPIs();
            }
        }
    }*/

    public interface UpdateFrag {
        void updatefrag(String status);
    }

    UpdateFrag updatfrag;

    public void updateApi(UpdateFrag listener) {
        updatfrag = listener;
    }


    @Override
    protected void onResume() {
        super.onResume();


        try {
            PackageInfo info = this.getPackageManager().getPackageInfo(this.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("TAG", "printHashKey() Hash Key: " + hashKey);
                Log.e("hsh",hashKey);

            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("TAG", "printHashKey()", e);
        } catch (Exception e) {
            Log.e("TAG", "printHashKey()", e);
        }


        User user = UserPreferences.getUser(this);
        if (!TextUtils.isEmpty(user.getPhotoUrl())) {
            if (user.getPhotoUrl().contains(",")) {
                loadImageBase64(user);
            } else
                Glide.with(this)
                        .load(user.getPhotoUrl())
                        .apply(new RequestOptions().centerCrop().error(R.drawable.ic_profile_gray))
                        .into(nav_user_image);
        }
        nav_user_name.setText(user.getFullName());

    }

    @AfterViews
    public void prepareToolbar() {
        setSupportActionBar(mToolbar);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        View hView = mDrawerView.getHeaderView(0);
        textViewUserPoints = (TextView) hView.findViewById(R.id.textViewUserPoints);
        nav_user_name = (TextView) hView.findViewById(R.id.mProfileNameText);
        nav_user_image = (ImageView) hView.findViewById(R.id.mHeaderPicture);

        Menu menu = mDrawerView.getMenu();

        // find MenuItem you want to change
        MenuItem nav_store = menu.findItem(R.id.mStore);

        // set new title to the MenuItem
        if (UserPreferences.getUser(this).getSubscription_id() != 2) {
            nav_store.setTitle("PAIDTOGO FREE STORE");
        } else {
            nav_store.setTitle("PAIDTOGO PRO STORE");
        }




       /* User user = UserPreferences.getUser(this);
        if (!TextUtils.isEmpty(user.getPhotoUrl())) {
            if (user.getPhotoUrl().contains(",")) {
                loadImageBase64(user);
            } else
                Glide.with(this)
                        .load(user.getPhotoUrl())
                        .apply(new RequestOptions().centerCrop().error(R.drawable.ic_profile_gray))
                        .into(nav_user_image);
        }

        nav_user_name.setText(user.getFullName());*/
        UserEarning userEarning = new UserEarning();
//        userEarning = UserPreferences.getEarning(getContext());
        String mainPoints = UserPreferences.getMainPoints(getContext());
        // nav_user_earnings.setText("Dollars Earned: $"+userEarning.getDollar()+"\nPoints Earned: "+userEarning.getPoint());

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setTitle("");

        nav_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDrawerLayout.isDrawerOpen(mDrawerView)) {
                    mDrawerLayout.closeDrawer(Gravity.START);
                } else {
                    mDrawerLayout.openDrawer(Gravity.START);
                }
            }
        });
    }

    @AfterViews
    public void initialState() {


        //confirmDialog();

        HomeFragment initialFragment = HomeFragment_.builder().build();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mFragmentContainer, initialFragment)
                .commit();

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getCoinsValue();

         int openAppCount = UserPreferences.getOpenAppCount(this);
         if (openAppCount == 3) {
             ratingDialog();
         }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sidebar_menu, menu);

         menu.findItem(R.id.mStore).setTitle("jvbk,hv ,hk");


        return true;
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


                    /*----get Lat and Long---------*/

                    double lat = gps.getLatitude();
                    double lon = gps.getLongitude();
                    mHandler.user_lat = lat;
                    mHandler.user_lng = lon;

                    String mLatitute = Double.toString(lat);
                    String mLongitute = Double.toString(lon);

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

                    /*----get Lat and Long---------*/

                    double lat = gps.getLatitude();
                    double lon = gps.getLongitude();
                    mHandler.user_lat = lat;
                    mHandler.user_lng = lon;

                    String mLatitute = Double.toString(lat);
                    String mLongitute = Double.toString(lon);

                }
            }
        } else if (requestCode == REQUEST_CODE_ACTIVITY) {
            if (data != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mFragmentContainer, new PoolRulesFragment())
                        .commit();
            }
        }
        toolbar_image_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (layout_status.equalsIgnoreCase("list")) {
                    toolbar_image_new.setBackground(getContext().getResources().getDrawable(R.drawable.activity_graph));
                    layout_status = "graph";
                } else {
                    toolbar_image_new.setBackground(getContext().getResources().getDrawable(R.drawable.ic_menu_prize_list));
                    layout_status = "list";
                }
                updatfrag.updatefrag(layout_status);
            }
        });

    }

    private void displayLocationSettingsRequest(Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context).addApi(LocationServices.API).build();
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
                        //    Log.i(TAG, "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        //  Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(MainActivity.this, REQUEST_CHECK_SETTINGS);
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

    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment fragment = null;
        switch (item.getItemId()) {

            case R.id.profile:
                ProfileActivity_.intent(this).start();
                break;

            case R.id.home:
                fragment = HomeFragment_.builder().build();
                break;

            case R.id.leaderboard:
                fragment = LeaderBoardFragment_.builder().build();
                Bundle bundle = new Bundle();
                bundle.putString("title", "Leaderboard");
                fragment.setArguments(bundle);
                break;

           /* case R.id.activity:
                fragment = UserActivityFragment_.builder().build();
                break;*/

            case R.id.balance:
                fragment = new BalanceFragment();
                Bundle bundlebalance = new Bundle();
                bundlebalance.putString("title", "Balance");
                fragment.setArguments(bundlebalance);
                /*getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mFragmentContainer, new BalanceFragment())
                        .commit();*/

               /* getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mFragmentContainer, new UserBalanceFragmentNew())
                        .commit();*/
                break;

            case R.id.gym_checkin:
                /*fragment = UserBalanceFragment_.builder().build();*/
                Intent i = new Intent(MainActivity.this, MapActivity.class);
                startActivity(i);
                break;

         /*   case R.id.coupons:
                fragment = CouponsFragment_.builder().build();
                break;*/

            case R.id.settings:
                fragment = SettingsFragment_.builder().build();
                break;

            case R.id.activity_type:
                ActivityType_.intent(this).start();
                break;

            case R.id.stats:
                fragment = new UserStatsFragment();

               /* getSupportFragmentManager().beginTransaction()
//                .setCustomAnimations(R.anim.fragment_slide_left_enter,
//                        R.anim.fragment_slide_left_exit,
//                        R.anim.fragment_slide_right_enter,
//                        R.anim.fragment_slide_right_exit)
                        .replace(R.id.mFragmentContainer, new UserStatsFragment())
                        .commit();*/
                break;

            case R.id.help:

                Intent intnt = new Intent(this, HowItWorks.class).putExtra("from", "main");
                ;
                startActivity(intnt);
               /* getSupportFragmentManager().beginTransaction()
//                .setCustomAnimations(R.anim.fragment_slide_left_enter,
//                        R.anim.fragment_slide_left_exit,
//                        R.anim.fragment_slide_right_enter,
//                        R.anim.fragment_slide_right_exit)
                        .replace(R.id.mFragmentContainer, new HowItWorksWebView())
                        .commit();*/
                break;

            case R.id.charts:
                fragment = new ChartFragment();
                Bundle bundlebalance1 = new Bundle();
                bundlebalance1.putString("title", "Charts");
                fragment.setArguments(bundlebalance1);


//                fragment = new ChartFragment();

                /*getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mFragmentContainer, new UserStatsFragment())
                        .commit();*/
                break;

            case R.id.terms:
                Intent in = new Intent(MainActivity.this, TermsandConditionActivity.class);
                startActivity(in);
                break;

            case R.id.winners:
//                fragment = LeaderBoardFragment_.builder().build();
//                Bundle bundle2=new Bundle();
//                bundle2.putString("title","Winners");
//                fragment.setArguments(bundle2);

                fragment = WinnersFragment_.builder().build();
                break;

           /* case R.id.prizelist:
                fragment = PrizeTableFragment_.builder().build();
                break;*/

            case R.id.upgrade:
                Intent intent = new Intent(this, UpgradeToProActivity.class);
                startActivity(intent);
                break;

            case R.id.pool_rules:

                fragment = new PoolRulesFragment();

                /*getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mFragmentContainer, new PoolRulesFragment())
                        .commit();*/
                break;


            case R.id.complaint:
                fragment = new ComplaintContactFragment();

               /* getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mFragmentContainer, new ComplaintContactFragment())
                        .commit();*/
                break;


            case R.id.mStore:
                fragment = new OfferFragment();
               /* getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mFragmentContainer, new OfferFragment())
                        .commit();*/


                break;


            case R.id.rate_on_store:
                Intent rateIntent = new Intent(android.content.Intent.ACTION_VIEW);
                rateIntent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.aaronevans.paidtogo"));
                startActivity(rateIntent);
                break;



            case R.id.earnCoins:
                startActivity(new Intent(MainActivity.this, EarnCoinsActivity.class));

                //fragment = new EarnCoinsFragment();
               /* Intent rateIntent = new Intent(android.content.Intent.ACTION_VIEW);
                rateIntent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.aaronevans.paidtogo"));
                startActivity(rateIntent);*/
                break;


            case R.id.mLogOut:

                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(this);
                }

                builder.setTitle("Logout")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                mPresenter.logout();
                                LoginActivity_.intent(MainActivity.this)
                                        .flags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                                        .start()
                                        .withAnimation(android.R.anim.fade_in, android.R.anim.fade_out);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                break;

        }

        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mFragmentContainer, fragment).addToBackStack("null")
                    .commit();
        }
        mDrawerLayout.closeDrawer(Gravity.START);
        return super.onOptionsItemSelected(item);

    }

    public void openFragment(String type) {
        Fragment fragment = null;
        switch (type) {
            case Constants.LEADERBOARD:
                fragment = LeaderBoardFragment_.builder().build();
                Bundle bundle = new Bundle();
                bundle.putString("title", "Leaderboard");
                fragment.setArguments(bundle);
                break;
            case Constants.STATS:
                fragment = new UserStatsFragment();
                break;
            case Constants.BALANCE:
                fragment = new BalanceFragment();

                break;
        }

        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mFragmentContainer, fragment).addToBackStack("null")
                    .commit();
        }

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onSupportNavigateUp() {
        mDrawerLayout.openDrawer(Gravity.START);
        return super.onSupportNavigateUp();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @AfterViews
    public void prepareDrawer() {
        mHeaderPicture = mDrawerView.getHeaderView(0).findViewById(R.id.mHeaderPicture);
        mHeaderPicture.setClipToOutline(true);
    }

    public TabLayout getTabLayout() {
        return mTabLayout;
    }

    @Override
    public void showProgressDialog() {
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setTitle(getResources().getString(R.string.app_name));
        mProgressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        if (mProgressDialog!=null&&mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
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

    @Override
    @AfterViews
    public void startPresenter() {
        mPresenter = new MainPresenter().start(this);
//        mPresenter.initLocation();
//        mPresenter.checkGps();
//        googleFit = new GoogleFit(this);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void launchActivity(List<PoolType> poolTypes) {
        /*AvailablePoolsActivity_.intent(this)
                .poolType(poolTypes.get(typePool))
                .location(location)
                .start();*/
    }

    @Override
    public void updateLocation(Location location) {
        this.location = location;
        com.aaronevans.paidtogo.data.mHandler.user_lng = location.getLongitude();
        mHandler.user_lat = location.getLatitude();
        mPresenter.stopLocation();
    }

    public Location getLocation() {
        return location;
    }

    private void loadImageBase64(User user) {
        String[] userUrlImage = user.getPhotoUrl().split(",");
        byte[] imageByteArray = Base64.decode(userUrlImage[1], Base64.DEFAULT);
        Glide.with(this)
                .load(imageByteArray)
                .apply(new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.NONE))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(nav_user_image);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {

            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public void passText(NewBalanceResponse data) {
       /* UserBalanceFragmentNew frag = (UserBalanceFragmentNew) getSupportFragmentManager().findFragmentById(R.id.mFragmentContainer);
        if (frag != null) {
            frag.setAmount(totalAmount, totalPoints, pendingPayment);
        }*/

        BalanceFragment frag = (BalanceFragment) getSupportFragmentManager().findFragmentById(R.id.mFragmentContainer);
        if (frag != null) {
            frag.setAmount(data);
        }

    }


   /* public GoogleFit getGoogleFit() {
        return googleFit;
    }*/


    void getCoinsValue() {
        PaidToGoService
                .getServiceClient()
                .getCoinsValue("coin_value_points")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String stringResponse = response.body().string().toString();
                            Gson gson = new Gson();
                            GetCoinsValue finalResponse = gson.fromJson(stringResponse, GetCoinsValue.class);
                            coinsValue = finalResponse.getResult().getSettingValue();

                            UserPreferences.saveCoinsValuePoints(MainActivity.this, coinsValue);

                            getBalance();
                            getUSDPricePaid();

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


    void getBalance() {


        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String endDate = dateFormat.format(cal.getTime());

        final Calendar cal2 = Calendar.getInstance();
        cal2.add(Calendar.DATE, -30);
        String startDate = dateFormat.format(cal2.getTime());


        PaidToGoService
                .getServiceClient()

                .getBalance(UserPreferences.getUser(getContext()).getId(), startDate, endDate)

                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::loadNestedUser,
                        this::onError

                );
    }

    public void loadNestedUser(ArrayList<BalanceResponse> userResponse) {
        int coins = (int) (userResponse.get(0).getBalance().getEarnedPoints() / Double.parseDouble(coinsValue));
        textViewUserPoints.setText(coins + " Coins");
    }


    public void onError(Throwable throwable) {
        if (throwable instanceof HttpException) {
            showErrorAlert(PaidToGoService.attendError((HttpException) throwable).getDetail());
        } else {
            showErrorAlert(getContext().getString(R.string.connection_problem));
        }
    }


    private void ratingDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.rating_dialog);
        TextView textViewNotNow = dialog.findViewById(R.id.textViewNotNow);
        LinearLayout linearLayout = dialog.findViewById(R.id.linearLayout);
        RatingBar ratingBar = dialog.findViewById(R.id.ratingBar);


        ratingBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    Uri uri = Uri.parse("market://details?id=" + getPackageName());
                    Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    try {
                        startActivity(myAppLinkToMarket);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(MainActivity.this, " unable to find market app", Toast.LENGTH_LONG).show();
                    }

                    UserPreferences.saveOpenAppCount(MainActivity.this, 4);
                    dialog.dismiss();
                    //Toast.makeText(getContext(), "working fine", Toast.LENGTH_LONG).show();
                }
                return true;

            }
        });


        textViewNotNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserPreferences.saveOpenAppCount(MainActivity.this, 0);
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }


    void getUSDPricePaid() {
        PaidToGoService
                .getServiceClient()
                .getCoinsValue("coin_value_in_usd_paid_user")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String stringResponse = response.body().string().toString();
                            Gson gson = new Gson();
                            GetCoinsValue finalResponse = gson.fromJson(stringResponse, GetCoinsValue.class);
                            String settingValue = finalResponse.getResult().getSettingValue();
                            UserPreferences.saveUSDPricePaid(MainActivity.this, settingValue);
                            getUSDPriceFree();
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

    void getUSDPriceFree() {
        PaidToGoService
                .getServiceClient()
                .getCoinsValue("coin_value_in_usd_free_user")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String stringResponse = response.body().string().toString();
                            Gson gson = new Gson();
                            GetCoinsValue finalResponse = gson.fromJson(stringResponse, GetCoinsValue.class);
                            String settingValue = finalResponse.getResult().getSettingValue();
                            UserPreferences.saveUSDPriceFree(MainActivity.this, settingValue);
                            //pagerSetup();
                            // mBinding.mIndicator.setupWithViewPager(mBinding.mPager, true);
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