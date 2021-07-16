package com.aaronevans.paidtogo.ui.main.winners;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;

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
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.entities.ActiveCommute;
import com.aaronevans.paidtogo.data.local.UserPreferences;
import com.aaronevans.paidtogo.data.remote.response.WinnersResponse;
import com.aaronevans.paidtogo.databinding.FragmentWinnersBinding;
import com.aaronevans.paidtogo.ui.BaseFragment;
import com.aaronevans.paidtogo.ui.main.winners.components.ExerciseAdapter;
import com.aaronevans.paidtogo.ui.main.winners.components.UsersAdapter;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.BindingObject;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.DataBound;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import static com.aaronevans.paidtogo.ui.main.MainActivity.toolbar_image;
import static com.aaronevans.paidtogo.ui.main.MainActivity.toolbar_title;

/**
 * Created by leandro on 7/11/17.
 */

@DataBound
@EFragment(R.layout.fragment_winners)
public class WinnersFragment extends BaseFragment implements WinnersContract.View {

    ArrayList<WinnersResponse> a;
    WinnersContract.ViewModel mViewModel = new WinnersViewModel();
    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    WinnersContract.Presenter mPresenter;
    @BindingObject
    FragmentWinnersBinding mBinding;
    int page_selected = 0, pager_pos = 0;
    String selected_month = "", selected_year = "", spinner_item, spinner_item_year;
    ArrayList<String> month_list = new ArrayList<>();
    ArrayList<String> year_list = new ArrayList<>();
    boolean first_time = true;
    static ProgressDialog mProgressDialog;
    String title = null, date = "2020-02-01";




    private AdColonyInterstitial add;
    private AdColonyInterstitialListener listener;
    private AdColonyAdOptions adOptions;

    boolean isAddShow=false;


    List<ActiveCommute> userActiveCommute=new ArrayList<>();
    private int userPosition;


    @ViewById
    RelativeLayout ll_month, ll_year;
    private AppCompatActivity context;

    @AfterViews
    public void setup() {
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
            }

            public void onPageSelected(int position) {
                if (position == 1) {
                    if (UserPreferences.getUser(context).getSubscription_id() != 2) {
                        showProAlert();
                    } else {
                        UsersAdapter tempadapter = new UsersAdapter(a.get(position).leaderboards.getActiveCommute(), title);
                        mBinding.mRecyclerView.setAdapter(tempadapter);
                    }
                } else {
                    UsersAdapter tempadapter = new UsersAdapter(a.get(position).leaderboards.getActiveCommute(), title);
                    mBinding.mRecyclerView.setAdapter(tempadapter);
                }
                page_selected = position;
            }
        });

        mBinding.mIndicator.setupWithViewPager(mBinding.mPager, true);
        mBinding.mTabLayout.addTab(mBinding.mTabLayout.newTab().setText("Active Commute"));
        mBinding.mTabLayout.addTab(mBinding.mTabLayout.newTab().setText("Exercise"));

       /* mBinding.mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0: {
                        UsersAdapter tempadapter = new UsersAdapter(a.get(page_selected).leaderboards.getActiveCommute(), title);
                        mBinding.mRecyclerView.setAdapter(tempadapter);
                        pager_pos = 0;
                        break;
                    }
                    case 1: {
                        UsersAdapter tempadapter = new UsersAdapter(a.get(page_selected).leaderboards.getExercise(), title);
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


    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = (MainActivity)context;
    }

    @Click(R.id.ll_month)
    public void showMonthPopUp() {

        //Creating the instance of PopupMenu
        PopupMenu popup = new PopupMenu(getActivity(), ll_month);
        popup.getMenuInflater().inflate(R.menu.month_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item)
            {
                mBinding.tvMonth.setText(item.getTitle().toString());

                selected_month = item.getTitle().toString();
                getSelectedMonth(item.getTitle().toString());
                return true;
            }
        });
        popup.show();//showing popup menu
    }

    private void getSelectedMonth(String month_name) {

        switch (month_name)
        {
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
            mPresenter.loadUserData(UserPreferences.getUser(getContext()).getId(), getDate(),selected_month);
        }
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
                    mPresenter.loadUserData(UserPreferences.getUser(getContext()).getId(), getDate(),selected_month);
                }

                return true;
            }
        });
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return mLayoutManager;
    }

    @BindingAdapter("items")
    public static void setItems(ViewPager view, List<WinnersResponse> viewModels) {
        if (view.getAdapter() instanceof ExerciseAdapter) {
            ((ExerciseAdapter) view.getAdapter()).setItems(viewModels);
        }
    }

    @BindingAdapter("items")
    public static void setItems(RecyclerView view, List<ActiveCommute> viewModels) {
        if (view.getAdapter() instanceof UsersAdapter)
        {
            ((UsersAdapter) view.getAdapter()).setItems(viewModels);
        }
    }

    @Override
    public void onWinnersData(ArrayList<WinnersResponse> winnersResponses) {
        userActiveCommute.clear();
        a.clear();
        a.addAll(winnersResponses);
        if (a.size() == 0) {
            //   Toast.makeText(getActivity(), "a", Toast.LENGTH_SHORT).show();

            mBinding.tvNoData.setVisibility(View.VISIBLE);
            mBinding.mRecyclerView.setVisibility(View.GONE);

        }
        else
        {
            mBinding.tvNoData.setVisibility(View.GONE);
            mBinding.mRecyclerView.setVisibility(View.VISIBLE);

            for (int i = 0; i < a.get(0).leaderboards.getActiveCommute().size(); i++) {
                Log.e("the position is", i + "");
                if(a.get(0).leaderboards.getActiveCommute().get(i).getId()!=null){
                    if (UserPreferences.getUser(getContext()).getId().equalsIgnoreCase(a.get(0).leaderboards.getActiveCommute().get(i).getId().toString())) {
                        userPosition = i;
                        a.get(0).leaderboards.getActiveCommute().get(i).setPosition(i+"");
                        userActiveCommute .add(a.get(0).leaderboards.getActiveCommute().get(i));
                        break;
                    }
                }
            }

            if(a.size()>1){

                for (int i = 0; i < a.get(1).leaderboards.getActiveCommute().size(); i++) {
                    Log.e("the position is", i + "");
                    if (UserPreferences.getUser(getContext()).getId().equalsIgnoreCase(a.get(1).leaderboards.getActiveCommute().get(i).getId().toString())) {
                        userPosition = i;
                        a.get(1).leaderboards.getActiveCommute().get(i).setPosition(i+"");
                        userActiveCommute .add(a.get(1).leaderboards.getActiveCommute().get(i));
                        break;
                    }
                }

            }



            UsersAdapter adapter = new UsersAdapter(a.get(0).leaderboards.getActiveCommute(), title);
            mBinding.mRecyclerView.setAdapter(adapter);





            ExerciseAdapter Eadapter = new ExerciseAdapter(a,userActiveCommute,userPosition);
            mBinding.mPager.setAdapter(Eadapter);

        }



        first_time = false;
    }

    @UiThread(delay = 2000)
    @AfterViews
    @Override
    public void startPresenter() {
        try {
            mPresenter = new WinnersPresenter().start(this);
            mPresenter.loadUserData(UserPreferences.getUser(getContext()).getId(), getDate(),selected_month);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        new AlertDialog.Builder(getContext())
                .setTitle(getResources().getString(R.string.app_name))
                .setMessage(msg)
                .setPositiveButton(R.string.ok, null)
                .create()
                .show();
    }

    @Override
    public void onResume() {
        super.onResume();

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

        // try {
        toolbar_image.setVisibility(View.GONE);
        toolbar_title.setVisibility(View.VISIBLE);
        toolbar_title.setText("Winners");
        title="Winners";

//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

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
        for (int i = 2020; i > 1920; i--) {
            year_list.add(String.valueOf(i));
        }
    }

    private String getDate() {
        int selectedMonth = Integer.valueOf(selected_month);
        String date = selected_year + "-" + selectedMonth + "-" + "01";
        Log.e("date", "getDate289++" + date);
        return date;
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


            @Override
            public void onClosed(AdColonyInterstitial ad) {
                Toast.makeText(getActivity(),"add completed ",Toast.LENGTH_LONG).show();
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


    public String getCurrentMonthName() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMM");
        String month_name = month_date.format(calendar.getTime());
        return  month_name;
    }



    public void showProAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

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
                        context.startActivityForResult(intent, 201);
                    }
                });

        // Showing Alert Message
        alertDialog.show();
    }

}