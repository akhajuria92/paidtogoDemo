package com.aaronevans.paidtogo.ui.main.winners.components;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.entities.ActiveCommute;
import com.aaronevans.paidtogo.data.local.UserPreferences;
import com.aaronevans.paidtogo.data.remote.response.WinnersResponse;
import com.aaronevans.paidtogo.databinding.ItemWinnerExcerciseBinding;
import com.aaronevans.paidtogo.ui.GlideApp;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import androidx.core.util.Pools;
import androidx.viewpager.widget.PagerAdapter;

/**
 * Created by leandro on 7/11/17.
 */

public class ExerciseAdapter extends PagerAdapter {

    private final List<WinnersResponse> mViewModels = new ArrayList<>();
    private final Pools.SimplePool<ItemWinnerExcerciseBinding> mBindings = new Pools.SimplePool<>(3);
    List<ActiveCommute> userActiveCommute;
    int userPosition;


    public ExerciseAdapter(List<WinnersResponse> viewModels, List<ActiveCommute> userActiveCommute, int userPosition) {
        mViewModels.clear();
        mViewModels.addAll(viewModels);
        this.userActiveCommute = userActiveCommute;
        this.userPosition = userPosition;
        notifyDataSetChanged();
    }

    public void setItems(List<WinnersResponse> viewModels) {
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
        ItemWinnerExcerciseBinding binding = mBindings.acquire();
        if (binding == null) {
            binding = ItemWinnerExcerciseBinding.inflate(LayoutInflater.from(container.getContext()), container, false);

            // binding.textViewPosition.setVisibility(View.VISIBLE);
            if (position == 0) {
                binding.mTitle.setText("PAIDTOGO FREE POOL");
            } else {
                binding.mTitle.setText("PAIDTOGO PRO POOL");
            }

            if ((mViewModels.size() > 1)) {
                binding.setViewModel(mViewModels.get(position));
                binding.mProfilePicture.setClipToOutline(true);
                try {
                    double earnedItems = userActiveCommute.get(position).getEarned_points();
                    double coinsValue = Double.parseDouble(UserPreferences.getCoinsValue(container.getContext()));
                    int coins = (int) (earnedItems / coinsValue);

                    String usdValue = UserPreferences.getUSDPricePaid(container.getContext());
                    double totalEarnedUsd = coins * Double.parseDouble(usdValue);


                    binding.mPointsText.setText("$" + totalEarnedUsd + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (userActiveCommute.size() > 0) {
                    //binding.mPlaceText.setVisibility(View.VISIBLE);
                    binding.textViewPosition.setText(Integer.parseInt(userActiveCommute.get(position).getPosition()) + 1 + "");
                    int temp = Integer.parseInt(userActiveCommute.get(position).getPosition()) + 1;
                    if (temp == 1) {
                        binding.textViewRankth.setText("st");
                    } else if (temp == 2) {
                        binding.textViewRankth.setText("nd");
                    } else if (temp == 3) {
                        binding.textViewRankth.setText("rd");
                    } else {
                        binding.textViewRankth.setText("th");
                    }
                    GlideApp.with(binding.mProfilePicture).clear(binding.mProfilePicture);
                    GlideApp.with(binding.mProfilePicture)
                            .load(userActiveCommute.get(position).getProfilePicture())
                            .placeholder(R.drawable.ic_profile_placeholder)
                            .centerCrop()
                            .apply(RequestOptions.circleCropTransform())
                            .into(binding.mProfilePicture);
                } else {
                    binding.setViewModel(mViewModels.get(0));
                    binding.mPointsText.setText("$0.00");
                    binding.textViewPosition.setText("-");
                    binding.textViewRankth.setVisibility(View.GONE);
                }
            } else {
                if (position == 0) {
                    binding = ItemWinnerExcerciseBinding.inflate(LayoutInflater.from(container.getContext()), container, false);
                    binding.setViewModel(mViewModels.get(position));
                    binding.mProfilePicture.setClipToOutline(true);

                    try {


                        double earnedItems = userActiveCommute.get(position).getEarned_points();
                        double coinsValue = Double.parseDouble(UserPreferences.getCoinsValue(container.getContext()));
                        int coins = (int) (earnedItems / coinsValue);


                        String usdValue = UserPreferences.getUSDPriceFree(container.getContext());
                        double totalEarnedUsd = coins * Double.parseDouble(usdValue);

                        binding.mPointsText.setText("$" + totalEarnedUsd + "");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (userActiveCommute.size() > 0) {
                        binding.textViewPosition.setVisibility(View.VISIBLE);
                        binding.textViewPosition.setText(Integer.parseInt(userActiveCommute.get(position).getPosition()) + 1 + "");
                        int temp = Integer.parseInt(userActiveCommute.get(position).getPosition()) + 1;
                        if (temp == 1) {
                            binding.textViewRankth.setText("st");
                        } else if (temp == 2) {
                            binding.textViewPosition.setText("nd");
                        } else if (temp == 3) {
                            binding.textViewPosition.setText("rd");
                        } else {
                            binding.textViewPosition.setText("th");
                        }
                        GlideApp.with(binding.mProfilePicture).clear(binding.mProfilePicture);
                        GlideApp.with(binding.mProfilePicture)
                                .load(userActiveCommute.get(position).getProfilePicture())
                                .placeholder(R.drawable.ic_profile_placeholder)
                                .centerCrop()
                                .apply(RequestOptions.circleCropTransform())
                                .into(binding.mProfilePicture);
                    } else {
                        binding.setViewModel(mViewModels.get(0));
                        binding.mPointsText.setText("$0.00");
                        binding.textViewPosition.setText("-");
                        binding.textViewRankth.setVisibility(View.GONE);
                    }

                } else {
                    binding.setViewModel(mViewModels.get(0));
                    binding.mPointsText.setText("$0.00");
                    binding.textViewPosition.setText("-");
                    binding.textViewRankth.setVisibility(View.GONE);
                }
            }

            if (position == 0) {
                if (userActiveCommute.size() > 0) {
                    binding.mProfilePicture.setClipToOutline(true);
                    binding.textViewPosition.setText(userPosition + 1 + "");
                    int temp = userPosition + 1;
                    if (temp == 1) {
                        binding.mPlaceLabelText.setText("st");
                    } else if (temp == 2) {
                        binding.mPlaceLabelText.setText("nd");
                    } else if (temp == 3) {
                        binding.mPlaceLabelText.setText("rd");
                    } else {
                        binding.mPlaceLabelText.setText("th");
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
                binding.mPointsText.setText("$0.00");
                // binding.mFundsAvailable.setText("-");
                binding.textViewPosition.setText("-");
                binding.textViewRankth.setVisibility(View.GONE);
                binding.mTitle.setText("PAIDTOGO PRO POOL");
            }
        }
        container.addView(binding.getRoot());
        return binding;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ItemWinnerExcerciseBinding binding = (ItemWinnerExcerciseBinding) object;
        container.removeView(binding.getRoot());
        mBindings.release(binding);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ItemWinnerExcerciseBinding) object).getRoot();
    }

}