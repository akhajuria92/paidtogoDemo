package com.aaronevans.paidtogo.ui.main.activity.components;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.aaronevans.paidtogo.data.local.SettingsPreferences;
import com.aaronevans.paidtogo.data.local.UserPreferences;
import com.aaronevans.paidtogo.data.remote.response.ActivitiesResponse;
import com.aaronevans.paidtogo.databinding.ItemActivityDayBinding;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by leandro on 9/11/17.
 */

public class UserActivityAdapter extends RecyclerView.Adapter<UserActivityAdapter.ViewHolder> {

    private List<ActivitiesResponse> mViewModels = new ArrayList<>();
    ItemActivityDayBinding mBinding;
    FragmentActivity activity;

    {
        setHasStableIds(true);
    }

    public UserActivityAdapter() {

    }

    public UserActivityAdapter(FragmentActivity activity, List<ActivitiesResponse> items) {
        this.activity = activity;
        if (items != mViewModels) {
            mViewModels = items;
        }
        notifyDataSetChanged();
    }

    public void setItems(List<ActivitiesResponse> items) {
        if (items != mViewModels) {
            mViewModels = items;
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(ItemActivityDayBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mViewModels.get(position));

        final Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DATE, -(7-(position+1)));
        cal.add(Calendar.DAY_OF_YEAR, -position);
        DateFormat dateFormat = new SimpleDateFormat("EEEE - MMM dd");
        String date = dateFormat.format(cal.getTime());

//        Log.d("citytower", "onBindViewHolder: "+Calendar.DATE);
//        Log.d("citytower", "onBindViewHolder: "+position);
//        Log.d("citytower", "onBindViewHolder: "+date);
//        Log.d("citytower", "onBindViewHolder: "+cal.getTime());

//        if (position == 0) {
//            mBinding.mHeader.setText("Today");
//        }
//        else if (position == 1) {
//            mBinding.mHeader.setText("Yesterday");
//        }
//        else {
//            mBinding.mHeader.setText(date.toString());
//        }

        mBinding.mHeader.setText(date.toString());

//        Log.d("citytower", "onBindViewHolder: "+date);
//        mBinding.mPointsText.setText( String.valueOf(mViewModels.get(position).getEarnedPoints())+" points" );
//        mBinding.mEarningsText.setText(String.format("%.1f",mViewModels.get(position).getEarnedMoney()));
//        mBinding.mTotalStatusText.setText(String.format("%.1f",mViewModels.get(position).getSavedCalories())+ " Calories Burned\n"+String.format("%.1f",mViewModels.get(position).getSavedCo2())+" Lbs CO2 Offset\n 0.00 Gal. Gas Saved");
//        mBinding.mDailyWalkedMilesText.setText(String.format("%.1f",mViewModels.get(position).getWalkMiles())+" miles"+" / "+String.format("%.1f",mViewModels.get(position).getTotalSteps())+" steps");
//        mBinding.mDailyBikedMilesText.setText(String.format("%.1f",mViewModels.get(position).getBikeMiles())+" mi");
//        mBinding.mDailyWorkoutedText.setText(mViewModels.get(position).getGymCheckins().toString());


        double coinsValue = Double.parseDouble(UserPreferences.getCoinsValue(activity));
        int coins = (int) (mViewModels.get(position).getEarnedPoints() / coinsValue);
        mBinding.mPointsText.setText(coins + " Coin(s)");
        mBinding.mEarningsText.setText(String.format("%.1f", mViewModels.get(position).getEarnedMoney()));
        mBinding.mTotalStatusText.setText(String.format("%.1f", mViewModels.get(position).getSavedCalories()) + " Calories Burned\n" + String.format("%.1f", mViewModels.get(position).getSavedCo2()) + " Lbs CO2 Offset\n 0.00 Gal. Gas Saved");
//        mBinding.mDailyWalkedMilesText.setText(String.format("%.1f",mViewModels.get(position).getWalkMiles())+" miles"+" / "+String.format("%.1f",mViewModels.get(position).getTotalSteps())+" steps");
       // mBinding.mDailyWalkedMilesText.setText(String.format("%.1f", mViewModels.get(position).getMilesTraveled()) + " mi");


        if( SettingsPreferences.isMilesKm(activity)){
            mBinding.mDailyWalkText.setText("Kilometers");
            mBinding.mDailyWalkedMilesText.setText(String.format("%.1f",mViewModels.get(position).getMilesTraveled()) +" km");

        }else{
            mBinding.mDailyWalkText.setText("Miles");
            mBinding.mDailyWalkedMilesText.setText(String.format("%.1f",mViewModels.get(position).getMilesTraveled()) +" mi");
        }

        mBinding.mDailyBikedMilesText.setText(String.valueOf(mViewModels.get(position).getTotalSteps()));
        mBinding.mDailyWorkoutedText.setText(mViewModels.get(position).getGymCheckins().toString());

    }

    @Override
    public int getItemCount() {
        return mViewModels.size();
    }

    @Override
    public long getItemId(int position) {
        return mViewModels.get(position).hashCode();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(ItemActivityDayBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }

        public void bind(ActivitiesResponse viewModel) {
            mBinding.setViewModel(viewModel);
            mBinding.executePendingBindings();
        }
    }
}
