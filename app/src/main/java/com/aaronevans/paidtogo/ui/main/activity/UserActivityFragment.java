package com.aaronevans.paidtogo.ui.main.activity;

import android.app.ProgressDialog;
import androidx.databinding.BindingAdapter;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.local.UserPreferences;
import com.aaronevans.paidtogo.data.remote.response.ActivitiesResponse;
import com.aaronevans.paidtogo.databinding.FragmentMainUserActivityBinding;
import com.aaronevans.paidtogo.ui.BaseFragment;
import com.aaronevans.paidtogo.ui.main.activity.components.UserActivityAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.BindingObject;
import org.androidannotations.annotations.DataBound;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import java.util.ArrayList;
import java.util.List;
import static com.aaronevans.paidtogo.ui.main.MainActivity.toolbar_image;
import static com.aaronevans.paidtogo.ui.main.MainActivity.toolbar_title;

/**
 * Created by leandro on 8/11/17.
 */
@DataBound
@EFragment(R.layout.fragment_main_user_activity)
public class UserActivityFragment extends BaseFragment implements UserActivityContract.View{
    private ProgressDialog mProgressDialog = null;
    UserActivityContract.Presenter mPresenter;
    @BindingObject FragmentMainUserActivityBinding mBinding;

    UserActivityContract.ViewModel mViewModel = new UserActivityViewModel();

    @AfterViews public void setup() {
    /*    ((BaseActivity)getActivity()).setOnBackPressedListener(new BaseBackPressedListener(getActivity()){ public void doBack() {
            getActivity().finish();
        }});*/
        mBinding.mRecyclerView.setAdapter(new UserActivityAdapter());
        mBinding.mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        mBinding.setViewModel(mViewModel);
    }

    @BindingAdapter("items")
    public static void setItems(RecyclerView view, List<ActivitiesResponse> items) {
        if (view.getAdapter() instanceof UserActivityAdapter) {
            ((UserActivityAdapter) view.getAdapter()).setItems(items);
        }
    }

    @Override
    public void onLoadActivityData(ArrayList<ActivitiesResponse> useractivityResponse) {
        UserActivityAdapter userActivityAdapter= new UserActivityAdapter(getActivity(),useractivityResponse);

        mBinding.mRecyclerView.setAdapter(userActivityAdapter);
    }

    @UiThread(delay = 500)
    @AfterViews
    @Override
    public void startPresenter() {
        mPresenter = new UserActivityPresenter().start(this);
        mPresenter.loadActivityData(UserPreferences.getUser(getContext()).getId());
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
                .setPositiveButton(R.string.alert_accept, null)
                .create()
                .show();
    }

    @Override
    public void onResume() {
        super.onResume();

        toolbar_image.setVisibility(View.GONE);
        toolbar_title.setVisibility(View.VISIBLE);
        toolbar_title.setText("Activity Feed");
    }
}
