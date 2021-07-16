package com.aaronevans.paidtogo.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Activity_Home implements Parcelable {

    double walk_miles, bike_miles, gym_checkins;

    public Activity_Home(double walk_miles, double bike_miles, double gym_checkins) {
        this.walk_miles = walk_miles;
        this.bike_miles = bike_miles;
        this.gym_checkins = gym_checkins;
    }

    protected Activity_Home(Parcel in) {
        walk_miles = in.readDouble();
        bike_miles = in.readDouble();
        gym_checkins = in.readDouble();
    }

    public static final Creator<Activity_Home> CREATOR = new Creator<Activity_Home>() {
        @Override
        public Activity_Home createFromParcel(Parcel in) {
            return new Activity_Home(in);
        }

        @Override
        public Activity_Home[] newArray(int size) {
            return new Activity_Home[size];
        }
    };

    public double getWalk_miles() {
        return walk_miles;
    }

    public double getBike_miles() {
        return bike_miles;
    }

    public double getGym_checkins() {
        return gym_checkins;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(walk_miles);
        parcel.writeDouble(bike_miles);
        parcel.writeDouble(gym_checkins);
    }
}
