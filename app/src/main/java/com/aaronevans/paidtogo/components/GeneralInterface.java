package com.aaronevans.paidtogo.components;

import com.aaronevans.paidtogo.ui.main.settings.components.SettingsAdapter;

/**
 * Created by Farhan Arshad on 6/6/2018.
 */

public class GeneralInterface {
    public interface OnSyncListener {
        void onSync(int poolPosition, int activityType);
    }

    public interface OnActivityPost {
        void onActivityPost(int totalSteps, float totalMiles);
    }

    public interface OnSettingsClickListener {
        void onSettingsClick(SettingsAdapter.SETTINGS settingsType, boolean isChecked);
    }
}
