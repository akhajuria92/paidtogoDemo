package com.aaronevans.paidtogo.data.sensors;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.ui.main.activity_type.components.ActivityTypeContract;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Farhan Arshad on 6/22/2018.
 */

public class GPSLocator implements android.location.LocationListener {
    private final ActivityTypeContract.View view;
    private LocationManager mLocationManager;
    private float totalDistance = 0;    // in meters
    private Location lastLocation;      // last known user location
    private double speed; // in meters per sec

    public GPSLocator(ActivityTypeContract.View view) {
        this.view = view;
        mLocationManager = (LocationManager) view.getContext().getSystemService(Context.LOCATION_SERVICE);
        new RxPermissions((Activity) view.getContext())
                .request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribeOn(Schedulers.trampoline())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        granted -> {
                            if (granted)
                                registerLocationManager();
                            else {
                                view.hideProgressDialog();
                                view.showPermissionMission("Permission Missing");
                            }
                        }
                );
    }

    public float getTotalDistance() {
        return totalDistance;
    }

    /*public Location getLastLocation() {
        if (lastLocation == null) {
            lastLocation = new Location("last");
        }
        return lastLocation;
    }*/

    public double getSpeed() {
        return speed;
    }

    @SuppressLint("MissingPermission")
    private void registerLocationManager() {
//        int networkProvider = mLocationManager.getAllProviders().indexOf(LocationManager.NETWORK_PROVIDER);
        int gpsProvider = mLocationManager.getAllProviders().indexOf(LocationManager.GPS_PROVIDER);
        if (gpsProvider >= 0) {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 5, this);
//        } else if (networkProvider >= 0) {
//            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 0, this);
        } else {
            view.showErrorAlert("No GPS location provider found. GPS data display will not be available.");
        }
        if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            view.hideProgressDialog();
            view.showPermissionMission(view.getContext().getResources().getString(R.string.please_enable_location));
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        /*float accuracy = 0;
        if (location.hasAccuracy()) {
            accuracy = location.getAccuracy();
        }*/
        Log.i("location_error", String.valueOf(location.getLatitude()));
        if (lastLocation == null) {
            lastLocation = location;
        }
        double distance = lastLocation.distanceTo(location);
        if (location.getAccuracy() < distance &&
                location.getAccuracy() <= 25 && location.getAccuracy() >= 10) {
            totalDistance += distance;
            lastLocation = location;
        }
        speed = 0;
        if (location.hasSpeed()) {
            speed = location.getSpeed();
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

    public void unregisterListener() {
        if (mLocationManager != null)
            mLocationManager.removeUpdates(this);
    }
}
