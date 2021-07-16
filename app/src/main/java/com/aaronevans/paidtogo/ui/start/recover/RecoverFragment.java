package com.aaronevans.paidtogo.ui.start.recover;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentTransaction;

import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.ui.start.login.LoginFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by evaristo on 13/12/16.
 */

@EFragment(R.layout.fragment_recover)
public class RecoverFragment extends Fragment implements RecoverContract.View {

    @ViewById
    TextView mTitle;
    @ViewById
    TextView mSubmit;
    @ViewById
    TextInputLayout mEmailRecoverLabel;
    @ViewById
    TextInputEditText mEmailRecover;

    RecoverContract.Presenter mPresenter;
    ProgressDialog mProgressDialog;

    @AfterViews
    void setTitle() {
        mTitle.setText(getString(R.string.cancle_text));
        Typeface type = Typeface.createFromAsset(getResources().getAssets(), getResources().getString(R.string.font_open_sans_italic));
        mEmailRecoverLabel.setTypeface(type);
    }

    @Click(R.id.mBack)
    void back() {
        hideKeyboard();
        getActivity().onBackPressed();
    }


    @Click(R.id.mTitle)
    public void finishFragmnet() {
        getFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up)
                .addToBackStack(null)
                .replace(R.id.mFragmentContainer, LoginFragment_.builder().build())
                .commit();
    }

    @AfterViews
    void setup() {
        mEmailRecover.requestFocus();
        showKeyboard();
    }

    private void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEmailRecover, InputMethodManager.SHOW_IMPLICIT);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEmailRecover.getWindowToken(), 0);
    }

    @Override
    @AfterViews
    public void startPresenter() {
        mPresenter = new RecoverPresenter().start(this);
    }

    @Override
    public void showProgressDialog() {
        mProgressDialog = new ProgressDialog(getContext());
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
        new AlertDialog.Builder(getContext())
                .setMessage(msg)
                .setPositiveButton(R.string.alert_Dismiss, null)
                .create()
                .show();
    }

    @Click(R.id.mSubmit)
    public void submit() {
        mPresenter.recoverPassword(mEmailRecover.getText().toString());
    }

    @Override
    public boolean showError(boolean show) {
        if (show) {
//            mEmailRecoverLabel.setErrorEnabled(true);
//            mEmailRecoverLabel.setError(getString(R.string.fill_email));
            showErrorAlert("An email address shall be specified");
            return true;
        } else {
            mEmailRecoverLabel.setErrorEnabled(false);
            mEmailRecoverLabel.setError(null);
            return false;
        }
    }

}