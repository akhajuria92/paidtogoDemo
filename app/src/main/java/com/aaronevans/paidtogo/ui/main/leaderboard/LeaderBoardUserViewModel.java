package com.aaronevans.paidtogo.ui.main.leaderboard;

import androidx.databinding.Observable;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.aaronevans.paidtogo.components.PaidToGoUtils;

/**
 * Created by leandro on 7/11/17.
 */

public class LeaderBoardUserViewModel {

    private final ObservableField<String> mImageUrl = new ObservableField<>("");
    private final ObservableField<String> mUserName = new ObservableField<>("Some User");
    private final ObservableInt           mPosition = new ObservableInt(1);
    private final ObservableField<String> mCardinal = new ObservableField<>("st");

    ObservableInt.OnPropertyChangedCallback mOrdinalCallback = new Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(Observable observable, int i) {
            mCardinal.set(PaidToGoUtils.ordinal(mPosition.get()));
        }
    };

    private final LeaderBoardUserModel mModel;

    public LeaderBoardUserViewModel() { mModel = null; };

    public LeaderBoardUserViewModel(LeaderBoardUserModel model) {
        this.mModel = model;

    }



    public ObservableField<String> getImageUrl() {
        return mImageUrl;
    }

    public ObservableField<String> getUserName() {
        return mUserName;
    }

    public ObservableInt getPosition() {
        return mPosition;
    }

    public ObservableField<String> getCardinal() {
        return mCardinal;
    }
}