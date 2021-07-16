package com.aaronevans.paidtogo.ui.main.winners;

import android.util.Log;
import android.widget.Toast;

import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.remote.PaidToGoService;
import com.aaronevans.paidtogo.data.remote.response.WinnersResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class WinnersPresenter implements WinnersContract.Presenter {

    WinnersContract.View mView;

    @Override
    public WinnersContract.Presenter start(WinnersContract.View view) {
        this.mView=view;
        return this;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void loadUserData(String id, String date,String  month)
    {

        if (Integer.parseInt(month)>5)
        {

            Log.e("id","++++"+date);
            Log.e("id","++++"+id);
            mView.showProgressDialog();
            PaidToGoService
                    .getServiceClient()
                    .getWinnerswithnew(id, date,"1")
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            this::loadNestedUser,
                            this::onError
                    );
        }
        else
        {

            Log.e("id","++++"+date);
            Log.e("id","++++"+id);
            mView.showProgressDialog();
            PaidToGoService
                    .getServiceClient()
                    .getWinners(id, date)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            this::loadNestedUser,
                            this::onError
                    );

        }
        }


    public void loadNestedUser(ArrayList<WinnersResponse> userResponse) {

        Log.e("userResponse","45++++"+userResponse.toString());

        mView.hideProgressDialog();
        mView.onWinnersData(userResponse);
    }

    public void onError(Throwable throwable) {
        Log.e("userResponse","onError++++"+throwable.getMessage());

        mView.hideProgressDialog();
        if (throwable instanceof HttpException) {
            mView.showErrorAlert(PaidToGoService.attendError((HttpException) throwable).getDetail());
        } else {
            mView.showErrorAlert(mView.getContext().getString(R.string.connection_problem));
        }
    }
}