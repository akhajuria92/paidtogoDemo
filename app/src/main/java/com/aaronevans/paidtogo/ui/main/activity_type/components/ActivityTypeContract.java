package com.aaronevans.paidtogo.ui.main.activity_type.components;

import android.content.Context;

import com.aaronevans.paidtogo.ui.contracts.BasePresenter;
import com.aaronevans.paidtogo.ui.contracts.BaseView;
import com.aaronevans.paidtogo.ui.contracts.LoaderView;

/**
 * Created by leandro on 2/11/17.
 */

public interface ActivityTypeContract {

    interface Presenter extends BasePresenter<View, Presenter> {

    }

    interface ViewModel {
        void setContext(Context context);
    }

    interface View extends BaseView, LoaderView {
        void showPermissionMission(String msg);
    }
}
