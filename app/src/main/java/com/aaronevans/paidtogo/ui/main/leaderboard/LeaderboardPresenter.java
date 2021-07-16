package com.aaronevans.paidtogo.ui.main.leaderboard;

import android.util.Log;
import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.remote.PaidToGoService;
import com.aaronevans.paidtogo.data.remote.response.LeaderboardResponse;
import java.util.ArrayList;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class LeaderboardPresenter implements LeaderBoardContract.Presenter {

    LeaderBoardContract.View mView;

    @Override
    public LeaderBoardContract.Presenter start(LeaderBoardContract.View view) {
        this.mView=view;
        return this;
    }
    @Override
    public void destroy() {

    }

    @Override
    public void loadUserData(String id, String month, String year, String toolbar_title)
    {
        Log.e("year","++++"+year);
        Log.e("month","++++"+month);
        Log.e("id","++++"+id);

        mView.showProgressDialog();

        PaidToGoService
                .getServiceClient()
                .getLeaderboards(id, year, month)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::loadNestedUser,
                        this::onError
                );
    }

    public void loadNestedUser(ArrayList<LeaderboardResponse> userResponse) {
        Log.e("userResponse","45++++"+userResponse.toString());

        mView.hideProgressDialog();
        mView.onLoadLeaderBoardData(userResponse);
    }

    public void onError(Throwable throwable) {
        Log.e("userResponse","onError++++"+throwable.getMessage());

        mView.hideProgressDialog();
        if (throwable instanceof HttpException) {
            mView.showErrorAlert(PaidToGoService.attendError((HttpException) throwable).getDetail());
        } else {
            mView.showErrorAlert("Without internet");
        }
    }
}