package com.aaronevans.paidtogo.ui.profile.components;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.aaronevans.paidtogo.ui.profile.edit.EditProfileFragment_;
import com.aaronevans.paidtogo.ui.profile.organizations.OrganizationsFragment_;

/**
 * Created by leandro on 23/11/17.
 */

public class TwoFragmentsAdapter extends FragmentPagerAdapter {

    private Fragment f1, f2;

    public TwoFragmentsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            if (f1 == null) {
                f1 = EditProfileFragment_.builder().build();
            }
            return f1;
        } else {
            if (f2 == null) {
                f2 = OrganizationsFragment_.builder().build();
            }
            return f2;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return position == 0 ? "ACCOUNT" : "MY Pools";
    }
}
