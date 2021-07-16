package com.aaronevans.paidtogo.ui.main.home.components;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.entities.Activity_Home;
import com.aaronevans.paidtogo.databinding.LayoutMainHomeStatsBinding;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Farhan Arshad on 6/6/2018.
 */

public class ActivityPager extends PagerAdapter {
    private List<Activity_Home> mItemsActivities = new ArrayList<>();


    public ActivityPager(ArrayList<Activity_Home> listActivities) {
        mItemsActivities.addAll(listActivities);

    }



    @Override
    public int getCount() {
        return mItemsActivities.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
//        View itemView = layoutInflater.inflate(R.layout.layout_main_home_stats, container, false);


        LayoutMainHomeStatsBinding itemActivity = LayoutMainHomeStatsBinding.inflate(inflater, container, false);

        itemActivity.mDailyWalkedMilesText.setText(String.format("%.2f",mItemsActivities.get(position).getWalk_miles()));

        itemActivity.mDailyBikedMilesText.setText(String.format("%.2f",mItemsActivities.get(position).getBike_miles()));
        itemActivity.mDailyWorkoutedText.setText(String.valueOf(mItemsActivities.get(position).getGym_checkins()));


        itemActivity.setActivity(mItemsActivities.get(position));
        container.addView(itemActivity.getRoot());
        return itemActivity.getRoot();



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
}
