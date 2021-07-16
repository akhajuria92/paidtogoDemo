package com.aaronevans.paidtogo.ui.Utils;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.aaronevans.paidtogo.R;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetDirection extends AsyncTask<String, Void, String> {
    AllPointsListener listener;
    private String key;
    private String response;
    private int deliveryCount;
    String origin;
    String destination;
    //via:9.9280694,-84.09072459999999|via:30.901922413482314,75.84238417554934|via:51.17889991879489,-1.8263999372720716

    Activity activity;

    public GetDirection(Activity activity, String origin, String destination) {
        key = "AIzaSyB4hsjRGf_GVW0Bim6yUKmWlPpYBVqa1KM";
        this.origin = origin;
        this.destination = destination;
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... urls) {
        try {
            URL url = new URL("https://maps.googleapis.com/maps/api/directions/json?"
                    + "origin=" + origin
                    + "&destination=" + destination
                    + "&mode=" + "walking"
                    + "&key=" + key);

            HttpURLConnection urlConnection = (HttpURLConnection) ((url.openConnection()));
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            Log.e("Url is", url.toString());
            int responseCode = urlConnection.getResponseCode();
            Log.e("respose code is", urlConnection.getResponseCode() + "");
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedInputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                response = convertInputStreamToString(inputStream);
            } else {
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    protected void onPostExecute(String result) {
        try {
            JSONObject mainObject = new JSONObject(result);
            JSONArray routesArray = mainObject.getJSONArray("routes");
            JSONObject innerObject = routesArray.getJSONObject(0);
            JSONObject polyLineObject = innerObject.getJSONObject("overview_polyline");
            JSONArray legs = innerObject.getJSONArray("legs");
            String distance=legs.getJSONObject(0).getJSONObject("distance").getString("text");
            String time=legs.getJSONObject(0).getJSONObject("duration").getString("text");
            String encodedPolyLine = polyLineObject.getString("points");
            List<LatLng> allPoints = decodePoly(encodedPolyLine);
            listener.onSuccessfullyGetRoute(allPoints,distance,time);
          /*  polylineOptions.addAll(allPoints);
            googleMap.addPolyline(polylineOptions);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String convertInputStreamToString(InputStream in) {
        String result = "";
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in));

            StringBuilder str = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                str.append(line + "\n");
            }
            in.close();
            result = str.toString();
        } catch (Exception ex) {
            result = "Error";
        }
        return result;
    }


    public  List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }
        return poly;
    }


    public interface AllPointsListener {
        void onSuccessfullyGetRoute(List<LatLng> allPoints, String distance, String time);
    }

    public void routeHandler(AllPointsListener listener) {
        this.listener = listener;
    }
}