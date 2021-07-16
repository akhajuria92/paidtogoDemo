package com.aaronevans.paidtogo.ui.main.prizeTable;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("prize/management")
    Call<List<PrizePojo>> getPrizeList(@Query("year") String year, @Query("month") String month);

}