package com.aaronevans.paidtogo.ui.main.stats;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.aaronevans.paidtogo.data.local.SettingsPreferences;
import com.aaronevans.paidtogo.data.local.UserPreferences;
import com.aaronevans.paidtogo.data.remote.response.StatsResponse;
import com.aaronevans.paidtogo.databinding.StatsItemBinding;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by leandro on 9/11/17.
 */

public class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.ViewHolder> {

    private List<StatsResponse> mViewModels = new ArrayList<>();
    StatsItemBinding mBinding;
    FragmentActivity activity;
    String type;
    {
        setHasStableIds(true);
    }


    public StatsAdapter(FragmentActivity activity,List<StatsResponse> items,String filterType) {
        this.activity=activity;
        if (items != mViewModels) {
            mViewModels = items;
        }

        this.type=filterType;

        notifyDataSetChanged();
    }

    public void setItems(List<StatsResponse> items) {
        if (items != mViewModels) {
            mViewModels = items;
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(StatsItemBinding.inflate(inflater,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mViewModels.get(position));
        String date;
        if(type.toLowerCase().equals("daily")){
            final Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DATE, -(7-(position+1)));
            cal.add(Calendar.DAY_OF_YEAR , - position-1);
            DateFormat dateFormat = new SimpleDateFormat("EEEE - MMM dd");
             date = dateFormat.format(cal.getTime());
        }else if(type.toLowerCase().equals("weekly")){
            final Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DATE, -(7-(position+1)));
            cal.add(Calendar.DAY_OF_YEAR , - position*7);
            DateFormat dateFormat = new SimpleDateFormat("EEEE - MMM dd");
             date = dateFormat.format(cal.getTime());
        }else {
            final Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DATE, -(7-(position+1)));
            cal.add(Calendar.DAY_OF_YEAR , - position*30);
            DateFormat dateFormat = new SimpleDateFormat("EEEE - MMM dd");
            date= dateFormat.format(cal.getTime());
        }



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
        mBinding.mPointsText.setText(mViewModels.get(position).getEarnedPoints() + " Coin(s)");
        mBinding.mEarningsText.setText(String.format("%.1f",mViewModels.get(position).getEarnedMoney()));
        mBinding.mTotalStatusText.setText(String.format("%.1f",mViewModels.get(position).getSavedCalories())+ " Calories Burned\n"+String.format("%.1f",mViewModels.get(position).getSavedCo2())+" Lbs CO2 Offset\n 0.00 Gal. Gas Saved");
//        mBinding.mDailyWalkedMilesText.setText(String.format("%.1f",mViewModels.get(position).getWalkMiles())+" miles"+" / "+String.format("%.1f",mViewModels.get(position).getTotalSteps())+" steps");
        mBinding.mDailyWalkedMilesText.setText(String.format("%.1f",mViewModels.get(position).getTotalSteps()));


        if(SettingsPreferences.isMilesKm(activity)){
            mBinding.mDailyBicycleText.setText("Kilometers traveled");
        }else{
            mBinding.mDailyBicycleText.setText("Miles traveled");

        }


        mBinding.mDailyBikedMilesText.setText(String.valueOf(String.format("%.1f",mViewModels.get(position).getMilesTraveled())));
        mBinding.mDailyWorkoutedText.setText(String.valueOf(mViewModels.get(position).getGymCheckins()));

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

        public ViewHolder(StatsItemBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }

        public void bind(StatsResponse viewModel) {
            mBinding.setViewModel(viewModel);
            mBinding.executePendingBindings();
        }
    }
}
