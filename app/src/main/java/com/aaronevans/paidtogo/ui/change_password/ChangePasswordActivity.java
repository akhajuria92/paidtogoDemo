package com.aaronevans.paidtogo.ui.change_password;

import android.widget.FrameLayout;
import android.widget.TextView;

import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.ui.BaseActivity;
import com.aaronevans.paidtogo.ui.change_password.fragment.ChangePasswordFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Infinix Android on 19/1/2017.
 */
@EActivity(R.layout.activity_change_password)
public class ChangePasswordActivity extends BaseActivity implements ChangePasswordContract {

    @ViewById
    FrameLayout mFragmentContainer;
    @ViewById
    TextView mTitle;

    @AfterViews
    void fillFragment() {
        mTitle.setText(getString(R.string.change_password));
        getSupportFragmentManager().beginTransaction()
                .add(R.id.mFragmentContainer, ChangePasswordFragment_.builder().build())
                .commit();
    }

    @Click(R.id.mBack)
    void back() {
        onBackPressed();
    }

    @Override
    public void launchActivity(String label) {

    }
}
