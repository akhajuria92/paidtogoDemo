package com.aaronevans.paidtogo.ui.start.recover;

import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.entities.Error;
import com.aaronevans.paidtogo.data.entities.RecoverPassword;
import com.aaronevans.paidtogo.data.remote.PaidToGoService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * Created by evaristo on 23/12/16.
 */

public class RecoverPresenter implements RecoverContract.Presenter {

    RecoverContract.View mView;

    @Override
    public void recoverPassword(String email) {
        if (mView.showError(email.isEmpty())) return;

        mView.showProgressDialog();

        PaidToGoService.getServiceClient()
                .recoverPassword(new RecoverPassword(email))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::successful,
                        this::unSuccessful);
    }

    private void successful(Error error) {
        mView.hideProgressDialog();
        mView.showErrorAlert(error.getDetail());
    }

    private void unSuccessful(Throwable throwable) {
        mView.hideProgressDialog();

        if (throwable instanceof HttpException) {
            mView.showErrorAlert(PaidToGoService.attendError((HttpException) throwable).getDetail());
        } else {
            mView.showErrorAlert(mView.getContext().getString(R.string.connection_problem));
        }
    }

    @Override
    public RecoverContract.Presenter start(RecoverContract.View view) {
        mView = view;
        return this;
    }

    @Override
    public void destroy() {

    }
}
