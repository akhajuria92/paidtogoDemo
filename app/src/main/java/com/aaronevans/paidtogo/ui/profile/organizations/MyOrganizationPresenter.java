package com.aaronevans.paidtogo.ui.profile.organizations;

import com.aaronevans.paidtogo.PaidToGoApp;
import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.entities.RejectInvitation;
import com.aaronevans.paidtogo.data.remote.PaidToGoService;
import com.aaronevans.paidtogo.data.remote.response.AcceptInviteResponse;
import com.aaronevans.paidtogo.data.remote.response.PoolResponse;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * Created by Infinix Android on 16/1/2017.
 */

public class MyOrganizationPresenter implements MyOrganizationContract.Presenter {

    public static final String CODE_ACTIVITY_UNSUCCESSFUL = "ACTIVITY_UNSUCCESSFUL";
    private MyOrganizationContract.View mView = null;

    @Override
    public MyOrganizationContract.Presenter start(MyOrganizationContract.View view) {
        mView = view;
        return this;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void getOrganization(String userId) {
        // cargo las actividades del usuario
        if (PaidToGoApp.isInternetConnection(mView.getContext())) {
            mView.showProgressDialog();
         /*   PaidToGoService.getServiceClient().getActivities(userId)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            this::loadOrganizations,
                            this::onError
                    );*/
           /* String date1, date2;

            final Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = dateFormat.format(cal.getTime());
            date1=date;
            final Calendar cal1 = Calendar.getInstance();
            cal1.add(Calendar.DATE, 0);
            DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            String date0 = dateFormat1.format(cal1.getTime());
            date2=date0;*/
            PaidToGoService.getServiceClient().getActivitieswithoutdate(userId,"true")
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            this::loadOrganizations,
                            this::onError
                    );
        } else
            mView.showErrorAlert(mView.getContext().getResources().getString(R.string.check_internet_connecrion));

    }

    @Override
    public void rejectInvitation(RejectInvitation id) {
        mView.showProgressDialog();
        PaidToGoService.getServiceClient().rejectInvitation(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::rejectedInvitation,
                        this::onRejectError
                );
    }

    private void rejectedInvitation(AcceptInviteResponse updateResponse) {
        mView.hideProgressDialog();
        mView.showMessage(updateResponse.getMessage(), true);
    }

    public void loadOrganizations(ArrayList<PoolResponse> responseBody) {
        mView.hideProgressDialog();
        ArrayList<PoolResponse> activities = new ArrayList<>();
       /* activities.addAll(responseBody.getSponsor());
        activities.add(responseBody.getNationalPool());*/
        mView.onOrganizationResponse(responseBody);
    }

    public void onError(Throwable throwable) {
        mView.hideProgressDialog();
        if (throwable instanceof HttpException) {
            mView.showErrorAlert(PaidToGoService.attendError((HttpException) throwable).getDetail());
        } else {
            mView.showErrorAlert(mView.getContext().getString(R.string.connection_problem));
        }
    }

    public void onRejectError(Throwable throwable) {
        mView.hideProgressDialog();
        if (throwable instanceof HttpException) {
            mView.showMessage(PaidToGoService.attendError((HttpException) throwable).getDetail(), true);
        } else {
            mView.showMessage(mView.getContext().getString(R.string.connection_problem), false);
        }
    }
}
