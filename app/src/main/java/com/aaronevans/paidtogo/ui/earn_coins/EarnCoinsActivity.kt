package com.aaronevans.paidtogo.ui.earn_coins

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.aaronevans.paidtogo.PaidToGoApp
import com.aaronevans.paidtogo.R
import com.aaronevans.paidtogo.data.local.UserPreferences
import com.aaronevans.paidtogo.data.remote.PaidToGoService
import com.aaronevans.paidtogo.data.remote.request.earned_points.EarnedPoints
import com.aaronevans.paidtogo.data.remote.request.get_coins_value.GetCoinsValue
import com.adcolony.sdk.*
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class EarnCoinsActivity : AppCompatActivity() {

    private var add: AdColonyInterstitial? = null
    private var listener: AdColonyInterstitialListener? = null
    private var adOptions: AdColonyAdOptions? = null

    lateinit var textViewWatchAdd: TextView;
    lateinit var textViewAdsLeft: TextView;
    lateinit var textViewCancel: TextView;
    lateinit var imageViewBack: ImageView;
    var linearLayoutWatchAdd: LinearLayout? = null
    var adsLeft=0;
    lateinit var mProgressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_earn_coins)
        adSetup()
        findViews();
        getEarnedPoints();
    }


    public override fun onResume() {
        super.onResume()
        if (add == null || add!!.isExpired) {
            AdColony.requestInterstitial(PaidToGoApp.REWARDS_ADS_ZONE_ID, listener!!, adOptions)
        }


    }
    private fun findViews() {
        textViewWatchAdd =findViewById<TextView>(R.id.textViewWatchAdd)
        textViewAdsLeft =findViewById<TextView>(R.id.textViewAdsLeft)
        imageViewBack =findViewById<ImageView>(R.id.imageViewBack)
        textViewCancel =findViewById<TextView>(R.id.textViewCancel)
        linearLayoutWatchAdd = findViewById<LinearLayout>(R.id.linearLayoutWatchAdd)

        textViewWatchAdd.setOnClickListener(View.OnClickListener {


            if(add!=null)
            {
                add!!.show();
            }


        })
        imageViewBack.setOnClickListener(View.OnClickListener { finish() })
        textViewCancel.setOnClickListener(View.OnClickListener { finish() })
    }

    internal fun adSetup() {
        val appOptions = AdColonyAppOptions()
                .setUserID("unique_user_id")
                .setKeepScreenOn(true)

        // Configure AdColony in your launching Activity's onCreate() method so that cached ads can
        // be available as soon as possible.
        AdColony.configure(this, appOptions, PaidToGoApp.ADS_APP_ID, PaidToGoApp.REWARDS_ADS_ZONE_ID)

        // Optional user metadata sent with the ad options in each request
        val metadata = AdColonyUserMetadata()
                .setUserAge(26)
                .setUserEducation(AdColonyUserMetadata.USER_EDUCATION_BACHELORS_DEGREE)
                .setUserGender(AdColonyUserMetadata.USER_MALE)

        // Ad specific options to be sent with request
        adOptions = AdColonyAdOptions().setUserMetadata(metadata)


        // Create and set a reward listener
        AdColony.setRewardListener {
            // Query reward object for info here

        }

        // Set up listener for interstitial ad callbacks. You only need to implement the callbacks
        // that you care about. The only required callback is onRequestFilled, as this is the only
        // way to get an ad object.
        listener = object : AdColonyInterstitialListener() {
            override fun onRequestFilled(ad: AdColonyInterstitial) {
                linearLayoutWatchAdd!!.visibility = View.VISIBLE
                add = ad
            }

            override fun onRequestNotFilled(zone: AdColonyZone?) {
                //Toast.makeText(this@EarnCoinsActivity,"something went wrong",Toast.LENGTH_LONG).show()
                // Ad request was not filled
                // progress.setVisibility(View.INVISIBLE);

            }

            override fun onOpened(ad: AdColonyInterstitial?) {
                // Ad opened, reset UI to reflect state change
                //showButton.setEnabled(false);
                ///progress.setVisibility(View.VISIBLE);

            }

            override fun onExpiring(ad: AdColonyInterstitial?) {
                // Request a new ad if ad is expiring
                //showButton.setEnabled(false);
                //progress.setVisibility(View.VISIBLE);
                AdColony.requestInterstitial(PaidToGoApp.REWARDS_ADS_ZONE_ID, this, adOptions)

            }


            override fun onClosed(ad: AdColonyInterstitial?) {
                updatePoint()
                //super.onClosed(ad);
            }

        }




        if (add == null || add!!.isExpired) {
            AdColony.requestInterstitial(PaidToGoApp.REWARDS_ADS_ZONE_ID, listener!!, adOptions)
        }
    }



    fun showProgressDialog() {
        mProgressDialog = ProgressDialog(this)
        mProgressDialog.setTitle(resources.getString(R.string.app_name))
        mProgressDialog.show()
    }


    fun hideProgressDialog() {
        if (mProgressDialog.isShowing) {
            mProgressDialog.cancel()
        }
    }


    internal fun updatePoint() {
        showProgressDialog()
        PaidToGoService
                .getServiceClient()
                .updatePoints(UserPreferences.getUser(this).id, "1")
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if(adsLeft!=0){
                            adsLeft=adsLeft-1;
                            textViewAdsLeft.text = adsLeft.toString()
                        }
                        hideProgressDialog()
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        hideProgressDialog()
                        onError(t)
                    }
                })
    }


    fun showErrorAlert(msg: String) {
        AlertDialog.Builder(this)
                .setTitle(resources.getString(R.string.app_name))
                .setMessage(msg)
                .setPositiveButton(R.string.alert_accept, null)
                .create()
                .show()
    }

    fun onError(throwable: Throwable) {
        if (throwable is HttpException) {
            showErrorAlert(PaidToGoService.attendError(throwable).detail)
        } else {
            showErrorAlert(this!!.getString(R.string.connection_problem))
        }
    }



    internal fun getEarnedPoints() {
        showProgressDialog()
        PaidToGoService
                .getServiceClient()
                .getPointsAdded(UserPreferences.getUser(this).id)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        val stringResponse = response.body()!!.string().toString()
                        val gson = Gson()
                        val finalResponse = gson.fromJson(stringResponse, EarnedPoints::class.java)
                        var pointsEarned = finalResponse.points
                        adsLeft=3-pointsEarned;
                        textViewAdsLeft.text = adsLeft.toString()
                        hideProgressDialog()
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        hideProgressDialog()
                        onError(t)
                    }
                })
    }
}
