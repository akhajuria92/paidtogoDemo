package com.aaronevans.paidtogo.ui.main.prizeTable;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.ui.UpgradeToPro.UpgradeToProActivity;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import static com.aaronevans.paidtogo.ui.main.MainActivity.toolbar_image;
import static com.aaronevans.paidtogo.ui.main.MainActivity.toolbar_title;

@EFragment(R.layout.fragment_prize_table)
public class PrizeTableFragment extends Fragment implements Controller.response {

    @ViewById
    RelativeLayout ll_month, ll_year;
    Context context;
    String selected_year, selected_month;

    @ViewById
    ViewPager viewpager;

    @ViewById
    TextView tv_month, tv_year, tv_no_data;

    ArrayList<Fragment> arrayList;

    @ViewById
    LinearLayout ll_dot;
    TextView[] dot;

    Controller controller;

    @ViewById
    RecyclerView mRecyclerView;

    @Click(R.id.ll_month)
    public void showMonthPopUp()
    {
        //Creating the instance of PopupMenu
        PopupMenu popup = new PopupMenu(getActivity(), ll_month);
        popup.getMenuInflater().inflate(R.menu.month_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                Controller controller = new Controller();
                tv_month.setText(item.getTitle().toString());
                controller.start(selected_month, selected_year, (Controller.response) PrizeTableFragment.this, context);
                switch (String.valueOf(item.getItemId()))
                {
                    case "Jan":
                        selected_month = "1";
                        break;

                    case "Feb":
                        selected_month = "2";
                        break;

                    case "Mar":
                        selected_month = "3";
                        break;
                    case "Apr":
                        selected_month = "4";
                        break;

                    case "May":
                        selected_month = "5";
                        break;

                    case "June":
                        selected_month = "6";
                        break;

                    case "July":
                        selected_month = "7";
                        break;

                    case "Aug":
                        selected_month = "8";
                        break;

                    case "Sept":
                        selected_month = "9";
                        break;

                    case "Oct":
                        selected_month = "10";
                        break;

                    case "Nov":
                        selected_month = "11";
                        break;

                    case "Dec":
                        selected_month = "12";
                        break;

                }
                return true;
            }
        });
        popup.show();//showing popup menu
    }

    @Click(R.id.ll_year)
    public void showYearPopUp() {
        PopupMenu menu = new PopupMenu(context, ll_year);

        int year = 2020;
        for (int i = 1; i < 20; i++) {
            menu.getMenu().add(Menu.NONE, i, i, String.valueOf(year));
            year = year - 1;
        }

        menu.show();
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                selected_year = item.getTitle().toString();
                tv_year.setText(item.getTitle().toString());
                Controller controller = new Controller();
                controller.start(selected_month, selected_year, (Controller.response) PrizeTableFragment.this, context);
                return true;
            }
        });
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbar_image.setVisibility(View.GONE);
        toolbar_title.setVisibility(View.VISIBLE);
        toolbar_title.setText("Prize Table");
    }

    public void addDot(int page_position) {
        dot = new TextView[arrayList.size()];
        ll_dot.removeAllViews();

        for (int i = 0; i < dot.length; i++) {

            dot[i] = new TextView(context);
            dot[i].setText(Html.fromHtml("&#9673;"));
            dot[i].setTextSize(35);
            dot[i].setTextColor(getResources().getColor(R.color.gray_progress));// Inactive color
            ll_dot.addView(dot[i]);
        }

        //active dot
        dot[page_position].setTextColor(getResources().getColor(R.color.black));// active color
    }

    @AfterViews
    void setUpPager() {
        arrayList = new ArrayList<>();
        arrayList.add(new PricePagerFragmentOne());
        arrayList.add(new PricePagerFragmentOne());

        PrizePagerAdapter pagerAdapter = new PrizePagerAdapter(context, arrayList);
        viewpager.setAdapter(pagerAdapter);
        viewpager.setPageMargin(20);
        addDot(0);

        // whenever the page changes
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                addDot(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                Log.e("PageScrollStateChanged", "+++" + i);
                if (i == 1) {
                    showProAlert();
                }
            }
        });

        selected_month = String.valueOf(Calendar.getInstance().get(Calendar.MONTH));
        selected_year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));

        controller = new Controller();
        controller.start(selected_month, selected_year, (Controller.response) PrizeTableFragment.this, context);
    }

    @Override
    public void passList(List<PrizePojo> changesList) {
        if(changesList.size()>0)
        {
            tv_no_data.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            PrizeListAdapter prizeListAdapter = new PrizeListAdapter(changesList.get(0).prize, context);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            mRecyclerView.setAdapter(prizeListAdapter);
        }
        else
        {
            tv_no_data.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
    }

    public void showProAlert() {
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle("Paidtogo");

        // Setting Dialog Message
        alertDialog.setMessage("You are not subscribed to Pro");

        // On pressing Settings button
        alertDialog.setPositiveButton("Upgrade to Pro",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        moveNext();
                    }
                });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    private void moveNext() {
        Intent intent = new Intent(getActivity(), UpgradeToProActivity.class);
        startActivity(intent);
    }

}