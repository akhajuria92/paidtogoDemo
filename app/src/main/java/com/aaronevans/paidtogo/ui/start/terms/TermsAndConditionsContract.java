package com.aaronevans.paidtogo.ui.start.terms;

import com.aaronevans.paidtogo.ui.contracts.BasePresenter;
import com.aaronevans.paidtogo.ui.contracts.BaseView;
import com.aaronevans.paidtogo.ui.contracts.LoaderView;

/**
 * Created by evaristo on 23/12/16.
 */

public interface TermsAndConditionsContract {

    interface View extends BaseView, LoaderView {
        void loadText(String terms);
    }

    interface Presenter extends BasePresenter<View, Presenter> {
        void getTerms(String id);
    }
}
