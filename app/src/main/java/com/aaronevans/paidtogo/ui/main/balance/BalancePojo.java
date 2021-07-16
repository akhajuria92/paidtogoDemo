package com.aaronevans.paidtogo.ui.main.balance;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BalancePojo {

@SerializedName("code")
@Expose
public String code;
@SerializedName("data")
@Expose
public List<Object> data = null;
@SerializedName("full_data")
@Expose
public List<FullDatum> fullData = null;
@SerializedName("total_amount")
@Expose
public Integer totalAmount;
@SerializedName("paid_payment")
@Expose
public Integer paidPayment;
@SerializedName("totalPoints")
@Expose
public Double totalPoints;
@SerializedName("pending_payment")
@Expose
public Integer pendingPayment;

}