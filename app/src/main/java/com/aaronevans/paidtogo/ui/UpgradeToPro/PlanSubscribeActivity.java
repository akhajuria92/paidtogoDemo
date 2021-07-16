package com.aaronevans.paidtogo.ui.UpgradeToPro;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.aaronevans.paidtogo.data.entities.User;
import com.aaronevans.paidtogo.data.local.UserPreferences;
import com.aaronevans.paidtogo.data.remote.PaidToGoService;
import com.aaronevans.paidtogo.data.remote.response.UpgradeToPro;
import com.aaronevans.paidtogo.ui.next_steps.NextStepsActivity;
import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.SkuDetails;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.ui.BaseActivity;
import com.aaronevans.paidtogo.ui.TermsandConditionActivity.TermsandConditionActivity;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class PlanSubscribeActivity extends BaseActivity {

    LinearLayout ll_plan_yearly, ll_plan_monthly;
    TextView tv_plan_desc, tv_terms, tv_privacy_policy, tv_subscribe;
    ImageView iv_close;

    String licenseKey = "";
    String merchantId = "";
    String productId = "";
    private ProgressDialog mProgressDialog = null;

    private BillingProcessor billingProcessor;
    private int productType;   // 1->monthly 2-> yearly

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_subscribe);
        setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        init();

        ll_plan_monthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 productId = "pro_user_free_user";
                //productId = "android.test.purchased";
                productType = 1;
                ll_plan_monthly.setBackground(getResources().getDrawable(R.drawable.border));
                ll_plan_yearly.setBackground(getResources().getDrawable(R.drawable.border_white));
                tv_plan_desc.setText(getResources().getString(R.string.payment_charge_monthly));
            }
        });

        ll_plan_yearly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productType = 2;
                productId = "pro_user_year";
                ll_plan_yearly.setBackground(getResources().getDrawable(R.drawable.border));
                ll_plan_monthly.setBackground(getResources().getDrawable(R.drawable.border_white));
                tv_plan_desc.setText(getResources().getString(R.string.payment_charge_yearly));
            }
        });

        tv_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (productId.equals("")) {
                    Toast.makeText(PlanSubscribeActivity.this, "Please choose one plan", Toast.LENGTH_LONG).show();
                } else {
                    showAlert(PlanSubscribeActivity.this, "Paidtogo", getResources().getString(R.string.upgrade_alert_msg));
                }
            }
        });

        tv_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlanSubscribeActivity.this, TermsandConditionActivity.class);
                startActivity(intent);
            }
        });

        tv_privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.paidtogo.com/privacy_policy";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void init() {
        ll_plan_monthly = findViewById(R.id.ll_plan_monthly);
        ll_plan_yearly = findViewById(R.id.ll_plan_yearly);
        tv_plan_desc = findViewById(R.id.tv_plan_desc);
        tv_terms = findViewById(R.id.tv_terms);
        tv_privacy_policy = findViewById(R.id.tv_privacy_policy);
        iv_close = findViewById(R.id.iv_close);
        tv_subscribe = findViewById(R.id.tv_subscribe);


        initializePurchase();
    }

    public void finishActivyity() {
        finish();
    }


    void initializePurchase() {
        billingProcessor = new BillingProcessor(this, licenseKey, merchantId, new BillingProcessor.IBillingHandler() {
            @Override
            public void onProductPurchased(String productId, TransactionDetails details) {
                upgradePlan();
            }

            @Override
            public void onPurchaseHistoryRestored() {

            }

            @Override
            public void onBillingError(int errorCode, Throwable error) {

            }

            @Override
            public void onBillingInitialized() {

            }
        });
    }


    public void showAlert(Context context, String title, String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        // Setting Dialog Title
        alertDialog.setTitle(title);
        // Setting Dialog Message
        alertDialog.setMessage(message);
        // On pressing Settings button
        alertDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(!billingProcessor.isPurchased(productId)){
                            billingProcessor.purchase(PlanSubscribeActivity.this,productId);
                        }else{
                            SkuDetails value = billingProcessor.getSubscriptionListingDetails(productId);
                            Log.e("detail is",value.toString());
                            Toast.makeText(context, "Already Purchased", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        // on pressing cancel button
        alertDialog.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        // Showing Alert Message
        alertDialog.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            billingProcessor.handleActivityResult(requestCode, resultCode, data);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    void upgradePlan() {
        String startDate;
        String endDate;

        if (productType == 1) {
            final Calendar cal1 = Calendar.getInstance();
            DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            startDate = dateFormat1.format(cal1.getTime());

            cal1.add(Calendar.DATE, 30);
            endDate = dateFormat1.format(cal1.getTime());
        } else {

            final Calendar cal1 = Calendar.getInstance();
            DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            startDate = dateFormat1.format(cal1.getTime());

            cal1.add(Calendar.YEAR, 301);
            endDate = dateFormat1.format(cal1.getTime());

        }
        showProgressDialog();
        PaidToGoService
                .getServiceClient()
                .upgradeToPro(UserPreferences.getUser(this).getAccessToken(), startDate, endDate)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        hideProgressDialog();
                        User user = UserPreferences.getUser(getApplicationContext());
                        user.setSubscription_id(2);
                        UserPreferences.saveUser(getApplicationContext(),user);

                        Toast.makeText(getApplicationContext(),UserPreferences.getUser(getApplicationContext()).getSubscription_id()+"",Toast.LENGTH_LONG).show();

                        try {
                            String stringResponse=response.body().string().toString();
                            Gson gson=new Gson();
                            UpgradeToPro finalResponse = gson.fromJson(stringResponse, UpgradeToPro.class);
                            String  message=finalResponse.getMessage();
                            startActivity(new Intent(PlanSubscribeActivity.this, NextStepsActivity.class));
                            finish();

                          //  Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }



                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        hideProgressDialog();
                        onError(t);
                    }
                });
    }


    public void onError(Throwable throwable) {
        if (throwable instanceof HttpException) {
            showErrorAlert(PaidToGoService.attendError((HttpException) throwable).getDetail());
        } else {
            showErrorAlert(getString(R.string.connection_problem));
        }
    }


    public void showProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle(getResources().getString(R.string.app_name));
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    public void showErrorAlert(String msg) {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.app_name))
                .setMessage(msg)
                .setPositiveButton(R.string.alert_accept, null)
                .create()
                .show();
    }


}
