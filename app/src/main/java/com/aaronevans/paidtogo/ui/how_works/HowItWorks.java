package com.aaronevans.paidtogo.ui.how_works;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.ui.HowItWorksWebView;
import com.aaronevans.paidtogo.ui.TermsandConditionActivity.TermsandConditionActivity;
import com.aaronevans.paidtogo.ui.UpgradeToPro.UpgradeToProActivity;
import com.aaronevans.paidtogo.ui.main.MainActivity;
import com.aaronevans.paidtogo.ui.main.MainActivity_;

public class HowItWorks extends AppCompatActivity {

    private String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setFullScreenActivity();
        setContentView(R.layout.activity_how_it_works);
        findViews();
    }

    void findViews() {

        TextView tv_how_it_work = findViewById(R.id.tv_how_it_work);
        TextView tv_terms_conditions = findViewById(R.id.tv_terms_conditions);
        TextView tv_pro_free = findViewById(R.id.tv_pro_free);
        TextView tv_cancel = findViewById(R.id.tv_cancel);


        tv_how_it_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HowItWorks.this, HowItWorksWebView.class);
                startActivity(intent);
            }
        });
        tv_terms_conditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HowItWorks.this, TermsandConditionActivity.class);
                startActivity(intent);
            }
        });


        tv_pro_free.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HowItWorks.this, UpgradeToProActivity.class);
                startActivity(intent);
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                from = getIntent().getStringExtra("from");
                if (from.equals("main")) {
                    finish();
                } else {
                    MainActivity_.intent(HowItWorks.this)
                            .flags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                            .start()
                            .withAnimation(android.R.anim.fade_in, android.R.anim.fade_out);
                }

            }
        });


    }

    private void setFullScreenActivity() {
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


    }

    @Override
    public void onBackPressed() {

    }
}
