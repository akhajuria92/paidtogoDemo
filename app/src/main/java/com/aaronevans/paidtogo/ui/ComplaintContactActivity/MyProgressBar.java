package com.aaronevans.paidtogo.ui.ComplaintContactActivity;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.TextView;

import com.aaronevans.paidtogo.R;

public  class MyProgressBar {

    Activity activity;

    public MyProgressBar(Activity activity)
    {
        this.activity = activity;
    }

    Dialog dialog;
    public  void show( String msg){
        dialog = new Dialog(activity);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.progrees_bar);
        if(msg.equals("")){

            msg="Loading...";

        }
        ((TextView)dialog.findViewById(R.id.mpb_msg)).setText(msg);

        dialog.show();

    }
    public void dismiss(){
        if(dialog!=null) {
            dialog.dismiss();
        }
    }

}