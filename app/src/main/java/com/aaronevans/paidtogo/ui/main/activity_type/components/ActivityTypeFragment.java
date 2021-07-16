package com.aaronevans.paidtogo.ui.main.activity_type.components;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.provider.CalendarContract;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aaronevans.paidtogo.ui.main.MainActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.components.ActivityService;
import com.aaronevans.paidtogo.data.local.SettingsPreferences;
import com.aaronevans.paidtogo.data.local.UserPreferences;
import com.aaronevans.paidtogo.data.mHandler;
import com.aaronevans.paidtogo.data.remote.PaidToGoService;
import com.aaronevans.paidtogo.data.remote.request.ActivityRegister;
import com.aaronevans.paidtogo.data.remote.response.activity_register_response.ActivityRegisterResponse;
import com.aaronevans.paidtogo.ui.BaseActivity;
import com.aaronevans.paidtogo.ui.BaseFragment;
import com.aaronevans.paidtogo.ui.main.activity_type.ActivityRegisteredFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import at.grabner.circleprogress.CircleProgressView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

@EFragment(R.layout.fragment_register_activity)
public class ActivityTypeFragment extends BaseFragment implements ActivityTypeContract.View , SensorEventListener {

    //MapView map;
    double distance;
    private GoogleMap googleMap;
    public static final float PARAMETER_CONVERT_MILES = 0.00062f;
    public static final float PARAMETER_MILES = 0.00062f;
    public static final float PARAMETER_CONVERT_KM = 1000;
    private static final long _5_SEC = 1 * 1000;
    private static final long _2_SEC = 1 * 1000;

    private PolylineOptions polylineOptions;
    private Polyline finalPolyLine;

    public ActivityService activityService;
    @ViewById
    ImageView img;
    @ViewById
    ImageView mBanner;
    @ViewById
    ImageView mPoolTypeImg;
    @ViewById
    TextView mTvStart, mTvStop, mTvPause, mTvSteps, mTextViewGoMap, cur_speed, avg_speed;
    @ViewById
    CircleProgressView mCircleProgress;
    @ViewById
    RelativeLayout activity_map;
    @ViewById
    TextView miles;
    @ViewById
    TextView gas;
    @ViewById
    TextView co2;
    @ViewById
    TextView cal;
    @ViewById
    TextView steps;
    @ViewById
    Chronometer mChronometer;
    private long pauseOffset;

    @ViewById
    TextView textViewUnit;

    String miles_temp = "", gas_temp = "", co2_temp = "", cal_temp = "", steps_temp = "";

    SupportMapFragment map;
    private ProgressDialog mProgressDialog;
    private Timer timer;
    long syncTime;
    ArrayList<ActivityRegister> activity1;
    ArrayList<ActivityRegister> activity2;
    boolean c1, c2;
    BaseActivity baseActivity;
    ArrayList<String> Avg_speed = new ArrayList<>();
    //  double avg_speed_count;
    int avg_sum = 0;
    int previousActivity = 1;
    double current_step;
    int act_type;
    long elapsedMillis;
    private long timeWhenStopped = 0;
    public Context baseContext;


    public static List<LatLng> allLatLongList = new ArrayList<>();

    private ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceDisconnected(ComponentName className) {
            activityService = null;
            stopRefresher();
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            activityService = ((ActivityService.LocalBinder) service).getService();
            hideProgressDialog();
            showHidePause(true);

            mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                @Override
                public void onChronometerTick(Chronometer chronometer) {
                    long time = SystemClock.elapsedRealtime() - chronometer.getBase();
                    int h = (int) (time / 3600000);
                    int m = (int) (time - h * 3600000) / 60000;
                    int s = (int) (time - h * 3600000 - m * 60000) / 1000;
                    String t = (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);
                    chronometer.setText(t);
                }
            });

            mChronometer.setBase(SystemClock.elapsedRealtime());
            mChronometer.setText("00:00:00");

            mChronometer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
            mChronometer.start();
            showElapsedTime();


            broadCastReceiverLocation = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    //Toast.makeText(getActivity(), "Location Receive", Toast.LENGTH_LONG).show();

                    double lat = intent.getDoubleExtra("lat", 0.0);
                    double lng = intent.getDoubleExtra("lng", 0.0);

                    LatLng currentPosition = new LatLng(lat, lng);
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(currentPosition).zoom(20).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


                    LatLng latLng = new LatLng(lat, lng);
                    allLatLongList.add(latLng);
                    setPolyline();
                   /* try {
                        showSensorData();
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "exception occur", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }*/
                }
            };

            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (activityService != null) {
                        cur_speed.setText(String.format("%.1f", activityService.getSpeed()));
                        String temp_speed = String.format("%.1f", activityService.getAverageSpeed());
                        avg_speed.setText(temp_speed);


                        try {
                            showSensorData();
                        } catch (Exception e) {
                            Toast.makeText(getContext(), "exception occur", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }
                }
            };


            getActivity().registerReceiver(broadcastReceiver, new IntentFilter("stepsCount"));
            getActivity().registerReceiver(broadCastReceiverLocation, new IntentFilter("location"));

           /* timer = new Timer(true);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    showSensorData();
                }
            }, _5_SEC, _2_SEC);*/
        }
    };
    private ArrayList<Double> speedcounts;
    private double averSpeedcount;
    private double averSpeed = 0.0;
    private BroadcastReceiver broadcastReceiver;
    private BroadcastReceiver broadCastReceiverLocation;
    private String averageTime;
    private Toast toast;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        baseContext = context;


    }

    @Override
    public void onResume() {
        super.onResume();
        speedcounts = new ArrayList<>();
        //allLatLongList.clear();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            getActivity().unregisterReceiver(broadcastReceiver);
        }
    }

    @AfterViews
    public void onCreateView() {
        baseActivity = (BaseActivity) getActivity();
        ((BaseActivity) getActivity()).setOnBackPressedListener(new BaseBackPressedListener(getActivity()) {
            public void doBack() {
                if (activity_map.getVisibility() == View.GONE) {
                    if (activityService != null)
                        activityService.stopSelf();
                    stopRefresher();
                    if (mTvStart.getVisibility() == View.GONE) {
                        postActivity();
                    }
                    getActivity().finish();
                    showHidePause(false);
                } else {
                    activity_map.setVisibility(View.GONE);
                }
            }
        });


        if (SettingsPreferences.isMilesKm(getContext())) {
            textViewUnit.setText("KM");
        } else {
            textViewUnit.setText("Miles");
        }


        if (miles_temp.equalsIgnoreCase("")) {

            if (SettingsPreferences.isMilesKm(getContext())) {
                miles.setText("  Kilometers traveled: " + "0.0" + " Kilometers");
            } else {
                miles.setText("  Miles traveled: " + "0.0" + " miles");
            }


        } else {
            if (SettingsPreferences.isMilesKm(getContext())) {
                miles.setText("  Kilometers traveled: " + miles_temp + " Kilometers");
            } else {
                miles.setText("  Miles traveled: " + miles_temp + " miles");
            }

        }
        if (gas_temp.equalsIgnoreCase("")) {
            gas.setText("  Gas saved: " + "0.0" + " gal");
        } else {
            gas.setText("  Gas saved: " + gas_temp + " gal");
        }
        if (co2_temp.equalsIgnoreCase("")) {
            co2.setText("  CO2 Offset: " + "0.0" + " Lbs");
        } else {
            co2.setText("  CO2 Offset: " + co2_temp + " Lbs");
        }
        if (cal_temp.equalsIgnoreCase("")) {
            cal.setText("  Calories burned: " + "0.0" + " cal");
        } else {
            cal.setText("  Calories burned: " + cal_temp + " cal");
        }
        if (steps_temp.equalsIgnoreCase("")) {
            steps.setText("  Steps: " + "0.0" + " steps");
        } else {
            steps.setText("  Steps: " + steps_temp + " steps");
        }

        map = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        map.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                googleMap.setMyLocationEnabled(true);

                // For dropping a marker at a point on the Map
                if (mHandler.user_lat != 0.0) {
                    LatLng sydney = new LatLng(mHandler.user_lat, mHandler.user_lng);
                    //     googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

                    // For zooming automatically to the location of the marker
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(18).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }

            }
        });

        /*if (SettingsPreferences.isAutoTrackingON(getContext()))
            startPresenter();*/

    }


    @Click(R.id.mTvStart)
    public void startActivity() {
//        ArrayList<String> items = new ArrayList<>();
//        items.add("Walk/Run");
//        items.add("Bike");
//        Dialogs.getInstance().showBottomSheetWithPickerDialog(getContext(), items, 0, new BottomDialogCallBack() {
//            @Override
//            public void onCallBack(String item, int position) {
//                // etGender.setText(item);
//                if(item.equalsIgnoreCase("Walk/Run")){
//                    act_type=1;
//                }else{
//                    act_type=2;
//                }
//                baseActivity.mstopService();
//                startPresenter();
//            }
//        });


        if (Build.VERSION.SDK_INT == 29) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED) {
                //ask for permission
                requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 101);
            } else {
                act_type = 1;
                baseActivity.mstopService();

                startPresenter();
            }
        } else {
            act_type = 1;
            baseActivity.mstopService();
            startPresenter();
        }

    }

    @Click(R.id.mTextViewGoMap)
    public void startMapActivity() {
        activity_map.setVisibility(View.VISIBLE);
    }

    @Click(R.id.img)
    public void backimg() {
        activity_map.setVisibility(View.GONE);
    }

    private void showSensorData() {
        //  double speed = activityService.getSpeed();
        distance = activityService.getTotalDistance();

        if (SettingsPreferences.isMilesKm(getActivity())) {
            miles_temp = String.format("%.2f", distance / PARAMETER_CONVERT_KM);
        } else {
            miles_temp = String.format("%.2f", distance * PARAMETER_CONVERT_MILES);
        }

        getActivity().runOnUiThread(() -> {
           /* if (speed <= 10) {
                if (previousActivity == 1) {
                    c1 = true;
                    activity1.get(activity1.size() - 1).setMilesTraveled((String.valueOf((float) distance * PARAMETER_CONVERT_MILES)));
                    activity1.get(activity1.size() - 1).setTotalSteps((String.valueOf(activityService.getCurrentSteps())));
                    activity1.get(activity1.size() - 1).setCalories((String.valueOf(((float) distance * PARAMETER_CONVERT_MILES) * 100)));
                } else {
                    long currentTime = Calendar.getInstance().getTimeInMillis();
                    activity2.get(activity2.size() - 1).setEndDateTime(formatTime(currentTime,
                            "yyyy-MM-dd HH:mm:ss"));
                    activity2.get(activity2.size() - 1).setEndLatitude(String.valueOf(mHandler.user_lat));
                    activity2.get(activity2.size() - 1).setEndLongitude(String.valueOf(mHandler.user_lng));
                    activity1.add(new ActivityRegister("1", String.valueOf(UserPreferences.getToken(getContext())), String.valueOf(formatTime(syncTime, "yyyy-MM-dd HH:mm:ss")), "0.0", "0.0", (String.valueOf((float) distance * PARAMETER_CONVERT_MILES)), String.valueOf(mHandler.user_lat), String.valueOf(mHandler.user_lng), (String.valueOf(activityService.getCurrentSteps())), "0", (String.valueOf(((float) distance * PARAMETER_CONVERT_MILES) * 100)), "0", "0"));

                }
                mPoolTypeImg.setImageResource(R.drawable.ic_walkrun);
                mPoolTypeImg.setBackgroundResource(R.color.green_balance);
                previousActivity = 1;
            } else {
                c2 = true;
                if (previousActivity == 2) {
                    activity2.get(activity2.size() - 1).setMilesTraveled((String.valueOf((float) distance * PARAMETER_CONVERT_MILES)));
                    activity2.get(activity2.size() - 1).setTotalSteps((String.valueOf(activityService.getCurrentSteps())));
                    activity2.get(activity2.size() - 1).setCalories((String.valueOf(((float) distance * PARAMETER_CONVERT_MILES) * 100)));
                } else {
                    long currentTime = Calendar.getInstance().getTimeInMillis();
                    activity1.get(activity1.size() - 1).setEndDateTime(formatTime(currentTime,
                            "yyyy-MM-dd HH:mm:ss"));
                    activity1.get(activity1.size() - 1).setEndLatitude(String.valueOf(mHandler.user_lat));
                    activity1.get(activity1.size() - 1).setEndLongitude(String.valueOf(mHandler.user_lng));
                    activity2.add(new ActivityRegister("2", String.valueOf(UserPreferences.getToken(getContext())), String.valueOf(formatTime(syncTime, "yyyy-MM-dd HH:mm:ss")), "0.0", "0.0", (String.valueOf((float) distance * PARAMETER_CONVERT_MILES)), String.valueOf(mHandler.user_lat), String.valueOf(mHandler.user_lng), (String.valueOf(activityService.getCurrentSteps())), "0", (String.valueOf(((float) distance * PARAMETER_CONVERT_MILES) * 100)), "0", "0"));

                }
                mPoolTypeImg.setImageResource(R.drawable.ic_bike);
                mPoolTypeImg.setBackgroundResource(R.color.green_balance);
                previousActivity = 2;
            }*/
//        update miles


            activity1.get(0).setMilesTraveled((String.valueOf((float) distance * PARAMETER_CONVERT_MILES)));
            activity1.get(0).setTotalSteps((String.valueOf(activityService.getCurrentSteps())));
            activity1.get(0).setCalories((String.valueOf(((float) distance * PARAMETER_CONVERT_MILES) * 100)));
            if (act_type == 1) {
                //     mPoolTypeImg.setImageResource(R.drawable.ic_walkrun);
            } else {
                // mPoolTypeImg.setImageResource(R.drawable.ic_bike);
            }


           /* if(speed!=0&&speed!=0.0) {
                Log.e("speed is",speed+"");
                speedcounts.add(speed); //size of speedcounts is only 1
                for(int i = 0; i < speedcounts.size(); i++) { //just loop once
                    averSpeedcount += speedcounts.get(i).doubleValue();
                    averSpeed = averSpeedcount / speedcounts.size();
                }


                //   avg_sum++;
                // avg_speed_count = avg_speed_count + speed;
                // avg_speed_count = avg_speed_count / avg_sum;
        *//*        Avg_speed.add(String.format("%.1f", activityService.getSpeed()));
                double total_temp = 0.0;
                for (int i = 0; i < Avg_speed.size(); i++) {
                    int j = i + 1;
                    if (j < Avg_speed.size() && i == 0) {
                        total_temp = Double.parseDouble(Avg_speed.get(i));
                    } else if (j < Avg_speed.size() && i != 0) {
                        total_temp = total_temp + Double.parseDouble(Avg_speed.get(j));
                    } else {
                        total_temp = total_temp + Double.parseDouble(Avg_speed.get(i));
                    }
                }
                total_temp = total_temp / Avg_speed.size();*//*

            }*/

            averSpeed = activityService.getAverageSpeed();

            //miles_temp = String.format("%.1f", (float) distance * PARAMETER_CONVERT_MILES);
            steps_temp = String.format("%.2f", activityService.getCurrentSteps());
            cal_temp = String.format("%.2f", (distance * PARAMETER_CONVERT_MILES) * 100);
            gas_temp = String.format("%.2f", (distance * PARAMETER_CONVERT_MILES) * 0.04);
            co2_temp = String.format("%.2f", (distance * PARAMETER_CONVERT_MILES) * 0.888);

            if (miles_temp.equalsIgnoreCase("")) {
                if (SettingsPreferences.isMilesKm(getContext())) {
                    miles.setText("  Kilometers traveled: " + "0.0" + " Kilometers");

                } else {
                    miles.setText("  Miles traveled: " + "0.0" + " miles");
                }

            } else {
                if (SettingsPreferences.isMilesKm(getContext())) {
                    miles.setText("  Kilometers traveled: " + miles_temp + " Kilometers");

                } else {
                    miles.setText("  Miles traveled: " + miles_temp + " miles");
                }
            }
            if (gas_temp.equalsIgnoreCase("")) {
                gas.setText("  Gas saved: " + "0.0" + " gal");
            } else {
                gas.setText("  Gas saved: " + gas_temp + " gal");
            }
            if (co2_temp.equalsIgnoreCase("")) {
                co2.setText("  CO2 Offset: " + "0.0" + " Lbs");
            } else {
                co2.setText("  CO2 Offset: " + co2_temp + " Lbs");
            }
            if (cal_temp.equalsIgnoreCase("")) {
                cal.setText("  Calories burned: " + "0.0" + " cal");
            } else {
                cal.setText("  Calories burned: " + cal_temp + " cal");
            }
            if (steps_temp.equalsIgnoreCase("")) {
                steps.setText("  Steps: " + "0.0" + " steps");
            } else {
                steps.setText("  Steps: " + steps_temp + " steps");
            }


            //mCircleProgress.setValue(0);

            if (SettingsPreferences.isMilesKm(getContext())) {
                miles.setText("  Kilometers traveled: " + miles_temp + " Kilometers");
            } else {
                miles.setText("  Miles traveled: " + miles_temp + " miles");
            }

           /* double value = distance * PARAMETER_CONVERT_MILES;
            double previousValue=0.0;
            if(value!=previousValue){
                if(toast!=null){
                    toast.cancel();
                }
                toast=Toast.makeText(getActivity(),value+"",Toast.LENGTH_LONG);
                toast.show();
                previousValue=value;
            }*/



//  to change the code corret for miles
//        String data = String.format("%.2f", distance * 0.00062);




//             comment code by sanjeev
            String data = String.format("%.2f", distance * PARAMETER_CONVERT_MILES);

            mCircleProgress.setValue(Float.parseFloat(data));
            String msg = String.format(Locale.ENGLISH, "Steps: %.0f", activityService.getCurrentSteps());
            mTvSteps.setText(msg);



            //hideProgressDialog();
        });
    }

    private void showHidePause(boolean isShowPause) {
        if (isShowPause) {
            mTvPause.setVisibility(View.VISIBLE);
            mTvStop.setVisibility(View.VISIBLE);
            mTvStart.setVisibility(View.GONE);
        } else {
            mTvPause.setVisibility(View.GONE);
            mTvStop.setVisibility(View.GONE);
            mTvStart.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void showErrorAlert(String msg) {
        new AlertDialog.Builder(getContext())
                .setTitle("Paidtogo")
                .setMessage(msg)
                .setPositiveButton(R.string.alert_accept, null)
                .create()
                .show();
    }

    @Override
    public void startPresenter() {
       /* Location location = ((MainActivity) getActivity()).getLocation();
        if(location!=null) {
           mHandler.user_lat = location.getLatitude();
           mHandler.user_lng = location.getLongitude();
        }*/

        syncTime = UserPreferences.getSyncTime(getContext());
        activity1 = new ArrayList<>();
        activity2 = new ArrayList<>();
        activity1.add(new ActivityRegister("1", String.valueOf(UserPreferences.getToken(getContext())), String.valueOf(formatTime(syncTime, "yyyy-MM-dd HH:mm:ss")), "0.0", "0.0", "0.0", String.valueOf(mHandler.user_lat), String.valueOf(mHandler.user_lng), "0", "0", "0", "0", "0"));
        activity2.add(new ActivityRegister("2", String.valueOf(UserPreferences.getToken(getContext())), String.valueOf(formatTime(syncTime, "yyyy-MM-dd HH:mm:ss")), "0.0", "0.0", "0.0", String.valueOf(mHandler.user_lat), String.valueOf(mHandler.user_lng), "0", "0", "0", "0", "0"));

        connectActivityService();
        mCircleProgress.setMaxValue(1);
        mCircleProgress.setBarColor(ContextCompat.getColor(getActivity(), R.color.color_gradient1), ContextCompat.getColor(getActivity(), R.color.color_gradient2));
        //mCircleProgress.setUnitScale(0.01f);
        mCircleProgress.setValue(0.00f);
        // mCircleProgress.setValue(Float.parseFloat("0.96"));
        // mCircleProgress.setText("0.5");
        showHidePause(false);
    }

    private void connectActivityService() {
        if (!ActivityService.isServiceRunning)
            showProgressDialog();
        getContext().startService(new Intent(getContext(), ActivityService.class));
        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                getContext().bindService(new Intent(getContext(), ActivityService.class), mConnection, 0);
            }
        }, 1000);
    }

    private void stopRefresher() {
        if (timer != null) {
            timer.cancel();
        }
        showHidePause(false);
    }

    @Override
    public void showPermissionMission(String msg) {
        new AlertDialog.Builder(getContext())
                .setTitle(getResources().getString(R.string.location_disabled))
                .setMessage(msg)
                .setPositiveButton(R.string.alert_accept, (dialogInterface, i) -> startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS")))
                .create()
                .show();
    }

    @Click(R.id.mTvPause)
    public void stopActivity() {
//        if (activityService != null)
//            activityService.stopSelf();
//        stopRefresher();   //comment for changing the text of
//        mChronometer.stop();

//
//        Toast.makeText(activityService, "koko", Toast.LENGTH_SHORT).show();

        // add new line of code
//        mTvPause.setText("Resume");
//        mTvStop.setVisibility(View.VISIBLE);
//        mTvStart.setVisibility(View.GONE);
//        if (timer != null) {
//            timer.cancel();
//        }

//        showHidePause(false); commenet code

        // code for resume
        if (mTvPause.getText().toString().equals("Pause"))
        {
            mTvPause.setText("Resume");
            mTvStop.setVisibility(View.VISIBLE);
            mTvStart.setVisibility(View.GONE);
            mChronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - mChronometer.getBase();

            if (timer != null) {
                timer.cancel();
            }
        }
        else
        {
            mTvPause.setText("Pause");


            mChronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);

            mChronometer.start();
//            running = false;
            mTvStop.setVisibility(View.VISIBLE);
            mTvStart.setVisibility(View.GONE);

        }

    }

    private void showElapsedTime() {
        elapsedMillis = SystemClock.elapsedRealtime() - mChronometer.getBase();
        elapsedMillis = elapsedMillis / 1000;
        pauseOffset = SystemClock.elapsedRealtime() - mChronometer.getBase();
//        Toast.makeText(getActivity(), "Elapsed milliseconds: " + elapsedMillis,
//                Toast.LENGTH_SHORT).show();
    }

    @Click(R.id.mTvStop)
    public void stop2Activity() {
        timeWhenStopped = mChronometer.getBase() - SystemClock.elapsedRealtime();
        mChronometer.stop();
        showElapsedTime();
        if (activityService != null)
            stopRefresher();
        if (act_type == 1) {
            double temp = activityService.getCurrentSteps() / ((float) distance * PARAMETER_CONVERT_MILES);
            activity1.get(0).setMilesTraveled(String.format("%.2f", ((float) distance * PARAMETER_CONVERT_MILES)));
            if (averSpeed > 10.0) {

                postActivity();
                //showErrorAlert("Your Speed was more than 10mph while Walking or Running which is suspicious. So your current activity will be counted!");
            } else {
                postActivity();
            }
        } else if (act_type == 2) {
            if (averSpeed > 20.0) {
               // showErrorAlert("Your Speed was more than 20mph while Bike which is suspicious. So your current activity will be counted!");
                postActivity();

            } else {
                postActivity();
            }
        }


        averageTime = activityService.getAverageTime();
        averSpeed = activityService.getAverageSpeed();
        activityService.stopSelf();


        showHidePause(false);

    }


    @Click(R.id.mBackArrowDown)
    void retrieveActivity() {
        getActivity().finish();
    }

    @Override
    public void showProgressDialog() {
        if (mProgressDialog != null)
            mProgressDialog.cancel();
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setTitle(getResources().getString(R.string.app_name));
        mProgressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void postActivity() {

        activity1.get(0).setActivityType(String.valueOf(act_type));
        activity1.get(0).setActivityType(String.valueOf(act_type));
        long currentTime = Calendar.getInstance().getTimeInMillis();
        activity1.get(0).setEndDateTime(formatTime(currentTime,
                "yyyy-MM-dd HH:mm:ss"));
        activity1.get(0).setEndLatitude(String.valueOf(mHandler.user_lat));
        activity1.get(0).setEndLongitude(String.valueOf(mHandler.user_lng));
        if (previousActivity == 1) {
            activity1.get(activity2.size() - 1).setEndDateTime(formatTime(currentTime,
                    "yyyy-MM-dd HH:mm:ss"));
            activity1.get(activity2.size() - 1).setEndLatitude(String.valueOf(mHandler.user_lat));
            activity1.get(activity2.size() - 1).setEndLongitude(String.valueOf(mHandler.user_lng));
        } else {
            activity2.get(activity2.size() - 1).setEndDateTime(formatTime(currentTime,
                    "yyyy-MM-dd HH:mm:ss"));
            activity2.get(activity2.size() - 1).setEndLatitude(String.valueOf(mHandler.user_lat));
            activity2.get(activity2.size() - 1).setEndLongitude(String.valueOf(mHandler.user_lng));
        }
        ArrayList<ActivityRegister> temp = new ArrayList<>();
        activity1.get(0).setCreatedAt(formatTime(currentTime,
                "yyyy-MM-dd HH:mm:ss"));
        //   if(c1==true) {
        temp.addAll(activity1);
        //  }
      /*  if(c2==true) {
            temp.addAll(activity2);
        }*/
        //    if(c1==true||c2==true) {

        showProgressDialog();

        Gson gson=new Gson();
        String data=gson.toJson(temp);
        Log.e("data is ",data);

        PaidToGoService.getServiceClient().registerActivity(temp)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::registerActivityResponse,
                        this::onError
                );
        //   }
        //  }
    }

    public String formatTime(long time, String regex) {
        return DateFormat.format(regex, time).toString();
    }

    private void registerActivityResponse(ResponseBody body) {
        hideProgressDialog();
        try {
           /* Gson gson = new Gson();
            String json = gson.toJson(activity);*/
            String stringResponse = body.string();
            JSONObject jsonObject = new JSONObject(stringResponse);
            if (jsonObject.has("error")) {
                if (jsonObject.optString("error").equalsIgnoreCase("maximum points limit reached for today!")) {

                    if (UserPreferences.getUser(getContext()).getSubscription_id() == 1) {
                        showPointsCapDialog(getActivity(), "You have reached your Coins cap for the day. Upgrade to Pro to increase your limit to \\(limit) coins per day");
                    } else {
                        showPointsCapDialog(getActivity(), "You have reached your daily payment cap. Please wait 24 hours to continue earning per mile.");
                    }

                }
            } else {

                Toast.makeText(getContext(), "Activity Registered", Toast.LENGTH_SHORT).show();
                Gson gson = new Gson();
                ActivityRegisterResponse activity = gson.fromJson(stringResponse, ActivityRegisterResponse.class);
                Toast.makeText(getContext(), "Activity Registered", Toast.LENGTH_SHORT).show();
                ActivityRegisteredFragment fragment = new ActivityRegisteredFragment();
                Bundle bundle = new Bundle();
                bundle.putString("miles", miles_temp);
                bundle.putString("gas", gas_temp);
                bundle.putString("co2", co2_temp);
                bundle.putString("cal", cal_temp);
                bundle.putString("time", String.valueOf(pauseOffset/1000));
                bundle.putString("steps", String.valueOf(activity1.get(0).getTotalSteps()));
                bundle.putString("averageSpeed", String.format("%.2f", averSpeed));
                bundle.putString("averageTime", averageTime);

              /*  bundle.putString("miles", String.valueOf(activity.getMilesTraveled()));
                bundle.putString("gas", String.valueOf(activity.getSaveTraffic()));
                bundle.putString("co2", String.valueOf(activity.getSavedCo2()));
                bundle.putString("cal", String.valueOf(activity.getSavedCalories()));
                bundle.putString("time", String.valueOf(elapsedMillis));
                bundle.putString("steps", String.valueOf(activity1.get(0).getTotalSteps()));*/


                baseActivity.mstartService();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction()
//                .setCustomAnimations(R.anim.fragment_slide_left_enter,
//                        R.anim.fragment_slide_left_exit,
//                        R.anim.fragment_slide_right_enter,
//                        R.anim.fragment_slide_right_exit)
                        .replace(R.id.mFragmentContainerActivity, fragment)
                        .commit();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onError(Throwable throwable) {
        if (throwable instanceof HttpException) {
            hideProgressDialog();
            showErrorAlert(PaidToGoService.attendError((HttpException) throwable).getDetail());
        } else {
            hideProgressDialog();
            showErrorAlert(getContext().getString(R.string.connection_problem));
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 101:
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        act_type = 1;
                        baseActivity.mstopService();
                        startPresenter();
                    } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        showProAlert();
                    } else {
                        if (Build.VERSION.SDK_INT >= 23) {
                            boolean showRationale = shouldShowRequestPermissionRationale(Manifest.permission.ACTIVITY_RECOGNITION);
                            if (!showRationale) {
                                showProAlert();
                            }
                        }
                    }
                }
                break;
        }
    }


    public void showProAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(baseContext);
        // Setting Dialog Title
        alertDialog.setTitle("Permission Alert");

        // Setting Dialog Message
        alertDialog.setMessage("Paid to go required physical activity permission to track your steps.");

        // On pressing Settings button
        alertDialog.setPositiveButton("Okay",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", baseContext.getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, 301);
                        dialog.cancel();
                    }
                });

        // Showing Alert Message
        alertDialog.show();
    }


    void setPolyline() {

        List<LatLng> copyList = new ArrayList<>();
        if (finalPolyLine == null) {
            polylineOptions = new PolylineOptions();
            polylineOptions.geodesic(true);
            polylineOptions.width(24f);
            polylineOptions.color(getResources().getColor(R.color.app_green));
            polylineOptions.addAll(allLatLongList);
            finalPolyLine = googleMap.addPolyline(polylineOptions);
        } else {
            for (int i = 0; i < allLatLongList.size(); i++) {
                if (i % 2 == 0) {
                    copyList.add(allLatLongList.get(i));
                }
            }


            finalPolyLine.setPoints(copyList);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}