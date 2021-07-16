package com.aaronevans.paidtogo.ui.inviteorg.organizations;

import com.aaronevans.paidtogo.data.entities.AcceptInvitation;
import com.aaronevans.paidtogo.data.remote.response.EligiblePool;
import com.aaronevans.paidtogo.ui.contracts.BasePresenter;
import com.aaronevans.paidtogo.ui.contracts.BaseView;
import com.aaronevans.paidtogo.ui.contracts.LoaderView;

import java.util.ArrayList;

/**
 * Created by evaristo on 12/12/16.
 */

public interface AddOrganizationContract {

    interface View extends BaseView, LoaderView {
        void loadListInvitations(ArrayList<EligiblePool> invitationsResponse);
        void loadListSearch(ArrayList<EligiblePool> searchResponse);

        void acceptedInvitation(String detail);
    }

    interface Presenter extends BasePresenter<View, Presenter> {
        void getInvitations(String id,double lat,double lng);
        void getSearch(String id,double lat,double lng,String query);

        void acceptInvitation(AcceptInvitation id);
    }
}
