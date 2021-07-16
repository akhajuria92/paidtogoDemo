package com.aaronevans.paidtogo.ui.start;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import android.widget.FrameLayout;

/*import com.facebook.FacebookSdk;*/
import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.ui.BaseActivity;
import com.aaronevans.paidtogo.ui.how_works.HowItWorks;
import com.aaronevans.paidtogo.ui.main.MainActivity;
import com.aaronevans.paidtogo.ui.main.MainActivity_;
import com.aaronevans.paidtogo.ui.start.login.LoginFragment_;
import com.aaronevans.paidtogo.ui.start.recover.RecoverFragment_;
import com.aaronevans.paidtogo.ui.start.signup.SignUpFragment_;
import com.aaronevans.paidtogo.ui.start.terms.TermsAndConditionsFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Farhan Arshad on 07/12/16.
 */

@EActivity(R.layout.activity_login)
public class LoginActivity extends BaseActivity implements LoginContract {

    public static final String LOGIN = "login";
    public static final String REGISTER = "register";
    public static final String RECOVER_PASSWORD = "recover_password";
    public static final String TERMS_AND_CONDITIONS = "terms_and_conditions";

    @ViewById
    FrameLayout mFragmentContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FacebookSdk.sdkInitialize(this);
        setStatusBarColor(getResources().getColor(R.color.color_blue));
    }

    //  If you do not want to do initialization before views are created, you can safely remove your onCreate() method and do everything in an @AfterViews annotated method.
    @AfterViews
    void fillFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.mFragmentContainer, LoginFragment_.builder().build())
                .commit();
    }


    @Override
    public void launchActivity(String label) {
        if (label.equals(MainActivity.LABEL)) {

            Intent intent=new Intent(this, HowItWorks.class);
            intent.putExtra("from","signup");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
           /* MainActivity_.intent(this)
                    .flags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                    .start()
                    .withAnimation(android.R.anim.fade_in, android.R.anim.fade_out);*/
        }
    }

    @Override
    public void loadFragment(String label) {
        switch (label) {
            case REGISTER:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mFragmentContainer, SignUpFragment_.builder().build())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
                break;
            case RECOVER_PASSWORD:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mFragmentContainer, RecoverFragment_.builder().build())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
                break;
            case TERMS_AND_CONDITIONS:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mFragmentContainer, TermsAndConditionsFragment_.builder().build())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }
}
