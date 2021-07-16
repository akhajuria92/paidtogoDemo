package com.aaronevans.paidtogo.ui.poolRules.adapter;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aaronevans.paidtogo.R;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {
    List<String> listOfFirst;

    public ItemsAdapter(List<String> listOfFirst) {
        this.listOfFirst=listOfFirst;
    }

    @NonNull
    @Override
    public ItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_items, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        SpannableString string = new SpannableString(listOfFirst.get(position));
//        string.setSpan(new BulletSpan(30, Color.BLACK, 15), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.textViewItem.setText(string);
    }

    @Override
    public int getItemCount() {
        return listOfFirst.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewItem=itemView.findViewById(R.id.textViewItem);
        }
    }

}
