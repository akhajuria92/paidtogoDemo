package com.aaronevans.paidtogo.ui.main.settings.components;

import android.Manifest;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import com.aaronevans.paidtogo.components.GeneralInterface;
import com.aaronevans.paidtogo.data.local.SettingsPreferences;
import com.aaronevans.paidtogo.databinding.ItemSettingsHeaderBinding;
import com.aaronevans.paidtogo.databinding.ItemSettingsSwitchBinding;
import com.aaronevans.paidtogo.ui.BaseActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by leandro on 22/11/17.
 */

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder> {

    final static Item[] items = {
            new Header("Location"),
         //   new Setting(SETTINGS.NONE, "Geolocation"),
            new Setting(SETTINGS.AUTO_TRACKER, "Auto Tracking (Only for Walk/Run)"),
       //     new Header("App Notifications"),
            new Setting(SETTINGS.MILES_KM, "Miles/KM Switch"),

    };
    private final GeneralInterface.OnSettingsClickListener onItemClickListener;
    private final BaseActivity baseActivity;

    public SettingsAdapter(BaseActivity baseActivity, GeneralInterface.OnSettingsClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.baseActivity = baseActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            LayoutInflater inflater = LayoutInflater.from(baseActivity);
            ItemSettingsSwitchBinding binding = ItemSettingsSwitchBinding.inflate(inflater, parent, false);
            return new SettingViewHolder(binding);
        } else {
            LayoutInflater inflater = LayoutInflater.from(baseActivity);
            ItemSettingsHeaderBinding binding = ItemSettingsHeaderBinding.inflate(inflater, parent, false);
            return new HeaderViewHolder(binding);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return items[position] instanceof Setting ? 0 : 1;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setText(items[position].title);
        holder.setSettings(items[position].settingsType);
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    private void updateSettings(Switch mSettings, SETTINGS settingsType) {
        switch (settingsType) {
            case AUTO_TRACKER:
                mSettings.setChecked(SettingsPreferences.isAutoTrackingON(baseActivity));
                break;

            case MILES_KM:
                mSettings.setChecked(SettingsPreferences.isMilesKm(baseActivity));
                break;
        }
    }

    public enum SETTINGS {AUTO_TRACKER, MILES_KM, NONE}

    private static class Item {
        String title;
        SETTINGS settingsType = SETTINGS.NONE;

        public Item(String title) {
            this.title = title;
        }

        public Item(SETTINGS settingsType, String title) {
            this.title = title;
            this.settingsType = settingsType;
        }
    }

    private static class Setting extends Item {

        public Setting(SETTINGS settings, String title) {
            super(settings, title);
        }
    }

    private static class Header extends Item {

        public Header(String title) {
            super(title);
        }
    }

    public abstract class ViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {

        T mBinding;

        public ViewHolder(T itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }

        public abstract void setText(String text);

        public abstract void setSettings(SETTINGS settingsType);
    }

    public class SettingViewHolder extends ViewHolder<ItemSettingsSwitchBinding> {
        CompoundButton.OnCheckedChangeListener checkChange;

        public SettingViewHolder(ItemSettingsSwitchBinding itemView) {
            super(itemView);
        }

        @Override
        public void setText(String text) {
            mBinding.setText(text);
        }

        @Override
        public void setSettings(SETTINGS settingsType) {
            if (settingsType != SETTINGS.NONE) {
                ItemSettingsSwitchBinding item = mBinding;
                updateSettings(item.mSettings, settingsType);
                checkChange = (compoundButton, isChecked) -> new RxPermissions(baseActivity)
                        .request(Manifest.permission.ACCESS_FINE_LOCATION)
                        .subscribeOn(Schedulers.trampoline())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(granted -> {
                                    if (granted) {
                                        if (onItemClickListener != null)
                                            onItemClickListener.onSettingsClick(items[getAdapterPosition()].settingsType,
                                                    isChecked);
                                    } else {
                                        mBinding.mSettings.setOnCheckedChangeListener(null);
                                        mBinding.mSettings.setChecked(false);
                                        mBinding.mSettings.setOnCheckedChangeListener(checkChange);
                                    }
                                }
                        );
                mBinding.mSettings.setOnCheckedChangeListener(checkChange);
            }
        }
    }

    public class HeaderViewHolder extends ViewHolder<ItemSettingsHeaderBinding>
    {

        public HeaderViewHolder(ItemSettingsHeaderBinding itemView) {
            super(itemView);
        }

        @Override
        public void setText(String text) {
            mBinding.setText(text);
        }

        @Override
        public void setSettings(SETTINGS text) {

        }

    }

}