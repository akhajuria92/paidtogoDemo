package com.aaronevans.paidtogo.ui.next_steps;

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

public class NextStepsActivity extends AppCompatActivity {
    ImageView iv_close;
    TextView textViewTapHere;
    TextView tv_done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setFullScreenActivity();
        setContentView(R.layout.activity_next_steps);
        init();


    }

    private void init() {
        iv_close = findViewById(R.id.iv_close);
        textViewTapHere = findViewById(R.id.textViewTapHere);
        tv_done = findViewById(R.id.tv_done);
        textViewTapHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NextStepsActivity.this, HowItWorksWebView.class);
                startActivity(intent);

            }
        });


        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private void setFullScreenActivity() {
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
}
