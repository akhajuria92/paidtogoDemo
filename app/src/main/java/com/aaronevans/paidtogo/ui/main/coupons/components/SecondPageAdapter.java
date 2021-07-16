package com.aaronevans.paidtogo.ui.main.coupons.components;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.aaronevans.paidtogo.databinding.ItemCouponRedeemedBinding;

/**
 * Created by leandro on 21/11/17.
 */

public class SecondPageAdapter extends RecyclerView.Adapter<SecondPageAdapter.ViewHolder> {
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemCouponRedeemedBinding binding = ItemCouponRedeemedBinding.inflate(inflater, parent, false);
        return new RedeemedViewHolder(binding);
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

    public class RedeemedViewHolder extends ViewHolder<ItemCouponRedeemedBinding> {

        public RedeemedViewHolder(ItemCouponRedeemedBinding binding) {
            super(binding);
        }
    }
}
