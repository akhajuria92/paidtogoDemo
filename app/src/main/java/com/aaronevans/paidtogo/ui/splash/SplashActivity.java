package com.aaronevans.paidtogo.ui.splash;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.local.UserPreferences;
import com.aaronevans.paidtogo.ui.BaseActivity;
import com.aaronevans.paidtogo.ui.main.MainActivity_;
import com.aaronevans.paidtogo.ui.start.LoginActivity_;
import com.jaredrummler.android.device.DeviceName;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by evaristo on 06/12/16.
 */

@EActivity(R.layout.activity_splash)
public class SplashActivity extends BaseActivity {

    public static int TIME_SPLASH = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        keyHash();
    }

    private void keyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("TAG", "printHashKey() Hash Key: " + hashKey);
                Log.e("hsh",hashKey);
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }


    @AfterViews
    void start() {
        new Handler().postDelayed(this::changeActivity, TIME_SPLASH);
    }

    void changeActivity() {
        if (UserPreferences.getUser(this) == null)
            LoginActivity_.intent(this)
                    .start()
                    .withAnimation(android.R.anim.fade_in, android.R.anim.fade_out);
        else
            MainActivity_.intent(this)
                    .start()
                    .withAnimation(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}
