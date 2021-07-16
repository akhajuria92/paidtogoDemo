package com.aaronevans.paidtogo.ui.storeoffer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.aaronevans.paidtogo.R;

public class SuccessfullPurchase extends AppCompatActivity {

    private String amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setFullScreenActivity();
        setContentView(R.layout.activity_successfull_purchase);
        amount=getIntent().getStringExtra("amount");
        findViews();
    }


    void findViews(){
        TextView textViewMessage=findViewById(R.id.textViewMessage);
        ImageView imageViewCross=findViewById(R.id.imageViewCross);
        imageViewCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        textViewMessage.setText("Thanks for purchasing offer. The amount "+"$"+amount+" will be provided to your Paypal account within 30 days");
    }

    private void setFullScreenActivity() {
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

    }
}
