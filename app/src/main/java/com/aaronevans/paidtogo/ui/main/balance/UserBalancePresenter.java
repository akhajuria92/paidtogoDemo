package com.aaronevans.paidtogo.ui.main.balance;

import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.remote.PaidToGoService;
import com.aaronevans.paidtogo.data.remote.response.BalanceResponse;
import java.util.ArrayList;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class UserBalancePresenter implements UserBalanceContract.Presenter {

    UserBalanceContract.View mView;
    @Override
    public UserBalanceContract.Presenter start(UserBalanceContract.View view) {
        this.mView=view;
        return this;
    }

    @Override
    public void destroy() {

    }

    public void loadNestedUser(ArrayList<BalanceResponse> userResponse) {
        mView.hideProgressDialog();
        mView.onLoadBalanceData(userResponse);

    }
    public void onError(Throwable throwable) {
        mView.hideProgressDialog();
        if (throwable instanceof HttpException) {
            mView.showErrorAlert(PaidToGoService.attendError((HttpException) throwable).getDetail());
        } else {
            mView.showErrorAlert(mView.getContext().getString(R.string.connection_problem));
        }
    }

    @Override
    public void loadBalanceData(String id) {
        mView.showProgressDialog();
        PaidToGoService
                .getServiceClient()
                .getBalance(id,"","")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::loadNestedUser,
                        this::onError

                );
    }
}