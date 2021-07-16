package com.aaronevans.paidtogo.ui.map;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.components.LocationAlertIntentService;
import com.aaronevans.paidtogo.data.entities.GymCheckIns;
import com.aaronevans.paidtogo.data.mHandler;
import com.aaronevans.paidtogo.ui.BaseActivity;

public class MapActivity extends BaseActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private static final String TAG = "Tag";
    private static final int REQUEST_CHECK_SETTINGS = 1;
    private GoogleMap mMap;
    TextView title, distance, count_checkin, btn_checkin;
    ImageView image;
    ConstraintLayout constraintLayout;
    private GeofencingClient mGeofencingClient;

    Geofence geofencingRequest;
    Location mLocation;
    private static final int LOC_PERM_REQ_CODE = 1;
    //meters
    private static final int GEOFENCE_RADIUS = 100;
    //in milli seconds
    private static final int GEOFENCE_EXPIRATION = 6000;
    LocationManager mLocationManager;

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            com.aaronevans.paidtogo.data.mHandler.user_lat = location.getLatitude();
            com.aaronevans.paidtogo.data.mHandler.user_lng = location.getLongitude();
           /* mLocation.setLatitude(location.getLatitude());
            mLocation.setLongitude(location.getLongitude());*/
            mLocation= new Location(location);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));
            onMapReady(mMap);
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

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        title = findViewById(R.id.gym_name);
        distance = findViewById(R.id.gym_distance);
        image = findViewById(R.id.mLogoImage);
        count_checkin = findViewById(R.id.checkin_count);
        constraintLayout = findViewById(R.id.marker_click);
        btn_checkin = findViewById(R.id.btn_checkin);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(MapActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                    2);
            return;
        }else{
            mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            boolean gps_enabled = false;

            try {
                gps_enabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            } catch (Exception ex) {
            }
            if(!gps_enabled){
                displayLocationSettingsRequest(getApplicationContext());
            }
            else{
                showmap();
            }
        }

        Toolbar toolbar = findViewById(R.id.mToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void showmap(){
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mGeofencingClient = LocationServices.getGeofencingClient(this);


            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 20000,
                    10, mLocationListener);
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 20000,
                    10, mLocationListener);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        }



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.





    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    /*    ArrayList<GymCheckIns> checkIns = new ArrayList<>();
        checkIns.add(new GymCheckIns(33.6845992, 72.9860723, "bhurka", 1, ""));
        checkIns.add(new GymCheckIns(33.6847688, 72.9889154, "asdd", 2, ""));
        checkIns.add(new GymCheckIns(33.6831038, 72.9885077, "dfdf", 3, ""));
        checkIns.add(new GymCheckIns(33.6812535, 72.990168, "sdfsf", 4, ""));
        checkIns.add(new GymCheckIns(33.6825034, 72.9861099, "vdfv", 5, ""));
        checkIns.add(new GymCheckIns(33.6777537, 73.0023077, "dfvdfv", 6, ""));

        addLocationAlert(33.6845992, 72.9860723,"200");
        addLocationAlert(33.6847688, 72.9889154,"200");
        addLocationAlert(33.6831038, 72.9885077,"200");
        addLocationAlert(33.6812535, 72.990168,"200");
        addLocationAlert(33.6825034, 72.9861099,"200");
        addLocationAlert(33.6777537, 73.0023077,"200");*/

//        if(com.infinixsoft.paidtogo.data.mHandler.reg_pools!=null) {
//            for (int i = 0; i < com.infinixsoft.paidtogo.data.mHandler.reg_pools.size(); i++) {
//                for (int j = 0; j < com.infinixsoft.paidtogo.data.mHandler.reg_pools.get(i).getGymLocations().size(); j++) {
//                    LatLng sydney = new LatLng(mHandler.reg_pools.get(i).getGymLocations().get(j).getLattitude(), mHandler.reg_pools.get(i).getGymLocations().get(j).getLongitude());
//                    mMap.addMarker(new MarkerOptions().position(sydney).title(mHandler.reg_pools.get(i).getGymLocations().get(j).getName()));
//                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12));
//                }
//
//            }
//        }
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                constraintLayout.setVisibility(View.GONE);
            }
        });
        Bitmap bitmap = createUserBitmap("");

        if(com.aaronevans.paidtogo.data.mHandler.reg_pools!=null) {
            for (int i = 0; i < com.aaronevans.paidtogo.data.mHandler.reg_pools.size(); i++) {
                for (int j = 0; j < com.aaronevans.paidtogo.data.mHandler.reg_pools.get(i).getGymLocations().size(); j++) {
                    Marker marker = mMap.addMarker(new MarkerOptions().anchor(0.25f, 0.445f)//0.5f, 0.907f
                            .icon(BitmapDescriptorFactory.fromBitmap(bitmap)).position(new LatLng(mHandler.reg_pools.get(i).getGymLocations().get(j).getLattitude(), mHandler.reg_pools.get(i).getGymLocations().get(j).getLongitude())));
                    marker.setTag(com.aaronevans.paidtogo.data.mHandler.reg_pools.get(i).getGymLocations().get(j));

                    addLocationAlert(com.aaronevans.paidtogo.data.mHandler.reg_pools.get(i).getGymLocations().get(j).getLattitude(), com.aaronevans.paidtogo.data.mHandler.reg_pools.get(i).getGymLocations().get(j).getLongitude(),com.aaronevans.paidtogo.data.mHandler.reg_pools.get(i).getPoolRadius());

                }

            }
        }
       /* mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Location loctemp=mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);*/
if(mLocation!=null) {
    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLocation.getLatitude(), mLocation.getLongitude()), 15));

}        mMap.setMyLocationEnabled(true);

      /*  for (int j = 0; j < checkIns.size(); j++) {

            mMap.setOnMarkerClickListener(this);
            Marker marker = mMap.addMarker(new MarkerOptions().anchor(0.25f, 0.445f)//0.5f, 0.907f
                    .icon(BitmapDescriptorFactory.fromBitmap(bitmap)).position(new LatLng(checkIns.get(j).getLattitude(), checkIns.get(j).getLongitude())));
            marker.setTag(checkIns.get(j));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(checkIns.get(j).getLattitude(), checkIns.get(j).getLongitude()), 12));
        }*/

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        GymCheckIns g = (GymCheckIns) marker.getTag();
        title.setText(g.getName());
        Location startPoint = new Location("locationA");
        startPoint.setLatitude(com.aaronevans.paidtogo.data.mHandler.user_lat);
        startPoint.setLongitude(com.aaronevans.paidtogo.data.mHandler.user_lng);

        Location endPoint = new Location("locationA");
        endPoint.setLatitude(g.getLattitude());
        endPoint.setLongitude(g.getLongitude());

        double distanceinm = startPoint.distanceTo(endPoint);
        //distance.setText(String.valueOf(distanceinm));
        btn_checkin.setOnClickListener(null);
        btn_checkin.setBackgroundResource(R.color.gray_progress);
        if (distanceinm < 20) {
            btn_checkin.setBackgroundResource(R.color.green_balance);
           btn_checkin.setBackgroundColor(getResources().getColor(R.color.green_balance));
            btn_checkin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

        // Object someObj = marker.getTag();
        constraintLayout.setVisibility(View.VISIBLE);


      /*  Stack<GymCheckIns> myStack = new Stack<GymCheckIns>();
        myStack.push(someObj);
        someObj = myStack.pop();*/


        return false;
    }

    private Geofence getGeofence(double lat, double lang, String key, String radius) {
        return new Geofence.Builder()
                .setRequestId(key)
                .setCircularRegion(lat, lang, Float.parseFloat(radius))
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                        Geofence.GEOFENCE_TRANSITION_DWELL)
                .setLoiteringDelay(10000)
                .build();
    }


    private PendingIntent getGeofencePendingIntent() {
        Intent intent = new Intent(MapActivity.this, LocationAlertIntentService.class);
        return PendingIntent.getService(getApplicationContext(), 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private GeofencingRequest getGeofencingRequest(Geofence geofence) {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();

        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_DWELL);
        builder.addGeofence(geofence);
        return builder.build();
    }

    private void addLocationAlert(double lat, double lng, String radius) {

        String key = "" + lat + "-" + lng;
        Geofence geofence = getGeofence(lat, lng, key,radius);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mGeofencingClient.addGeofences(getGeofencingRequest(geofence),
                getGeofencePendingIntent())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                          /*  Toast.makeText(MapActivity.this,
                                    "Location alter has been added",
                                    Toast.LENGTH_SHORT).show();*/
                        } else {
                           /* Toast.makeText(MapActivity.this,
                                    "Please Turn On Your Location and Wifi!",
                                    Toast.LENGTH_SHORT).show();*/
                        }
                    }
                });


    }
    private void removeLocationAlert() {

            mGeofencingClient.removeGeofences(getGeofencePendingIntent())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                               /* Toast.makeText(getActivity(),
                                        "Location alters have been removed",
                                        Toast.LENGTH_SHORT).show();*/
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Location alters could not be removed",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(mGeofencingClient!=null) {
            removeLocationAlert();
        }
    }

    private Bitmap createUserBitmap(String icontype) {
        Bitmap result = null;
        try {
            result = Bitmap.createBitmap(dp(42), dp(56), Bitmap.Config.ARGB_8888);//42, 56
            result.eraseColor(Color.TRANSPARENT);
            Canvas canvas = new Canvas(result);
            Drawable drawable;

                drawable = getResources().getDrawable(R.drawable.marker_pin);


            drawable.setBounds(0, 0, dp(24), dp(36)); //22,36
            drawable.draw(canvas);

            Paint roundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            RectF bitmapRect = new RectF();
            canvas.save();

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ph_user);
            // URL url = new URL("https://cdt.org/files/2015/10/2015-10-06-FB-person.png");
            //   Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());

            //  Bitmap bitmap = BitmapFactory.decodeFile(url.toString());// generate bitmap here if your image comes from any url
            if (bitmap != null) {
                BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                Matrix matrix = new Matrix();
                float scale = dp(16) / (float) bitmap.getWidth(); // 52
                matrix.postTranslate(dp(2), dp(2));//5,5
                matrix.postScale(scale, scale);
                roundPaint.setShader(shader);
                shader.setLocalMatrix(matrix);
                bitmapRect.set(dp(4), dp(4), dp(16 + 4), dp(16 + 4));//32,32
                canvas.drawRoundRect(bitmapRect, dp(28), dp(28), roundPaint);
            }
            canvas.restore();
            try {
                canvas.setBitmap(null);
            } catch (Exception e) {
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return result;
    }

    public int dp(float value) {
        if (value == 0) {
            return 0;
        }
        return (int) Math.ceil(getResources().getDisplayMetrics().density * value);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.e("DialogPermission","Ho! Ho! Ho!");  // Log not printed
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==2){
            boolean gps_enabled = false;

            try {
                gps_enabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            } catch (Exception ex) {
            }
            if(!gps_enabled){
                displayLocationSettingsRequest(getApplicationContext());
            }
            else{
                showmap();

            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
           // showmap();
            if(resultCode==1){
                if(mLocationManager!=null) {
                    showmap();

                }
            }
        }

    }

    private void displayLocationSettingsRequest(Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i(TAG, "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(MapActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }

}
