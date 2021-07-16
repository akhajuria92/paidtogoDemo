package com.aaronevans.paidtogo.ui.main.stats;

import com.aaronevans.paidtogo.data.remote.response.StatsResponse;
import com.aaronevans.paidtogo.ui.contracts.BasePresenter;
import com.aaronevans.paidtogo.ui.contracts.BaseView;
import com.aaronevans.paidtogo.ui.contracts.LoaderView;

import java.util.ArrayList;

/**
 * Created by leandro on 8/11/17.
 */

public interface UserStatsContract {
    interface ViewModel {
       // ObservableArrayList<StatsResponse> getDaysViewModels();
    }
    interface Model {

    }

interface View extends BaseView, LoaderView {
    void onLoadStatsData(ArrayList<StatsResponse> useractivityResponse);

}

interface Presenter extends BasePresenter<UserStatsContract.View, UserStatsContract.Presenter> {

    void loadStatsData(String id);
}
}
