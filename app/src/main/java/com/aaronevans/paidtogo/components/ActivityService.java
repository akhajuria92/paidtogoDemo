package com.aaronevans.paidtogo.components;

import android.Manifest;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;

import androidx.core.app.ActivityCompat;

import android.util.Log;
import android.widget.Toast;

import com.aaronevans.paidtogo.BuildConfig;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Farhan Arshad on 6/28/2018.
 */

public class ActivityService extends Service implements LocationListener, SensorEventListener {

    public static boolean isServiceRunning = false;
    private final IBinder binder = new LocalBinder();
    private Application context;
    private LocationManager mLocationManager;

    private float totalDistance = 0;    // in meters
    private Location lastLocation;      // last known user location
    private Double speed; // in meters per sec
    private SensorManager sensorManager;
    private boolean isAvailable = false;
    private float lastSteps = 0;
    private float currentSteps = 0;
    private Location firstLocation;
    private Location currentlocation;


    List<Double> allSpeed = new ArrayList<>();


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplication();
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        totalDistance = 0;
        allSpeed.clear();

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

        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            isAvailable = false;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            isServiceRunning = false;
            registerUpdateManager();
        } else {
            stopSelf();
        }
        return Service.START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onLocationChanged(Location location) {
        DecimalFormat doubleFormat = new DecimalFormat("0.00");
        currentlocation = location;
        if (BuildConfig.DEBUG)
            Log.i("location_error", String.valueOf(location.getLatitude()));
        if (firstLocation == null) {
            firstLocation = location;
        }
        if (lastLocation == null) {
            lastLocation = location;
        }
        speed = 0.0;
       /* speed = Math.sqrt(
                Math.pow(location.getLongitude() - lastLocation.getLongitude(), 2)
                        + Math.pow(location.getLatitude() - lastLocation.getLatitude(), 2)
        ) / (location.getTime() - this.lastLocation.getTime());*/
        double distance = lastLocation.distanceTo(location);
        /*if (location.getAccuracy() <= 25 && distance != 0.0 && distance != 0) {
            speed = distance / ((location.getTime() - lastLocation.getTime()) / 1000);
            speed = speed * 2.236936f;
        }*/
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
      /*  if (location.getAccuracy() < distance &&
                location.getAccuracy() <= 25 && location.getAccuracy() >= 10 && speed != 0) {*/

        //if (location.getAccuracy() <= 25 && location.getAccuracy() >= 10 && speed != 0) {
            //  mHandler.m_distance=totalDistance;

            totalDistance += distance;
            //   UserPreferences.saveTotalDistance(totalDistance,context);
            lastLocation = location;
        //}
        if (!speed.isInfinite()) {
            allSpeed.add(speed);
        }

        Intent i = new Intent("location");
        i.putExtra("lat",location.getLatitude());
        i.putExtra("lng",location.getLongitude());
        getApplicationContext().sendBroadcast(i);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onDestroy() {
        if (mLocationManager != null)
            mLocationManager.removeUpdates(this);
        if (sensorManager != null)
            sensorManager.unregisterListener(this);
        isServiceRunning = false;
        super.onDestroy();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
       // Toast.makeText(getApplicationContext(), sensorEvent.values[0] + "", Toast.LENGTH_LONG).show();
        currentSteps = sensorEvent.values[0];
        if (lastSteps == 0) {
            lastSteps = currentSteps;
            // mHandler.m_steps=lastSteps;
            //   UserPreferences.saveTotalSteps(currentSteps,context);
        }


        // send steps update
        Intent i = new Intent("stepsCount");
        context.sendBroadcast(i);

        if (BuildConfig.DEBUG)
            Log.i("steps_error", String.valueOf(currentSteps));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public float getCurrentSteps() {
        return currentSteps - lastSteps;
    }

    public double getSpeed() {
        double speedinDouble = 0.0;
        DecimalFormat doubleFormat = new DecimalFormat("0.00");
        try {
            if (doubleFormat.format(speed) != "∞") {
                speedinDouble = Double.parseDouble(doubleFormat.format(speed));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return speedinDouble;
    }

    public float getTotalDistance() {
        return totalDistance;
    }


    public double getAverageSpeed() {
        double averageSpeed;
        double totalSpeed = 0.0;
        for (int i = 0; i < allSpeed.size(); i++) {
            totalSpeed = totalSpeed + allSpeed.get(i);
        }
        averageSpeed = totalSpeed / allSpeed.size();
        if (averageSpeed < 0) {
            averageSpeed = 0.0;
        }
        return averageSpeed;
    }

    public String getAverageTime() {
        if (getAverageSpeed() > 0) {
            double timePerHour = 1 / getAverageSpeed();
            double seconds = timePerHour * 3600;
            return getDurationString((int) (seconds));
        } else {
            return "00:00:00";
        }

    }

    public class LocalBinder extends Binder {
        public ActivityService getService() {
            return ActivityService.this;
        }
    }


    private String getDurationString(int seconds) {

        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;

        return twoDigitString(hours) + " : " + twoDigitString(minutes) + " : " + twoDigitString(seconds);
    }

    private String twoDigitString(int number) {

        if (number == 0) {
            return "00";
        }

        if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }

}
