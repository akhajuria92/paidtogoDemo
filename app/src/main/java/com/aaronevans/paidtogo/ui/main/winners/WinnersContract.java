package com.aaronevans.paidtogo.ui.main.winners;

import androidx.databinding.ObservableArrayList;
import com.aaronevans.paidtogo.data.entities.ActiveCommute;
import com.aaronevans.paidtogo.data.remote.response.WinnersResponse;
import com.aaronevans.paidtogo.ui.contracts.BasePresenter;
import com.aaronevans.paidtogo.ui.contracts.BaseView;
import com.aaronevans.paidtogo.ui.contracts.LoaderView;
import java.util.ArrayList;

/**
 * Created by leandro on 7/11/17.
 */

public interface WinnersContract {

    interface ViewModel {

        ObservableArrayList<ActiveCommute> getUserViewModels();

        ObservableArrayList<WinnersResponse> getExerciseViewModels();
    }
    interface Model {

    }
    interface View extends BaseView, LoaderView {
        void onWinnersData(ArrayList<WinnersResponse> winnersResponses);

    }

    interface Presenter extends BasePresenter<WinnersContract.View, WinnersContract.Presenter> {

        void loadUserData(String id, String date,String seleted_date);
    }
}
