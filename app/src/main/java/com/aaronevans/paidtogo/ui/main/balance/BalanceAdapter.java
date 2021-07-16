package com.aaronevans.paidtogo.ui.main.balance;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.remote.response.balance_response.Payment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BalanceAdapter extends RecyclerView.Adapter<com.aaronevans.paidtogo.ui.main.balance.BalanceAdapter.MyViewHolder> {

    Activity mainactivity;
    com.aaronevans.paidtogo.ui.main.balance.BalanceAdapter.OnItemClickListener onItemClickListener;
    List<Payment> list;
    Context context;

    public void customNotify(List<Payment> payments) {
        this.list=payments;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public BalanceAdapter(List<Payment> numberList, Context ctx) {
        this.list = numberList;
        context = ctx;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_balance, parent, false);
        return new BalanceAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BalanceAdapter.MyViewHolder holder, final int position) {

        try {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

            Date date = sdf.parse(list.get(position).getUpdatedAt());
            DateFormat dateFormat = new SimpleDateFormat("MM/yyyy");
            String formattedDate = dateFormat.format(date);
            holder.tv_date.setText(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }



        holder.tv_coins.setText("Coins: "+list.get(position).getCoins());
        holder.tv_payment.setText("Amount: $"+list.get(position).getAmount());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_date, tv_payment,tv_coins;

        public MyViewHolder(View view) {
            super(view);
            tv_date = view.findViewById(R.id.tv_date);
            tv_payment = view.findViewById(R.id.tv_payment);
            tv_coins = view.findViewById(R.id.tv_coins);
        }
    }
}