package com.aaronevans.paidtogo.ui.ComplaintContactActivity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Base64;

import com.bumptech.glide.Glide;
import com.aaronevans.paidtogo.PaidToGoApp;
import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.remote.PaidToGoService;
import com.aaronevans.paidtogo.data.remote.request.ComplaintContactBody;
import com.aaronevans.paidtogo.data.remote.response.UpdateResponse;

import java.io.ByteArrayOutputStream;

import androidx.appcompat.app.AlertDialog;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class ComplaintPresenter implements ComplaintContract.Presenter
{

    ComplaintContract.View mView;


    @Override
    public ComplaintContract.Presenter start(ComplaintContract.View view) {
       this.mView=view;
       return  this;
    }

    @Override
    public void postComplaint(String name, String email, String title, String reason, String description, Uri mSelectedImageUri) {

        if (PaidToGoApp.isInternetConnection(mView.getContext())) {
            mView.showProgressDialog();
            ComplaintContactBody complaintContactBody = new ComplaintContactBody();
            complaintContactBody.setName(name);
            complaintContactBody.setEmail(email);
            complaintContactBody.setTitle(title);
            complaintContactBody.setReason(reason);
            complaintContactBody.setDescription(description);

            if (mSelectedImageUri != null)
            {

                getBitmap(mSelectedImageUri)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(ComplaintPresenter::toBase64)
                        .doOnNext(photo64 -> complaintContactBody.setPhoto("data:image/jpeg;base64," + photo64))
                        .switchMap(photo64 -> PaidToGoService
                                .getServiceClient()
                                .compliant(complaintContactBody)
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread()))
                        .subscribe(
                                this::loadNestedUser,
                                this::onError
                        );
            }
            else

                PaidToGoService.getServiceClient().compliant(complaintContactBody)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                this::loadNestedUser,
                                this::onError
                        );



        } else
            mView.showErrorAlert(mView.getContext().getResources().getString(R.string.check_internet_connecrion));
    }


    public static String toBase64(Bitmap image) {
        //int compress = (image.getByteCount()) / (4 * 1024*1024);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 80, output);
        byte[] bytes = output.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    public Observable<Bitmap> getBitmap(Uri path) {
        return Observable.fromCallable(() -> Glide.with(mView.getContext())
                .asBitmap()
                .load(path)
                .submit(300, 300).get())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public void loadNestedUser(UpdateResponse userResponse) {
        mView.hideProgressDialog();
        if (userResponse.getCode().equals(PaidToGoService.CODE_SUCCESS)) {
            new AlertDialog.Builder(mView.getContext())
                    .setTitle("Success")
                    .setMessage("Compliant Sent Successfully")
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mView.onBack();

                        }
                    })
                    .create()
                    .show();
        }
    }

    @Override
    public void destroy() {

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
