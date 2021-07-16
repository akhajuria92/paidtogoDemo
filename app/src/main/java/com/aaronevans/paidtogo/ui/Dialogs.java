package com.aaronevans.paidtogo.ui;

import android.app.Dialog;
import android.content.Context;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import android.view.View;

import android.widget.TextView;


import com.aaronevans.paidtogo.R;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.ArrayList;


public class Dialogs {

    private static Dialogs mDialogsInstance;
    private Dialog mDialog;



    public static Dialogs getInstance() {
        if (mDialogsInstance == null) {
            mDialogsInstance = new Dialogs();
        }
        return mDialogsInstance;
    }

    public void dismissDialog() {
        try {
            if (mDialog != null) {
                mDialog.dismiss();
            }
        } catch (Exception ignore) {
        }
    }

    private void showDialog() {
        try {
            if (mDialog != null) {
                mDialog.show();
            }
        } catch (Exception ignore) {
        }
    }


    public void showBottomSheetWithPickerDialog(Context mContext, ArrayList<String> list, int selectedPosition, final BottomDialogCallBack callBack) {
        dismissDialog();
        mDialog = new BottomSheetDialog(mContext, R.style.full_screen_activity_dialog);
        mDialog.setContentView(R.layout.bottom_sheet_with_picker_dialog);
        mDialog.setCancelable(false);

        String[] array = list.toArray(new String[list.size()]);
        final NumberPicker numberPicker = (NumberPicker) mDialog.findViewById(R.id.picker);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(array.length);
        numberPicker.setDisplayedValues(array);
        numberPicker.setValue(selectedPosition);

        mDialog.findViewById(R.id.tvOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = numberPicker.getDisplayedValues()[numberPicker.getValue() - 1];
                callBack.onCallBack(str, (numberPicker.getValue() - 1));
                dismissDialog();
            }
        });


        TextView tvCancel = (TextView) mDialog.findViewById(R.id.tvCancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
            }
        });

        showDialog();
    }

}
