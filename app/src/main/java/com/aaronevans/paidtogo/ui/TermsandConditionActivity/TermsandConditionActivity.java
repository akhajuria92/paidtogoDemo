package com.aaronevans.paidtogo.ui.TermsandConditionActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.ui.main.MainActivity_;

import androidx.appcompat.app.AppCompatActivity;

public class TermsandConditionActivity extends AppCompatActivity {

    ImageView mBack;
    TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termsand_condition);
        init();
        mTitle.setText(getResources().getString(R.string.terms_and_conditions));
        mBack.setImageDrawable(getResources().getDrawable(R.drawable.ic_back));
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                /*Intent in=new Intent(TermsandConditionActivity.this, MainActivity_.class);
                startActivity(in);*/
            }
        });
    }



    private void init() {
        mBack = findViewById(R.id.mBack);
        mTitle = findViewById(R.id.mTitle);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}