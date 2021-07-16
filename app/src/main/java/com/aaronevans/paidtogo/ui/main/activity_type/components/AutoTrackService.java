package com.aaronevans.paidtogo.ui.main.activity_type.components;

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
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import com.aaronevans.paidtogo.BuildConfig;
import com.aaronevans.paidtogo.data.local.UserPreferences;
import java.util.Timer;
import java.util.TimerTask;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

/**
 * Created by fabio on 30/01/2016.
 */

public class AutoTrackService extends Service implements LocationListener, SensorEventListener {
    private float totalDistance = 0;    // in meters
    private Location lastLocation;      // last known user location
    private double speed; // in meters per sec
    public int counter=0;
    private boolean isAvailable = false;
    private LocationManager mLocationManager;
    private SensorManager sensorManager;
    private Application context;
    private float lastSteps = 0;
    private float currentSteps = 0;
    double avg_speed;
    double avg_speed_flag;
    public AutoTrackService(Context applicationContext) {
        super();
        Log.i("HERE", "here I am!");
    }

    public AutoTrackService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();
        context = getApplication();
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            registerUpdateManager();
        } else {
            stopSelf();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("EXIT", "ondestroy!");
        Intent broadcastIntent = new Intent("uk.ac.shef.oak.ActivityRecognition.RestartSensor");
        sendBroadcast(broadcastIntent);
        stoptimertask();
    }

    private Timer timer;
    private TimerTask timerTask;
    long oldTime=0;
    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, to wake up every 1 second
        timer.schedule(timerTask, 1000, 1000); //
    }

    /**
     * it sets the timer to print the counter every x seconds 
     */

    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                Log.i("in timer", "in timer ++++  "+ (counter++));
            }
        };
    }

    /**
     * not needed
     */
    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void registerUpdateManager() {
        int gpsProvider = mLocationManager.getAllProviders().indexOf(LocationManager.GPS_PROVIDER);
        if (gpsProvider >= 0) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 2, this);
        }

        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            isAvailable = false;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        currentSteps = sensorEvent.values[0];
        Log.e("ATrackServiceRaghav++", "onSensorChanged+currentSteps"+currentSteps);

        if (lastSteps == 0)
        {
            lastSteps = currentSteps;
            Log.e("ATrackServiceRaghav++", "onSensorChanged+lastSteps"+lastSteps);
            // mHandler.m_steps=lastSteps;
            UserPreferences.saveTotalSteps(lastSteps, context);
        }
        if (BuildConfig.DEBUG)
        {
            Log.i("steps_error", String.valueOf(currentSteps));
            Log.e("ATrackServiceRaghav++", "onSensorChanged+currentSteps"+currentSteps);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if (BuildConfig.DEBUG)
            Log.i("location_error", String.valueOf(location.getLatitude()));
        if (lastLocation == null) {
            lastLocation = location;
            Log.e("ATrackServiceRaghav++", "onLocationChanged+lastLocation"+lastLocation);
        }

        double distance = lastLocation.distanceTo(location);
        Log.e("ATrackServiceRaghav++", "onLocationChanged+distance"+distance);

        speed = 0;
        if(location.getAccuracy() <= 25&&distance!=0.0&&distance!=0) {
            speed = distance / ((location.getTime() - lastLocation.getTime()) / 1000);
            speed = speed * 2.236936f;
        }
        if (location.hasSpeed()) {
            speed = location.getSpeed();
            Log.e("ATrackServiceRaghav++", "onLocationChanged+speed"+speed);

        }
        if(speed!=0.0){
            avg_speed_flag++;
            avg_speed=(avg_speed+speed)/avg_speed_flag;
            Log.e("ATrackServiceRaghav++", "onLocationChanged+avg_speed"+avg_speed);

        }

        if (location.getAccuracy() < distance &&
                location.getAccuracy() <= 25 && location.getAccuracy() >= 10 && speed != 0) {
            //  mHandler.m_distance=totalDistance;

            totalDistance += distance;
            if(avg_speed<10.0&&distance>0.0){
                //UserPreferences.saveTotalDistance(totalDistance,context);
            }
            if(avg_speed>2.0&&avg_speed<20.0&&distance>0.0){
                UserPreferences.saveTotalDistanceOnBike(totalDistance,context);
            }

            lastLocation = location;
            Log.e("ATrackServiceRaghav++", "onLocationChanged+lastLocation"+lastLocation);

        }
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

}
