package com.aaronevans.paidtogo.ui.main.leaderboard;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableArrayList;

import com.aaronevans.paidtogo.data.entities.ActiveCommute;
import com.aaronevans.paidtogo.data.remote.response.LeaderboardResponse;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by leandro on 7/11/17.
 */

public class LeaderBoardViewModel extends BaseObservable implements LeaderBoardContract.ViewModel {

    private final LeaderBoardModel mModel = new LeaderBoardModel();

    private final ObservableArrayList<ActiveCommute> mUserViewModels = new ObservableArrayList<>();
    private final ObservableArrayList<LeaderboardResponse> mExerciseViewModels = new ObservableArrayList<>();

    CompositeDisposable mDisposables = new CompositeDisposable();
    {

//        mModel.getLeaderBoardsStream()
//                .flatMapIterable(ls->ls)
//                .map(LeaderBoard::getCurrentUserPosition) //TODO will change
//                .map(LeaderBoardUserModel::new)
//                .map(LeaderBoardUserViewModel::new)
//                .toList()
//                .subscribe(
//                        leaderBoardUserViewModels -> {
//                            mUserViewModels.clear();
//                            mUserViewModels.addAll()
//                        }
//                )

      /*  mUserViewModels.add(new ActiveCommute());
        mUserViewModels.add(new ActiveCommute());
        mUserViewModels.add(new ActiveCommute());
        mUserViewModels.add(new ActiveCommute());

        mExerciseViewModels.add(new LeaderBoardExerciseViewModel());
        mExerciseViewModels.add(new LeaderBoardExerciseViewModel());
        mExerciseViewModels.add(new LeaderBoardExerciseViewModel());
        mExerciseViewModels.add(new LeaderBoardExerciseViewModel());
        mExerciseViewModels.add(new LeaderBoardExerciseViewModel());
        mExerciseViewModels.add(new LeaderBoardExerciseViewModel());
        mExerciseViewModels.add(new LeaderBoardExerciseViewModel());
        mExerciseViewModels.add(new LeaderBoardExerciseViewModel());
        mExerciseViewModels.add(new LeaderBoardExerciseViewModel());*/
    }

    @Override
    public ObservableArrayList<ActiveCommute> getUserViewModels() {
        return mUserViewModels;
    }

    @Override
    public ObservableArrayList<LeaderboardResponse> getExerciseViewModels() {
        return mExerciseViewModels;
    }
}