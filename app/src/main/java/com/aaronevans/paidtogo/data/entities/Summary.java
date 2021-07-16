package com.aaronevans.paidtogo.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Summary implements Parcelable {



    @SerializedName("co2")
    @Expose
    private Double co2;
    @SerializedName("calories")
    @Expose
    private Double calories;
    @SerializedName("miles")
    @Expose
    private Double miles;
    @SerializedName("steps")
    @Expose
    private Double steps;
    @SerializedName("traffic")
    @Expose
    private Double traffic;
    @SerializedName("earned_money")
    @Expose
    private Double earnedMoney;
    @SerializedName("earned_points")
    @Expose
    private Double earnedPoints;

    protected Summary(Parcel in) {
        if (in.readByte() == 0) {
            co2 = null;
        } else {
            co2 = in.readDouble();
        }
        if (in.readByte() == 0) {
            calories = null;
        } else {
            calories = in.readDouble();
        }
        if (in.readByte() == 0) {
            miles = null;
        } else {
            miles = in.readDouble();
        }
        if (in.readByte() == 0) {
            steps = null;
        } else {
            steps = in.readDouble();
        }
        if (in.readByte() == 0) {
            traffic = null;
        } else {
            traffic = in.readDouble();
        }
        if (in.readByte() == 0) {
            earnedMoney = null;
        } else {
            earnedMoney = in.readDouble();
        }
        if (in.readByte() == 0) {
            earnedPoints = null;
        } else {
            earnedPoints = in.readDouble();
        }
    }

    public static final Creator<Summary> CREATOR = new Creator<Summary>() {
        @Override
        public Summary createFromParcel(Parcel in) {
            return new Summary(in);
        }

        @Override
        public Summary[] newArray(int size) {
            return new Summary[size];
        }
    };

    public Double getCo2() {
        return co2;
    }

    public void setCo2(Double co2) {
        this.co2 = co2;
    }

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public Double getMiles() {
        return miles;
    }

    public void setMiles(Double miles) {
        this.miles = miles;
    }

    public Double getSteps() {
        return steps;
    }

    public void setSteps(Double steps) {
        this.steps = steps;
    }

    public Double getTraffic() {
        return traffic;
    }

    public void setTraffic(Double traffic) {
        this.traffic = traffic;
    }

    public Double getEarnedMoney() {
        return earnedMoney;
    }

    public void setEarnedMoney(Double earnedMoney) {
        this.earnedMoney = earnedMoney;
    }

    public Double getEarnedPoints() {
        return earnedPoints;
    }

    public void setEarnedPoints(Double earnedPoints) {
        this.earnedPoints = earnedPoints;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (co2 == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(co2);
        }
        if (calories == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(calories);
        }
        if (miles == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(miles);
        }
        if (steps == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(steps);
        }
        if (traffic == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(traffic);
        }
        if (earnedMoney == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(earnedMoney);
        }
        if (earnedPoints == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(earnedPoints);
        }
    }
}