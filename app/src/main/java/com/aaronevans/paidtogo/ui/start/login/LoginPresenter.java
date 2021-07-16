package com.aaronevans.paidtogo.ui.start.login;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;

/*import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;*/
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.entities.EmailAuth;
import com.aaronevans.paidtogo.data.entities.User;
import com.aaronevans.paidtogo.data.local.UserPreferences;
import com.aaronevans.paidtogo.data.remote.PaidToGoService;
import com.aaronevans.paidtogo.data.remote.request.FacebookBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;

/**
 * Created by evaristo on 12/12/16.
 */

public class LoginPresenter implements LoginContract.Presenter {

    LoginContract.View mView;
    String email ;
    String firstName;
    String lastName ;

    String user_id ;
    String pic ;

    private CallbackManager mCallbackManager;
    private String mFacebookToken;

    @Override
    public void login(String email, String password) {
        UserPreferences.saveSocialToken(mView.getContext(), "");


        if (mView.showMissingEmail(email.isEmpty()) || mView.showMissingPassword(password.isEmpty()))
            return;
        mView.showProgressDialog();
        PaidToGoService.getServiceClient().login(new EmailAuth(email, password))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::loadNestedUser,
                        this::onError
                );
    }

    public void loadNestedUser(User userResponse) {

        mView.hideProgressDialog();
        UserPreferences.saveUser(mView.getContext(), userResponse);
        mView.launchMainActivity();
    }

    public void loadNestedUserFB(User userResponse) {
       /* User u = new User();
        u.setEmail(userResponse.getEmail());
        u.setFirstName(userResponse.getFirstName());
        u.setLastName(userResponse.getLastName());
        u.setAccessToken(userResponse.getAccessToken());
        u.setId(userResponse.getUserId());*/
        mView.hideProgressDialog();
        UserPreferences.saveUser(mView.getContext(), userResponse);
        mView.launchMainActivity();
    }

    public void onError(Throwable throwable) {
        mView.hideProgressDialog();
        if (throwable instanceof HttpException) {
            mView.showErrorAlert(PaidToGoService.attendError((HttpException) throwable).getDetail());
        } else {
            mView.showErrorAlert(mView.getContext().getString(R.string.connection_problem));
        }
    }

    @Override
    public void facebookLogin(Fragment fragment) {
        mView.showProgressDialog();
        if (AccessToken.getCurrentAccessToken() == null) {
            LoginManager.getInstance().logInWithReadPermissions(fragment, Arrays.asList("public_profile", "email"));
        } else {
            setFacebookData(AccessToken.getCurrentAccessToken());
        }
    }

    private void facebookRetrieveUser() {
        FacebookBody facebookBody = new FacebookBody(email,firstName,lastName,user_id,pic);
        UserPreferences.saveSocialToken(mView.getContext(), AccessToken.getCurrentAccessToken().getToken());
        PaidToGoService.getServiceClient().registerWithFb(facebookBody)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::loadNestedUserFB,
                        this::onError
                );
    }

    @Override
    public void attendResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public LoginContract.Presenter start(LoginContract.View view) {
        mView = view;
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance()
                .registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        setFacebookData(loginResult.getAccessToken());

                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException error) {
                        LoginPresenter.this.onError(error);
                    }
                });
        return this;
    }
    private void setFacebookData(final AccessToken accessToken)
    {
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Application code
                        Log.i("Response",response.toString());

                        try {
                            email = response.getJSONObject().getString("email");
                            firstName = response.getJSONObject().getString("first_name");
                            lastName = response.getJSONObject().getString("last_name");
                            // gender = response.getJSONObject().getString("gender");
                            user_id = response.getJSONObject().getString("id");
                            pic = "https://graph.facebook.com/"+ user_id +"/picture?type=large";

                            if (email==null)
                                email ="";
                            if (firstName==null)
                                firstName ="";
                            if (lastName==null)
                                lastName ="";
                            if (pic==null)
                                pic ="";


                            //    pic = link;.

                            Log.i("Login" + "Email", email);
                            Log.i("Login"+ "FirstName", firstName);
                            Log.i("Login" + "LastName", lastName);
                            //   Log.i("Login" + "Gender", gender);
                            facebookRetrieveUser();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email,first_name,last_name,gender");
        request.setParameters(parameters);
        request.executeAsync();

    }
    @Override
    public void destroy() {

    }
}
