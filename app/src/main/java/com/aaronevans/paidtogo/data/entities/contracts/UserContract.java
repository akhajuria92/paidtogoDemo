package com.aaronevans.paidtogo.data.entities.contracts;

import com.aaronevans.paidtogo.data.entities.User;

/**
 * Created by evaristo on 20/12/16.
 */

public interface UserContract {

    User setId(String id);

    User setAccessToken(String AccessToken);

    User setPhoto(String photoSend);

    User setEmail(String email);

    User setFirstName(String firstName);

    User setLastName(String lastName);

    User setBio(String bio);

    User setPassword(String password);

    String getId();

    String getAccessToken();

    String getPhotoUrl();

    String getEmail();

    String getFirstName();

    String getLastName();

    String getBio();

    String getFullName();
}
