package com.aaronevans.paidtogo.data.remote;

import com.aaronevans.paidtogo.data.remote.response.UpgradeToPro;
import com.aaronevans.paidtogo.data.remote.response.purchase_payout.PurchasePaymentResponse;
import com.aaronevans.paidtogo.ui.profile.ProfilePicmodel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.aaronevans.paidtogo.data.entities.AcceptInvitation;
import com.aaronevans.paidtogo.data.entities.Balance;
import com.aaronevans.paidtogo.data.entities.EmailAuth;
import com.aaronevans.paidtogo.data.entities.Error;
import com.aaronevans.paidtogo.data.entities.LeaderboardData;
import com.aaronevans.paidtogo.data.entities.Pool;
import com.aaronevans.paidtogo.data.entities.PoolType;
import com.aaronevans.paidtogo.data.entities.RecoverPassword;
import com.aaronevans.paidtogo.data.entities.RejectInvitation;
import com.aaronevans.paidtogo.data.entities.TermsAndConditions;
import com.aaronevans.paidtogo.data.entities.User;
import com.aaronevans.paidtogo.data.entities.UserId;
import com.aaronevans.paidtogo.data.entities.contracts.ApiError;
import com.aaronevans.paidtogo.data.remote.request.ActivityRegister;
import com.aaronevans.paidtogo.data.remote.request.BalanceRequest;
import com.aaronevans.paidtogo.data.remote.request.ChangePasswordBody;
import com.aaronevans.paidtogo.data.remote.request.ComplaintContactBody;
import com.aaronevans.paidtogo.data.remote.request.FacebookBody;
import com.aaronevans.paidtogo.data.remote.request.LeaderboardBody;
import com.aaronevans.paidtogo.data.remote.request.PoolBody;
import com.aaronevans.paidtogo.data.remote.request.RouteBody;
import com.aaronevans.paidtogo.data.remote.request.StatsBody;
import com.aaronevans.paidtogo.data.remote.request.UpdateProfile;
import com.aaronevans.paidtogo.data.remote.response.AcceptInviteResponse;
import com.aaronevans.paidtogo.data.remote.response.ActivitiesResponse;
import com.aaronevans.paidtogo.data.remote.response.BalanceResponse;
import com.aaronevans.paidtogo.data.remote.response.EligiblePool;
import com.aaronevans.paidtogo.data.remote.response.InvitationsResponse;
import com.aaronevans.paidtogo.data.remote.response.LeaderboardResponse;
import com.aaronevans.paidtogo.data.remote.response.PoolResponse;
import com.aaronevans.paidtogo.data.remote.response.RouteResponse;
import com.aaronevans.paidtogo.data.remote.response.StatsResponse;
import com.aaronevans.paidtogo.data.remote.response.TyCResponse;
import com.aaronevans.paidtogo.data.remote.response.UpdateResponse;
import com.aaronevans.paidtogo.data.remote.response.WinnersResponse;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by evaristo on 20/12/16.
 */

public class PaidToGoService {

    public static final String CODE_PROFILE_SUCCESSFUL = "PROFILE_SUCCESSFUL";
    public static final String CODE_SUCCESS = "Success";
    public static String API_URL = "https://www.paidtogo.com/api/v1/";
    //    public static String API_URL = "http://192.168.0.14:8080/api/v1/";
    private static ApiClientInterface sApiClient;
    private static Retrofit sRestAdapter;

    public static ApiClientInterface getServiceClient() {
        if (sApiClient == null) {
            initApiClient();
        }
        return sApiClient;
    }

    private static void initApiClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(60, TimeUnit.SECONDS);

        httpClient.connectTimeout(60, TimeUnit.SECONDS);
        httpClient.addInterceptor(interceptor);

        Gson gson = new GsonBuilder()
                .setLenient()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        sRestAdapter = new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(sSLFactoryForClient(httpClient).build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        sApiClient = sRestAdapter.create(ApiClientInterface.class);

    }

    public static OkHttpClient.Builder sSLFactoryForClient(OkHttpClient.Builder httpClient) {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {

                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            httpClient.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            httpClient.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return httpClient;
    }

    public static ApiError attendError(HttpException error) {
        try {
            return new Gson().fromJson(error.response().errorBody().string(), Error.class);
        } catch (IOException e) {
            return new Error("IOException", "418");
        } catch (Exception e) {
            return new Error("Exception", "500");
        }
    }

    public interface ApiClientInterface {
        @POST("register")
        Observable<User> registerUser(@Body User user);

        @POST("social/login")
        Observable<User> registerWithFb(@Body FacebookBody params);

        @POST("login")
        Observable<User> login(@Body EmailAuth auth);

        @POST("recover_pass")
        Observable<Error> recoverPassword(@Body RecoverPassword email);

        @POST("tyc")
        Observable<TyCResponse> getTermsAndConditions(@Body TermsAndConditions id);

        @GET("invitation")
        Observable<InvitationsResponse> getInvitations(@Query("user_id") UserId id);

        @GET("pooleligible")
        Observable<ArrayList<EligiblePool>> getEligiblePools(@Query("user_id") String id, @Query("lat") double lat, @Query("long") double lng);

        @GET("pools/search")
        Observable<ArrayList<EligiblePool>> getSearch(@Query("user_id") String id, @Query("pool_name") String query);

        @POST("pool/action/subscribe")
        Observable<AcceptInviteResponse> acceptInvitation(@Body AcceptInvitation id);

        @POST("pool/action/unsubscribe")
        Observable<AcceptInviteResponse> rejectInvitation(@Body RejectInvitation id);

        @POST("profile")
        Observable<UpdateResponse> updateUser(@Body UpdateProfile userUpdate);

        @POST("balance")
        Observable<Balance> getBalance(@Body BalanceRequest balanceRequest);

        @POST("change-password")
        Observable<UpdateResponse> changePassword(@Body ChangePasswordBody changePasswordBody);

        @POST("compliant")
        Observable<UpdateResponse> compliant(@Body ComplaintContactBody complaintContactBody);

        @GET("pool")
        Observable<ArrayList<PoolResponse>> getActivitieswithoutdate(@Query("user_id") String id, @Query("subscribed") String subscribed);

        @GET("pool")
        Observable<ArrayList<PoolResponse>> getActivities(@Query("user_id") String id, @Query("subscribed") String subscribed, @Query("activity_start_date") String start, @Query("activity_end_date") String end);

        @POST("activity_route")
        Observable<RouteResponse> getPointsRoute(@Body RouteBody routeBody);

        @GET("user/leaderboard")
        Observable<ArrayList<LeaderboardResponse>> getLeaderboards(@Query("user_id") String id, @Query("year") String year, @Query("month") String month);

        @GET("user/userprizeleaderboard")
        Observable<ArrayList<WinnersResponse>> getWinners(@Query("user_id") String id, @Query("date") String date);


        @GET("user/userprizeleaderboard")
        Observable<ArrayList<WinnersResponse>> getWinnerswithnew(@Query("user_id") String id, @Query("date") String date,@Query("new") String one);

        @POST("leaderboards")
        Observable<LeaderboardData> getLeaderboardsPositions(@Body LeaderboardBody leaderboardBody);

        @GET("pool_types")
        Observable<PoolType> getPoolById(@Query("id") String idPool);

        @POST("mystatus")
        Observable<StatsResponse> getMyStatus(@Body StatsBody statusBody);

        @GET("activity/series")
        Observable<ArrayList<StatsResponse>> getStatsDaily(@Query("user_id") String user_id, @Query("span") String span, @Query("pool_id") String pool_id);

        @GET("activity/series")
        Observable<ArrayList<StatsResponse>> getStatsWeekly(@Query("user_id") String user_id, @Query("span") String span, @Query("pool_id") String pool_id);

        @GET("activity/series")
        Observable<ArrayList<StatsResponse>> getStatsMonthly(@Query("user_id") String user_id, @Query("span") String span, @Query("pool_id") String pool_id);

        @GET("pool_types")
        Observable<List<PoolType>> getPoolTypes();

        @GET("pool_types")
        Observable<PoolType> getPoolTypesById(@Query("id") String idPoolType);

        @GET("pools")
        Observable<ArrayList<Pool>> getPools(@Body PoolBody poolBody);

        @POST("activity")
        Observable<ResponseBody> registerActivity(@Body ArrayList<ActivityRegister> activityBody);

        @GET("user_activities/daily")
        Observable<ArrayList<ActivitiesResponse>> getActivity(@Query("user_id") String id);

        @GET("balance")
        Observable<ArrayList<BalanceResponse>> getBalance(@Query("user_id") String id,@Query("start_date") String startDate,@Query("end_date") String end_date);

        @GET("offers")
        Call<ResponseBody> getOffers(@Query("user_id")String id);



        @GET("getSettings")
        Call<ResponseBody> getCoinsValue(@Query("setting_name") String setting_name);


        @GET("upgradeSub")
        Call<ResponseBody> upgradeToPro(@Query("access_token") String access_token,@Query("start_date") String start_date,@Query("end_date") String end_date);


        @FormUrlEncoded
        @POST("pay/offer")
        Call<PurchasePaymentResponse> purchasePayout(@Field("user_id") String id, @Field("offer_id") String offer_id,@Field("month") String month);



        @FormUrlEncoded
        @POST("changeDistanceUnit")
        Call<ResponseBody> changeDistanceUnit(@Field("user_id") String user_id,@Field("distance_unit_type") String distance_unit_type);


        @FormUrlEncoded
        @POST("add/points/ads")
        Call<ResponseBody> updatePoints(@Field("user_id") String id, @Field("points") String points);


        @FormUrlEncoded
        @POST("get/points/ads")
        Call<ResponseBody> getPointsAdded(@Field("user_id") String id);

        @FormUrlEncoded
        @POST("delete")
        Call<ResponseBody> deleteAccount(@Field("user_id") String id);


        @Multipart
        @POST("updateProfilePicture")
        Call<ProfilePicmodel> addpic(@Part("access_token") RequestBody access_token,
                                            @Part MultipartBody.Part part1);
    }
}