package com.aaronevans.paidtogo.ui.main.balance;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.remote.response.balance_response.NewBalanceResponse;
import com.aaronevans.paidtogo.ui.BaseFragment;
import com.aaronevans.paidtogo.webService.Controller;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentBalancePayment extends BaseFragment implements Controller.response {

    Context context;
    LinearLayout ll_pastPayments_tab, ll_pending_tab, llpoints, llpro;

    RecyclerView mRecyclerViewPro, mRecyclerViewPoints;
    Controller controller;
    BalanceRequest balanceRequest;
    List<FullDatum> points_list, pro_list;
    SetData setData;
    NewBalanceResponse newBalanceResponse;
    private BalanceAdapter prizeListAdapter;

    public interface SetData {
        void passText(NewBalanceResponse data);
    }

    public FragmentBalancePayment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.balance_payment_layout, container, false);
        ll_pastPayments_tab = view.findViewById(R.id.ll_pastPayments_tab);
        ll_pending_tab = view.findViewById(R.id.ll_pending_tab);
        llpoints = view.findViewById(R.id.llpoints);
        llpro = view.findViewById(R.id.llpro);
        mRecyclerViewPro = view.findViewById(R.id.mRecyclerViewPro);
        mRecyclerViewPoints = view.findViewById(R.id.mRecyclerViewPoints);
        points_list = new ArrayList<>();
        pro_list = new ArrayList<>();
        setData = (SetData) context;

        callWebServiceBalance();

        ll_pastPayments_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_pastPayments_tab.setBackground(getResources().getDrawable(R.drawable.border_gray));
                ll_pending_tab.setBackground(getResources().getDrawable(R.drawable.border_darker_gray));
                //ll_pro_tab.setBackgroundColor(ContextCompat.getColor(context, R.color.gray_progress));

                setList(true);
            }
        });

        ll_pending_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_pending_tab.setBackground(getResources().getDrawable(R.drawable.border_gray));
                ll_pastPayments_tab.setBackground(getResources().getDrawable(R.drawable.border_darker_gray));
                //ll_points_tab.setBackgroundColor(ContextCompat.getColor(context, R.color.gray_progress));

                setList(false);
            }
        });
        return view;
    }

    private void callWebServiceBalance() {

        controller = new Controller();
        balanceRequest = new BalanceRequest();
        balanceRequest.month = getCurrentMonth();
        balanceRequest.year = getCurrentYear();
        balanceRequest.userId = "270";
        controller.start(balanceRequest, (Controller.response) FragmentBalancePayment.this, context);
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

    @Override
    public void passList(NewBalanceResponse newBalanceResponse) {
        this.newBalanceResponse=newBalanceResponse;
        setList(true);
        setData.passText(newBalanceResponse);
       /* myLog("103", "++passList++", fullDatumList.toString());

        if (fullDatumList.size() > 0) {
            for (int i = 0; i < fullDatumList.size(); i++) {
                switch (fullDatumList.get(i).poolId) {
                    case 288:
                        points_list.add(fullDatumList.get(i));
                        break;
                    case 326:
                        pro_list.add(fullDatumList.get(i));
                        break;
                }
            }
            setList();
        }
        setData.passText(String.valueOf(balancePojo.totalAmount), String.valueOf(balancePojo.totalPoints), String.valueOf(balancePojo.pendingPayment));*/
    }

    private void setList(boolean pastPayments) {
        /*llpoints.setVisibility(View.VISIBLE);
        llpro.setVisibility(View.GONE);*/
      /*  ll_points_tab.setBackground(getResources().getDrawable(R.drawable.border_gray));
        ll_pro_tab.setBackgroundColor(ContextCompat.getColor(context, R.color.gray_progress));*/
      if(pastPayments){

          mRecyclerViewPoints.setVisibility(View.GONE);
          mRecyclerViewPro.setVisibility(View.VISIBLE);

          if (newBalanceResponse.getPastPayments().size() > 0) {
              if(prizeListAdapter==null){

                  prizeListAdapter = new BalanceAdapter(newBalanceResponse.getPastPayments(), context);
                  mRecyclerViewPro.setLayoutManager(new LinearLayoutManager(context));
                  mRecyclerViewPro.setAdapter(prizeListAdapter);
              }else{
                  prizeListAdapter.customNotify(newBalanceResponse.getPastPayments());
              }

          }
      }else{
          mRecyclerViewPro.setVisibility(View.GONE);
          mRecyclerViewPoints.setVisibility(View.VISIBLE);

          if (newBalanceResponse.getPendingPayments().size() > 0) {


//              prizeListAdapter.customNotify(newBalanceResponse.getPendingPayments());
              prizeListAdapter = new BalanceAdapter(newBalanceResponse.getPendingPayments(), context);
              mRecyclerViewPoints.setLayoutManager(new LinearLayoutManager(context));
              mRecyclerViewPoints.setAdapter(prizeListAdapter);




          }
      }

    }
}