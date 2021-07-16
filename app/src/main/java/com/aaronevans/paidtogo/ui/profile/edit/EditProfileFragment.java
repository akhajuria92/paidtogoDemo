package com.aaronevans.paidtogo.ui.profile.edit;

import android.animation.LayoutTransition;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;

import com.aaronevans.paidtogo.data.remote.PaidToGoService;
import com.aaronevans.paidtogo.ui.main.MainActivity;
import com.aaronevans.paidtogo.ui.start.LoginActivity_;
import com.google.android.material.textfield.TextInputLayout;

import android.util.Log;
import android.util.Patterns;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.entities.User;
import com.aaronevans.paidtogo.data.local.UserPreferences;
import com.aaronevans.paidtogo.databinding.FragmentProfileEditBinding;
import com.aaronevans.paidtogo.ui.BaseFragment;
import com.aaronevans.paidtogo.ui.change_password.ChangePasswordActivity_;
import com.aaronevans.paidtogo.ui.profile.ProfileActivity;
import com.aaronevans.paidtogo.ui.profile.ProfileContract;
import com.aaronevans.paidtogo.ui.profile.ProfilePresenter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.BindingObject;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.DataBound;
import org.androidannotations.annotations.EFragment;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Created by leandro on 23/11/17.
 */
@DataBound
@EFragment(R.layout.fragment_profile_edit)
public class EditProfileFragment extends BaseFragment {

    @BindingObject
    FragmentProfileEditBinding mBinding;
    ProfileContract.Presenter mPresenter;
    private ProgressDialog mProgressDialog;


    @AfterViews
    public void setup() {
        mPresenter = ((ProfileActivity) getActivity()).getPresenter();
        if (mPresenter == null)
            mPresenter = new ProfilePresenter().start(((ProfileActivity) getActivity()));
        ViewGroup root = (ViewGroup) mBinding.getRoot();
        LayoutTransition layoutTransition = root.getLayoutTransition();
        layoutTransition.enableTransitionType(LayoutTransition.CHANGING);
        mBinding.mContainer.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        User user = UserPreferences.getUser(getContext());
        mBinding.mFirstName.setText(user.getFirstName());
        mBinding.mLastName.setText(user.getLastName());
        mBinding.mEmail.setText(user.getEmail());


    }

    @Click(R.id.mChangePassword)
    public void changePassword() {
        ChangePasswordActivity_.intent(this)
                .flags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .start()
                .withAnimation(android.R.anim.fade_in, android.R.anim.fade_out);
    }


    @Click(R.id.mOk)
    public void updateData() {
        mPresenter.updateDataUser(
                mBinding.mEmail.getText().toString(),
                mBinding.mFirstName.getText().toString(),
                mBinding.mLastName.getText().toString(),
                UserPreferences.getUser(getContext()).getBio(),
                mBinding.mLinkPayPal.getText().toString(),
                UserPreferences.getToken(getActivity()));
    }


    @Click(R.id.delete)
    void deleteAccont() {
        deleteddialog();

    }

    private void deleteddialog() {
        android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(getActivity());
        dialog.setCancelable(false);
        dialog.setTitle("Delete Account");
        dialog.setMessage("Are you sure you want to delete this Account?" );
        dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //Action for "Delete".
                dialog.dismiss();
                deleteAccount();

            }
        })
                .setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Action for "Cancel".

                        dialog.dismiss();
                    }
                });

        final android.app.AlertDialog alert = dialog.create();
        alert.show();
    }




    public boolean showErrors() {
        return checkField(mBinding.mEmail, mBinding.mEmailIL, true, R.string.fill_email) ||
                checkField(mBinding.mFirstName, mBinding.mFirstNameIL, false, R.string.fill_name) ||
                checkField(mBinding.mLastName, mBinding.mLastNameIL, false, R.string.fill_surname);
    }

    private boolean checkField(EditText et, TextInputLayout lbl, boolean isEmail, int string) {

        if (isEmail) {
            if (et.getText().toString().trim().isEmpty()) {
                lbl.setErrorEnabled(true);
                lbl.setError(getString(string));
                et.requestFocus();
                return true;
            } else if (!Patterns.EMAIL_ADDRESS.matcher(et.getText().toString()).matches()) {
                lbl.setErrorEnabled(true);
                lbl.setError(getString(R.string.invalid_email));
                et.requestFocus();
                return true;
            } else {
                lbl.setErrorEnabled(false);
                return false;
            }

        } else {

            if (et.getText().toString().trim().isEmpty()) {
                lbl.setErrorEnabled(true);
                lbl.setError(getString(string));
                et.requestFocus();
                return true;
            } else {
                lbl.setErrorEnabled(false);
                return false;
            }

        }
    }

    void deleteAccount() {
        showProgressDialog();
        PaidToGoService
                .getServiceClient()
                .deleteAccount(UserPreferences.getUser(getContext()).getId())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        hideProgressDialog();
                        try {
                            String stringResponse = response.body().string().toString();
                            Toast.makeText(getActivity(),"Account Deleted Successfully",Toast.LENGTH_LONG).show();
                            UserPreferences.removeUser(getActivity());
                            LoginActivity_.intent(getActivity())
                                    .flags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                                    .start()
                                    .withAnimation(android.R.anim.fade_in, android.R.anim.fade_out);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        hideProgressDialog();
                        showErrorAlert(t.getMessage());
                    }
                });
    }


    private void showProgressDialog() {
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setTitle(getResources().getString(R.string.app_name));
        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


    private void showErrorAlert(String message) {
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setTitle(getResources().getString(R.string.app_name))
                .setPositiveButton(R.string.alert_accept, null)
                .create()
                .show();
    }


}
