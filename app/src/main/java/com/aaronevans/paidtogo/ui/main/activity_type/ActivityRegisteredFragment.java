package com.aaronevans.paidtogo.ui.main.activity_type;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aaronevans.paidtogo.data.local.SettingsPreferences;
import com.aaronevans.paidtogo.data.mHandler;
import com.aaronevans.paidtogo.ui.Utils.GetDirection;
import com.aaronevans.paidtogo.ui.main.activity_type.components.ActivityTypeFragment;
import com.bumptech.glide.request.RequestOptions;
import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.local.UserPreferences;
import com.aaronevans.paidtogo.ui.BaseActivity;
import com.aaronevans.paidtogo.ui.BaseFragment;
import com.aaronevans.paidtogo.ui.GlideApp;
import com.aaronevans.paidtogo.ui.main.activity_type.components.BaseBackPressedListener;
import com.aaronevans.paidtogo.ui.main.leaderboard.LeaderBoardFragment;
import com.aaronevans.paidtogo.ui.main.leaderboard.LeaderBoardFragment_;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

public class ActivityRegisteredFragment extends BaseFragment {
    TextView type, miles, gas, co2, cal, steps, tv_Time, btn_leader, btn_share, textViewMap, tv_per_mile, textViewDistanceType, textViewDistanceType_map;
    TextView miles_map, gas_map, co2_map, cal_map, steps_map, tv_avgspeed;

    BaseActivity baseActivity;
    ImageView profilepic;
    LinearLayout mBack;
    File imagePath;
    private RelativeLayout activity_map, view_top;

    private GoogleMap googleMap;


    GetDirection.AllPointsListener allPointsListener;


    SupportMapFragment map;
    private PolylineOptions polylineOptions;
    private Polyline finalPolyLine;
    String enrolldate;

    public ActivityRegisteredFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_registered, container, false);
        profilepic = view.findViewById(R.id.mProfilePicture);
        activity_map = view.findViewById(R.id.activity_map);
        profilepic.setClipToOutline(true);
        type = view.findViewById(R.id.type);
        miles = view.findViewById(R.id.miles);
        gas = view.findViewById(R.id.gas);
        co2 = view.findViewById(R.id.co2);
        cal = view.findViewById(R.id.cal);
        textViewMap = view.findViewById(R.id.textViewMap);
        textViewDistanceType = view.findViewById(R.id.textViewDistanceType);
        textViewDistanceType_map = view.findViewById(R.id.textViewDistanceType_map);
        steps = view.findViewById(R.id.steps);
        mBack = view.findViewById(R.id.mBack);
        tv_Time = view.findViewById(R.id.tv_Time);
        tv_per_mile = view.findViewById(R.id.tv_per_mile);
        btn_leader = view.findViewById(R.id.btn_leader);
        btn_share = view.findViewById(R.id.btn_share);
        view_top = view.findViewById(R.id.view_top);
        tv_avgspeed = view.findViewById(R.id.tv_avgspeed);
        // = view.findViewById(R.id.tv_Time);


        miles_map = view.findViewById(R.id.miles_map);
        gas_map = view.findViewById(R.id.gas_map);
        co2_map = view.findViewById(R.id.co2_map);
        cal_map = view.findViewById(R.id.cal_map);
        steps_map = view.findViewById(R.id.steps_map);

        setGoogleMap();


        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        baseActivity = (BaseActivity) getActivity();
        ((BaseActivity) getActivity()).setOnBackPressedListener(new BaseBackPressedListener(getActivity()) {
            public void doBack() {
                getActivity().finish();
            }
        });

        btn_leader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*getFragmentManager().beginTransaction()
//                .setCustomAnimations(R.anim.fragment_slide_left_enter,
//                        R.anim.fragment_slide_left_exit,
//                        R.anim.fragment_slide_right_enter,
//                        R.anim.fragment_slide_right_exit)
                        .replace(R.id.mFragmentContainerActivity, new LeaderBoardFragment())
                        .commit();*/

                Fragment fragment = LeaderBoardFragment_.builder().build();
                Bundle bundle = new Bundle();
                bundle.putString("title", "Leaderboard");
                fragment.setArguments(bundle);

                getFragmentManager().beginTransaction()
                        .replace(R.id.mFragmentContainer, fragment).addToBackStack("null")
                        .commit();

            }
        });

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mview) {
                Bitmap bitmap = getBitmapFromView(view);
                saveBitmap(bitmap);
                shareIt();
            }
        });

        textViewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity_map.setVisibility(View.VISIBLE);

            }
        });


        view_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity_map.setVisibility(View.GONE);
            }
        });
        return view;

    }


    void setGoogleMap() {
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

                setPolyline(ActivityTypeFragment.allLatLongList);

                /*String origin = start_latitude + "," + start_longitude;
                String destination = end_latitude + "," + end_longitude;


                *//*String destination = "30.7333" + "," + "76.7794";
                String origin = "30.7046" + "," + "76.7179";*//*


                GetDirection getDirection = new GetDirection(getActivity(), origin, destination);
                getDirection.routeHandler(allPointsListener);
                getDirection.execute();*/
            }
        });
    }

    public Bitmap getBitmapFromView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    private void saveBitmap(Bitmap bitmap) {
        imagePath = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_DCIM) + "/scrnshot.png"); ////File imagePath
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }

    private void shareIt() {
        enrolldate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String user_id=UserPreferences.getUser(getContext()).getId();

        String url_link = "https://www.paidtogo.com/referral-link/app-store/"+user_id+"/"+enrolldate+"?type=andriod";


        //  Uri uri = Uri.fromFile(imagePath);
        Uri uri = FileProvider.getUriForFile(getContext(), getContext().getApplicationContext().getPackageName() + ".provider", imagePath);
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        sharingIntent.setType("text/plain");
        String shareBody = "Paidtogo pays cash for walking and running. Download the free app and try it out!:"+url_link;
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "My Catch score");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        setRetainInstance(true);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            try {
                setImageUrl1(profilepic, UserPreferences.getUser(getContext()).getPhotoUrl());

                if (SettingsPreferences.isMilesKm(getContext())) {

                    String milesValue = bundle.getString("miles", "0");
                    if (milesValue.equals("")) {
                        miles.setText("0.0" + " Kilometers");
                        miles_map.setText("0.0" + " Kilometers");
                    } else {
                        miles.setText(milesValue + " Kilometers");
                        miles_map.setText(milesValue + " Kilometers");
                    }
                    textViewDistanceType.setText(" Kilometers Traveled: ");
                    textViewDistanceType_map.setText(" Kilometers Traveled: ");


                } else {
                    String milesValue = bundle.getString("miles", "0");
                    if (milesValue.equals("")) {
                        miles.setText("0.0" + " miles");
                        miles_map.setText("0.0" + " miles");
                    } else {
                        miles.setText(milesValue + " miles");
                        miles_map.setText(milesValue + " miles");
                    }
                    textViewDistanceType.setText(" Miles Traveled: ");
                    textViewDistanceType_map.setText(" Miles Traveled: ");
                }


                String gasValue = bundle.getString("gas", "0");
                if (gasValue.equals("")) {
                    gas.setText("0.0" + " gal");
                    gas_map.setText("0.0" + " gal");
                } else {
                    gas.setText(gasValue + " gal");
                    gas_map.setText(gasValue + " gal");
                }


                String co2Value = bundle.getString("co2", "0");
                if (co2Value.equals("")) {
                    co2.setText("0.0" + " gal");
                    co2_map.setText("0.0" + " Lbs");
                } else {
                    co2.setText(co2Value+ " Lbs");
                    co2_map.setText(co2Value + " Lbs");
                }


                String calValue = bundle.getString("cal", "0");
                if (calValue.equals("")) {
                    cal.setText("0.0" + " cal");
                    cal_map.setText("0.0" + " cal");
                } else {
                    cal.setText(calValue + " cal");
                    cal_map.setText(calValue + " cal");
                }


                String stepsValue = bundle.getString("steps", "0");
                if (stepsValue.equals("")) {
                    steps.setText("0.0" + " steps");
                    steps_map.setText("0.0" + " steps");
                } else {
                    steps.setText(stepsValue + " steps");
                    steps_map.setText(stepsValue + " steps");
                }





                // miles_map.setText(bundle.getString("miles", "0") + " miles");


                String avrValue = bundle.getString("averageSpeed", "0");
                if(avrValue.equals("")||avrValue.equals("NaN")){
                    tv_avgspeed.setText("0.00" + " mph");
                }else{
                    tv_avgspeed.setText(avrValue + " mph");
                }


                tv_per_mile.setText(bundle.getString("averageTime", "0"));
                ///tv_avgspeed.setText(bundle.getString("averageSpeed", "0") + " mph");


                int sec = Integer.valueOf(bundle.getString("time", "0"));
                Log.e("time+157+++", String.valueOf(sec));
                Date d = new Date(sec * 1000L);
                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss"); // HH for 0-23
                df.setTimeZone(TimeZone.getTimeZone("GMT"));
                String time = df.format(d);
                tv_Time.setText(time);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setImageUrl1(ImageView view, String url) {
        GlideApp.with(view).clear(view);
        GlideApp.with(view)
                .load(url)
                .placeholder(R.drawable.ic_profile_placeholder)
                .centerCrop()
                .apply(RequestOptions.circleCropTransform())
                .into(view);
    }


    void setPolyline(List<LatLng> allLatLongList) {

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
}