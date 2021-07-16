package com.aaronevans.paidtogo.components;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;

/**
 * Created by Farhan Arshad on 6/28/2018.
 */

public class GPSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        LocationManager mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            int gpsProvider = mLocationManager.getAllProviders().indexOf(LocationManager.GPS_PROVIDER);
            if (gpsProvider > 0 && !ActivityService.isServiceRunning) {
                try{
                    context.startService(new Intent(context, ActivityService.class));
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        } else {
            context.stopService(new Intent(context, ActivityService.class));
        }
    }
}
