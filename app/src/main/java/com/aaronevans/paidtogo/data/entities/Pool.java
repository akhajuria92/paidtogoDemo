package com.aaronevans.paidtogo.data.entities;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by evaristo on 01/02/17.
 */

@Parcel
public class Pool implements Parcelable {

    public static final Creator<Pool> CREATOR = new Creator<Pool>() {
        @Override
        public Pool createFromParcel(android.os.Parcel in) {
            return new Pool(in);
        }

        @Override
        public Pool[] newArray(int size) {
            return new Pool[size];
        }
    };
    @Expose
    @SerializedName("pool_type")
    PoolType pooltype;
    @Expose
    String banner = "";
    @Expose
    @SerializedName("destination_latitude")
    String latitude;
    @Expose
    @SerializedName("destination_longitude")
    String longitude;
    @Expose
    @SerializedName("country")
    String country;
    @Expose
    @SerializedName("state")
    String state;
    @Expose
    @SerializedName("earned_money_per_mile")
    String moneyPerMile;
    @Expose
    @SerializedName("end_date_time")
    String endDateTime;
    @Expose
    @SerializedName("icon_photo")
    String photoUrl;
    @Expose
    @SerializedName("money_available")
    String moneyAvailable;
    @Expose
    String id;
    @Expose
    String name;
    @Expose
    String open;
    @Expose
    @SerializedName("photo_icon_description")
    String photoIconDescription;
    @Expose
    @SerializedName("quant_members")
    String countMembers;
    @Expose
    @SerializedName("limit_per_day")
    String limitPerDay;
    @Expose
    @SerializedName("limit_per_month")
    String limitPerMonth;
    @Expose
    @SerializedName("start_date")
    String startDate;
    @Expose
    @SerializedName("terms_and_condition")
    String termsAndConditions;
    @Expose
    @SerializedName("show_banner")
    int showBanner;

    public Pool() {

    }

    protected Pool(android.os.Parcel in) {
        pooltype = in.readParcelable(PoolType.class.getClassLoader());
        banner = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        country = in.readString();
        state = in.readString();
        moneyPerMile = in.readString();
        endDateTime = in.readString();
        photoUrl = in.readString();
        moneyAvailable = in.readString();
        id = in.readString();
        name = in.readString();
        open = in.readString();
        photoIconDescription = in.readString();
        countMembers = in.readString();
        limitPerDay = in.readString();
        limitPerMonth = in.readString();
        startDate = in.readString();
        termsAndConditions = in.readString();
        showBanner = in.readInt();
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    @Override
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeParcelable(pooltype, flags);
        dest.writeString(banner);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(country);
        dest.writeString(state);
        dest.writeString(moneyPerMile);
        dest.writeString(endDateTime);
        dest.writeString(photoUrl);
        dest.writeString(moneyAvailable);
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(open);
        dest.writeString(photoIconDescription);
        dest.writeString(countMembers);
        dest.writeString(limitPerDay);
        dest.writeString(limitPerMonth);
        dest.writeString(startDate);
        dest.writeString(termsAndConditions);
        dest.writeInt(showBanner);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getLimitPerMonth() {
        return limitPerMonth;
    }

    public PoolType getPooltype() {
        return pooltype;
    }

    public String getBanner() {
        return "https://www.paidtogo.com/images/pools/" + banner;       // do need to remove static path
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getMoneyPerMile() {
        return moneyPerMile;
    }

    public String getEndDateTime() {
        Date date;
        SimpleDateFormat dfInput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dfOutput = new SimpleDateFormat("MM/dd/yyyy");
        try {
            date = dfInput.parse(endDateTime);
            return dfOutput.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getMoneyAvailable() {
        return moneyAvailable;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String isOpen() {
        return open;
    }

    public String getPhotoIconDescription() {
        return photoIconDescription;
    }

    public String getCountMembers() {
        return countMembers;
    }

    public String getLimitPerDay() {
        return limitPerDay;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    public int isShowBanner() {
        return showBanner;
    }

}
