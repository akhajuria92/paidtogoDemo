package com.aaronevans.paidtogo.ui.main.coupons.components;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.aaronevans.paidtogo.databinding.ItemCouponRedeemableBinding;
import com.aaronevans.paidtogo.databinding.ItemCouponUnredeemableBinding;

/**
 * Created by leandro on 21/11/17.
 */

public class FirstPageAdapter extends RecyclerView.Adapter<FirstPageAdapter.ViewHolder> {
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == 0) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            ItemCouponRedeemableBinding binding = ItemCouponRedeemableBinding.inflate(inflater, parent, false);
            return new RedeemableViewHolder(binding);
        } else {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            ItemCouponUnredeemableBinding binding = ItemCouponUnredeemableBinding.inflate(inflater, parent, false);
            return new UnRedeemableViewHolder(binding);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }


    public class ViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
        T mBinding;

        public ViewHolder(T binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }

    public class RedeemableViewHolder extends ViewHolder<ItemCouponRedeemableBinding> {

        public RedeemableViewHolder(ItemCouponRedeemableBinding binding) {
            super(binding);
        }
    }

    public class UnRedeemableViewHolder extends ViewHolder<ItemCouponUnredeemableBinding> {

        public UnRedeemableViewHolder(ItemCouponUnredeemableBinding binding) {
            super(binding);
        }
    }
}
