package com.aaronevans.paidtogo.ui.start.signup;

import android.content.Intent;
import android.net.Uri;

import com.aaronevans.paidtogo.ui.contracts.BasePresenter;
import com.aaronevans.paidtogo.ui.contracts.BaseView;
import com.aaronevans.paidtogo.ui.contracts.LoaderView;
import com.aaronevans.paidtogo.ui.contracts.ToolbarRelated;

/**
 * Created by evaristo on 12/12/16.
 */

public interface SignUpContract {

    interface View extends BaseView, LoaderView,
            ToolbarRelated.ToolbarCreator,
            ToolbarRelated.UpNavigator {

        boolean showErrors();

        void loadProfilePhoto(Uri uri);

        void promptUserForPhoto(Intent photoIntent, int requestCode);

        void launchMainActivity();
    }

    interface Presenter extends BasePresenter<View, Presenter> {
        int PICTURE_REQUEST_CODE = 101;

        void attendUserResult(int resultCode, Intent data);

        void registerUser(String email, String name, String lastName, String password, String retypePassword, String bio);

        void pickProfilePhoto();
    }
}
