package com.aaronevans.paidtogo.ui.poolRules.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.aaronevans.paidtogo.R;

import java.util.List;

public class PoolRulesAdapter  extends PagerAdapter {
    FragmentActivity activity;

    List<String> listOfFirst;
    List<String> listOfSecond;


    public PoolRulesAdapter(FragmentActivity activity, List<String> listOfFirst, List<String> listOfSecond) {
        this.activity=activity;
        this.listOfFirst=listOfFirst;
        this.listOfSecond=listOfSecond;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return o==view;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.adapter_pools, null);
        RecyclerView mRecyclerView =view.findViewById(R.id.mRecyclerView);
        TextView textViewTitle =view.findViewById(R.id.textViewTitle);


        if(position==0){
            textViewTitle.setText("Paidtogo Pro Rules");
            ItemsAdapter adapter=new ItemsAdapter(listOfFirst);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
            mRecyclerView.setAdapter(adapter);

        }else{
            textViewTitle.setText("Paidtogo Free Pool Rules");
            ItemsAdapter adapter=new ItemsAdapter(listOfSecond);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
            mRecyclerView.setAdapter(adapter);
        }

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ConstraintLayout)object);
    }

}
