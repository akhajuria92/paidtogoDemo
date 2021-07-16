package com.aaronevans.paidtogo.ui.main.home.components;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aaronevans.paidtogo.data.entities.Activity_Home;
import com.aaronevans.paidtogo.data.local.UserPreferences;
import com.aaronevans.paidtogo.data.remote.PaidToGoService;
import com.aaronevans.paidtogo.data.remote.response.PoolResponse;
import com.aaronevans.paidtogo.databinding.ItemExcerciseBinding;
import com.aaronevans.paidtogo.databinding.ItemExcerciseLastBinding;
import com.aaronevans.paidtogo.ui.BottomDialogCallBack;
import com.aaronevans.paidtogo.ui.Dialogs;
import com.aaronevans.paidtogo.ui.inviteorg.InviteOrganizationActivity_;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * Created by leandro on 2/11/17.
 */

public class ExercisePager extends PagerAdapter {
    ViewGroup mcon;
    int mpos;
    int check = 0;
    String mPreviousChoice;
    private final Context context;
    String settingValue;

    private ArrayList<PoolResponse> mItems = new ArrayList<>();

    public ExercisePager(Context context, ArrayList<PoolResponse> listActivities, String settingValue) {
        this.context = context;
        mItems.addAll(listActivities);
        mpos = 0;
        mPreviousChoice = "Today";
        this.settingValue = settingValue;
    }


   /* public void setdata() {

    }*/

    @Override
    public int getCount() {
        if(mItems.size()==1){
            return mItems.size()+1;
        }else{
            return  mItems.size();
        }

    }

    /* public int getItemPosition(Object object) {
         return POSITION_NONE;
     }*/
    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }
  /* @Override
   public int getItemPosition(Object object) {
       if (object instanceof HomeFragment) {
           // Create a new method notifyUpdate() in your fragment
           // it will get call when you invoke
           // notifyDatasetChaged();
           ((HomeFragment) object).notifyUpdate();
       }
       //don't return POSITION_NONE, avoid fragment recreation.
       return super.getItemPosition(object);
   }*/

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View recycled;

        if(mItems.size()==1){
            ItemExcerciseLastBinding mLastCard;
            mLastCard = ItemExcerciseLastBinding.inflate(inflater, container, false);
            ArrayList<Activity_Home> activity = new ArrayList<>();
            if (mItems.get(0).getWalkMiles() != null && mItems.get(0).getBikeMiles() != null) {
                activity.add(new Activity_Home(mItems.get(0).getWalkMiles(), mItems.get(0).getBikeMiles(), mItems.get(0).getGymCheckins()));
            }
            if (activity.size() > 0) {
                ActivityPager mAdapter = new ActivityPager(activity);
                mLastCard.mAddLayout.mPager.setAdapter(mAdapter);
            }

            mLastCard.mAddLayout.setActivityStatistics(mItems.get(0).getSummary());
            mLastCard.mAddLayout.cardTitle.setText(mItems.get(0).getName());

            if (mItems.get(0).getSummary() != null) {
                // String.format("%.2f", (mItems.get(position).getSummary().getEarnedPoints()));
                mLastCard.mAddLayout.mKCalBurntText2.setText(String.format("%.2f", mItems.get(0).getSummary().getCalories()));
                mLastCard.mAddLayout.mCO2OffsetText2.setText(String.format("%.2f", mItems.get(0).getSummary().getCo2()));
                int steps = mItems.get(0).getSummary().getSteps().intValue();
                mLastCard.mAddLayout.mStepsText2.setText(steps+"");
                mLastCard.mAddLayout.mMilesTraveledText2.setText(String.format("%.2f", mItems.get(0).getSummary().getMiles()));
            }

            //UserPreferences.getUser(getContext()).getId()

            if (mItems.get(0).getRewardType() == 1) {
                // itemExcercise.reward_type.setText("USD");
                mLastCard.mAddLayout.mLocalPointsText2.setText(String.format("%.2f", mItems.get(0).getSummary().getEarnedMoney()));
                UserPreferences.saveMainPoints(context, String.format("%.2f", mItems.get(0).getSummary().getEarnedMoney()));
            } else if (mItems.get(0).getRewardType() == 2) {
                //  itemExcercise.reward_type.setText("Points");
                if (UserPreferences.getUser(context).getSubscription_id()!=null && UserPreferences.getUser(context).getSubscription_id()== 2) {
                    Double totalEarnedUsd = 0.0;
                    double coinsValue = 1.0;
                    mLastCard.mAddLayout.rewardType.setText("COIN(S)");
                    double earnedItems=mItems.get(0).getSummary().getEarnedPoints();
                    try {
                        coinsValue=Double.parseDouble(UserPreferences.getCoinsValue(context));
                        int coins = (int)(earnedItems / coinsValue);
                        String usdValue = UserPreferences.getUSDPricePaid(context);
                        totalEarnedUsd = coins * Double.parseDouble(usdValue);
                        mLastCard.mAddLayout.usdValue.setText("USD value: $"+String.format("%.2f", totalEarnedUsd));
                        mLastCard.mAddLayout.mLocalPointsText2.setText(coins+"");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                } else {
                    Double totalEarnedUsd = 0.0;
                    double coinsValue = 1.0;
                    mLastCard.mAddLayout.rewardType.setText("COIN(S)");
                    double earnedItems=mItems.get(0).getSummary().getEarnedPoints();
                    try {
                        coinsValue=Double.parseDouble(UserPreferences.getCoinsValue(context));
                        int coins = (int)(earnedItems / coinsValue);
                        String usdValue = UserPreferences.getUSDPriceFree(context);
                        totalEarnedUsd = coins * Double.parseDouble(usdValue);
                        mLastCard.mAddLayout.usdValue.setText("USD value: $"+String.format("%.2f", totalEarnedUsd));
                        mLastCard.mAddLayout.mLocalPointsText2.setText(coins+"");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                UserPreferences.saveMainPoints(context, String.format("%.2f", mItems.get(0).getSummary().getEarnedPoints()));
            }

            //itemExcercise.mKCalBurntText2.setText("0.0");
            mLastCard.mAddLayout.mGasSavedText2.setText("0.00");
            mLastCard.mAddLayout.mspanText.setText(mPreviousChoice);

            ArrayList<String> items = new ArrayList<String>();
            items.add("Today");
            items.add("This Week");
            items.add("This Month");

            mLastCard.mAddLayout.mspanText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialogs.getInstance().showBottomSheetWithPickerDialog(context, items, 0, new BottomDialogCallBack() {
                        @Override
                        public void onCallBack(String item, int position) {
                            // etGender.setText(item);
                            mLastCard.mAddLayout.mspanText.setText(item);
                            mPreviousChoice = item;
                            getData(item);

                        }
                    });
                }
            });

            mLastCard.mAddLayout.imageView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialogs.getInstance().showBottomSheetWithPickerDialog(context, items, 0, new BottomDialogCallBack() {
                        @Override
                        public void onCallBack(String item, int position) {
                            // etGender.setText(item);
                            mLastCard.mAddLayout.mspanText.setText(item);
                            mPreviousChoice = item;
                            getData(item);

                        }
                    });
                }
            });

            recycled = mLastCard.getRoot();
        }
        else {
            ItemExcerciseLastBinding mLastCard;
            mLastCard = ItemExcerciseLastBinding.inflate(inflater, container, false);
            ArrayList<Activity_Home> activity = new ArrayList<>();
            if (mItems.get(position).getWalkMiles() != null && mItems.get(position).getBikeMiles() != null) {
                activity.add(new Activity_Home(mItems.get(position).getWalkMiles(), mItems.get(position).getBikeMiles(), mItems.get(position).getGymCheckins()));
            }
            if (activity.size() > 0) {
                ActivityPager mAdapter = new ActivityPager(activity);
                mLastCard.mAddLayout.mPager.setAdapter(mAdapter);
            }
            mLastCard.mAddLayout.setActivityStatistics(mItems.get(position).getSummary());
            mLastCard.mAddLayout.cardTitle.setText(mItems.get(position).getName());

            if (mItems.get(position).getSummary() != null) {
                // String.format("%.2f", (mItems.get(position).getSummary().getEarnedPoints()));
                mLastCard.mAddLayout.mKCalBurntText2.setText(String.format("%.2f", mItems.get(position).getSummary().getCalories()));
                mLastCard.mAddLayout.mCO2OffsetText2.setText(String.format("%.2f", mItems.get(position).getSummary().getCo2()));

                int steps = mItems.get(position).getSummary().getSteps().intValue();
                mLastCard.mAddLayout.mStepsText2.setText(steps+"");

                mLastCard.mAddLayout.mMilesTraveledText2.setText(String.format("%.2f", mItems.get(position).getSummary().getMiles()));
            }

            //UserPreferences.getUser(getContext()).getId()

            if (mItems.get(position).getRewardType() == 1) {
                // itemExcercise.reward_type.setText("USD");
                mLastCard.mAddLayout.mLocalPointsText2.setText(String.format("%.2f", mItems.get(position).getSummary().getEarnedMoney()));
                UserPreferences.saveMainPoints(context, String.format("%.2f", mItems.get(position).getSummary().getEarnedMoney()));
            } else if (mItems.get(position).getRewardType() == 2) {
                //  itemExcercise.reward_type.setText("Points");
                if (UserPreferences.getUser(context).getSubscription_id() == 2) {
                    Double totalEarnedUsd = 0.0;
                    double coinsValue = 1.0;
                    mLastCard.mAddLayout.rewardType.setText("COINS");
                    double earnedItems=mItems.get(position).getSummary().getEarnedPoints();
                    try {
                        coinsValue=Double.parseDouble(UserPreferences.getCoinsValue(context));
                        int coins = (int)(earnedItems / coinsValue);
                        String usdValue = UserPreferences.getUSDPricePaid(context);
                        totalEarnedUsd = coins * Double.parseDouble(usdValue);
                        mLastCard.mAddLayout.usdValue.setText("USD value up to: $"+String.format("%.2f", totalEarnedUsd));
                        mLastCard.mAddLayout.mLocalPointsText2.setText(coins+"");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                } else {
                    Double totalEarnedUsd = 0.0;
                    double coinsValue = 1.0;
                    mLastCard.mAddLayout.rewardType.setText("COINS");
                    double earnedItems=mItems.get(position).getSummary().getEarnedPoints();
                    try {
                        coinsValue=Double.parseDouble(UserPreferences.getCoinsValue(context));
                        int coins = (int)(earnedItems / coinsValue);
                        String usdValue = UserPreferences.getUSDPriceFree(context);
                        totalEarnedUsd = coins * Double.parseDouble(usdValue);
                        mLastCard.mAddLayout.usdValue.setText("USD value up to: $"+String.format("%.2f", totalEarnedUsd));
                        mLastCard.mAddLayout.mLocalPointsText2.setText(coins+"");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                UserPreferences.saveMainPoints(context, String.format("%.2f", mItems.get(position).getSummary().getEarnedPoints()));
            }

            //itemExcercise.mKCalBurntText2.setText("0.0");
            mLastCard.mAddLayout.mGasSavedText2.setText("0.00");
            mLastCard.mAddLayout.mspanText.setText(mPreviousChoice);

            ArrayList<String> items = new ArrayList<String>();
            items.add("Today");
            items.add("This Week");
            items.add("This Month");

            mLastCard.mAddLayout.mspanText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialogs.getInstance().showBottomSheetWithPickerDialog(context, items, position, new BottomDialogCallBack() {
                        @Override
                        public void onCallBack(String item, int position) {
                            // etGender.setText(item);
                            mLastCard.mAddLayout.mspanText.setText(item);
                            mPreviousChoice = item;
                            getData(item);

                        }
                    });
                }
            });

            mLastCard.mAddLayout.imageView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialogs.getInstance().showBottomSheetWithPickerDialog(context, items, position, new BottomDialogCallBack() {
                        @Override
                        public void onCallBack(String item, int position) {
                            // etGender.setText(item);
                            mLastCard.mAddLayout.mspanText.setText(item);
                            mPreviousChoice = item;
                            getData(item);

                        }
                    });
                }
            });

            recycled = mLastCard.getRoot();
        }





        /*if (position == getCount() - 1) {
            ItemExcerciseLastBinding mLastCard;
            mLastCard = ItemExcerciseLastBinding.inflate(inflater, container, false);
            Log.e("FIRST CASE", "FIRST CASE");
            if(mItems.size()==1){




            }

            recycled = mLastCard.getRoot();
        } else {
            ItemExcerciseBinding itemExcercise = ItemExcerciseBinding.inflate(inflater, container, false);
            ArrayList<Activity_Home> activity = new ArrayList<>();
            if (mItems.get(position).getWalkMiles() != null && mItems.get(position).getBikeMiles() != null) {
                activity.add(new Activity_Home(mItems.get(position).getWalkMiles(), mItems.get(position).getBikeMiles(), mItems.get(position).getGymCheckins()));
            }
            if (activity.size() > 0) {
                ActivityPager mAdapter = new ActivityPager(activity);
                itemExcercise.mPager.setAdapter(mAdapter);
            }
            itemExcercise.setActivityStatistics(mItems.get(position).getSummary());
            itemExcercise.cardTitle.setText(mItems.get(position).getName());

            if (mItems.get(position).getSummary() != null) {
                itemExcercise.mKCalBurntText2.setText(String.format("%.2f", mItems.get(position).getSummary().getCalories()));
                itemExcercise.mCO2OffsetText2.setText(String.format("%.2f", mItems.get(position).getSummary().getCo2()));
                itemExcercise.mStepsText2.setText(String.format("%.2f", mItems.get(position).getSummary().getSteps()));
                itemExcercise.mMilesTraveledText2.setText(String.format("%.2f", mItems.get(position).getSummary().getMiles()));
            }
            if (mItems.get(position).getRewardType() == 1) {
                itemExcercise.mLocalPointsText2.setText(String.format("%.2f", mItems.get(position).getSummary().getEarnedMoney()));
                UserPreferences.saveMainPoints(context, String.format("%.2f", mItems.get(position).getSummary().getEarnedMoney()));
            } else if (mItems.get(position).getRewardType() == 2) {
                if (UserPreferences.getUser(context).getSubscription_id() == 2) {
                    Double totalEarnedUsd = 0.0;
                    double coinsValue = 1.0;
                    itemExcercise.rewardType.setText("COINS");
                    double earnedItems=mItems.get(position).getSummary().getEarnedPoints();
                    try {
                        coinsValue=Double.parseDouble(UserPreferences.getCoinsValue(context));
                        int coins = (int)(earnedItems / coinsValue);
                        String usdValue = UserPreferences.getUSDPricePaid(context);
                        totalEarnedUsd = coins * Double.parseDouble(usdValue);
                        itemExcercise.usdValue.setText("USD value: $"+String.format("%.2f", totalEarnedUsd));
                        itemExcercise.mLocalPointsText2.setText(coins+"");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                } else {
                    Double totalEarnedUsd = 0.0;
                    double coinsValue = 1.0;
                    itemExcercise.rewardType.setText("COINS");
                    double earnedItems=mItems.get(position).getSummary().getEarnedPoints();
                    try {
                        coinsValue=Double.parseDouble(UserPreferences.getCoinsValue(context));
                        int coins = (int)(earnedItems / coinsValue);
                        String usdValue = UserPreferences.getUSDPriceFree(context);
                        totalEarnedUsd = coins * Double.parseDouble(usdValue);
                        itemExcercise.usdValue.setText("USD value: $"+String.format("%.2f", totalEarnedUsd));
                        itemExcercise.mLocalPointsText2.setText(coins+"");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                UserPreferences.saveMainPoints(context, String.format("%.2f", mItems.get(position).getSummary().getEarnedPoints()));
            }
            itemExcercise.mGasSavedText2.setText("0.00");
            itemExcercise.mspanText.setText(mPreviousChoice);

            ArrayList<String> items = new ArrayList<String>();
            items.add("Today");
            items.add("This Week");
            items.add("This Month");

            itemExcercise.mspanText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialogs.getInstance().showBottomSheetWithPickerDialog(context, items, 0, new BottomDialogCallBack() {
                        @Override
                        public void onCallBack(String item, int position) {
                            // etGender.setText(item);
                            itemExcercise.mspanText.setText(item);
                            mPreviousChoice = item;
                            getData(item);

                        }
                    });
                }
            });

            itemExcercise.imageView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialogs.getInstance().showBottomSheetWithPickerDialog(context, items, 0, new BottomDialogCallBack() {
                        @Override
                        public void onCallBack(String item, int position) {
                            // etGender.setText(item);
                            itemExcercise.mspanText.setText(item);
                            mPreviousChoice = item;
                            getData(item);

                        }
                    });
                }
            });

            recycled = itemExcercise.getRoot();

        }*/
        container.addView(recycled);
        return recycled;
    }

    public void onAddClick() {
        InviteOrganizationActivity_.intent(context).start();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View recycled = (View) object;
        container.removeView(recycled);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    public void getData(String span) {
        String date1 = null, date2 = null;
        if (span.equalsIgnoreCase("Today")) {
            final Calendar cal = Calendar.getInstance();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = dateFormat.format(cal.getTime());
            date1 = date;
            final Calendar cal1 = Calendar.getInstance();
            cal1.add(Calendar.DATE, 1);
            DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            String date0 = dateFormat1.format(cal1.getTime());
            date2 = date0;

        } else if (span.equalsIgnoreCase("This Week")) {
            final Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -7);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = dateFormat.format(cal.getTime());
            date1 = date;
            final Calendar cal1 = Calendar.getInstance();
            cal1.add(Calendar.DATE, 1);
            DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            String date0 = dateFormat1.format(cal1.getTime());
            date2 = date0;

        } else if (span.equalsIgnoreCase("This Month")) {

            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = 1;
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            c.set(year, month, day);
            int numOfDaysInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
            String date = dateFormat.format(c.getTime());
            date1 = date;
            System.out.println("First Day of month: " + c.getTime());
            c.add(Calendar.DAY_OF_MONTH, numOfDaysInMonth-1);
            String date0 = dateFormat.format(c.getTime());
            date2 = date0;
            System.out.println("Last Day of month: " + c.getTime());

           /* final Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -30);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = dateFormat.format(cal.getTime());
            date1 = date;
            final Calendar cal1 = Calendar.getInstance();
            cal1.add(Calendar.DATE, 1);
            DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            String date0 = dateFormat1.format(cal1.getTime());
            date2 = date0;*/
        }

        PaidToGoService.getServiceClient().getActivities(UserPreferences.getUser(context).getId(), "true", date1, date2)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::loadListActivities,
                        this::onError
                );
    }

    public void loadListActivities(ArrayList<PoolResponse> responseBody) {

       /* ArrayList<PoolResponse> activities = new ArrayList<>();
        activities.addAll(responseBody);*/
        /*activities.addAll(responseBody.getSponsorPools());
        activities.add(responseBody.getNationalPool());*/
        mItems.clear();
        mItems.addAll(responseBody);
        notifyDataSetChanged();
        //  instantiateItem(mcon,mpos);
    }

    public void onError(Throwable throwable) {

        if (throwable instanceof HttpException) {
            // mView.showErrorAlert(PaidToGoService.attendError((HttpException) throwable).getDetail());
        } else {
            // mView.showErrorAlert(mView.getContext().getString(R.string.connection_problem));
        }
    }
}
