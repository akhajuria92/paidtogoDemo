package com.aaronevans.paidtogo.ui.inviteorg.organizations;

import android.app.ProgressDialog;
import android.graphics.Rect;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.entities.AcceptInvitation;
import com.aaronevans.paidtogo.data.entities.User;
import com.aaronevans.paidtogo.data.local.UserPreferences;
import com.aaronevans.paidtogo.data.mHandler;
import com.aaronevans.paidtogo.data.remote.response.EligiblePool;
import com.aaronevans.paidtogo.databinding.FragmentInviteorgOrganizationsBinding;
import com.aaronevans.paidtogo.ui.BaseFragment;
import com.aaronevans.paidtogo.ui.inviteorg.organizations.components.OrganizationsAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.BindingObject;
import org.androidannotations.annotations.DataBound;
import org.androidannotations.annotations.EFragment;

import java.util.ArrayList;

/**
 * Created by leandro on 24/11/17.
 */

@DataBound
@EFragment(R.layout.fragment_inviteorg_organizations)
public class OrganizationsFragment extends BaseFragment implements AddOrganizationContract.View {

    @BindingObject
    FragmentInviteorgOrganizationsBinding mBinding;

    AddOrganizationContract.Presenter mPresenter;
    ProgressDialog mProgressDialog;


    @AfterViews
    public void setup() {
        OrganizationsAdapter adapter = new OrganizationsAdapter(new ArrayList<>());
        mBinding.mSearchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if(mHandler.user_lat==0.0 || mHandler.user_lng==0.0){

                    }
                    mPresenter.getSearch( UserPreferences.getUser(getContext()).getId(), mHandler.user_lat,mHandler.user_lng,String.valueOf(v.getText()));
                    return true;
                }
                return false;
            }
        });
        mBinding.mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mBinding.mRecyclerView.setAdapter(adapter);
        mBinding.mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int index = parent.getChildAdapterPosition(view);
                outRect.top = 24;
                outRect.left = 24;
                outRect.right = 24;
                outRect.bottom = index == parent.getAdapter().getItemCount() - 1 ? 24 : 0;
            }
        });



    }
/*
    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            mHandler.user_lng = location.getLongitude();
            mHandler.user_lat = location.getLatitude();
            User user = UserPreferences.getUser(getContext());
            UserId id = new UserId(user.getId());
            mPresenter.getInvitations(id, String.valueOf(mHandler.user_lat),mHandler.user_lng);

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };*/

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
        new AlertDialog.Builder(getContext())
                .setMessage(msg)
                .setTitle(getResources().getString(R.string.app_name))
                .setPositiveButton(R.string.alert_accept, null)
                .create()
                .show();
    }

    @Override
    @AfterViews
    public void startPresenter() {
        mPresenter = new AddOrganizationPresenter().start(this);
        User user = UserPreferences.getUser(getContext());

       // mPresenter.getInvitations(user.getId(), 44.5657,-123.2878);
        mPresenter.getInvitations(user.getId(), mHandler.user_lat,mHandler.user_lng);

    }

    @Override
    public void loadListInvitations(ArrayList<EligiblePool> invitationsResponse) {
        OrganizationsAdapter adapter = new OrganizationsAdapter(invitationsResponse);
        adapter.setClickListener(position -> {
            User user = UserPreferences.getUser(getContext());
            AcceptInvitation id = new AcceptInvitation(user.getId(),
                    String.valueOf(invitationsResponse.get(position).getId()));
            mPresenter.acceptInvitation(id);
        });
        mBinding.mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void loadListSearch(ArrayList<EligiblePool> searchResponse) {
        OrganizationsAdapter adapter = new OrganizationsAdapter(searchResponse);
        adapter.setClickListener(position -> {
            User user = UserPreferences.getUser(getContext());
            AcceptInvitation id = new AcceptInvitation(user.getId(),
                    String.valueOf(searchResponse.get(position).getId()));
            mPresenter.acceptInvitation(id);
        });
        mBinding.mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void acceptedInvitation(String detail) {
        new AlertDialog.Builder(getContext())
                .setMessage(detail)
                .setTitle(getResources().getString(R.string.app_name))
                .setPositiveButton(R.string.alert_accept, (dialogInterface, i) -> startPresenter())
                .create()
                .show();
    }
}
