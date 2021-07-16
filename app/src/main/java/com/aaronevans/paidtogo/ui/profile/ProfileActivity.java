package com.aaronevans.paidtogo.ui.profile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.aaronevans.paidtogo.components.FetchPath;
import com.aaronevans.paidtogo.data.remote.PaidToGoService;
import com.aaronevans.paidtogo.data.remote.response.offers_response.OffersResponse;
import com.aaronevans.paidtogo.ui.profile.edit.ProfilePicImplementation;
import com.aaronevans.paidtogo.ui.profile.edit.ProfilePicView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.entities.User;
import com.aaronevans.paidtogo.data.local.UserPreferences;
import com.aaronevans.paidtogo.databinding.ActivityProfileBinding;
import com.aaronevans.paidtogo.ui.BaseActivity;
import com.aaronevans.paidtogo.ui.profile.components.TwoFragmentsAdapter;
import com.aaronevans.paidtogo.ui.profile.edit.EditProfileFragment_;
import com.aaronevans.paidtogo.ui.start.LoginActivity_;
import com.aaronevans.paidtogo.ui.start.signup.SignUpContract;
import com.google.gson.Gson;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.BindingObject;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.DataBound;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.ObservableFloat;
import androidx.fragment.app.Fragment;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by leandro on 22/11/17.
 */
@DataBound
@EActivity(R.layout.activity_profile)
public class ProfileActivity extends BaseActivity implements ProfileContract.View, ProfilePicView {

    @BindingObject
    ActivityProfileBinding mBinding;

    ProfileContract.Presenter mPresenter;
    ObservableFloat mProgress = new ObservableFloat(0);
    private ProgressDialog mProgressDialog = null;
    private Uri mOutputFileUri;
    private Uri mSelectedImageUri;
    ProfilePicImplementation profilePicImplementation;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mLogOut:

                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(this);
                }
                builder.setTitle("Logout")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                mPresenter.logout();
                                LoginActivity_.intent(ProfileActivity.this)
                                        .flags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                                        .start()
                                        .withAnimation(android.R.anim.fade_in, android.R.anim.fade_out);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                break;


            case android.R.id.home:
                // API 5+ solution
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

        return true;
    }

    @AfterViews
    public void setup() {

        /*Toolbar toolbar = findViewById(R.id.mToolbar);
        setSupportActionBar(toolbar);*/


        setSupportActionBar(mBinding.mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Profile");

        mBinding.setSelf(this);
        mBinding.mPager.setAdapter(new TwoFragmentsAdapter(getSupportFragmentManager()));
        mBinding.mProfilePicture.setClipToOutline(true);
        mBinding.mTabLayout.setupWithViewPager(mBinding.mPager);
        mBinding.mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.app_green));
        mBinding.mAppBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            mProgress.set((verticalOffset * -1f) / mBinding.mAppBar.getTotalScrollRange());
        });
        showUserData();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public ProfileContract.Presenter getPresenter() {
        return mPresenter;
    }

    private void showUserData() {
        User user = UserPreferences.getUser(this);
        if (!TextUtils.isEmpty(user.getPhotoUrl())) {
            if (user.getPhotoUrl().contains(",")) {
                loadImageBase64(user);
            } else
                Glide.with(this)
                        .load(user.getPhotoUrl())
                        .placeholder(R.drawable.ic_profile_placeholder)
                        .apply(new RequestOptions().centerCrop().error(R.drawable.ic_profile_placeholder))
                        .into(mBinding.mProfilePicture);
        }
        mBinding.mProfileNameText.setText(user.getFullName());
    }

    private void loadImageBase64(User user) {

        String[] userUrlImage = user.getPhotoUrl().split(",");
        byte[] imageByteArray = Base64.decode(userUrlImage[1], Base64.DEFAULT);



        Glide.with(this)
                .load(imageByteArray)
                .apply(new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.NONE))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mBinding.mProfilePicture);
    }

    @Click(R.id.mTakePhoto)
    public void pickProfilePhoto() {
        mPresenter.pickProfilePhoto();
    }

    public ObservableFloat getProgress() {
        return mProgress;
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
        new AlertDialog.Builder(ProfileActivity.this)
                .setTitle(getResources().getString(R.string.app_name))
                .setMessage(msg)
                .setPositiveButton(R.string.alert_accept, null)
                .create()
                .show();
        showUserData();
    }

    @Override
    @AfterViews
    public void startPresenter() {
        mPresenter = new ProfilePresenter()
                .start(this);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void loadProfilePhoto(Uri uri) {
        Glide.with(this)
                .load(uri)
                .apply(new RequestOptions().centerCrop())
                .into(mBinding.mProfilePicture);
    }

    @Override
    public boolean showErrors() {
        Fragment fragment = ((TwoFragmentsAdapter) mBinding.mPager.getAdapter()).getItem(0);
        return ((EditProfileFragment_) fragment).showErrors();

    }

    @Override

    public void promptUserForPhoto(Intent photoIntent, int requestCode) {
        startActivityForResult(photoIntent, requestCode);

    }

    @OnActivityResult(SignUpContract.Presenter.PICTURE_REQUEST_CODE)
    void onPicture(int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            boolean isCamera;
            if (data == null || data.getData() == null) {
                isCamera = true;

            } else {
                final String action = data.getAction();
                isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);


            }
            if (isCamera) {
                mSelectedImageUri = mOutputFileUri;
            } else {
                mSelectedImageUri = data.getData();
            }



        }
//        if (mSelectedImageUri != null) {
//            String filePath = FetchPath.getPath(this, mSelectedImageUri);
//            uploadImage(filePath);
//
//        }

        mPresenter.attendUserResult(resultCode, data);
    }

//    private void uploadImage(String filePath) {
//        profilePicImplementation = new ProfilePicImplementation(this,this);
//        profilePicImplementation.addPic(UserPreferences.getUser(getContext()).getAccessToken(),filePath);
//    }

    @Override
    public String getAge() {
        return null;
    }

    @Override
    public String getGender() {
        return null;
    }

    @Override
    public boolean isForExercise() {
        return false;
    }

    @Override
    public boolean isWork() {
        return false;
    }

    @Override
    public boolean isChanging() {
        return false;
    }

    @Override
    public boolean isWalkRun() {
        return false;
    }

    @Override
    public boolean isBike() {
        return false;
    }

    @Override
    public boolean isBusTrain() {
        return false;
    }


    @Override
    public void onSucesss(ProfilePicmodel profilePicmodel) {

    }

    @Override
    public void onerror(String error) {

    }

    @Override
    public void onInternet(String tag) {

    }
}
