package com.aaronevans.paidtogo.ui.ComplaintContactActivity;

import android.net.Uri;

import com.aaronevans.paidtogo.ui.contracts.BasePresenter;
import com.aaronevans.paidtogo.ui.contracts.BaseView;
import com.aaronevans.paidtogo.ui.contracts.LoaderView;

public interface ComplaintContract {

    interface View extends BaseView, LoaderView{
        void onBack();
    }


    interface Presenter extends BasePresenter<View, Presenter>{
        void postComplaint(String name, String email, String title, String reason, String description, Uri uri);
    }

}