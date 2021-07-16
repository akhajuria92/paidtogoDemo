package com.aaronevans.paidtogo.ui.main.winners.components;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.entities.ActiveCommute;
import com.aaronevans.paidtogo.databinding.ItemLeaderboardUserBinding;
import com.aaronevans.paidtogo.ui.GlideApp;
import java.util.ArrayList;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by leandro on 7/11/17.
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {
    ItemLeaderboardUserBinding mBinding;
    List<ActiveCommute> mItems = new ArrayList<>();
    String title = null;
    {
        this.setHasStableIds(true);
    }

    public UsersAdapter() {

    }

    public UsersAdapter(List<ActiveCommute> mItems, String title) {
        if (this.mItems != mItems) {
            this.mItems.clear();
            this.mItems = mItems;
            this.title = title;
            notifyDataSetChanged();
        }
    }

    public void setItems(List<ActiveCommute> mItems) {
        //  this.mItems = mItems;
        // notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(ItemLeaderboardUserBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.bind(mItems.get(position));

    //    mBinding.llPoints.setVisibility(View.VISIBLE);
        mBinding.mPositionText.setText(String.valueOf(position + 1));
   //     mBinding.mPotentialEar.setText("Potential Earnings");
        mBinding.mProfileNameText.setText(mItems.get(position).getFirstName() + " " + mItems.get(position).getLastName());
        mBinding.mPointsText.setText(("($" + mItems.get(position).getSum_prize()+".00" + ")"));

        setImageUrl1(mBinding.mProfilePicture, mItems.get(position).getProfilePicture());
        int temp = position + 1;

        if (temp % 10 == 1  && temp %100 !=11) {
            mBinding.mCardinalText.setText("st");
        } else if (temp % 10 == 2 && temp%100 !=12) {
            mBinding.mCardinalText.setText("nd");
        } else if (temp % 10 == 3 & temp%100 !=13) {
            mBinding.mCardinalText.setText("rd");
        } else {
            mBinding.mCardinalText.setText("th");
        }

    }

   /* @Override
    public long getItemId(int position) {
        return mItems.get(position).getPosition().get();
        // Design patterns, Dependency injections, mvvm, material design ,sqlite, junit test cases in android
    }*/

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        public ViewHolder(ItemLeaderboardUserBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.mProfilePicture.setClipToOutline(true);
        }

        public void bind(ActiveCommute viewModel) {
            mBinding.setViewModel(viewModel);
            mBinding.executePendingBindings();
        }
    }

    /*@BindingAdapter("imageUrl")*/
    public void setImageUrl1(ImageView view, String url) {
        GlideApp.with(view).clear(view);
        GlideApp.with(view)
                .load(url)
                .placeholder(R.drawable.ic_profile_placeholder)
                .centerCrop()
                .into(view);
    }

}