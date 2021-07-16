
package com.aaronevans.paidtogo.data.remote.response.purchase_payout;
import com.google.gson.annotations.SerializedName;
public class PurchasePaymentResponse {

    @SerializedName("message")
    private String mMessage;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

}
