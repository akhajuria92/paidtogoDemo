package com.aaronevans.paidtogo.ui.change_password.fragment;

import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import com.aaronevans.paidtogo.PaidToGoApp;
import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.local.UserPreferences;
import com.aaronevans.paidtogo.data.remote.PaidToGoService;
import com.aaronevans.paidtogo.data.remote.request.ChangePasswordBody;
import com.aaronevans.paidtogo.data.remote.response.UpdateResponse;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * Created by Infinix Android on 19/1/2017.
 */

public class ChangePasswordPresenter implements ChangePasswordContract.Presenter {

    ChangePasswordContract.View mView;

    @Override
    public ChangePasswordContract.Presenter start(ChangePasswordContract.View view) {
        this.mView = view;
        return this;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void postChangePassword(String oldpass, String password) {
        if (PaidToGoApp.isInternetConnection(mView.getContext())) {
            mView.showProgressDialog();
            ChangePasswordBody changePasswordBody = new ChangePasswordBody();
            changePasswordBody.setNew_password(password);
            changePasswordBody.setPassword_repeat(password);
            changePasswordBody.setOld_password(oldpass);
            changePasswordBody.setAccess_token(UserPreferences.getToken(mView.getContext()));

            PaidToGoService.getServiceClient().changePassword(changePasswordBody)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            this::loadNestedUser,
                            this::onError
                    );
        } else
            mView.showErrorAlert(mView.getContext().getResources().getString(R.string.check_internet_connecrion));

    }

    public void loadNestedUser(UpdateResponse userResponse) {
        mView.hideProgressDialog();
        if(userResponse.getNewPassword()!=null){
            mView.showErrorAlert(userResponse.getNewPassword().get(0));
        }else if(userResponse.getError()!=null){
            mView.showErrorAlert(userResponse.getError());
        }else  if (userResponse.getDetail()!=null) {
            new AlertDialog.Builder(mView.getContext())
                    .setTitle("Password")
                    .setMessage(userResponse.getDetail())
                    .setPositiveButton(R.string.alert_accept, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mView.onBack();
                        }
                    })
                    .create()
                    .show();
        }
    }

    public void onError(Throwable throwable) {
        mView.hideProgressDialog();
        if (throwable instanceof HttpException) {
            mView.showErrorAlert(PaidToGoService.attendError((HttpException) throwable).getDetail());
        } else {
          //  mView.showErrorAlert(mView.getContext().getString(R.string.connection_problem));
        }
    }

}