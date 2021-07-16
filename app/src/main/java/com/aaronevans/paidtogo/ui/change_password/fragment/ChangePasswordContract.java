package com.aaronevans.paidtogo.ui.change_password.fragment;

import com.aaronevans.paidtogo.ui.contracts.BasePresenter;
import com.aaronevans.paidtogo.ui.contracts.BaseView;
import com.aaronevans.paidtogo.ui.contracts.LoaderView;

/**
 * Created by Infinix Android on 19/1/2017.
 */

public interface ChangePasswordContract {

    interface View extends BaseView, LoaderView {
        void onBack();
    }

    interface Presenter extends BasePresenter<View, Presenter> {
        void postChangePassword(String oldpassword, String password);
    }

}