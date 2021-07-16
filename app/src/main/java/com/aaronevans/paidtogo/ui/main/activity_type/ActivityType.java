package com.aaronevans.paidtogo.ui.main.activity_type;

import android.view.View;

import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.ui.BaseActivity;
import com.aaronevans.paidtogo.ui.main.activity_type.components.ActivityTypeFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import static com.aaronevans.paidtogo.ui.main.MainActivity.toolbar_image;
import static com.aaronevans.paidtogo.ui.main.MainActivity.toolbar_title;

@EActivity(R.layout.activity_type)
public class ActivityType extends BaseActivity {

    @AfterViews
    public void init() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.mFragmentContainer, ActivityTypeFragment_.builder().build())
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        toolbar_image.setVisibility(View.VISIBLE);
        toolbar_title.setVisibility(View.GONE);
    }
}
