package com.aaronevans.paidtogo.ui.profile.organizations.components;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.remote.response.PoolResponse;
import com.aaronevans.paidtogo.databinding.ItemProfileOrganizationBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leandro on 24/11/17.
 */

public class OrganizationsAdapter extends RecyclerView.Adapter<OrganizationsAdapter.ViewHolder> {
    private final List<PoolResponse> organizations;
    private Context context;
    private OnRemoveOrganizationListener mListener;

    public OrganizationsAdapter(ArrayList<PoolResponse> organizations) {
        this.organizations = organizations;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemProfileOrganizationBinding binding = ItemProfileOrganizationBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PoolResponse item = organizations.get(position);
        Glide.with(context).load(item.getBanner()).apply(new RequestOptions().error(R.mipmap.ic_launcher_round)).into(holder.mBinding.mOrganizationLogo);
       if(item.getType().equalsIgnoreCase("national_pool")){
            holder.mBinding.mRemoveButton.setVisibility(View.GONE);
       }
        // holder.mBinding.mOrganizationLocation.setText(item.getCountry());
        holder.mBinding.mOrganizationName.setText(item.getName());
    }

    public void setClickListener(OnRemoveOrganizationListener listener) {
        this.mListener = listener;
    }

    public OnRemoveOrganizationListener getListener() {
        return mListener;
    }

    @Override
    public int getItemCount() {
        return organizations.size();
    }

    public interface OnRemoveOrganizationListener {
        void onRemoveOrganization(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemProfileOrganizationBinding mBinding;

        public ViewHolder(ItemProfileOrganizationBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
            mBinding.setAdapter(OrganizationsAdapter.this);
            mBinding.setSelf(this);
        }
    }
}
