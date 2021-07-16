package com.aaronevans.paidtogo.data.local;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Farhan Arshad on 6/28/2018.
 */

public class SettingsPreferences {
    private final static String PREFERENCES = "PaidToGo_settings";
    private final static String AUTO_TRACKING = "auto_tracking";
    private final static String MILES_KM="miles_km";

    public static void setAutoTracking(Context context, boolean isOn) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        preferences.edit().putBoolean(AUTO_TRACKING, isOn).apply();
    }

    public static boolean isAutoTrackingON(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return preferences.getBoolean(AUTO_TRACKING, false);
    }

    public static void setMilesKm(Context context, boolean isOn) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        preferences.edit().putBoolean(MILES_KM, isOn).apply();
    }

    public static boolean isMilesKm(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return preferences.getBoolean(MILES_KM, false);
    }



}
