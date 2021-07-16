package com.aaronevans.paidtogo.background_service;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.aaronevans.paidtogo.BuildConfig;
import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.local.SettingsPreferences;
import com.aaronevans.paidtogo.data.local.UserPreferences;
import com.aaronevans.paidtogo.ui.main.MainActivity;

import java.text.DecimalFormat;

import static androidx.core.app.NotificationCompat.PRIORITY_MIN;

public class SensorService extends Service implements SensorEventListener, LocationListener {
    private SensorManager sm;
    private Sensor sensor;
    private LocationManager mLocationManager;


    public static final float PARAMETER_CONVERT_MILES = 0.00062f;
    public static final float PARAMETER_CONVERT_KM = 1000;


    private float totalDistance = 0;    // in meters
    private Location lastLocation;      // last known user location
    private Double speed; //
    private Location firstLocation;


    private float lastSteps = 0;
    private float currentSteps = 0;


    String miles_temp = "", gas_temp = "", co2_temp = "", cal_temp = "", steps_temp = "";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        currentSteps = event.values[0];
        if (lastSteps == 0) {
            lastSteps = currentSteps;
            // mHandler.m_steps=lastSteps;
            //   UserPreferences.saveTotalSteps(currentSteps,context);
        }
    }
    @Override
    public void onDestroy() {//here u should unregister sensor
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();
        if(sm!=null){
            sm.unregisterListener(this);
        }
    }

    @Override//here u should register sensor and write onStartCommand not onStart
    public int onStartCommand(Intent intent, int flags, int startId) {
        String title=intent.getStringExtra("title");
        String message=intent.getStringExtra("message");
        String channelId = getString(R.string.app_name);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(channelId, "My Background Service");
        } else {
            channelId="";
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
        new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(contentIntent);

        Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(PRIORITY_MIN)
                .setContentTitle(title)
                .setContentText(message)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(101, notification);

        sm=(SensorManager)getSystemService(SENSOR_SERVICE);
        sensor=sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        sm.unregisterListener(this);
        sm.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);








        return Service.START_STICKY;
    }



    @RequiresApi(Build.VERSION_CODES.O)
    private String  createNotificationChannel(String channelId , String channelName){
        NotificationChannel chan = new NotificationChannel(channelId,
                channelName, NotificationManager.IMPORTANCE_NONE);
        NotificationManager service = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        service.createNotificationChannel(chan);
        return channelId;
    }





    private void registerUpdateManager() {

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        criteria.setAltitudeRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
        criteria.setBearingRequired(false);

//API level 9 and up
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);



        int gpsProvider = mLocationManager.getAllProviders().indexOf(LocationManager.GPS_PROVIDER);
        if (gpsProvider >= 0) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }

            mLocationManager.requestLocationUpdates(1000, 1, criteria, this, null);


          /*  mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, , this);
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, this);*/
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        DecimalFormat doubleFormat = new DecimalFormat("0.00");
        if (BuildConfig.DEBUG)
            Log.i("location_error", String.valueOf(location.getLatitude()));
        if (firstLocation == null) {
            firstLocation = location;
        }
        if (lastLocation == null) {
            lastLocation = location;
        }
        speed = 0.0;
        double distance = lastLocation.distanceTo(location);
        if (location.hasSpeed()) {
            speed = location.getSpeed() * 2.2369;
        } else {
            if (distance != 0.0 && distance != 0) {
                speed = distance / ((location.getTime() - lastLocation.getTime()) / 1000);
                speed = speed * 2.236936f;


            } else {
                speed = 0.0;
            }
        }

        if (location.getAccuracy() <= 25 && location.getAccuracy() >= 10 && speed != 0) {
            totalDistance += distance;
            lastLocation = location;
        }


        if (SettingsPreferences.isMilesKm(getApplicationContext())) {
            miles_temp = String.format("%.2f", distance / PARAMETER_CONVERT_KM);
        } else {
            miles_temp = String.format("%.2f", distance * PARAMETER_CONVERT_MILES);
        }

        UserPreferences.saveTotalDistance(miles_temp,getApplicationContext());
        UserPreferences.saveTotalSteps((getCurrentSteps()),getApplicationContext());
        UserPreferences.saveCalories((String.valueOf(((float) distance * PARAMETER_CONVERT_MILES) * 100)),getApplicationContext());

        gas_temp = String.format("%.2f", (distance * PARAMETER_CONVERT_MILES) * 0.04);
        co2_temp = String.format("%.2f", (distance * PARAMETER_CONVERT_MILES) * 0.888);

        UserPreferences.saveGas(gas_temp,getApplicationContext());
        UserPreferences.saveCO2(co2_temp,getApplicationContext());



    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public float getCurrentSteps() {
        return currentSteps - lastSteps;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent){
        Intent restartServiceIntent = new Intent(getApplicationContext(), this.getClass());
        restartServiceIntent.setPackage(getPackageName());

        PendingIntent restartServicePendingIntent = PendingIntent.getService(getApplicationContext(), 1, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(
                AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 1000,
                restartServicePendingIntent);

        super.onTaskRemoved(rootIntent);
    }

}
