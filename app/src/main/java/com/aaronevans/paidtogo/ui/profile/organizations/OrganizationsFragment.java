package com.aaronevans.paidtogo.ui.profile.organizations;

import android.animation.LayoutTransition;
import android.app.ProgressDialog;
import androidx.databinding.ObservableFloat;
import android.graphics.Rect;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.entities.RejectInvitation;
import com.aaronevans.paidtogo.data.entities.User;
import com.aaronevans.paidtogo.data.local.UserPreferences;
import com.aaronevans.paidtogo.data.remote.response.PoolResponse;
import com.aaronevans.paidtogo.databinding.FragmentProfileOrganizationsBinding;
import com.aaronevans.paidtogo.ui.BaseFragment;
import com.aaronevans.paidtogo.ui.inviteorg.InviteOrganizationActivity_;
import com.aaronevans.paidtogo.ui.profile.organizations.components.OrganizationsAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.BindingObject;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.DataBound;
import org.androidannotations.annotations.EFragment;

import java.util.ArrayList;

/**
 * Created by leandro on 24/11/17.
 */
@DataBound
@EFragment(R.layout.fragment_profile_organizations)
public class OrganizationsFragment extends BaseFragment implements MyOrganizationContract.View {

    @BindingObject
    FragmentProfileOrganizationsBinding mBinding;
    ObservableFloat mProgress = new ObservableFloat(0);
    private ProgressDialog mProgressDialog = null;
    private MyOrganizationContract.Presenter mPresenter = null;

    @AfterViews
    public void setup() {
        ViewGroup root = (ViewGroup) mBinding.getRoot();
        LayoutTransition layoutTransition = root.getLayoutTransition();
        layoutTransition.enableTransitionType(LayoutTransition.CHANGING);

        mBinding.mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mBinding.mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int index = parent.getChildAdapterPosition(view);
                outRect.top = 12;
                outRect.left = 12;
                outRect.right = 12;
                outRect.bottom = index == parent.getAdapter().getItemCount() - 1 ? 12 : 0;
            }
        });
        mBinding.mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                int scrollSize = recyclerView.computeVerticalScrollRange() - recyclerView.computeVerticalScrollExtent();
                int range = recyclerView.getLayoutManager().getChildAt(0).getHeight();
                int currentScroll = recyclerView.computeVerticalScrollOffset();
                float delta = Math.min((float) (scrollSize - currentScroll), range) / (float) range;
                mProgress.set(1 - delta);
            }
        });
        setUpAdapter(new ArrayList<>());
        mBinding.setSelf(this);
    }

    private void setUpAdapter(ArrayList<PoolResponse> organizations) {
        if (organizations.size() == 0) {
            mProgress.set(1);
        }
        OrganizationsAdapter adapter = new OrganizationsAdapter(organizations);
        adapter.setClickListener(position -> {
            removeOrganization(String.valueOf(organizations.get(position).getId()));
        });
        mBinding.mRecyclerView.setAdapter(adapter);

    }

    private void removeOrganization(String poolId) {
        User user = UserPreferences.getUser(getContext());
        RejectInvitation id = new RejectInvitation(user.getId(), poolId);
        mPresenter.rejectInvitation(id);
    }

    public ObservableFloat getProgress() {
        return mProgress;
    }

    @Override
    public void showProgressDialog() {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setTitle(getResources().getString(R.string.app_name));
        mProgressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        if (mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    @Override
    public void showErrorAlert(String msg) {
        new AlertDialog.Builder(getContext())
                .setTitle(getResources().getString(R.string.app_name))
                .setMessage(msg)
                .setPositiveButton(R.string.alert_accept, (dialogInterface, i) -> onOrganizationResponse(new ArrayList<>()))
                .create()
                .show();
    }

    @Override
    @AfterViews
    public void startPresenter() {
        mPresenter = new MyOrganizationPresenter().start(this);
        mPresenter.getOrganization(UserPreferences.getUser(getContext()).getId());
    }

    @Override
    public void onOrganizationResponse(ArrayList<PoolResponse> listActivities) {
        setUpAdapter(listActivities);
    }

    @Override
    public void showMessage(String msg, boolean updateData) {
        new AlertDialog.Builder(getContext())
                .setTitle(getResources().getString(R.string.app_name))
                .setPositiveButton(R.string.alert_accept, (dialogInterface, i) -> {
                    if (updateData)
                        startPresenter();
                })
                .setMessage(msg)
                .create()
                .show();
    }

    @Click({R.id.mAddOrganization, R.id.mAddButton})
    public void onAddOrganizationClick() {
        InviteOrganizationActivity_.intent(this).start();
    }
}
