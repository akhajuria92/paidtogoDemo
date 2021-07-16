package com.aaronevans.paidtogo.data.local;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Infinix Android on 20/2/2017.
 */

public class ListLocation implements Parcelable {
    public static final Creator<ListLocation> CREATOR = new Creator<ListLocation>() {
        @Override
        public ListLocation createFromParcel(Parcel in) {
            return new ListLocation(in);
        }

        @Override
        public ListLocation[] newArray(int size) {
            return new ListLocation[size];
        }
    };
    private List<Location> locations = null;

    public ListLocation() {
        locations = new ArrayList<>();
    }

    protected ListLocation(Parcel in) {
        locations = in.createTypedArrayList(Location.CREATOR);
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(locations);
    }
}
