package com.aaronevans.paidtogo.ui.main.home.components;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.components.GeneralInterface;

import java.util.ArrayList;

/**
 * Created by Farhan Arshad on 6/6/2018.
 */

public class SyncDialog {
    private final Context context;
    private AlertDialog.Builder builder;
    private GeneralInterface.OnSyncListener listener;
    private ArrayList<String> pools;
    private ArrayList<String> activityType;

    public SyncDialog(Context context) {
        this.context = context;
    }

    public SyncDialog build() {

        builder = new AlertDialog.Builder(context);

        View spinnerView = LayoutInflater.from(context).inflate(R.layout.layout_sync_dialog, null, false);

        Spinner spPool = (Spinner) spinnerView.findViewById(R.id.sp_pool);

        ArrayAdapter<String> ppolsAdapter = new ArrayAdapter<String>(context,
                R.layout.layout_spinner_item, R.id.tv_item, pools);
        spPool.setAdapter(ppolsAdapter);
        Spinner spActivityType = (Spinner) spinnerView.findViewById(R.id.sp_activity_type);

        ArrayAdapter<String> activityAdapter = new ArrayAdapter<String>(context,
                R.layout.layout_spinner_item, R.id.tv_item, activityType);
        spActivityType.setAdapter(activityAdapter);

        builder.setView(spinnerView);
        builder.setTitle(context.getString(R.string.app_name));
        builder.setMessage("Select Pool & Activity Type");
        builder.setPositiveButton(R.string.sync_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onSync(spPool.getSelectedItemPosition(), spActivityType.getSelectedItemPosition() + 1);
            }
        }).setNegativeButton(R.string.cancle_text, null);
        ;
        return this;
    }

    public void show() {
        if (builder != null)
            builder.create().show();
    }

    public SyncDialog setOnClickListener(GeneralInterface.OnSyncListener onClickListener) {
        this.listener = onClickListener;
        return this;
    }

    public SyncDialog setActivePools(ArrayList<String> pools) {
        this.pools = pools;
        return this;
    }

    public SyncDialog setActivityTypes(ArrayList<String> activityType) {
        this.activityType = activityType;
        return this;
    }
}
