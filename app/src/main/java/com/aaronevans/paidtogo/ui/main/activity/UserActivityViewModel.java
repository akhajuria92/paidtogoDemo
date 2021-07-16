package com.aaronevans.paidtogo.ui.main.activity;

import androidx.databinding.ObservableArrayList;

import com.aaronevans.paidtogo.data.remote.response.ActivitiesResponse;



/**
 * Created by leandro on 8/11/17.
 */

public class UserActivityViewModel implements UserActivityContract.ViewModel {

    private final ObservableArrayList<ActivitiesResponse> mDaysViewModel = new ObservableArrayList<>();

    public UserActivityViewModel() {
       /* mDaysViewModel.add(new UserActivityDayViewModel());
        mDaysViewModel.add(new UserActivityDayViewModel());
        mDaysViewModel.add(new UserActivityDayViewModel());*/
    }

    @Override
    public ObservableArrayList<ActivitiesResponse> getDaysViewModels() {
        return mDaysViewModel;
    }
}