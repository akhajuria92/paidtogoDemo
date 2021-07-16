package com.aaronevans.paidtogo.ui.main.stats;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatsPojo implements Serializable
{

@SerializedName("earned_money")
@Expose
public Integer earnedMoney;
@SerializedName("earned_points")
@Expose
public Double earnedPoints;
@SerializedName("miles_traveled")
@Expose
public Integer milesTraveled;
@SerializedName("saved_co2")
@Expose
public Integer savedCo2;
@SerializedName("saved_calories")
@Expose
public Double savedCalories;
@SerializedName("save_traffic")
@Expose
public Integer saveTraffic;
@SerializedName("total_steps")
@Expose
public Integer totalSteps;
private final static long serialVersionUID = -3982084158925470394L;

public StatsPojo withEarnedMoney(Integer earnedMoney) {
this.earnedMoney = earnedMoney;
return this;
}

public StatsPojo withEarnedPoints(Double earnedPoints) {
this.earnedPoints = earnedPoints;
return this;
}

public StatsPojo withMilesTraveled(Integer milesTraveled) {
this.milesTraveled = milesTraveled;
return this;
}

public StatsPojo withSavedCo2(Integer savedCo2) {
this.savedCo2 = savedCo2;
return this;
}

public StatsPojo withSavedCalories(Double savedCalories) {
this.savedCalories = savedCalories;
return this;
}

public StatsPojo withSaveTraffic(Integer saveTraffic) {
this.saveTraffic = saveTraffic;
return this;
}

public StatsPojo withTotalSteps(Integer totalSteps) {
this.totalSteps = totalSteps;
return this;
}

}