package com.aaronevans.paidtogo.ui.inviteorg;

import android.annotation.SuppressLint;
import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.databinding.ActivityInviteorgBinding;
import com.aaronevans.paidtogo.ui.BaseActivity;
import com.aaronevans.paidtogo.ui.inviteorg.organizations.OrganizationsFragment_;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.BindingObject;
import org.androidannotations.annotations.DataBound;
import org.androidannotations.annotations.EActivity;

/**
 * Created by leandro on 24/11/17.
 */

@DataBound
@SuppressLint("Registered")
@EActivity(R.layout.activity_inviteorg)
public class InviteOrganizationActivity extends BaseActivity {

    @BindingObject ActivityInviteorgBinding mBinding;

    @AfterViews public void setup() {
        setSupportActionBar(mBinding.mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.mFragmentContainer,OrganizationsFragment_.builder().build())
                .commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}