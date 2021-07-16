package com.aaronevans.paidtogo.ui.main.leaderboard;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aaronevans.paidtogo.PaidToGoApp;
import com.aaronevans.paidtogo.ui.UpgradeToPro.UpgradeToProActivity;
import com.aaronevans.paidtogo.ui.main.MainActivity;
import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyAdOptions;
import com.adcolony.sdk.AdColonyAppOptions;
import com.adcolony.sdk.AdColonyInterstitial;
import com.adcolony.sdk.AdColonyInterstitialListener;
import com.adcolony.sdk.AdColonyUserMetadata;
import com.adcolony.sdk.AdColonyZone;
import com.google.android.material.tabs.TabLayout;
import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.entities.ActiveCommute;
import com.aaronevans.paidtogo.data.local.UserPreferences;
import com.aaronevans.paidtogo.data.remote.response.LeaderboardResponse;
import com.aaronevans.paidtogo.databinding.FragmentMainLeaderboardBinding;
import com.aaronevans.paidtogo.ui.BaseFragment;
import com.aaronevans.paidtogo.ui.main.leaderboard.components.ExerciseAdapter;
import com.aaronevans.paidtogo.ui.main.leaderboard.components.UsersAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.BindingObject;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.DataBound;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import static com.aaronevans.paidtogo.ui.main.MainActivity.toolbar_image;
import static com.aaronevans.paidtogo.ui.main.MainActivity.toolbar_title;

/**
 * Created by leandro on 7/11/17.
 */

@DataBound
@EFragment(R.layout.fragment_main_leaderboard)
public class LeaderBoardFragment extends BaseFragment implements LeaderBoardContract.View {
    ArrayList<LeaderboardResponse> a;
    LeaderBoardContract.ViewModel mViewModel = new LeaderBoardViewModel();
    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    LeaderBoardContract.Presenter mPresenter;
    @BindingObject
    FragmentMainLeaderboardBinding mBinding;
    int page_selected = 0, pager_pos = 0;
    String selected_month = "", selected_year = "", spinner_item, spinner_item_year;
    ArrayList<String> month_list = new ArrayList<>();
    ArrayList<String> year_list = new ArrayList<>();
    boolean first_time = true;
    static ProgressDialog mProgressDialog;
    String title = null;
    Context context;

    List<ActiveCommute> userActiveCommute=new ArrayList<>();


    // userActiveCommute;

    @ViewById
    RelativeLayout ll_month, ll_year;

    @ViewById
    TextView tv_year, tv_no_data;


   /* final private String APP_ID = "appeabfb6fc9c9f49b597";
    final private String ZONE_ID = "vz0af1d8c6daac43a9bc";*/
    //final private String TAG = "AdColonyDemo";


    private AdColonyInterstitial add;
    private AdColonyInterstitialListener listener;
    private AdColonyAdOptions adOptions;

    boolean isAddShow = false;
    private int userPosition;

    public static AppCompatActivity baseContext;


    @AfterViews
    public void setup() {

        title="Leaderboard";

        a = new ArrayList<>();
        createMonthList();
        createYearList();
        selected_month = getCurrentMonth();
        selected_year = getCurrentYear();
        mProgressDialog = new ProgressDialog(getContext());



        mBinding.tvMonth.setText(getCurrentMonthName());

        //   mBinding.mRecyclerView.setAdapter(new UsersAdapter());
        //    mBinding.mPager.setAdapter(new ExerciseAdapter());

        mBinding.mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        mBinding.mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {

            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("page scrolled", position + "");
            }

            public void onPageSelected(int position) {
                if (position == 1) {
                    if (UserPreferences.getUser(context).getSubscription_id() != 2) {
                        showProAlert();
                    } else {
                        UsersAdapter tempadapter = new UsersAdapter(getActivity(), a.get(position).getLeaderboards().getActiveCommute(), title);
                        mBinding.mRecyclerView.setAdapter(tempadapter);
                    }
                } else {
                    UsersAdapter tempadapter = new UsersAdapter(getActivity(), a.get(position).getLeaderboards().getActiveCommute(), title);
                    mBinding.mRecyclerView.setAdapter(tempadapter);
                }

                /*if (position == 0) {
                    UsersAdapter tempadapter = new UsersAdapter(getActivity(),a.get(position).getLeaderboards().getActiveCommute(), title);
                    mBinding.mRecyclerView.setAdapter(tempadapter);
                } else {
                    UsersAdapter tempadapter = new UsersAdapter(getActivity(),a.get(position).getLeaderboards().getExercise(), title);
                    mBinding.mRecyclerView.setAdapter(tempadapter);
                }*/
                page_selected = position;
            }
        });

        mBinding.mIndicator.setupWithViewPager(mBinding.mPager, true);
       /* mBinding.mTabLayout.addTab(mBinding.mTabLayout.newTab().setText("Active Commute"));
        mBinding.mTabLayout.addTab(mBinding.mTabLayout.newTab().setText("Exercise"));

        mBinding.mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0: {
                        UsersAdapter tempadapter = new UsersAdapter(getActivity(), a.get(page_selected).getLeaderboards().getActiveCommute(), title);
                        mBinding.mRecyclerView.setAdapter(tempadapter);
                        pager_pos = 0;
                        break;
                    }
                    case 1: {
                        UsersAdapter tempadapter = new UsersAdapter(getActivity(), a.get(page_selected).getLeaderboards().getExercise(), title);
                        mBinding.mRecyclerView.setAdapter(tempadapter);
                        pager_pos = 1;
                        break;
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });*/

        mBinding.setViewModel(mViewModel);
        mBinding.setSelf(this);
        startPresenter();

//
//
//        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), R.layout.spinner_dropdown, month_list);
//        arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
//        mBinding.spMonth.setAdapter(arrayAdapter);
//        mBinding.spMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                selected_month = mBinding.spMonth.getSelectedItem().toString();
//                selected_month = String.valueOf(arg2 + 1);
//                Log.e("selected_month", "127++++" + String.valueOf(arg2));
//
//                if (!selected_month.equals("Month"))
//                    spinner_item = selected_month;
//                System.out.println("Month120++" + selected_month);
//                mBinding.tvMonth.setText(month_list.get(arg2));
//
//                if (!first_time) {
//                    mPresenter.loadUserData(UserPreferences.getUser(getContext()).getId(), selected_month, selected_year, title);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//            }
//        });

    }


    @Click(R.id.ll_year)
    public void showYearPopUp() {
        PopupMenu menu = new PopupMenu(context, ll_year);

        int year = 2021;
        for (int i = 1; i < 21; i++) {
            menu.getMenu().add(Menu.NONE, i, i, String.valueOf(year));
            year = year - 1;
        }
        menu.show();
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                selected_year = item.getTitle().toString();
                mBinding.tvYear.setText(item.getTitle().toString());

                if (!first_time) {
                    mPresenter.loadUserData(UserPreferences.getUser(getContext()).getId(), selected_month, selected_year, title);
                }

                return true;
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Click(R.id.ll_month)
    public void showMonthPopUp() {

        //Creating the instance of PopupMenu
        PopupMenu popup = new PopupMenu(getActivity(), ll_month);
        popup.getMenuInflater().inflate(R.menu.month_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                mBinding.tvMonth.setText(item.getTitle().toString());
                getSelectedMonth(item.getTitle().toString());
                return true;
            }
        });
        popup.show();//showing popup menu
    }

    private void getSelectedMonth(String month_name) {

        switch (month_name) {
            case "Jan":

                selected_month = "1";
                callApi(selected_month);
                break;

            case "Feb":
                selected_month = "2";
                callApi(selected_month);
                break;

            case "Mar":
                selected_month = "3";
                callApi(selected_month);
                break;

            case "Apr":
                selected_month = "4";
                callApi(selected_month);
                break;

            case "May":
                selected_month = "5";
                callApi(selected_month);
                break;

            case "June":
                selected_month = "6";
                callApi(selected_month);
                break;

            case "July":
                selected_month = "7";
                callApi(selected_month);
                break;

            case "Aug":
                selected_month = "8";
                callApi(selected_month);
                break;

            case "Sept":
                selected_month = "9";
                callApi(selected_month);
                break;

            case "Oct":
                selected_month = "10";
                callApi(selected_month);
                break;

            case "Nov":
                selected_month = "11";
                callApi(selected_month);
                break;

            case "Dec":
                selected_month = "12";
                callApi(selected_month);
                break;

        }
    }

    private void callApi(String selected_month) {

        if (!first_time) {
            mPresenter.loadUserData(UserPreferences.getUser(getContext()).getId(), selected_month, selected_year, title);
        }
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return mLayoutManager;
    }

    @BindingAdapter("items")
    public static void setItems(ViewPager view, List<LeaderboardResponse> viewModels) {
        if (view.getAdapter() instanceof ExerciseAdapter) {
            ((ExerciseAdapter) view.getAdapter()).setItems(viewModels);
        }
    }

    @BindingAdapter("items")
    public static void setItems(RecyclerView view, List<ActiveCommute> viewModels) {
        if (view.getAdapter() instanceof UsersAdapter) {
            ((UsersAdapter) view.getAdapter()).setItems(viewModels);
        }
    }

    @Override
    public void onLoadLeaderBoardData(ArrayList<LeaderboardResponse> leaderboardResponse) {
        userActiveCommute.clear();
        a.clear();
        a.addAll(leaderboardResponse);
        if (a.size() == 0) {
            mBinding.tvNoData.setVisibility(View.VISIBLE);
            mBinding.mRecyclerView.setVisibility(View.GONE);
        } else {
            mBinding.tvNoData.setVisibility(View.GONE);
            mBinding.mRecyclerView.setVisibility(View.VISIBLE);

            UsersAdapter adapter = new UsersAdapter(getActivity(), a.get(0).getLeaderboards().getActiveCommute(), title);
            mBinding.mRecyclerView.setAdapter(adapter);

            for (int i = 0; i < a.get(0).getLeaderboards().getActiveCommute().size(); i++) {
                Log.e("the position is", i + "");
                if (UserPreferences.getUser(getContext()).getId().equalsIgnoreCase(a.get(0).getLeaderboards().getActiveCommute().get(i).getId().toString())) {
                    userPosition = i;
                    a.get(0).getLeaderboards().getActiveCommute().get(i).setPosition(i+"");
                    userActiveCommute .add(a.get(0).getLeaderboards().getActiveCommute().get(i));
                    break;
                }
            }


            if(a.size()>1){

                for (int i = 0; i < a.get(1).getLeaderboards().getActiveCommute().size(); i++) {
                    Log.e("the position is", i + "");
                    if (UserPreferences.getUser(getContext()).getId().equalsIgnoreCase(a.get(1).getLeaderboards().getActiveCommute().get(i).getId().toString())) {
                        userPosition = i;
                        a.get(1).getLeaderboards().getActiveCommute().get(i).setPosition(i+"");
                        userActiveCommute .add(a.get(1).getLeaderboards().getActiveCommute().get(i));
                        break;
                    }
                }

            }

            ExerciseAdapter Eadapter = new ExerciseAdapter(a, userActiveCommute, userPosition);
            mBinding.mPager.setAdapter(Eadapter);
        }
        first_time = false;
    }

    //    @UiThread(delay = 1000)
    @AfterViews
    @Override
    public void startPresenter() {
        mPresenter = new LeaderboardPresenter().start(this);
        mPresenter.loadUserData(UserPreferences.getUser(getContext()).getId(), selected_month, selected_year, title);
    }

    @Override
    public void showProgressDialog() {
        mProgressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        if (mProgressDialog != null)
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
    }

    @Override
    public void showErrorAlert(String msg) {
        new AlertDialog.Builder(context)
                .setTitle(getResources().getString(R.string.app_name))
                .setMessage(msg)
                .setPositiveButton(R.string.ok, null)
                .create()
                .show();
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
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
            toolbar_image.setVisibility(View.GONE);
            toolbar_title.setVisibility(View.VISIBLE);
            toolbar_title.setText("Leaderboard");

            Bundle bundle = this.getArguments();
            if (bundle != null) {
                title = bundle.getString("title", "");
                toolbar_title.setText(title);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createMonthList() {
        month_list.add("January");
        month_list.add("February");
        month_list.add("March");
        month_list.add("April");
        month_list.add("May");
        month_list.add("June");
        month_list.add("July");
        month_list.add("August");
        month_list.add("September");
        month_list.add("October");
        month_list.add("November");
        month_list.add("December");
    }

    private void createYearList() {
        for (int i = 2021; i > 1920; i--) {
            year_list.add(String.valueOf(i));
        }
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
                // Ad passed back in request filled callback, ad can now be shown
                add = ad;
                if (!isAddShow) {
                    isAddShow = true;
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


            @Override
            public void onClosed(AdColonyInterstitial ad) {
               // Toast.makeText(getActivity(),"add completed ",Toast.LENGTH_LONG).show();
                //super.onClosed(ad);
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


    public void showProAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle("Upgrade To Pro");

        // Setting Dialog Message
        alertDialog.setMessage("Upgrade to Pro to earn 2x more Coins per mile and 3x more Coins per month. Use Coins to purchase Paypal payouts. You can also remove ads and enable background tracking with Pro. Try it free for 7 days!");

        // On pressing Settings button
        alertDialog.setPositiveButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ExerciseAdapter Eadapter = new ExerciseAdapter(a, userActiveCommute, userPosition);
                        mBinding.mPager.setAdapter(Eadapter);
                        mBinding.mPager.setCurrentItem(0);
                    }
                });

        // on pressing cancel button
        alertDialog.setNegativeButton("Upgrade",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ExerciseAdapter Eadapter = new ExerciseAdapter(a, userActiveCommute, userPosition);
                        mBinding.mPager.setAdapter(Eadapter);
                        mBinding.mPager.setCurrentItem(0);

                        Intent intent = new Intent(context, UpgradeToProActivity.class);
                        getActivity().startActivityForResult(intent, 201);
                    }
                });

        // Showing Alert Message
        alertDialog.show();
    }

}