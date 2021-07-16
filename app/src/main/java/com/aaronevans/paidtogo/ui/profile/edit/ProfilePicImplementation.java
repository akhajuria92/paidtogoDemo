package com.aaronevans.paidtogo.ui.profile.edit;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.aaronevans.paidtogo.components.RestApiClientAuth;
import com.aaronevans.paidtogo.data.remote.PaidToGoService;
import com.aaronevans.paidtogo.ui.profile.ProfilePicmodel;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePicImplementation implements profilePicPrsentor {
    Activity activity;
    ProfilePicView homeFragmentView;
    File file;
    MultipartBody.Part part1;
    RequestBody token_id;


    public ProfilePicImplementation(Context profileActivity, Context profileActivity1) {
        this.activity = (Activity) profileActivity;
        this.homeFragmentView = (ProfilePicView) profileActivity1;

    }

    @Override
    public void addPic(String toekn, String image) {



        file = new File(image);
        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
        part1 = MultipartBody.Part.createFormData("profile_picture", file.getName(), fileReqBody);
        token_id = RequestBody.create(MediaType.parse("text/plain"), toekn);


        PaidToGoService.ApiClientInterface apiInterface = RestApiClientAuth.Retrofit().create(PaidToGoService.ApiClientInterface.class);
        Call<ProfilePicmodel> call = apiInterface.addpic(token_id,part1);
        call.enqueue(new Callback<ProfilePicmodel>() {
            @Override
            public void onResponse(Call<ProfilePicmodel> call, Response<ProfilePicmodel> response) {

                if (response.isSuccessful()) {

                    ProfilePicmodel signupResponseModel=response.body();

                } else {
                    homeFragmentView.onerror("");
                }
            }

            @Override
            public void onFailure(Call<ProfilePicmodel> call, Throwable t) {
                Log.e("error", String.valueOf(t));
                homeFragmentView.onerror("");
            }
        });

    }

    }
