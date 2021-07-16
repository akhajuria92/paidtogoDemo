package com.aaronevans.paidtogo.ui.main.balance;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import com.aaronevans.paidtogo.R;

import androidx.fragment.app.Fragment;


public class FragmentDateBalance extends Fragment {

    Context context;
    LinearLayout ll_date;
    DatePicker picker;
    static final int DATE_DIALOG_ID = 1;

    public FragmentDateBalance() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.date_balance_layout, container, false);

//        ll_date = view.findViewById(R.id.ll_date);
//        ll_date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }



}