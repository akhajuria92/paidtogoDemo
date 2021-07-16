package com.aaronevans.paidtogo.ui;

import android.app.ProgressDialog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.mikephil.charting.charts.LineChart;
import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.remote.response.StatsResponse;

import java.util.ArrayList;

import retrofit2.HttpException;
import static com.aaronevans.paidtogo.ui.main.MainActivity.toolbar_image;
import static com.aaronevans.paidtogo.ui.main.MainActivity.toolbar_title;

public class HowItWorksWebView extends AppCompatActivity {
    private ProgressDialog mProgressDialog = null;
    LineChart chart;
    Button b1,b2,b3,b4,b5;
    TextView xText, yText;

    ArrayList<StatsResponse> stats;
    boolean check=false;
    String span;
    double pool_id;

    WebView mywebview;
    String webURL = "https://www.paidtogo.com/how_it_works";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_blank);

        mywebview =  findViewById(R.id.webv);

        mywebview.setWebViewClient(new WebViewClient());
        mywebview.getSettings().setJavaScriptEnabled(true);
        mywebview.loadUrl(webURL);

        ImageView mBack = findViewById(R.id.mBack);
        TextView mTitle=findViewById(R.id.mTitle);;

        mTitle.setText("");
        mBack.setImageDrawable(getResources().getDrawable(R.drawable.ic_back));
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void onError(Throwable throwable) {
        //  hideProgressDialog();
        if (throwable instanceof HttpException) {
            //  mView.showErrorAlert(PaidToGoService.attendError((HttpException) throwable).getDetail());
        } else {
            // mView.showErrorAlert(mView.getContext().getString(R.string.connection_problem));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try{
            toolbar_image.setVisibility(View.GONE);
            toolbar_title.setVisibility(View.VISIBLE);
            toolbar_title.setText("How it works");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

