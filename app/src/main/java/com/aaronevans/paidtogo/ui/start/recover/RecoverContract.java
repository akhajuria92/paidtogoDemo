package com.aaronevans.paidtogo.ui.start.recover;

import com.aaronevans.paidtogo.ui.contracts.BasePresenter;
import com.aaronevans.paidtogo.ui.contracts.BaseView;
import com.aaronevans.paidtogo.ui.contracts.LoaderView;

/**
 * Created by evaristo on 13/12/16.
 */

public interface RecoverContract {

    interface View extends BaseView, LoaderView {
        boolean showError(boolean show);
    }

    interface Presenter extends BasePresenter<View, Presenter> {
        void recoverPassword(String email);
    }
}
