package com.aaronevans.paidtogo.ui.main.balance;

import androidx.databinding.ObservableArrayList;

import com.aaronevans.paidtogo.data.remote.response.BalanceResponse;

/**
 * Created by leandro on 9/11/17.
 */

public class UserBalanceViewModel implements UserBalanceContract.ViewModel {
    ObservableArrayList<BalanceResponse> mExerciseModels = new ObservableArrayList<>();

    {
/*        mExerciseModels.add(new UserBalanceExerciseViewModel());
        mExerciseModels.add(new UserBalanceExerciseViewModel());
        mExerciseModels.add(new UserBalanceExerciseViewModel());
        mExerciseModels.add(new UserBalanceExerciseViewModel());
        mExerciseModels.add(new UserBalanceExerciseViewModel());
        mExerciseModels.add(new UserBalanceExerciseViewModel());*/

    }

    @Override
    public ObservableArrayList<BalanceResponse> getExerciseViewModels() {
        return null;
    }
}
