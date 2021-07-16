package com.aaronevans.paidtogo.ui.main.stats;

import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.remote.PaidToGoService;
import com.aaronevans.paidtogo.data.remote.response.StatsResponse;

import java.util.ArrayList;

import retrofit2.HttpException;

public class UserStatsPresenter implements UserStatsContract.Presenter {

    UserStatsContract.View mView;
    @Override
    public UserStatsContract.Presenter start(UserStatsContract.View view) {
        this.mView=view;
        return this;
    }

    @Override
    public void destroy() {

    }
    @Override
    public void loadStatsData(String id) {
        mView.showProgressDialog();
        /*PaidToGoService
                .getServiceClient()
                .getStats(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::loadNestedActivity,
                        this::onError

                );*/
    }



    public void loadNestedActivity(ArrayList<StatsResponse> userResponse) {
        mView.hideProgressDialog();
        mView.onLoadStatsData(userResponse);

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

