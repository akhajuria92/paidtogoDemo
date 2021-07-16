package com.aaronevans.paidtogo.ui.main.leaderboard;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

/**
 * Created by leandro on 8/11/17.
 */

public class LeaderBoardExerciseViewModel {

    private LeaderBoardExerciseModel mModel;

    public LeaderBoardExerciseViewModel() {
    }

    public LeaderBoardExerciseViewModel(LeaderBoardExerciseModel mModel) {
        this.mModel = mModel;
    }

    public void setModel(LeaderBoardExerciseModel mModel) {
        this.mModel = mModel;
    }

    private final ObservableField<String> mTitle = new ObservableField<>("Paidtogo Local");
    private final ObservableInt mPoints = new ObservableInt(2283);
    private final ObservableInt mPosition = new ObservableInt(1);
    private final ObservableField<String> mCardinal = new ObservableField<>("");
    private final ObservableField<String> mImageUrl = new ObservableField<>("");

    public ObservableField<String> getTitle() {
        return mTitle;
    }

    public ObservableInt getPoints() {
        return mPoints;
    }

    public ObservableInt getPosition() {
        return mPosition;
    }

    public ObservableField<String> getCardinal() {
        return mCardinal;
    }

    public ObservableField<String> getImageUrl() {
        return mImageUrl;
    }
}