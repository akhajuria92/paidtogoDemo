package com.aaronevans.paidtogo.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Farhan Arshad on 5/14/2018.
 */

public class ActivityDetail implements Parcelable {

    public static final Creator<ActivityDetail> CREATOR = new Creator<ActivityDetail>() {
        @Override
        public ActivityDetail createFromParcel(Parcel in) {
            return new ActivityDetail(in);
        }

        @Override
        public ActivityDetail[] newArray(int size) {
            return new ActivityDetail[size];
        }
    };
    @Expose
    @SerializedName("id")
    private String id = null;
    @Expose
    @SerializedName("activitiy_id")
    private String activitiyId = null;
    @Expose
    @SerializedName("steps_count")
    private String stepsCount = "0";
    @Expose
    @SerializedName("start_date_time")
    private String startDateTime = null;
    @Expose
    @SerializedName("end_date_time")
    private String endDateTime = null;
    @Expose
    @SerializedName("earned_reward")
    private String earnedReward = null;
    @Expose
    @SerializedName("reward_type")
    private String rewardType = null;
    @Expose
    @SerializedName("created_at")
    private String createdAt = null;
    @Expose
    @SerializedName("updated_at")
    private String updatedAt = null;

    protected ActivityDetail(Parcel in) {
        id = in.readString();
        activitiyId = in.readString();
        stepsCount = in.readString();
        startDateTime = in.readString();
        endDateTime = in.readString();
        earnedReward = in.readString();
        rewardType = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(activitiyId);
        parcel.writeString(stepsCount);
        parcel.writeString(startDateTime);
        parcel.writeString(endDateTime);
        parcel.writeString(earnedReward);
        parcel.writeString(rewardType);
        parcel.writeString(createdAt);
        parcel.writeString(updatedAt);
    }
}
