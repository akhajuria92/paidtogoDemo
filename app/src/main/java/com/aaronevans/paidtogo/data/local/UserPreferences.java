package com.aaronevans.paidtogo.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.aaronevans.paidtogo.PaidToGoApp;
import com.aaronevans.paidtogo.data.entities.User;
import com.aaronevans.paidtogo.data.entities.UserEarning;
import com.aaronevans.paidtogo.data.entities.contracts.UserContract;

import java.util.Calendar;

/**
 * Created by evaristo on 20/12/16.
 */

public class UserPreferences {

    private final static String PREFERENCES = "PaidToGo";
    private final static String USER = "User";
    private final static String TOKEN = "Token";
    private final static String DOLLAR = "Dollar";
    private final static String profil_pic = "pic";
    private final static String POINT = "Point";
    private final static String SOCIAL_TOKEN = "social_token";
    private final static String FCM_TOKEN = "fcm_token";
    private final static String ACTIVITY_SYNC_TIME = "activity_sync_time";
    private final static String KEY_TOTAL_DISTANCE = "TotalDistance";
    private final static String KEY_TOTAL_DISTANCE_ON_BIKE = "TotalDistanceOnBike";
    private final static String KEY_TOTAL_STEPS = "TotalSteps";
    private final static String CALORIES = "Calories";
    private final static String GAS = "Gas";
    private final static String CO2 = "CO2";
    private final static String START_TIME = "StartTime";
    private final static String START_TIME_ON_FEET = "StartTimeOnFeet";
    private final static String START_LNG_ON_FEET = "StartLngOnFeet";
    private final static String START_LAT_ON_FEET = "StartLatOnFeet";
    private final static String COINS_VALUE_POINT = "CoinsValuePoint";
    private final static String USD_PRICE_PAID = "UsdPricePaid";
    private final static String USD_PRICE_FREE= "UsdPriceFree";
    private final static String OPEN_APP_COUNT= "OpenAppCount";






    public static void setFcmToken(String token, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(FCM_TOKEN, token);
        editor.apply();
    }

    /**
     * Returns the total distance.
     */

    public static String getFcmToken(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return preferences.getString(FCM_TOKEN,null);
    }

    public static String getMainPoints(Context context) {
        return MAIN_POINTS;
    }

    public static void saveMainPoints(Context context,String main_points)
    {
            SharedPreferences preferences= context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
            preferences.edit().putString(MAIN_POINTS,main_points).apply();
    }

    //  private final static Intent AUTOTRACKINTENT;\
    private  final  static  String MAIN_POINTS= "MainPoints";

    public static void saveSocialToken(Context context, String token) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        preferences.edit().putString(SOCIAL_TOKEN, token).apply();
    }



    public static void saveCoinsValuePoints(Context context, String coinsValue) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        preferences.edit().putString(COINS_VALUE_POINT, coinsValue).apply();
    }


    public static String getCoinsValue(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return preferences.getString(COINS_VALUE_POINT, null);
    }


    public static void saveOpenAppCount(Context context, int coinsValue) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        preferences.edit().putInt(OPEN_APP_COUNT, coinsValue).apply();
    }


    public static int getOpenAppCount(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return preferences.getInt(OPEN_APP_COUNT, 0);
    }


    public static void saveUSDPricePaid(Context context, String usdPricePaid) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        preferences.edit().putString(USD_PRICE_PAID, usdPricePaid).apply();
    }


    public static String getUSDPricePaid(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return preferences.getString(USD_PRICE_PAID, null);
    }



    public static void saveUSDPriceFree(Context context, String usdPricePaid) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        preferences.edit().putString(USD_PRICE_FREE, usdPricePaid).apply();
    }


    public static String getUSDPriceFree(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return preferences.getString(USD_PRICE_FREE, null);
    }






    public static String getSocialToken(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return preferences.getString(SOCIAL_TOKEN, null);
    }

    public static User getUser(Context context, Class clazz) {
        try {
            SharedPreferences preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
            String user = preferences.getString(USER, "");
            return (User) new Gson().fromJson(user, clazz);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return  null;
        }
    }

    public static void saveUser(Context context, UserContract user) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        preferences.edit()
                .putString(USER, new Gson().toJson(user))
                .putString(TOKEN, user.getAccessToken())
                .apply();
    }

    public static void saveEarning(Context context, UserEarning userEarning) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        preferences.edit()
                .putString(DOLLAR , userEarning.getDollar())
                .putString(POINT , userEarning.getPoint())
                .apply();
    }

    public static UserEarning getEarning(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        UserEarning userEarning= new UserEarning();
        if(!preferences.contains(DOLLAR)){
            preferences.edit()
                    .putString(DOLLAR , "0.0")
                    .putString(POINT , "0")
                    .apply();
        }
        userEarning.setDollar(preferences.getString(DOLLAR,""));
        userEarning.setPoint(preferences.getString(POINT,""));
        return userEarning;
    }

    public static String getToken(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return preferences.getString(TOKEN, "");
    }

    public static String getprofile(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return preferences.getString(TOKEN, "");
    }

    public static void saveprofile(String distance, Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(profil_pic, distance);
        editor.apply();
    }



    public static User getUser(Context context) {
        return getUser(context, User.class);
    }

    public static void removeUser(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        preferences.edit()
                .remove(USER)
                .remove(TOKEN)
                .apply();
    }

    public static void saveDataLocation(Context context, ListLocation locations) {
        Gson gson = new Gson();
        String json = gson.toJson(locations);
        context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
                .edit().putString(PaidToGoApp.LIST_LOCATION, json).apply();
    }

    public static ListLocation getDataLocation(Context context) {
        Gson gson = new Gson();
        String json = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE).getString(PaidToGoApp.LIST_LOCATION, "");
        return gson.fromJson(json, ListLocation.class);
    }


    public static void saveSynyTime(Context context, long time) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        preferences.edit().putLong(ACTIVITY_SYNC_TIME, time).apply();
    }

    public static long getSyncTime(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        long syncTime = preferences.getLong(ACTIVITY_SYNC_TIME, 0);
        if (syncTime == 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.HOUR_OF_DAY, -24);
            syncTime = calendar.getTimeInMillis();
        }
        return syncTime;
    }


    /**
     * Saves the total distance.
     */
    public static void saveTotalDistance(String distance, Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_TOTAL_DISTANCE, distance);
        editor.apply();
    }

    /**
     * Returns the total distance.
     */
    public static float getTotalDistance(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return settings.getFloat(KEY_TOTAL_DISTANCE, 0);
    }

    public static void saveTotalSteps(float steps, Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(KEY_TOTAL_STEPS, steps);
        editor.apply();
    }

    public static float getTotalSteps(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return settings.getFloat(KEY_TOTAL_STEPS, 0);
    }




    public static void saveCalories(String calories, Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(CALORIES,calories );
        editor.apply();
    }

    public static String getCalories(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return settings.getString(CALORIES, "0.0");
    }


    public static void saveGas(String gas, Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(GAS,gas );
        editor.apply();
    }

    public static String getGas(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return settings.getString(GAS, "0.0");
    }


    public static void saveCO2(String c02, Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(CO2,c02 );
        editor.apply();
    }

    public static String getCO2(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return settings.getString(CO2, "0.0");
    }


    public static void saveAutoTrackStartTime(String time, Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(START_TIME, time);
        editor.apply();
    }

    public static String getAutoTrackEndTime(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return settings.getString(START_TIME, "");
    }

    /**
     * Saves the total distance.
     */
    public static void saveTotalDistanceOnBike(float distance, Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(KEY_TOTAL_DISTANCE_ON_BIKE, distance);
        editor.apply();
    }

    /**
     * Returns the total distance.
     */
    public static float getTotalDistanceOnBike(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return settings.getFloat(KEY_TOTAL_DISTANCE_ON_BIKE, 0);
    }

    public static void saveStartLatOnFeet(String distance, Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(START_LAT_ON_FEET, distance);
        editor.apply();
    }

    /**
     * Returns the total distance.
     */
    public static String getStartLatOnFeet(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return settings.getString(START_LAT_ON_FEET, "");
    }

    public static void saveStartLngOnFeet(String distance, Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(START_LNG_ON_FEET, distance);
        editor.apply();
    }

    /**
     * Returns the total distance.
     */

    public static String getStartLngOnFeet(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return settings.getString(START_LNG_ON_FEET, "");
    }

    public static void saveStartTimeOnFeet(String distance, Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(START_TIME_ON_FEET, distance);
        editor.apply();
    }

    public static String getStartTimeOnFeet(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return settings.getString(START_TIME_ON_FEET, "");
    }

}