package com.aaronevans.paidtogo.modelmocks;

/**
 * Created by leandro on 10/11/17.
 */

public class User {
    String name;
    String surname;
    String email;
    String imageUrl;

    public static User localMock() {
        User mock = new User();
        mock.name = "Jodie";
        mock.surname = "Allen";
        mock.email = "jodie.allen@infinixsoft.com";
        mock.imageUrl = "https://assets.entrepreneur.com/content/3x2/1300/20150406145944-dos-donts-taking-perfect-linkedin-profile-picture-selfie-mobile-camera-2.jpeg";
        return mock;
    }

    public static User mock() {
        User mock = new User();
        mock.name = "Robert";
        mock.surname = "Xiao";
        mock.email = "robxiao87@mockmail.com";
        mock.imageUrl = "http://www.pgconnects.com/london/wp-content/uploads/sites/4/2014/12/speaker_robert-xiao-500x.jpg";
        return mock;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
