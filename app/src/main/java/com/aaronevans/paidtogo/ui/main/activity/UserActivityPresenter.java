package com.aaronevans.paidtogo.ui.main.activity;

import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.remote.PaidToGoService;
import com.aaronevans.paidtogo.data.remote.response.ActivitiesResponse;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class UserActivityPresenter implements UserActivityContract.Presenter {

    UserActivityContract.View mView;
    @Override
    public UserActivityContract.Presenter start(UserActivityContract.View view) {
        this.mView=view;
        return this;
    }

    @Override
    public void destroy() {

    }
    @Override
    public void loadActivityData(String id) {
        mView.showProgressDialog();
        PaidToGoService
                .getServiceClient()
                .getActivity(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::loadNestedActivity,
                        this::onError

                );
    }



    public void loadNestedActivity(ArrayList<ActivitiesResponse> userResponse) {
        mView.hideProgressDialog();
        mView.onLoadActivityData(userResponse);

    }
    public void onError(Throwable throwable) {
        mView.hideProgressDialog();
        if (throwable instanceof HttpException) {
            mView.showErrorAlert(PaidToGoService.attendError((HttpException) throwable).getDetail());
        } else {
            mView.showErrorAlert(mView.getContext().getString(R.string.connection_problem));
        }
    }



}

