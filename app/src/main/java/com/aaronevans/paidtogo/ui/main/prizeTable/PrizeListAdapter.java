package com.aaronevans.paidtogo.ui.main.prizeTable;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aaronevans.paidtogo.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class PrizeListAdapter  extends RecyclerView.Adapter<PrizeListAdapter.MyViewHolder>
{

    Activity mainactivity;
    PrizeListAdapter.OnItemClickListener onItemClickListener;
    List<PrizePojo.Prize> list;
    Context context;

    public interface OnItemClickListener
    {
        void onItemClick(View v, int position);
    }

    public PrizeListAdapter(List<PrizePojo.Prize> numberList, Context ctx)
    {
        this.list = numberList;
        context = ctx;
    }

    @Override
    public PrizeListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prize_list, parent, false);
        return new PrizeListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PrizeListAdapter.MyViewHolder holder, final int position)
    {

        String str = list.get(position).amount.toString();
        double d = Double.valueOf(str).doubleValue();
        String amount = String.format("%.2f",d);

        holder.mProfileNameText.setText("$" + amount);

        holder.tv_ponits.setText("("+list.get(position).name +")");

        int temp = position + 1;
        holder.tv_position.setText(String.valueOf(temp));
        if (temp % 10 == 1 && temp % 100 != 11) {
            holder.mCardinalText.setText("st ");
        } else if (temp % 10 == 2 && temp % 100 != 12) {
            holder.mCardinalText.setText("nd");
        } else if (temp % 10 == 3 && temp % 100 != 13) {
            holder.mCardinalText.setText("rd ");
        } else {
            holder.mCardinalText.setText("th ");
        }

//        holder.ll_item_back.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                onItemClickListener.onItemClick(v, position);
//            }
//        });
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mProfileNameText,tv_ponits,tv_position,mCardinalText;
        public MyViewHolder(View view)
        {
            super(view);
            mProfileNameText=  view.findViewById(R.id.mProfileNameText);
            tv_ponits=  view.findViewById(R.id.mPointsText);
            tv_position=  view.findViewById(R.id.mPositionText);
            mCardinalText=  view.findViewById(R.id.mCardinalText);
        }
    }
}