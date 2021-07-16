package com.aaronevans.paidtogo.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Infinix Android on 15/2/2017.
 */

public class Pools implements Parcelable {

    public static final Creator<Pools> CREATOR = new Creator<Pools>() {
        @Override
        public Pools createFromParcel(Parcel in) {
            return new Pools(in);
        }

        @Override
        public Pools[] newArray(int size) {
            return new Pools[size];
        }
    };
    private List<Pool> pools = null;

    public Pools() {
        pools = new ArrayList<>();
    }

    protected Pools(Parcel in) {
        pools = in.createTypedArrayList(Pool.CREATOR);
    }

    public List<Pool> getPools() {
        return pools;
    }

    public void setPools(List<Pool> pools) {
        this.pools = pools;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(pools);
    }
}
