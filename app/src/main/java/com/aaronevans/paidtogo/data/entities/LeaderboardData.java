package com.aaronevans.paidtogo.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Infinix Android on 26/1/2017.
 */

public class LeaderboardData implements Parcelable {
    public static final Creator<LeaderboardData> CREATOR = new Creator<LeaderboardData>() {
        @Override
        public LeaderboardData createFromParcel(Parcel in) {
            return new LeaderboardData(in);
        }

        @Override
        public LeaderboardData[] newArray(int size) {
            return new LeaderboardData[size];
        }
    };
    @Expose
    @SerializedName("pool_id")
    private String poolId = null;
    @Expose
    @SerializedName("icon_photo")
    private String iconPhoto = null;
    @Expose
    @SerializedName("icon_photo_description")
    private String iconPhotoDescription = null;
    @Expose
    @SerializedName("name")
    private String name = null;
    @Expose
    @SerializedName("end_date_time")
    private String endDateTime = null;
    @Expose
    @SerializedName("pool_types_id")
    private String poolTypesId = null;
    @Expose
    @SerializedName("pool_types_name")
    private String poolTypesName = null;
    @Expose
    @SerializedName("leaderboard")
    private List<Leaderboard> listLeaderboards = null;
    private String positionInPool = null;

    protected LeaderboardData(Parcel in) {
        poolId = in.readString();
        iconPhoto = in.readString();
        iconPhotoDescription = in.readString();
        name = in.readString();
        endDateTime = in.readString();
        poolTypesId = in.readString();
        poolTypesName = in.readString();
        listLeaderboards = in.createTypedArrayList(Leaderboard.CREATOR);
        positionInPool = in.readString();
    }

    public LeaderboardData() {
        listLeaderboards = new ArrayList<>();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getPositionInPool() {
        return positionInPool;
    }

    public void setPositionInPool(String positionInPool) {
        this.positionInPool = positionInPool;
    }

    public List<Leaderboard> getListLeaderboards() {
        return listLeaderboards;
    }

    public void setListLeaderboards(List<Leaderboard> listLeaderboards) {
        this.listLeaderboards = listLeaderboards;
    }

    public String getPoolTypesName() {
        return poolTypesName;
    }

    public void setPoolTypesName(String poolTypesName) {
        this.poolTypesName = poolTypesName;
    }

    public String getPoolTypesId() {
        return poolTypesId;
    }

    public void setPoolTypesId(String poolTypesId) {
        this.poolTypesId = poolTypesId;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconPhoto() {
        return iconPhoto;
    }

    public void setIconPhoto(String iconPhoto) {
        this.iconPhoto = iconPhoto;
    }

    public String getIconPhotoDescription() {
        return iconPhotoDescription;
    }

    public void setIconPhotoDescription(String iconPhotoDescription) {
        this.iconPhotoDescription = iconPhotoDescription;
    }

    public String getPoolId() {
        return poolId;
    }

    public void setPoolId(String poolId) {
        this.poolId = poolId;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(poolId);
        parcel.writeString(iconPhoto);
        parcel.writeString(iconPhotoDescription);
        parcel.writeString(name);
        parcel.writeString(endDateTime);
        parcel.writeString(poolTypesId);
        parcel.writeString(poolTypesName);
        parcel.writeTypedList(listLeaderboards);
        parcel.writeString(positionInPool);
    }
}
