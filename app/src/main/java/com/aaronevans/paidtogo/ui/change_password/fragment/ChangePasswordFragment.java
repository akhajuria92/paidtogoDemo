package com.aaronevans.paidtogo.ui.change_password.fragment;

import android.app.ProgressDialog;
import com.google.android.material.textfield.TextInputEditText;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import com.aaronevans.paidtogo.R;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Infinix Android on 19/1/2017.
 */

@EFragment(R.layout.fragment_change_password)
public class ChangePasswordFragment extends Fragment implements ChangePasswordContract.View {

    @ViewById
    TextInputEditText mOldPassword, mNewPassword, mRepeatPassword;
    private ChangePasswordContract.Presenter mChangePasswordPresenter;
    private ProgressDialog mProgressDialog = null;

    @AfterViews
    public void startPresenter() {
        mChangePasswordPresenter = new ChangePasswordPresenter().start(this);
    }

    @Override
    public void showProgressDialog() {
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setTitle(getResources().getString(R.string.app_name));
        mProgressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showErrorAlert(String smg) {
        new AlertDialog.Builder(getContext())
                .setTitle(getResources().getString(R.string.app_name))
                .setMessage(smg)
                .setPositiveButton(R.string.alert_accept, null)
                .create()
                .show();
    }

    @Click(R.id.mTextViewChangePassword)
    public void sendChangePassword() {
        if (!TextUtils.isEmpty(mNewPassword.getText().toString()) &&
                !TextUtils.isEmpty(mRepeatPassword.getText().toString()) &&
                !TextUtils.isEmpty(mOldPassword.getText().toString())) {
            if (mNewPassword.getText().toString().equals(mRepeatPassword.getText().toString())) {
                mChangePasswordPresenter.postChangePassword(mOldPassword.getText().toString(),mNewPassword.getText().toString());
            } else {
                showErrorAlert(getResources().getString(R.string.not_must_password));
            }
        } else {
            showErrorAlert(getResources().getString(R.string.error_complete_all_fields));
        }
    }

    @Override
    public void onBack() {
        getActivity().onBackPressed();
    }

}