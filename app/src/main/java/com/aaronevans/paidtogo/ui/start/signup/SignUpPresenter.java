package com.aaronevans.paidtogo.ui.start.signup;

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
import android.util.Base64;

import com.bumptech.glide.Glide;
import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.entities.User;
import com.aaronevans.paidtogo.data.local.UserPreferences;
import com.aaronevans.paidtogo.data.remote.PaidToGoService;
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

/**
 * Created by evaristo on 20/12/16.
 */

public class SignUpPresenter implements SignUpContract.Presenter {

    private SignUpContract.View mView;
    private Uri mOutputFileUri;
    private Uri mSelectedImageUri;
    private User user;

    public static String toBase64(Bitmap image) {
        //int compress = (image.getByteCount()) / (4 * 1024*1024);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 80, output);
        byte[] bytes = output.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    @Override
    public SignUpContract.Presenter start(SignUpContract.View view) {
        mView = view;
        return this;
    }

    @Override
    public void destroy() {

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
            mView.loadProfilePhoto(mSelectedImageUri);
        }
    }

    @Override
    public void registerUser(String email, String name, String lastName, String password, String retypePassword, String bio) {
        if (mView.showErrors()) return;

        mView.showProgressDialog();

        user = new User()
                .setEmail(email)
                .setFirstName(name)
                .setLastName(lastName)
                .setPassword(password)
                .setBio(bio);

        if (mSelectedImageUri != null) {
            getBitmap(mSelectedImageUri)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(SignUpPresenter::toBase64)
                    .doOnNext(photo64 -> user.setPhoto("data:image/jpeg;base64," + photo64))
                    .switchMap(photo64 -> PaidToGoService
                            .getServiceClient()
                            .registerUser(user)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread()))
                    .subscribe(
                            this::loadNestedUser,
                            this::onError
                    );
        }
        else
            PaidToGoService
                .getServiceClient()
                .registerUser(user)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::loadNestedUser, this::onError);

    }

    public void loadNestedUser(User userResponse) {
        /*mView.hideProgressDialog();
        try {
            String response=userResponse.string().toString();
            Log.e("the response is",response);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        user.setId(userResponse.getId()).setAccessToken(userResponse.getAccessToken());
        user.setSubscription_id(userResponse.getSubscription_id());
        UserPreferences.saveSocialToken(mView.getContext(), "");
        UserPreferences.saveUser(mView.getContext(), user);



        mView.launchMainActivity();
    }

    public void onError(Throwable throwable) {
        mView.hideProgressDialog();
        if (throwable instanceof HttpException) {
            mView.showErrorAlert(PaidToGoService.attendError((HttpException) throwable).getDetail());
        } else {
            mView.showErrorAlert("Without internet");
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
