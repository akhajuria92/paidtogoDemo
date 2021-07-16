package com.aaronevans.paidtogo.ui.main.coupons;

import android.os.Bundle;
import androidx.annotation.Nullable;

import android.view.View;

import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.databinding.FragmentMainCouponsBinding;
import com.aaronevans.paidtogo.ui.BaseFragment;
import com.aaronevans.paidtogo.ui.main.MainActivity;
import com.aaronevans.paidtogo.ui.main.coupons.components.TwoPagesAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.BindingObject;
import org.androidannotations.annotations.DataBound;
import org.androidannotations.annotations.EFragment;

/**
 * Created by leandro on 21/11/17.
 */
@DataBound
@EFragment(R.layout.fragment_main_coupons)
public class CouponsFragment extends BaseFragment {

    CouponsContract.ViewModel mViewModel = new CouponsViewModel();

    @BindingObject FragmentMainCouponsBinding mBinding;

    @AfterViews public void start() {
        mBinding.mPager.setAdapter(new TwoPagesAdapter());
        mBinding.setViewModel(mViewModel);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).getTabLayout().setVisibility(View.VISIBLE);
            ((MainActivity) getActivity()).getTabLayout().setupWithViewPager(mBinding.mPager);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).getTabLayout().setVisibility(View.GONE);
        }

    }
}
