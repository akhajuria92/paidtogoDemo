package com.aaronevans.paidtogo.ui.start.login;

import android.content.Intent;
import androidx.fragment.app.Fragment;

import com.aaronevans.paidtogo.ui.contracts.BasePresenter;
import com.aaronevans.paidtogo.ui.contracts.BaseView;
import com.aaronevans.paidtogo.ui.contracts.LoaderView;

/**
 * Created by evaristo on 12/12/16.
 */

public interface LoginContract {

    interface View extends BaseView, LoaderView {
        boolean showMissingEmail(boolean show);

        boolean showMissingPassword(boolean show);

        boolean showMissingEmailPassword(boolean show);

        void launchSignUp();

        void launchMainActivity();

        void launchRecoverPassword();
    }

    interface Presenter extends BasePresenter<View, Presenter> {
        void login(String user, String password);

        void facebookLogin(Fragment fragment);

        void attendResult(int requestCode, int resultCode, Intent data);
    }
}
