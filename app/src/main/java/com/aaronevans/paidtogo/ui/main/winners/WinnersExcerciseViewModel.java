package com.aaronevans.paidtogo.ui.main.winners;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

public class WinnersExcerciseViewModel {

    private WinnersExcerciseModel mModel;

    public WinnersExcerciseViewModel() {
    }

    public WinnersExcerciseViewModel(WinnersExcerciseModel mModel) {
        this.mModel = mModel;
    }

    public void setModel(WinnersExcerciseModel mModel) {
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
