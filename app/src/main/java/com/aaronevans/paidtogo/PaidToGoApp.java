package com.aaronevans.paidtogo;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.multidex.MultiDex;

/*import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;*/

import com.aaronevans.paidtogo.data.local.UserPreferences;
import com.aaronevans.paidtogo.ui.main.MainActivity;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by leandro on 2/11/17.
 */

public class PaidToGoApp extends Application {
    public static final String LIST_LOCATION = "list_location";
    public static final String ADS_APP_ID = "appeabfb6fc9c9f49b597";
    public static final String ADS_ZONE_ID = "vz0af1d8c6daac43a9bc";
    public static final String REWARDS_ADS_ZONE_ID = "vz4ea796d2e61947c296";


    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(getResources().getString(R.string.default_font))
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
      /*  FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);*/
//        JodaTimeAndroid.init(this);
        MultiDex.install(this);
//        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        int appOpenCount=UserPreferences.getOpenAppCount(this);
        UserPreferences.saveOpenAppCount(this, appOpenCount+1);

    }

    public static boolean isInternetConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
