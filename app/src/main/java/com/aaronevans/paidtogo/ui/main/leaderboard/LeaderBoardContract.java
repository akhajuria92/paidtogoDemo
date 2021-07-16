package com.aaronevans.paidtogo.ui.main.leaderboard;

import androidx.databinding.ObservableArrayList;
import com.aaronevans.paidtogo.data.entities.ActiveCommute;
import com.aaronevans.paidtogo.data.remote.response.LeaderboardResponse;
import com.aaronevans.paidtogo.ui.contracts.BasePresenter;
import com.aaronevans.paidtogo.ui.contracts.BaseView;
import com.aaronevans.paidtogo.ui.contracts.LoaderView;
import java.util.ArrayList;

/**
 * Created by leandro on 7/11/17.
 */

public interface LeaderBoardContract {

    interface ViewModel {
        ObservableArrayList<ActiveCommute> getUserViewModels();
        ObservableArrayList<LeaderboardResponse> getExerciseViewModels();
    }

    interface Model {
    }
    interface View extends BaseView, LoaderView {
        void onLoadLeaderBoardData(ArrayList<LeaderboardResponse> leaderboardResponse);
    }

    interface Presenter extends BasePresenter<LeaderBoardContract.View, LeaderBoardContract.Presenter> {
        void loadUserData( String id, String month, String year, String toolbar_title);
    }
}