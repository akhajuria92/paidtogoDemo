package com.aaronevans.paidtogo.ui.profile;

import android.content.Intent;
import android.net.Uri;

import com.aaronevans.paidtogo.ui.contracts.BasePresenter;
import com.aaronevans.paidtogo.ui.contracts.BaseView;
import com.aaronevans.paidtogo.ui.contracts.LoaderView;

/**
 * Created by evaristo on 26/12/16.
 */

public interface ProfileContract {

    interface View extends BaseView, LoaderView {
        void loadProfilePhoto(Uri uri);

        boolean showErrors();

        void promptUserForPhoto(Intent photoIntent, int requestCode);

        String getAge();

        String getGender();

        boolean isForExercise();

        boolean isWork();

        boolean isChanging();

        boolean isWalkRun();

        boolean isBike();

        boolean isBusTrain();
    }

    interface Presenter extends BasePresenter<View, Presenter> {
        void logout();

        void attendUserResult(int resultCode, Intent data);

        void pickProfilePhoto();

        void updateDataUser(String email, String name, String lastName, String bio, String accountPaypal, String accessToken);
    }
}
