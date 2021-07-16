
package com.aaronevans.paidtogo.data.remote.request.get_coins_value;

import com.google.gson.annotations.SerializedName;

public class GetCoinsValue {

    @SerializedName("result")
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

}
