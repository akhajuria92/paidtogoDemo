package com.aaronevans.paidtogo.ui.main.leaderboard.components;

import androidx.core.util.Pools;
import androidx.viewpager.widget.PagerAdapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aaronevans.paidtogo.data.local.UserPreferences;
import com.bumptech.glide.request.RequestOptions;
import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.entities.ActiveCommute;
import com.aaronevans.paidtogo.data.remote.response.LeaderboardResponse;
import com.aaronevans.paidtogo.databinding.ItemLeaderboardExerciseBinding;
import com.aaronevans.paidtogo.ui.GlideApp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leandro on 7/11/17.
 */

public class ExerciseAdapter extends PagerAdapter {

    private final List<LeaderboardResponse> mViewModels = new ArrayList<>();
    private final Pools.SimplePool<ItemLeaderboardExerciseBinding> mBindings = new Pools.SimplePool<>(3);
    ///ActiveCommute userActiveCommute;


    List<ActiveCommute> userActiveCommute;


    public ExerciseAdapter(ArrayList<LeaderboardResponse> viewModels, List<ActiveCommute> userActiveCommute, int userPosition) {
        mViewModels.clear();
        mViewModels.addAll(viewModels);
        this.userActiveCommute = userActiveCommute;
        notifyDataSetChanged();
    }

    public void setItems(List<LeaderboardResponse> viewModels) {
        mViewModels.clear();
        mViewModels.addAll(viewModels);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ItemLeaderboardExerciseBinding binding = mBindings.acquire();

        if (binding == null) {
            binding = ItemLeaderboardExerciseBinding.inflate(LayoutInflater.from(container.getContext()), container, false);
            if(position==0){
                binding.mTitle.setText("PAIDTOGO FREE POOL");
            }else{
                binding.mTitle.setText("PAIDTOGO PRO POOL");
            }
            if(mViewModels.size()>1){

                    binding.setViewModel(mViewModels.get(position));
                    binding.mProfilePicture.setClipToOutline(true);
                    try {
                        double earnedItems = userActiveCommute.get(position).getEarned_points();
                        double coinsValue = Double.parseDouble(UserPreferences.getCoinsValue(container.getContext()));
                        int coins = (int) (earnedItems / coinsValue);

                        binding.mPointsText.setText(coins + "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                   binding.mFundsAvailable.setText("Funds Available: $" + mViewModels.get(position).getFunds_available());
                    if (userActiveCommute .size()>1) {
                        binding.mPlaceText.setVisibility(View.VISIBLE);
                        binding.mPlaceText.setText(Integer.parseInt(userActiveCommute.get(position).getPosition()) + 1 + "");
                        int temp = Integer.parseInt(userActiveCommute.get(position).getPosition()) + 1;
                        if (temp == 1) {
                            binding.textViewPositon.setText("st");
                        } else if (temp == 2 ) {
                            binding.textViewPositon.setText("nd");
                        } else if (temp == 3) {
                            binding.textViewPositon.setText("rd");
                        } else {
                            binding.textViewPositon.setText("th");
                        }
                        GlideApp.with(binding.mProfilePicture).clear(binding.mProfilePicture);
                        GlideApp.with(binding.mProfilePicture)
                                .load(userActiveCommute.get(position).getProfilePicture())
                                .placeholder(R.drawable.ic_profile_placeholder)
                                .centerCrop()
                                .apply(RequestOptions.circleCropTransform())
                                .into(binding.mProfilePicture);
                    }else{
                        binding.mPlaceText.setVisibility(View.VISIBLE);
                        binding.mPlaceText.setText(Integer.parseInt(userActiveCommute.get(0).getPosition()) + 1 + "");

                        int temp = Integer.parseInt(userActiveCommute.get(0).getPosition()) + 1;
                        if (temp == 1) {
                            binding.textViewPositon.setText("st");
                        } else if (temp == 2 ) {
                            binding.textViewPositon.setText("nd");
                        } else if (temp == 3) {
                            binding.textViewPositon.setText("rd");
                        } else {
                            binding.textViewPositon.setText("th");
                        }
                        GlideApp.with(binding.mProfilePicture).clear(binding.mProfilePicture);
                        GlideApp.with(binding.mProfilePicture)
                                .load(userActiveCommute.get(0).getProfilePicture())
                                .placeholder(R.drawable.ic_profile_placeholder)
                                .centerCrop()
                                .apply(RequestOptions.circleCropTransform())
                                .into(binding.mProfilePicture);
                    }
            }else{
                if (position == 0) {
                    Log.e("position is", position + "");

                    binding = ItemLeaderboardExerciseBinding.inflate(LayoutInflater.from(container.getContext()), container, false);
                    binding.setViewModel(mViewModels.get(position));
                    binding.mProfilePicture.setClipToOutline(true);
                    binding.mTitle.setText("PAIDTOGO FREE POOL");

                    try {


                        double earnedItems = userActiveCommute.get(position).getEarned_points();
                        double coinsValue = Double.parseDouble(UserPreferences.getCoinsValue(container.getContext()));
                        int coins = (int) (earnedItems / coinsValue);

                        binding.mPointsText.setText(coins + "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (userActiveCommute != null&&userActiveCommute.size()>0) {
                        binding.mFundsAvailable.setText("Funds Available: $" + mViewModels.get(position).getFunds_available());
                        binding.mPlaceText.setVisibility(View.VISIBLE);
                        binding.mPlaceText.setText(Integer.parseInt(userActiveCommute.get(position).getPosition()) + 1 + "");
                        int temp = Integer.parseInt(userActiveCommute.get(position).getPosition()) + 1;
                        if (temp == 1) {
                            binding.textViewPositon.setText("st");
                        } else if (temp == 2 ) {
                            binding.textViewPositon.setText("nd");
                        } else if (temp == 3) {
                            binding.textViewPositon.setText("rd");
                        } else {
                            binding.textViewPositon.setText("th");
                        }
                        GlideApp.with(binding.mProfilePicture).clear(binding.mProfilePicture);
                        GlideApp.with(binding.mProfilePicture)
                                .load(userActiveCommute.get(position).getProfilePicture())
                                .placeholder(R.drawable.ic_profile_placeholder)
                                .centerCrop()
                                .apply(RequestOptions.circleCropTransform())
                                .into(binding.mProfilePicture);
                    }

                } else {
                    binding.setViewModel(mViewModels.get(0));
                    binding.mPointsText.setText("-");
                    binding.mFundsAvailable.setText("-");
                    binding.mPlaceText.setText("-");
                    binding.textViewPositon.setVisibility(View.GONE);
                    binding.mTitle.setText("PAIDTOGO PRO POOL");
                }
            }

        }
        container.addView(binding.getRoot());
        return binding;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ItemLeaderboardExerciseBinding binding = (ItemLeaderboardExerciseBinding) object;
        container.removeView(binding.getRoot());
        mBindings.release(binding);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ItemLeaderboardExerciseBinding) object).getRoot();
    }

}