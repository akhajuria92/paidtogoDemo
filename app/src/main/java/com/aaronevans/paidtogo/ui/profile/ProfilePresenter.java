package com.aaronevans.paidtogo.ui.profile;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;

import com.aaronevans.paidtogo.components.FetchPath;
import com.aaronevans.paidtogo.ui.profile.edit.ProfilePicImplementation;
import com.bumptech.glide.Glide;
import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.entities.User;
import com.aaronevans.paidtogo.data.local.UserPreferences;
import com.aaronevans.paidtogo.data.remote.PaidToGoService;
import com.aaronevans.paidtogo.data.remote.request.UpdateProfile;
import com.aaronevans.paidtogo.data.remote.response.UpdateResponse;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

import static com.aaronevans.paidtogo.ui.start.signup.SignUpContract.Presenter.PICTURE_REQUEST_CODE;

/**
 * Created by evaristo on 26/12/16.
 */

public class ProfilePresenter implements ProfileContract.Presenter {

    ProfileContract.View mView;
    private Uri mOutputFileUri;
    private Uri mSelectedImageUri;
    private UpdateProfile updateProfile = null;
    ProfilePicImplementation profilePicImplementation;

    public static String toBase64(Bitmap image) {
        //int compress = (image.getByteCount()) / (4 * 1024*1024);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 80, output);
        byte[] bytes = output.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    @Override
    public ProfileContract.Presenter start(ProfileContract.View view) {
        mView = view;
        return this;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void logout() {
        UserPreferences.removeUser(mView.getContext());
    }

    @Override
    public void attendUserResult(int resultCode, Intent data) {
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

            if (mSelectedImageUri != null) {
                String filePath = FetchPath.getPath(mView.getContext(), mSelectedImageUri);
                uploadImage(filePath);

            }



            mView.loadProfilePhoto(mSelectedImageUri);
        }
    }

    private void uploadImage(String filePath) {
        profilePicImplementation = new ProfilePicImplementation(mView.getContext(),mView.getContext());
        profilePicImplementation.addPic(UserPreferences.getUser(mView.getContext()).getAccessToken(),filePath);
    }



    @Override
    public void pickProfilePhoto() {
        new RxPermissions((Activity) mView.getContext())
                .request(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        "android.permission.READ_EXTERNAL_STORAGE"
                )
                .subscribeOn(Schedulers.trampoline())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(granted -> {
                    if (granted) takePicture();
                    else
                        mView.showErrorAlert(mView.getContext().getString(R.string.missing_permissions));
                });
    }

    @Override
    public void updateDataUser(String email, String name, String lastName, String bio, String accountPaypal, String accessToken) {



        if (mView.showErrors()) return;

        mView.showProgressDialog();

        updateProfile = new UpdateProfile();
        if (!TextUtils.isEmpty(email))
            if (!email.equals(UserPreferences.getUser(mView.getContext()).getEmail()))
                updateProfile.setEmail(email);
        updateProfile.setFirstName(name);
        updateProfile.setLastName(lastName);
        updateProfile.setBio(bio);
        updateProfile.setPaypalAccount(accountPaypal);
        updateProfile.setAccessToken(accessToken);

        if (mSelectedImageUri != null) {
            getBitmap(mSelectedImageUri)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(ProfilePresenter::toBase64)
                    .flatMap(
                            photo64 -> {
                                updateProfile.setProfilePicture("data:image/jpeg;base64," + photo64);
                                return PaidToGoService
                                        .getServiceClient()
                                        .updateUser(updateProfile)
                                        .subscribeOn(Schedulers.newThread())
                                        .observeOn(AndroidSchedulers.mainThread());
                            }
                    )
                    .subscribe(
                            this::loadNestedUser,
                            this::onError
                    );
        } else PaidToGoService
                .getServiceClient()
                .updateUser(updateProfile)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::loadNestedUser,
                        this::onError
                );
    }

    public void loadNestedUser(UpdateResponse userResponse) {
        mView.hideProgressDialog();
        if (userResponse.getCode().equals(PaidToGoService.CODE_PROFILE_SUCCESSFUL)) {
            loadNewDatUser(updateProfile);
        }
    }

    private void loadNewDatUser(UpdateProfile updateProfile) {
        mView.hideProgressDialog();
        User user = UserPreferences.getUser(mView.getContext());
        if (!TextUtils.isEmpty(updateProfile.getEmail()))
            if (!updateProfile.getEmail().equals(UserPreferences.getUser(mView.getContext()).getEmail()))
                user.setEmail(updateProfile.getEmail());
        if (!TextUtils.isEmpty(updateProfile.getBio()))
            if (!updateProfile.getBio().equals(UserPreferences.getUser(mView.getContext()).getBio()))
                user.setBio(updateProfile.getBio());
        if (!TextUtils.isEmpty(updateProfile.getFirstName()))
            if (!updateProfile.getFirstName().equals(UserPreferences.getUser(mView.getContext()).getFirstName()))
                user.setFirstName(updateProfile.getFirstName());
        if (!TextUtils.isEmpty(updateProfile.getLastName()))
            if (!updateProfile.getLastName().equals(UserPreferences.getUser(mView.getContext()).getLastName()))
                user.setLastName(updateProfile.getLastName());
        if (!TextUtils.isEmpty(updateProfile.getProfilePicture()))
            if (!updateProfile.getProfilePicture().equals(UserPreferences.getUser(mView.getContext()).getPhotoUrl()))
                user.setPhoto(updateProfile.getProfilePicture());
//        user.setAge(mView.getAge());
//        user.setGender(mView.getGender());
//        user.setForExcercise(mView.isForExercise());
//        user.setWork(mView.isWork());
//        user.setChanging(mView.isChanging());
//        user.setWalkRun(mView.isWalkRun());
//        user.setBike(mView.isBike());
//        user.setBusTrain(mView.isBusTrain());

        UserPreferences.saveUser(mView.getContext(), user);
        mView.showErrorAlert(mView.getContext().getResources().getString(R.string.alert_profile));

    }

    public void onError(Throwable throwable) {
        mView.hideProgressDialog();
        if (throwable instanceof HttpException) {
            mView.showErrorAlert(PaidToGoService.attendError((HttpException) throwable).getDetail());
        } else {
            mView.showErrorAlert(mView.getContext().getString(R.string.connection_problem));
        }
    }

    public Observable<Bitmap> getBitmap(Uri path) {
        return Observable.fromCallable(() -> Glide.with(mView.getContext())
                .asBitmap()
                .load(path)
                .submit(300, 300).get())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void takePicture() {
        final File root = new File(Environment.getExternalStorageDirectory() + File.separator + mView.getContext().getString(R.string.app_name) + File.separator);
        if (!(root.mkdirs() || root.isDirectory())) {
            mView.showErrorAlert(mView.getContext().getString(R.string.cant_save_photo));
            return;
        }
        final String fname = "TMP_" + Calendar.getInstance().getTimeInMillis() + ".jpg";
        final File sdImageMainDirectory = new File(root, fname);
        mOutputFileUri = Uri.fromFile(sdImageMainDirectory);

        // Camera.
        final List<Intent> cameraIntents = new ArrayList<>();
        final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = mView.getContext().getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mOutputFileUri);
            cameraIntents.add(intent);
        }

        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_PICK);

        final Intent chooserIntent = Intent.createChooser(galleryIntent, mView.getContext().getString(R.string.select_source));

        // Add the camera options.
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));

        mView.promptUserForPhoto(chooserIntent, PICTURE_REQUEST_CODE);
    }

}