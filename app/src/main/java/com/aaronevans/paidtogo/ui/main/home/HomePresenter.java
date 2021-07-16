package com.aaronevans.paidtogo.ui.main.home;

import com.aaronevans.paidtogo.PaidToGoApp;
import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.entities.Activity;
import com.aaronevans.paidtogo.data.remote.PaidToGoService;
import com.aaronevans.paidtogo.data.remote.request.RegisterActivity;
import com.aaronevans.paidtogo.data.remote.response.PoolResponse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * Created by Infinix Android on 16/1/2017.
 */

public class HomePresenter implements HomeContract.Presenter {

    public static final String CODE_ACTIVITY_UNSUCCESSFUL = "ACTIVITY_UNSUCCESSFUL";
    private HomeContract.View mView = null;
    private List<Activity> listActities = null;

    @Override
    public HomeContract.Presenter start(HomeContract.View view) {
        mView = view;
        return this;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void loadActivities(String userId) {
        // cargo las actividades del usuario
        listActities = new ArrayList<>();
        if (PaidToGoApp.isInternetConnection(mView.getContext())) {
            mView.showProgressDialog();
            String date1, date2;

                final Calendar cal = Calendar.getInstance();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String date = dateFormat.format(cal.getTime());
                date1=date;
                final Calendar cal1 = Calendar.getInstance();
                cal1.add(Calendar.DATE, 1);
                DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                String date0 = dateFormat1.format(cal1.getTime());
                date2=date0;
            PaidToGoService.getServiceClient().getActivities(userId,"true",date1,date2)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            this::loadListActivities,
                            this::onError
                    );
        } else
            mView.showErrorAlert(mView.getContext().getResources().getString(R.string.check_internet_connecrion));
    }

    @Override
    public void registerActivity(RegisterActivity activity) {
        if (PaidToGoApp.isInternetConnection(mView.getContext())) {
            mView.showProgressDialog();
            /*PaidToGoService.getServiceClient().registerActivity(activity)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            this::registerActivityResponse,
                            this::onError
                    );*/
        } else
            mView.showErrorAlert(mView.getContext().getResources().getString(R.string.check_internet_connecrion));

    }

    private void registerActivityResponse(Activity activity) {
        mView.hideProgressDialog();
        mView.onRegisterActivity(activity);
    }

    public void loadListActivities(ArrayList<PoolResponse> responseBody) {
        mView.hideProgressDialog();
        ArrayList<PoolResponse> activities = new ArrayList<>();
        activities.addAll(responseBody);
        /*activities.addAll(responseBody.getSponsorPools());
        activities.add(responseBody.getNationalPool());*/
        mView.onActivitiesResponse(activities);
    }

    public void onError(Throwable throwable) {
        mView.hideProgressDialog();
        if (throwable instanceof HttpException) {
            mView.showErrorAlert(PaidToGoService.attendError((HttpException) throwable).getDetail());
        } else {
            try{
                mView.showErrorAlert(mView.getContext().getString(R.string.connection_problem));
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
