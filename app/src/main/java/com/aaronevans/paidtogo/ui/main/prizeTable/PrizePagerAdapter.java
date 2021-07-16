package com.aaronevans.paidtogo.ui.main.prizeTable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aaronevans.paidtogo.R;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;

public class PrizePagerAdapter extends PagerAdapter {

    Context context;
    ArrayList<Fragment> pager;

    public PrizePagerAdapter(Context context, ArrayList<Fragment> pager) {
        this.context = context;
        this.pager = pager;
    }

    @Override
    public int getCount() {
        return pager.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public  Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.prize_table_pager_item_one, container, false);
//        ImageView imageView = view.findViewById(R.id.image);
//        imageView.setBackgroundResource(pager.get(position));
        container.addView(view);
        return view;
     }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
}