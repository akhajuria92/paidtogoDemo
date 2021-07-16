package com.aaronevans.paidtogo.ui.UpgradeToPro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.ui.main.MainActivity;

import androidx.appcompat.app.AppCompatActivity;

public class UpgradeToProActivity extends AppCompatActivity {

    TextView tv_select_plan, tv_pool_rules;
    ImageView iv_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setFullScreenActivity();
        setContentView(R.layout.activity_upgrade_to_pro);
        init();

        tv_select_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpgradeToProActivity.this, PlanSubscribeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tv_pool_rules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("poolRule",true);
                setResult(MainActivity.REQUEST_CODE_ACTIVITY, intent);
                finish();
            }
        });

    }

    private void setFullScreenActivity() {
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    private void init() {
        tv_select_plan = findViewById(R.id.tv_select_plan);

        iv_close = findViewById(R.id.iv_close);
        tv_pool_rules = findViewById(R.id.tv_pool_rules);
    }


}
