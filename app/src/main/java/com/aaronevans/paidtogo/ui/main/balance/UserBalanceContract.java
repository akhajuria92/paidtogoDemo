package com.aaronevans.paidtogo.ui.main.balance;

import androidx.databinding.ObservableArrayList;

import com.aaronevans.paidtogo.data.remote.response.BalanceResponse;
import com.aaronevans.paidtogo.ui.contracts.BasePresenter;
import com.aaronevans.paidtogo.ui.contracts.BaseView;
import com.aaronevans.paidtogo.ui.contracts.LoaderView;

import java.util.ArrayList;

/**
 * Created by leandro on 9/11/17.
 */

public interface UserBalanceContract {
    interface ViewModel {

        ObservableArrayList<BalanceResponse> getExerciseViewModels();
    }
    interface Model {

    }
    interface View extends BaseView, LoaderView {
        void onLoadBalanceData(ArrayList<BalanceResponse> balanceResponse);

    }

    interface Presenter extends BasePresenter<UserBalanceContract.View, UserBalanceContract.Presenter> {

        void loadBalanceData(String id);
    }
}
