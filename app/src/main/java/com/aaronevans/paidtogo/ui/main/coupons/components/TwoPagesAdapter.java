package com.aaronevans.paidtogo.ui.main.coupons.components;

import android.graphics.Rect;

import androidx.viewpager.widget.PagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by leandro on 21/11/17.
 */

public class TwoPagesAdapter extends PagerAdapter {

    RecyclerView rv1, rv2;

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        RecyclerView rv;
        if (position == 0) {
            if (rv1 == null) {
                rv1 = new RecyclerView(container.getContext());
                rv1.addItemDecoration(new MarginItemDecorator());
                rv1.setLayoutManager(new LinearLayoutManager(container.getContext(),LinearLayoutManager.VERTICAL,false));
                rv1.setAdapter(new FirstPageAdapter());
            }
            rv = rv1;
        } else {
            if (rv2 == null) {
                rv2 = new RecyclerView(container.getContext());
                rv2.addItemDecoration(new MarginItemDecorator());
                rv2.setLayoutManager(new LinearLayoutManager(container.getContext(),LinearLayoutManager.VERTICAL,false));
                rv2.setAdapter(new SecondPageAdapter());
            }
            rv = rv2;
        }
        container.addView(rv);
        return rv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        RecyclerView rv = (RecyclerView) object;
        container.removeView(rv);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return position == 0 ? "AVAILABLE" : "REDEEMED";
    }

    class MarginItemDecorator extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int index = parent.getChildAdapterPosition(view);
            outRect.bottom = index == parent.getAdapter().getItemCount() - 1 ? 24 : 0;
            outRect.top = outRect.left = outRect.right = 24;
        }
    }
}
