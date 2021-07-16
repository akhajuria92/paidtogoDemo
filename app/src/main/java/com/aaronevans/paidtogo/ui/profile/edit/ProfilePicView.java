package com.aaronevans.paidtogo.ui.profile.edit;

import com.aaronevans.paidtogo.ui.profile.ProfilePicmodel;

public interface ProfilePicView {

    void onSucesss(ProfilePicmodel profilePicmodel);
    void onerror(String error);
    void  onInternet(String tag);
}

