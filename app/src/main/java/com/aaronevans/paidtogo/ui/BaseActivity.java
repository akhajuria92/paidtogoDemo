package com.aaronevans.paidtogo.ui;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.aaronevans.paidtogo.data.local.UserPreferences;
import com.aaronevans.paidtogo.ui.main.activity_type.components.AutoTrackService;
import com.aaronevans.paidtogo.ui.main.activity_type.components.OnBackPressedListener;

import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by leandro on 22/11/17.
 */

public class BaseActivity extends AppCompatActivity {
    Intent mServiceIntent;
    private AutoTrackService mSensorService;
    protected OnBackPressedListener onBackPressedListener;





    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    public void mstartService() {
        mSensorService = new AutoTrackService(getApplicationContext());
        mServiceIntent = new Intent(getApplicationContext(), mSensorService.getClass());
        if (!isMyServiceRunning(mSensorService.getClass())) {
            long currentTime = Calendar.getInstance().getTimeInMillis();
            UserPreferences.saveStartTimeOnFeet(formatTime(currentTime,
                    "yyyy-MM-dd HH:mm:ss"), getApplicationContext());
            UserPreferences.saveStartLatOnFeet(String.valueOf(com.aaronevans.paidtogo.data.mHandler.user_lat), getApplicationContext());
            UserPreferences.saveStartLngOnFeet(String.valueOf(com.aaronevans.paidtogo.data.mHandler.user_lng), getApplicationContext());
            startService(mServiceIntent);
        }
    }

    public String formatTime(long time, String regex) {
        return DateFormat.format(regex, time).toString();
    }

    @Override
    public void onBackPressed() {
        if (onBackPressedListener != null)
            onBackPressedListener.doBack();
        else
            super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        mSensorService = new AutoTrackService(getApplicationContext());
        mServiceIntent = new Intent(getApplicationContext(), mSensorService.getClass());
        if (isMyServiceRunning(mSensorService.getClass())) {
            stopService(mServiceIntent);
        }
        Log.i("MAINACT", "onDestroy!");
        super.onDestroy();
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("isMyServiceRunning?", true + "");
                return true;
            }
        }
        Log.i("isMyServiceRunning?", false + "");
        return false;
    }

    public void mstopService() {
        mSensorService = new AutoTrackService(getApplicationContext());
        mServiceIntent = new Intent(getApplicationContext(), mSensorService.getClass());
        if (isMyServiceRunning(mSensorService.getClass())) {
            stopService(mServiceIntent);
        }
    }









    public void setStatusBarColor(int statusBarColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(statusBarColor);
        }
    }







}