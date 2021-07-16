package com.aaronevans.paidtogo.ui.inviteorg.organizations.components;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.remote.response.EligiblePool;
import com.aaronevans.paidtogo.databinding.ItemInviteOrganizationBinding;

import java.util.ArrayList;

/**
 * Created by leandro on 24/11/17.
 */

public class OrganizationsAdapter extends RecyclerView.Adapter<OrganizationsAdapter.ViewHolder> {

    private final ArrayList<EligiblePool> invitations;
    private OnAddOrganizationListener mListener;

    private Context context;

    public OrganizationsAdapter(ArrayList<EligiblePool> invitations) {
        this.invitations = invitations;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemInviteOrganizationBinding binding = ItemInviteOrganizationBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
      //  Pool pool = invitations.get(position).getPool();

        EligiblePool cureent_item = invitations.get(position);

        if (cureent_item==null)
        {

        }
        else
        {
            if (cureent_item.getName()!=null) {
                holder.mBinding.mOrganizationName.setText(cureent_item.getName());
                Glide.with(context)
                        .load(invitations.get(position).getBanner())
                        .apply(new RequestOptions().centerCrop().error(R.mipmap.ic_launcher_round))
                        .into(holder.mBinding.mOrganizationLogo);
                holder.bind(position);
            }
        }

     //   holder.mBinding.mOrganizationLocation.setText(invitations.get(position).getCountry());

    }

    @Override
    public int getItemCount() {
        return invitations.size();
    }

    public void setClickListener(OnAddOrganizationListener listener) {
        this.mListener = listener;
    }

    public OnAddOrganizationListener getListener() {
        return mListener;
    }

    public interface OnAddOrganizationListener {
        void onAddOrganization(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemInviteOrganizationBinding mBinding;

        public ViewHolder(ItemInviteOrganizationBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
            mBinding.setAdapter(OrganizationsAdapter.this);
            mBinding.setSelf(this);
        }

        //TODO: We should pass here a VM.
        protected void bind(int position) {
        }
    }
}
