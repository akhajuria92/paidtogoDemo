package com.aaronevans.paidtogo.ui.start.signup;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.ui.Utils.SwitchButtonGreen;
import com.aaronevans.paidtogo.ui.main.MainActivity;
import com.aaronevans.paidtogo.ui.start.LoginActivity;
import com.aaronevans.paidtogo.ui.start.login.LoginFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.regex.Pattern;

/**
 * Created by evaristo on 07/12/16.
 */

@EFragment(R.layout.fragment_signup)
public class SignUpFragment extends Fragment implements SignUpContract.View {

    SignUpContract.Presenter mPresenter;

    @ViewById
    ImageView mProfilePhoto;
    @ViewById
    TextView mTitle;
    @ViewById
    TextView mCancel;
    @ViewById
    TextInputLayout mEmailLabel;
    @ViewById
    TextInputEditText mEmail;
    @ViewById
    TextInputLayout mFirstNameLabel;
    @ViewById
    TextInputEditText mFirstName;
    @ViewById
    TextInputLayout mLastNameLabel;
    @ViewById
    TextInputEditText mLastName;
    @ViewById
    TextInputLayout mPasswordLabel;
    @ViewById
    TextInputEditText mPassword;
    @ViewById
    TextInputLayout mRetypePassLabel;
    @ViewById
    TextInputEditText mRetypePass;
    @ViewById
    TextInputLayout mBioLabel;
    @ViewById
    TextInputEditText mBio;

//    @ViewById
//    AppCompatCheckBox mTermsAndConditions;

    @ViewById
    SwitchButtonGreen switchButton;

    ProgressDialog mProgressDialog;

    @Override
    @AfterViews
    public void startPresenter() {
        mPresenter = new SignUpPresenter().start(this);
    }

    @AfterViews
    void setTitle() {
        mTitle.setText(getString(R.string.sign_up));
        Typeface type = Typeface.createFromAsset(getResources().getAssets(), getResources().getString(R.string.font_open_sans_italic));
        mPasswordLabel.setTypeface(type);
        mRetypePassLabel.setTypeface(type);
    }

    @AfterViews
    public void switchButton() {
        switchButton.setChecked(true);
        switchButton.toggle(true);//switch without animation
        switchButton.setShadowEffect(true);//disable shadow effect
        switchButton.setEnabled(true);//disable button
        switchButton.setEnableEffect(true);//disable the switch animation

        switchButton.setOnCheckedChangeListener(new SwitchButtonGreen.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButtonGreen view, boolean isChecked) {
                if (isChecked) {
                    switchButton.setChecked(true);
//                    savePref.setNotification_status("Hide");
                } else {
                    switchButton.setChecked(false);
//                    savePref.setNotification_status("Show");
                }
            }
        });
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
//        new AlertDialog.Builder(getContext())
//                .setTitle(getResources().getString(R.string.app_name))
//                .setMessage(smg)
//                .setPositiveButton(R.string.alert_Dismiss, null)
//                .create()
//                .show();
        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(getContext()).create();
        alertDialog.setMessage(smg);
        alertDialog.setTitle(getResources().getString(R.string.app_name));

        alertDialog.setButton(android.app.AlertDialog.BUTTON_POSITIVE, getContext().getResources().getString(R.string.alert_Dismiss), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();

        Button positive_button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        if (positive_button != null) {
            positive_button.setTextColor(getResources().getColor(R.color.black));
            positive_button.setAllCaps(false);
        }
    }

    @Click(R.id.mProfilePhoto)
    public void pickProfilePhoto() {
        mPresenter.pickProfilePhoto();
    }

    @Click(R.id.mTermsText)
    public void launchTermsAndConditions() {
        ((LoginActivity) getActivity()).loadFragment(LoginActivity.TERMS_AND_CONDITIONS);
    }

    @Click(R.id.mCancel)
    public void finishFragmnet() {
        getFragmentManager().beginTransaction()
                 .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up)
                .addToBackStack(null)
                .replace(R.id.mFragmentContainer, LoginFragment_.builder().build())
                .commit();
    }

    @Override
    public void setupToolbar() {

    }

    @Override
    public boolean onSupportNavigateUp() {
        getActivity().onBackPressed();
        return false;
    }

    @Click(R.id.mBack)
    void back() {
        onSupportNavigateUp();
    }

    @Override
    public boolean showErrors() {
        return checkField(mEmail, mEmailLabel,true, R.string.fill_email) ||
                checkField(mFirstName, mFirstNameLabel,false, R.string.fill_first_name) ||
                checkField(mLastName, mLastNameLabel,false, R.string.fill_last__name) ||
                checkField(mPassword, mPasswordLabel,false, R.string.fill_password) ||
                checkboxsixDigits(mPassword, R.string.password_match)||
                checkField(mRetypePass, mRetypePassLabel,false, R.string.fill_password_verify) ||
                checkBothTexts(mPassword, mRetypePass, R.string.password_match) ||
                !checkTermsAndConditions();

        // checkField(mBio, mBioLabel, R.string.fill_bio) ||
    }

    private boolean checkboxsixDigits(TextInputEditText mPassword, int password_match) {
            if (mPassword.getText().toString().length()<6) {
                showErrorAlert(getResources().getString(R.string.passwordsixdigits));
                return true;
            } else {
                return false;
            }
    }


    private boolean checkField(EditText et, TextInputLayout lbl, boolean isEmail,int string) {
        if(isEmail){
            if (et.getText().toString().trim().isEmpty()) {
                //   lbl.setErrorEnabled(true);
//            lbl.setError(getString(string));
                showErrorAlert(getResources().getString(string));
                et.requestFocus();
                return true;
            }
            else if(!Patterns.EMAIL_ADDRESS.matcher(et.getText().toString()).matches())
            {
                showErrorAlert(getResources().getString(R.string.invalid_email));
                et.requestFocus();
                return true;
            }
            else
                {
                return false;
                }

        }else{
            if (et.getText().toString().trim().isEmpty()) {
                //   lbl.setErrorEnabled(true);
//            lbl.setError(getString(string));
                showErrorAlert(getResources().getString(string));
                et.requestFocus();
                return true;
            }else{
                return  false;
            }
        }
    }

    private boolean checkBothTexts(EditText et1, EditText et2, int string) {
        if (!(et1.getText().toString().trim().isEmpty() && et2.getText().toString().trim().isEmpty())) {
            if (!et1.getText().toString().trim().equals(et2.getText().toString().trim())) {
                showErrorAlert(getResources().getString(string));
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    private boolean checkTermsAndConditions() {
//        if (!mTermsAndConditions.isChecked())
//            showErrorAlert(getResources().getString(R.string.alert_terms));
//        return mTermsAndConditions.isChecked();
        if (!switchButton.isChecked())
            showErrorAlert(getResources().getString(R.string.alert_terms));
        return switchButton.isChecked();
    }

    @Click(R.id.mRegister)
    void SignUpUser() {
        mPresenter.registerUser(
                mEmail.getText().toString(),
                mFirstName.getText().toString(),
                mLastName.getText().toString(),
                mPassword.getText().toString(),
                mRetypePass.getText().toString(),
                mBio.getText().toString()
        );
    }

    @Override
    public void loadProfilePhoto(Uri uri) {
        Glide.with(this)
                .load(uri)
                .apply(new RequestOptions().centerCrop())
                .into(mProfilePhoto);
    }

    @Override
    public void promptUserForPhoto(Intent photoIntent, int requestCode) {
        startActivityForResult(photoIntent, requestCode);
    }

    @Override
    public void launchMainActivity() {
        ((LoginActivity) getActivity()).launchActivity(MainActivity.LABEL);
    }

    @OnActivityResult(SignUpContract.Presenter.PICTURE_REQUEST_CODE)
    void onPicture(int resultCode, Intent data) {
        mPresenter.attendUserResult(resultCode, data);
    }


}
