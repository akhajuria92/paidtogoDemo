package com.aaronevans.paidtogo.ui.main.activity;

import androidx.databinding.ObservableField;

/**
 * Created by leandro on 8/11/17.
 */

public class UserActivityDayViewModel {

    private final ObservableField<String> mWalkStatsString = new ObservableField<>("");
    private final ObservableField<String> mBicycleStatsString = new ObservableField<>("");
    private final ObservableField<String> mWorkoutStatsString = new ObservableField<>("");
    private final ObservableField<String> mMilesAndStepsString = new ObservableField<>("");

    public ObservableField<String> getWalkStatsString() {
        return mWalkStatsString;
    }

    public ObservableField<String> getBicycleStatsString() {
        return mBicycleStatsString;
    }

    public ObservableField<String> getWorkoutStatsString() {
        return mWorkoutStatsString;
    }

    public ObservableField<String> getMilesAndStepsString() {
        return mMilesAndStepsString;
    }
}
