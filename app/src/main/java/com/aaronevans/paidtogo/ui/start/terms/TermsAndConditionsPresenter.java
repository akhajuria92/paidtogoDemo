package com.aaronevans.paidtogo.ui.start.terms;

import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.entities.TermsAndConditions;
import com.aaronevans.paidtogo.data.remote.PaidToGoService;
import com.aaronevans.paidtogo.data.remote.response.TyCResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;


/**
 * Created by evaristo on 23/12/16.
 */

public class TermsAndConditionsPresenter implements TermsAndConditionsContract.Presenter {

    TermsAndConditionsContract.View mView;

    @Override
    public void getTerms(String id) {
        mView.showProgressDialog();

        PaidToGoService.getServiceClient()
                .getTermsAndConditions(new TermsAndConditions(id))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::successful,
                        this::unSuccessful);
    }

    private void successful(TyCResponse tyCResponse) {
        mView.hideProgressDialog();
        mView.loadText(tyCResponse.getTerms());
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
    public TermsAndConditionsContract.Presenter start(TermsAndConditionsContract.View view) {
        mView = view;
        return this;
    }

    @Override
    public void destroy() {

    }
}
