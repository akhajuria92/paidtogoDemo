package com.aaronevans.paidtogo.ui.main.activity;

import androidx.databinding.ObservableArrayList;

import com.aaronevans.paidtogo.data.remote.response.ActivitiesResponse;
import com.aaronevans.paidtogo.ui.contracts.BasePresenter;
import com.aaronevans.paidtogo.ui.contracts.BaseView;
import com.aaronevans.paidtogo.ui.contracts.LoaderView;

import java.util.ArrayList;



/**
 * Created by leandro on 8/11/17.
 */

public interface UserActivityContract {
    interface ViewModel {
        ObservableArrayList<ActivitiesResponse> getDaysViewModels();
    }
    interface Model {

    }

interface View extends BaseView, LoaderView {
    void onLoadActivityData(ArrayList<ActivitiesResponse> useractivityResponse);

}

interface Presenter extends BasePresenter<UserActivityContract.View, UserActivityContract.Presenter> {

    void loadActivityData( String id);
}
}
