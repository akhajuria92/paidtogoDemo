package com.aaronevans.paidtogo.data.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.aaronevans.paidtogo.ui.main.activity_type.components.ActivityTypeContract;

/**
 * Created by Farhan Arshad on 6/26/2018.
 */

public class StepCounter implements SensorEventListener {

    private final SensorManager sensorManager;
    private boolean isAvailable = false;
    private float lastSteps = 0;
    private float currentSteps = 0;

    public StepCounter(ActivityTypeContract.View view) {
        sensorManager = (SensorManager) view.getContext().getSystemService(Context.SENSOR_SERVICE);
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            isAvailable = false;
            view.showErrorAlert("Unable to find the Sensor to get the step count");
        }
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        currentSteps = sensorEvent.values[0];
        if (lastSteps == 0) {
            lastSteps = currentSteps;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void unregisterListener() {
        if (sensorManager != null)
            sensorManager.unregisterListener(this);
    }

    public float getCurrentSteps() {
        return currentSteps - lastSteps;
    }
}
