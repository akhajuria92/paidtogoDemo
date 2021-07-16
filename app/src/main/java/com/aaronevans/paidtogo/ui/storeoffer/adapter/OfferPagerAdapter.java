package com.aaronevans.paidtogo.ui.storeoffer.adapter;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;

import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.local.UserPreferences;
import com.aaronevans.paidtogo.data.remote.PaidToGoService;
import com.aaronevans.paidtogo.data.remote.response.offers_response.OffersResponse;
import com.aaronevans.paidtogo.data.remote.response.offers_response.Result;
import com.aaronevans.paidtogo.data.remote.response.purchase_payout.PurchasePaymentResponse;
import com.aaronevans.paidtogo.ui.storeoffer.SuccessfullPurchase;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class OfferPagerAdapter extends PagerAdapter {
    OffersResponse response;
    FragmentActivity activity;
    ProgressDialog mProgressDialog;
    public OfferPagerAdapter(FragmentActivity activity, OffersResponse response) {
        this.response=response;
        this.activity=activity;
    }

    @Override
    public int getCount() {
        return response.getResult().size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return o==view;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.adapter_offers, null);
        TextView textViewOfferName=view.findViewById(R.id.textViewOfferName);
        TextView textViewUnitRemaining=view.findViewById(R.id.textViewUnitRemaining);
        TextView textViewRequiredCoin=view.findViewById(R.id.textViewRequiredCoin);
        TextView textviewAmount=view.findViewById(R.id.textviewAmount);
        RelativeLayout relativeLayoutPurchase=view.findViewById(R.id.relativeLayoutPurchase);
        double paidEarnedPoints=0.0;
        double freeEarnedPoints=0.0;
        double totalEarnedPoints=0.0;
        double spentCoins=0.0;

        if(response.getmPoints().getPaid()!=null){
            paidEarnedPoints=response.getmPoints().getPaid().getAllEarnedPoints();
        }

        if(response.getmPoints().getFree()!=null){
            freeEarnedPoints=response.getmPoints().getFree().getAllEarnedPoints();
        }
        if(response.getSpent_coins()!=null){
            spentCoins=response.getSpent_coins();
        }


        totalEarnedPoints=paidEarnedPoints+freeEarnedPoints-spentCoins;
        double coinsValue=Double.parseDouble(UserPreferences.getCoinsValue(activity));

        double total_coin=response.getmPoints().getFree().getAllEarnedPoints();
        String spend_coin=response.getmPoints().getSpend_coins();


        double finalCoinValue = total_coin - Double.parseDouble(spend_coin);




        textViewOfferName.setText("Coins Available: "+(int)finalCoinValue);
        textViewUnitRemaining.setText("Units Remaining This Month: "+response.getResult().get(position).getUnitRemaining());
        textViewRequiredCoin.setText("Required Coins: "+response.getResult().get(position).getCoins());
        textviewAmount.setText("$ "+response.getResult().get(position).getAmount());
        relativeLayoutPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int coins=0;
                double coinsValue=Double.parseDouble(UserPreferences.getCoinsValue(activity));



                if(UserPreferences.getUser(activity).getSubscription_id() == 1){
                    coins= (int)(response.getmPoints().getFree().getAllEarnedPoints() / coinsValue);
                }
                else{
                    coins= (int)(response.getmPoints().getPaid().getAllEarnedPoints() / coinsValue);
                }



                if(coins<Integer.parseInt(response.getResult().get(position).getCoins())){
                    alertDialogMessage("Please earn more coins to purchase this offer");
                }else if(response.getResult().get(position).getUnitRemaining()==0){
                    alertDialogMessage("All the payout offers have been claimed for this month. Please check back at the beginning of next month to claim available offers");
                }else{
                    alertDialog("Do you want to invest your "+response.getResult().get(position).getCoins()+" "+"coins to purchase this offer?",response.getResult().get(position).getAmount(),response.getResult().get(position));
                }
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ConstraintLayout)object);
    }



    void alertDialog(String message, String amount, Result result){
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.setTitle("PaidToGo");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                purchasePayout(result.getId()+"",result.getMonth()+"",amount);

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        final AlertDialog dialog = builder.create();
        dialog.show();


    }



    void alertDialogMessage(String message){
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.setTitle("PaidToGo");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();

    }



    void purchasePayout( String offerId, String month,String amount){
        showProgressDialog();
        PaidToGoService
                .getServiceClient()
                .purchasePayout(UserPreferences.getUser(activity).getId(),offerId,month)
                .enqueue(new Callback<PurchasePaymentResponse>() {
                    @Override
                    public void onResponse(Call<PurchasePaymentResponse> call, Response<PurchasePaymentResponse> response) {
                        hideProgressDialog();
                        Intent intent=new Intent(activity, SuccessfullPurchase.class);
                        intent.putExtra("amount",amount);
                        activity.startActivity(intent);
                    }
                    @Override
                    public void onFailure(Call<PurchasePaymentResponse> call, Throwable t) {
                        hideProgressDialog();
                        onError(t);
                    }
                });
    }
    public void onError(Throwable throwable) {
        hideProgressDialog();
        if (throwable instanceof HttpException) {
            showErrorAlert(PaidToGoService.attendError((HttpException) throwable).getDetail());
        } else {
            showErrorAlert(activity.getString(R.string.connection_problem));
        }
    }


    private  void showProgressDialog(){
        mProgressDialog = new ProgressDialog(activity);
        mProgressDialog.setTitle(activity.getResources().getString(R.string.app_name));
        mProgressDialog.show();
    }

    private  void hideProgressDialog(){
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private  void showErrorAlert(String message){
        new AlertDialog.Builder(activity)
                .setMessage(message)
                .setTitle(activity.getResources().getString(R.string.app_name))
                .setPositiveButton(R.string.alert_accept, null)
                .create()
                .show();
    }

}