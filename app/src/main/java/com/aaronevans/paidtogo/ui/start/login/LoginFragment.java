package com.aaronevans.paidtogo.ui.start.login;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.ui.main.MainActivity;
import com.aaronevans.paidtogo.ui.start.LoginActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Objects;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

/**
 * Created by evaristo on 07/12/16.
 */

@EFragment(R.layout.fragment_login)
public class LoginFragment extends Fragment implements LoginContract.View {

    LoginContract.Presenter mPresenter;

    @ViewById
    TextInputLayout mEmailLabel;
    @ViewById
    TextInputEditText mEmail;
    @ViewById
    TextInputLayout mPasswordLabel;
    @ViewById
    TextInputEditText mPassword;

    ProgressDialog mProgressDialog;

    @Override
    @Click(R.id.mSignUpButton)
    public void launchSignUp() {
        ((LoginActivity) getActivity()).loadFragment(LoginActivity.REGISTER);
    }

    @Override
    public void launchMainActivity() {
        ((LoginActivity) getActivity()).launchActivity(MainActivity.LABEL);
    }

    @Click(R.id.mLogIn)
    public void login() {
        mPresenter.login(mEmail.getText().toString(), mPassword.getText().toString());
    }

    @Override
    @Click(R.id.mRecoverPassword)
    public void launchRecoverPassword() {
        ((LoginActivity) getActivity()).loadFragment(LoginActivity.RECOVER_PASSWORD);
    }

    @Override
    @AfterViews
    public void startPresenter() {
        mPresenter = new LoginPresenter().start(this);
        Typeface type = Typeface.createFromAsset(getResources().getAssets(), getResources().getString(R.string.font_open_sans_italic));
        mPasswordLabel.setTypeface(type);
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
    public void showErrorAlert(String msg) {
//        new AlertDialog.Builder(getContext())
//                .setMessage(msg)
//                .setTitle(getResources().getString(R.string.app_name))
//                .setPositiveButton(R.string.alert_accept, null)
//                .create()
//                .show();

        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(getContext()).create();
        alertDialog.setMessage(msg);
        alertDialog.setTitle(getResources().getString(R.string.app_name));
        alertDialog.setButton(android.app.AlertDialog.BUTTON_POSITIVE, "Dismiss", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();

        Button positive_button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        if(positive_button != null) {
            positive_button.setTextColor(getResources().getColor(R.color.black));
            positive_button.setAllCaps(false);
        }

    }

    public void showErrorAlertDialog(String msg) {
        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(getContext()).create();
        alertDialog.setMessage(msg);
        alertDialog.setTitle(getResources().getString(R.string.app_name));
        alertDialog.setButton(android.app.AlertDialog.BUTTON_POSITIVE, "Dismiss", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.black));
            }
        });
        alertDialog.show();

        Button positive_button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        if(positive_button != null) {
            positive_button.setTextColor(getResources().getColor(R.color.black));
            positive_button.setAllCaps(false);
        }
    }

    @Override
    public boolean showMissingEmail(boolean show) {
        if (show) {
            showErrorAlertDialog(Objects.requireNonNull(getContext()).getResources().getString(R.string.fill_email));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean showMissingPassword(boolean show) {
        if (show) {
            showErrorAlertDialog(Objects.requireNonNull(getContext()).getResources().getString(R.string.fill_password));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean showMissingEmailPassword(boolean show) {
        if (show) {
//            showErrorAlertDialog(R.string.app_name)
            return true;
        } else {

            return true;
        }
    }

    @Click(R.id.mLoginFb)
    public void loginWithFB() {
        mPresenter.facebookLogin(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.attendResult(requestCode, resultCode, data);
    }

}
