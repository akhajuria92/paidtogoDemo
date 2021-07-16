package com.aaronevans.paidtogo.ui.inviteorg.organizations;

import com.aaronevans.paidtogo.data.entities.AcceptInvitation;
import com.aaronevans.paidtogo.data.remote.PaidToGoService;
import com.aaronevans.paidtogo.data.remote.response.AcceptInviteResponse;
import com.aaronevans.paidtogo.data.remote.response.EligiblePool;

import java.util.ArrayList;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * Created by evaristo on 12/12/16.
 */

public class AddOrganizationPresenter implements AddOrganizationContract.Presenter {

    private AddOrganizationContract.View mView;

    @Override
    public AddOrganizationContract.Presenter start(AddOrganizationContract.View view) {
        mView = view;

        return this;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void getInvitations(String id, double lat, double lng) {
        if(lat==0.0||lng==0.0) {
            mView.showErrorAlert("Your device is not getting your location please make sure your location is on and restart the application");
        }else{
            mView.showProgressDialog();
            PaidToGoService.getServiceClient().getEligiblePools(id, lat, lng)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            this::loadInvitations,
                            this::onError
                    );
        }
    }

    @Override
    public void getSearch(String id, double lat, double lng, String query) {
        mView.showProgressDialog();
        PaidToGoService.getServiceClient().getSearch(id, query)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::loadSearch,
                        this::onError
                );
    }

    @Override
    public void acceptInvitation(AcceptInvitation id) {
        mView.showProgressDialog();
        PaidToGoService.getServiceClient().acceptInvitation(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::acceptedInvitation,
                        this::onError
                );
    }

    private void acceptedInvitation(AcceptInviteResponse updateResponse) {
        mView.hideProgressDialog();
        mView.acceptedInvitation(updateResponse.getMessage());
    }

    private void onError(Throwable throwable) {
        mView.hideProgressDialog();
        if (throwable instanceof HttpException) {
            mView.showErrorAlert(PaidToGoService.attendError((HttpException) throwable).getDetail());
        } else {
           // mView.showErrorAlert(mView.getContext().getString(R.string.connection_problem));
            mView.showErrorAlert("No Pool Found");
        }
    }

    private void loadInvitations(ArrayList<EligiblePool> invitationsResponse) {
        mView.hideProgressDialog();
        mView.loadListSearch(invitationsResponse);
    }

    private void loadSearch(ArrayList<EligiblePool> searchResponse) {
        mView.hideProgressDialog();
        mView.loadListSearch(searchResponse);
    }
}
