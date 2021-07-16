package com.aaronevans.paidtogo.ui.main.balance.components;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aaronevans.paidtogo.data.remote.response.BalanceResponse;
import com.aaronevans.paidtogo.databinding.ItemBalanceExerciseBinding;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by leandro on 9/11/17.
 */

public class UserBalanceAdapter extends RecyclerView.Adapter<UserBalanceAdapter.ViewHolder> {
    ItemBalanceExerciseBinding aBinding;
    private List<BalanceResponse> mViewModels = new ArrayList<>();
    private OnAddOrganizationListener mListener;

    {
        setHasStableIds(true);
    }


    public UserBalanceAdapter(List<BalanceResponse> mViewModels) {
        if(this.mViewModels!=mViewModels) {
            this.mViewModels.clear();
            this.mViewModels = mViewModels;
            notifyDataSetChanged();
        }


    }

/*    public void setItems(List<BalanceResponse> items) {
        if (mViewModels != items) {
            mViewModels = items;
        }
        notifyDataSetChanged();
    }*/


    public void setListener(OnAddOrganizationListener listener) {
        this.mListener = listener;
    }

    public void executeCallback() {
        if (mListener != null) mListener.onAddOrganizationClick();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        /*if (viewType == 0) {
            return new ViewHolder(ItemBalanceExerciseBinding.inflate(inflater, parent, false));
        } else {
              return new FooterViewHolder(ItemBalanceFooterBinding.inflate(inflater,parent,false));
        }*/
        return new ViewHolder(ItemBalanceExerciseBinding.inflate(inflater, parent, false));
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position == mViewModels.size()) {

        } else {

            aBinding.mHeader.setText(mViewModels.get(position).getName());
        if(mViewModels.get(position).getRewardType()!=null) {
            if (mViewModels.get(position).getRewardType() == 1) {
                if(mViewModels.get(position).getBalance().getEarnedMoney()==null){
                    aBinding.mValueText.setText("0.0" + " " + "USD");
                }else {
                    aBinding.mValueText.setText(String.valueOf(mViewModels.get(position).getBalance().getEarnedMoney()) + " " + "USD");
                }
            } else {
                    aBinding.mValueText.setText(String.valueOf(mViewModels.get(position).getBalance().getEarnedPoints()) + " " + "Points");
            }
        }
        if(mViewModels.get(position).getSponsors().size()>0){
            int size=mViewModels.get(position).getSponsors().size();
            for(int p=0 ; p<size;p++){

                if(p==0){
                    aBinding.mLatestActivitiesLabel.setText(mViewModels.get(position).getSponsors().get(p).getTitle());
                    aBinding.mLatestActivitiesLabel.setVisibility(View.VISIBLE);
                    aBinding.horizontalSeparator1.setVisibility(View.VISIBLE);
                }
                if(p==1){
                    aBinding.mPreviewString1.setText(mViewModels.get(position).getSponsors().get(p).getTitle());
                    aBinding.mPreviewString1.setVisibility(View.VISIBLE);
                    aBinding.horizontalSeparator2.setVisibility(View.VISIBLE);
                }
                if(p==2){
                    aBinding.mPreviewString2.setText(mViewModels.get(position).getSponsors().get(p).getTitle());
                    aBinding.mPreviewString2.setVisibility(View.VISIBLE);
                }
            }
        }
            holder.bind(mViewModels.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {

        return mViewModels.size();


    }
   /* @Override
    public long getItemId(int position) {
        return position == mViewModels.size() ? 199218 : mViewModels.get(position).hashCode();
    }*/

/*    public abstract class ViewHolder<T extends ViewDataBinding, VM> extends RecyclerView.ViewHolder {

        protected T mBinding;


        public ViewHolder(T itemView) {
            super(itemView.getRoot());
            aBinding=itemView;
        }

        abstract public void bind(VM viewModel);
    }*/
/*
    public class ExerciseViewHolder extends ViewHolder<ItemBalanceExerciseBinding,BalanceResponse> {

        public ExerciseViewHolder(ItemBalanceExerciseBinding itemView) {
            super(itemView);
            mBinding = itemView;
        }

        public void bind(BalanceResponse viewModel) {
            mBinding.setViewModel(viewModel);
            mBinding.executePendingBindings();
        }
    }*/

    public class ViewHolder extends RecyclerView.ViewHolder {



        public ViewHolder(ItemBalanceExerciseBinding itemView) {
            super(itemView.getRoot());
            aBinding = itemView;
        }

        public void bind(BalanceResponse viewModel) {
            aBinding.setViewModel(viewModel);
            aBinding.executePendingBindings();
        }
    }
/*
    public class FooterViewHolder extends  ViewHolder<ItemBalanceFooterBinding,Object> {

        public FooterViewHolder(ItemBalanceFooterBinding itemView) {
            super(itemView);
            mBinding = itemView;
            itemView.setAdapter(UserBalanceAdapter.this);
        }

        @Override
        public void bind(Object viewModel) {

        }
    }*/
}
