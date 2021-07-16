package com.aaronevans.paidtogo.ui.main.balance;

import android.app.ProgressDialog;
import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.local.UserPreferences;
import com.aaronevans.paidtogo.data.remote.response.BalanceResponse;
import com.aaronevans.paidtogo.databinding.FragmentMainBalanceBinding;
import com.aaronevans.paidtogo.ui.main.balance.components.UserBalanceAdapter;
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
 * Created by leandro on 9/11/17.
 */
@DataBound
@EFragment(R.layout.fragment_main_balance)
public class UserBalanceFragment extends Fragment implements UserBalanceContract.View{
    private ProgressDialog mProgressDialog = null;
    UserBalanceContract.ViewModel mViewModel = new UserBalanceViewModel();
    UserBalanceContract.Presenter mPresenter;
    @BindingObject FragmentMainBalanceBinding mBinding;

    @AfterViews public void setup() {
       // UserBalanceAdapter adapter = new UserBalanceAdapter();
       // adapter.setListener(() -> InviteOrganizationActivity_.intent(this).start());
      //  mBinding.mRecyclerView.setAdapter(adapter);

        mBinding.mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false));
        mBinding.setViewModel(mViewModel);
        mBinding.setSelf(this);
    }

    @BindingAdapter("items")
    public static void setItems(RecyclerView view, List<BalanceResponse> viewModels) {
        if (view.getAdapter() instanceof UserBalanceAdapter) {
           // ((UserBalanceAdapter) view.getAdapter()).setItems(viewModels);
        }
    }

    @UiThread(delay = 500)
    @AfterViews
    @Override
    public void startPresenter() {
        mPresenter = new UserBalancePresenter().start(this);
        mPresenter.loadBalanceData(UserPreferences.getUser(getContext()).getId());
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
    public void onLoadBalanceData(ArrayList<BalanceResponse> balanceResponse) {
        UserBalanceAdapter adapter= new UserBalanceAdapter(balanceResponse);
        mBinding.mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        toolbar_image.setVisibility(View.GONE);
        toolbar_title.setVisibility(View.VISIBLE);
        toolbar_title.setText("Balance");
    }
}
