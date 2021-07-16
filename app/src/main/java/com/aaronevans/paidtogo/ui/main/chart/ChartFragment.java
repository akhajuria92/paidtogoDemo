package com.aaronevans.paidtogo.ui.main.chart;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.entities.MyChartData;
import com.aaronevans.paidtogo.data.local.UserPreferences;
import com.aaronevans.paidtogo.data.mHandler;
import com.aaronevans.paidtogo.data.remote.PaidToGoService;
import com.aaronevans.paidtogo.data.remote.response.StatsResponse;
import com.aaronevans.paidtogo.ui.BaseFragment;
import com.aaronevans.paidtogo.ui.ComplaintContactActivity.MyProgressBar;
import com.aaronevans.paidtogo.ui.main.stats.MyValueFormatter;
import com.aaronevans.paidtogo.ui.main.stats.MyXAxisValueFormatter;
import com.aaronevans.paidtogo.ui.main.stats.MyYAxisValueFormatter;
import com.aaronevans.paidtogo.ui.main.stats.StatsAdapter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

import static com.aaronevans.paidtogo.ui.main.MainActivity.toolbar_title;

public class ChartFragment extends BaseFragment implements View.OnClickListener {

    BarChart chart;
    Button b1, b2, b3, b4, b5;
    TextView xText, yText;

    String span;
    double pool_id;


    private ProgressDialog mProgressDialog = null;
    MyProgressBar myProgressBar = null;

    ArrayList<StatsResponse> stats;
    Button btn_month,btn_week,btn_daily;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart, container, false);
        findViews(view);
        clickListener();
        return view;
    }

    private void findViews(View view) {


        btn_daily = view.findViewById(R.id.btn_daily);
        btn_month = view.findViewById(R.id.btn_month);
        btn_week = view.findViewById(R.id.btn_week);

        btn_week.setOnClickListener(this);
        btn_month.setOnClickListener(this);
        btn_daily.setOnClickListener(this);

        xText = (TextView) view.findViewById(R.id.Xtext);
        yText = (TextView) view.findViewById(R.id.Ytext);


        chart =  view.findViewById(R.id.chart);
        b1 = (Button) view.findViewById(R.id.b1);
        b2 = (Button) view.findViewById(R.id.b2);
        b3 = (Button) view.findViewById(R.id.b3);
        b4 = (Button) view.findViewById(R.id.b4);
        b5 = (Button) view.findViewById(R.id.b5);


        if (mHandler.reg_pools != null && mHandler.reg_pools.size() > 0) {
            pool_id = mHandler.reg_pools.get(0).getId();
            getData("daily");
        }

    /*    ((BaseActivity)getActivity()).setOnBackPressedListener(new BaseBackPressedListener(getActivity()){ public void doBack() {
          //  getActivity().finish();
            getActivity().getSupportFragmentManager().popBackStack();
        }});*/

        Spinner sp1 = (Spinner) view.findViewById(R.id.sp1);
        Spinner sp2 = (Spinner) view.findViewById(R.id.sp2);


        xText = (TextView) view.findViewById(R.id.Xtext);
        yText = (TextView) view.findViewById(R.id.Ytext);
        // Spinner Drop down elements
        List<String> items1 = new ArrayList<String>();
        items1.add("Daily");
        items1.add("Weekly");
        items1.add("Monthly");

        List<String> items2 = new ArrayList<String>();
        for (int i = 0; i < mHandler.reg_pools.size(); i++) {
            items2.add(mHandler.reg_pools.get(i).getName());
        }
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(), R.layout.toolbar_spinner_item_dropdown, items1);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getActivity(), R.layout.toolbar_spinner_item_dropdown, items2);

        // Drop down layout style - list view with radio button
        dataAdapter1.setDropDownViewResource(R.layout.spinner_dropdown);
        dataAdapter2.setDropDownViewResource(R.layout.spinner_dropdown);

        sp1.setSelection(0);
        sp2.setSelection(0);

        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {

                String get = parent.getItemAtPosition(i).toString();
                span = get;
                getData(get);

            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {
                //  Toast.makeText(getActivity(), "Please Select city From Top", Toast.LENGTH_SHORT).show();
            }
        });
        //

        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {

                String get = parent.getItemAtPosition(i).toString();
                span = get;
                pool_id = mHandler.reg_pools.get(i).getId();
                //getData(get);

            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {
                //  Toast.makeText(getActivity(), "Please Select city From Top", Toast.LENGTH_SHORT).show();
            }
        });

        sp1.setAdapter(dataAdapter1);
        sp2.setAdapter(dataAdapter2);

        if (sp1.getSelectedItemId() == 0) {
            yText.setText("Daily");
        } else if (sp1.getSelectedItemId() == 1) {
            yText.setText("Weekly");
        } else if (sp1.getSelectedItemId() == 2) {
            yText.setText("Monthly");
        }


    }


    void clickListener() {

        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                xText.setText("USD");

                b1.setBackgroundResource(R.drawable.ripple_medium_size_pressed);
                b1.setTextColor(getResources().getColor(R.color.colorPrimary));
                b2.setBackgroundResource(R.drawable.ripple_medium_size);
                b2.setTextColor(getResources().getColor(R.color.black));
                b3.setBackgroundResource(R.drawable.ripple_medium_size);
                b3.setTextColor(getResources().getColor(R.color.black));
                b4.setBackgroundResource(R.drawable.ripple_medium_size);
                b4.setTextColor(getResources().getColor(R.color.black));
                b5.setBackgroundResource(R.drawable.ripple_medium_size);
                b5.setTextColor(getResources().getColor(R.color.black));

                loadchart(0, "Money Earned");
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
               /* if(SettingsPreferences.isMilesKm(getActivity())){
                    xText.setText("Kilometers");
                   /// mBinding.mDailyBicycleText.setText("Kilometers traveled");
                }else{
                    xText.setText("Miles");
                }*/
                xText.setText("Miles");

                b2.setBackgroundResource(R.drawable.ripple_medium_size_pressed);
                b2.setTextColor(getResources().getColor(R.color.colorPrimary));
                b1.setBackgroundResource(R.drawable.ripple_medium_size);
                b1.setTextColor(getResources().getColor(R.color.black));
                b3.setBackgroundResource(R.drawable.ripple_medium_size);
                b3.setTextColor(getResources().getColor(R.color.black));
                b4.setBackgroundResource(R.drawable.ripple_medium_size);
                b4.setTextColor(getResources().getColor(R.color.black));
                b5.setBackgroundResource(R.drawable.ripple_medium_size);
                b5.setTextColor(getResources().getColor(R.color.black));
                loadchart(1, "Distance Traveled");
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                xText.setText("Cal");
                b3.setBackgroundResource(R.drawable.ripple_medium_size_pressed);
                b3.setTextColor(getResources().getColor(R.color.colorPrimary));
                b1.setBackgroundResource(R.drawable.ripple_medium_size);
                b1.setTextColor(getResources().getColor(R.color.black));
                b2.setBackgroundResource(R.drawable.ripple_medium_size);
                b2.setTextColor(getResources().getColor(R.color.black));
                b4.setBackgroundResource(R.drawable.ripple_medium_size);
                b4.setTextColor(getResources().getColor(R.color.black));
                b5.setBackgroundResource(R.drawable.ripple_medium_size);
                b5.setTextColor(getResources().getColor(R.color.black));
                loadchart(2, "Calories Burned");
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xText.setText("Lbs");
                b4.setBackgroundResource(R.drawable.ripple_medium_size_pressed);
                b4.setTextColor(getResources().getColor(R.color.colorPrimary));
                b1.setBackgroundResource(R.drawable.ripple_medium_size);
                b1.setTextColor(getResources().getColor(R.color.black));
                b2.setBackgroundResource(R.drawable.ripple_medium_size);
                b2.setTextColor(getResources().getColor(R.color.black));
                b3.setBackgroundResource(R.drawable.ripple_medium_size);
                b3.setTextColor(getResources().getColor(R.color.black));
                b5.setBackgroundResource(R.drawable.ripple_medium_size);
                b5.setTextColor(getResources().getColor(R.color.black));
                loadchart(3, "CO2 OffSet");
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xText.setText("Steps");
                b5.setBackgroundResource(R.drawable.ripple_medium_size_pressed);
                b5.setTextColor(getResources().getColor(R.color.colorPrimary));
                b1.setBackgroundResource(R.drawable.ripple_medium_size);
                b1.setTextColor(getResources().getColor(R.color.black));
                b2.setBackgroundResource(R.drawable.ripple_medium_size);
                b2.setTextColor(getResources().getColor(R.color.black));
                b3.setBackgroundResource(R.drawable.ripple_medium_size);
                b3.setTextColor(getResources().getColor(R.color.black));
                b4.setBackgroundResource(R.drawable.ripple_medium_size);
                b4.setTextColor(getResources().getColor(R.color.black));
                loadchart(4, "Total Steps");
            }
        });


    }


    public void loadchart(int type, String label) {
        if (stats != null) {
            ArrayList<StatsResponse> statsTemp = new ArrayList<>();
            statsTemp.addAll(stats);
            Collections.reverse(statsTemp);
            List<MyChartData> dataObjects = new ArrayList<>();


            ArrayList<BarEntry> values1 = new ArrayList<>();

            if (type == 0) {

                for (int i = 0; i < statsTemp.size(); i++) {
                    values1.add(new BarEntry(i,statsTemp.get(i).getEarnedPoints()));
                }

            } else if (type == 1) {

                for (int i = 0; i < statsTemp.size(); i++) {
                    values1.add(new BarEntry(i,statsTemp.get(i).getMilesTraveled()));
                }

            } else if (type == 2) {
                for (int i = 0; i < statsTemp.size(); i++) {
                    values1.add(new BarEntry(i,statsTemp.get(i).getSavedCalories()));
                }
            } else if (type == 3) {

                for (int i = 0; i < statsTemp.size(); i++) {
                    values1.add(new BarEntry(i,statsTemp.get(i).getSavedCo2()));
                }
            } else if (type == 4) {

                for (int i = 0; i < statsTemp.size(); i++) {
                    dataObjects.add(new MyChartData(i, statsTemp.get(i).getTotalSteps()));
                }
            }
            else
            {
                for (int i = 0; i < statsTemp.size(); i++) {
                    values1.add(new BarEntry(i,statsTemp.get(i).getTotalSteps()));
                }

            }


            YAxis left = chart.getAxisLeft();
            left.setValueFormatter(new MyYAxisValueFormatter());

            left.setGranularity(1f);
      /*

        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);

        DateFormat dateFormat = new SimpleDateFormat("MMM/dd");
        String date = dateFormat.format(cal.getTime());*/


            String[] values = new String[0];
            if (stats.size() == 7) {
                values = new String[7];
                for (int q = stats.size() - 1; q >= 0; q--) {

                    Calendar cal = Calendar.getInstance();

                    cal.add(Calendar.DATE, -q - 1);

                    DateFormat dateFormat = new SimpleDateFormat("dd-MMM");
                    DateFormat weekformat = new SimpleDateFormat("EEE");
                    String date = dateFormat.format(cal.getTime());
                    String day = weekformat.format(cal.getTime());

//                    values[7 - (q + 1)] = date;

                    values[7 - (q + 1)] = day;

                     cal = GregorianCalendar.getInstance();
                    cal.setTime(new Date());
                    cal.add(Calendar.DAY_OF_YEAR, -7);
                    Date weelk = cal.getTime();

                }
                // values = new String[]{"18 Oct", "19Oct", "20Oct", "21Oct", "22Oct", "23Oct", "24Oct"};
            } else if (stats.size() == 4) {
                values = new String[4];
                int lcheck = 0;


                for (int q = stats.size() - 1; q >= 0; q--) {

                    final Calendar cal = Calendar.getInstance();

                    cal.add(Calendar.DATE, -q * 7);

                    DateFormat dateFormat = new SimpleDateFormat("dd-MMM");
                    String date = dateFormat.format(cal.getTime());

                    values[4 - (q + 1)] = date;
                }

                /*for (int q = 27; q >= 0; q = q - 7) {

                    final Calendar cal = Calendar.getInstance();

                    cal.add(Calendar.DATE, -q);

                    DateFormat dateFormat = new SimpleDateFormat("dd-MMM");
                    String date = dateFormat.format(cal.getTime());

                    values[lcheck] = date;
                    lcheck++;

                }*/

                //values = new String[]{"18 Oct", "19Oct", "20Oct", "21Oct", "22Oct", "23Oct", "24Oct"};
            } else if (stats.size() == 12) {


                /*for (int q = stats.size() - 1; q >= 0; q--) {

                    final Calendar cal = Calendar.getInstance();

                    cal.add(Calendar.DATE, -q*30);

                    DateFormat dateFormat = new SimpleDateFormat("MMM-yy");
                    String date = dateFormat.format(cal.getTime());

                    values[12 - (q + 1)] = date;
                }*/

                values = new String[12];
                for (int q = stats.size() - 1; q >= 0; q--) {

                    final Calendar cal = Calendar.getInstance();

                    cal.add(Calendar.MONTH, -q);

                    DateFormat dateFormat = new SimpleDateFormat("MMM-yy");
                    String date = dateFormat.format(cal.getTime());

                    values[12 - (q + 1)] = date;

                }
                //values = new String[]{"18 Oct", "19Oct", "20Oct", "21Oct", "22Oct", "23Oct", "24Oct"};
            }
            chart.getAxisRight().setEnabled(false);

            XAxis xAxis = chart.getXAxis();
            xAxis.setValueFormatter(new MyXAxisValueFormatter(values));
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setAxisMinimum(0.0f);
            xAxis.setGranularity(1f);

            chart.getAxisLeft().setDrawGridLines(false);
            xAxis.setDrawGridLines(false);


            List<Entry> entries = new ArrayList<Entry>();


            //Collections.reverse(dataObjects);

            for (MyChartData data : dataObjects) {

                // turn your data into Entry objects
                entries.add(new Entry(data.getxAxis(), data.getyAxis()));
            }


            LineDataSet dataSet = new LineDataSet(entries, label); // add entries to dataset
            dataSet.setValueFormatter(new MyValueFormatter());
            //int[] colors = new int[] {R.color.green_balance,R.color.green_balance};
            //   int[] colorsradius = new int[] {R.color.green_balance,R.color.green_balance};
            int color = ContextCompat.getColor(getContext(), R.color.green_balance);

            dataSet.setColor(color);
            dataSet.setCircleColor(color);
            dataSet.setCircleRadius(4);
            dataSet.setLineWidth(2f);

            //code comment by sanjeev
//            LineData lineData = new LineData(dataSet);
//            chart.setDescription(null);
//            chart.setData(lineData);
//            chart.invalidate();

            // add code for  chart
            setData(values1);
            chart.invalidate();


        }
    }

    private void setData(ArrayList<BarEntry> values1) {

        BarDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values1);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();

        } else {
            set1 = new BarDataSet(values1, "Total Steps");

            set1.setDrawIcons(false);

            set1.setColor(getResources().getColor(R.color.app_green));





            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);



            data.setBarWidth(0.9f);

            chart.setData(data);

        }
    }

    public void getData(String type) {


        showProgressDialog();

        if (type.equalsIgnoreCase("daily")) {

            PaidToGoService
                    .getServiceClient()
                    .getStatsDaily(UserPreferences.getUser(getContext()).getId(), type, String.valueOf(pool_id))
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            this::loadNestedActivity,
                            this::onError

                    );
        } else if (type.equalsIgnoreCase("weekly")) {
            PaidToGoService
                    .getServiceClient()
                    .getStatsWeekly(UserPreferences.getUser(getContext()).getId(), type, String.valueOf(pool_id))
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            this::loadNestedActivity,
                            this::onError
                    );
        } else if (type.equalsIgnoreCase("monthly")) {
            PaidToGoService
                    .getServiceClient()
                    .getStatsMonthly(UserPreferences.getUser(getContext()).getId(), type, String.valueOf(pool_id))
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            this::loadNestedActivity,
                            this::onError
                    );
        }
    }


    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
        }
        mProgressDialog.setTitle(getResources().getString(R.string.app_name));
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }


    public void loadNestedActivity(ArrayList<StatsResponse> userResponse) {
        hideProgressDialog();
        stats = new ArrayList<>();
        stats.addAll(userResponse);


        b1.performClick();

        progressBar_status();

    }


    protected void progressBar_status() {
        if (myProgressBar != null) {
            myProgressBar.dismiss();
            myProgressBar = null;
        }
    }

    public void onError(Throwable throwable) {
        hideProgressDialog();
        if (throwable instanceof HttpException) {
            //  mView.showErrorAlert(PaidToGoService.attendError((HttpException) throwable).getDetail());
        } else {
            // mView.showErrorAlert(mView.getContext().getString(R.string.connection_problem));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_week:
                yText.setText("Weekly");
                getData("weekly");
                btn_week.setTextColor(getResources().getColor(R.color.white));
                btn_month.setTextColor(getResources().getColor(R.color.app_green));
                btn_daily.setTextColor(getResources().getColor(R.color.app_green));

                btn_week.setBackgroundResource(R.drawable.green_solid);
                btn_daily.setBackgroundResource(R.drawable.green_boarder);
                btn_month.setBackgroundResource(R.drawable.green_boarder);
                break;
            case R.id.btn_daily:
                getData("daily");
                yText.setText("Daily");


                btn_week.setTextColor(getResources().getColor(R.color.app_green));
                btn_month.setTextColor(getResources().getColor(R.color.app_green));
                btn_daily.setTextColor(getResources().getColor(R.color.white));

                btn_week.setBackgroundResource(R.drawable.green_boarder);
                btn_daily.setBackgroundResource(R.drawable.green_solid);
                btn_month.setBackgroundResource(R.drawable.green_boarder);
                break;
            case R.id.btn_month:
                getData("monthly");

                yText.setText("Monthly");



                btn_week.setTextColor(getResources().getColor(R.color.app_green));
                btn_month.setTextColor(getResources().getColor(R.color.white));
                btn_daily.setTextColor(getResources().getColor(R.color.app_green));

                btn_week.setBackgroundResource(R.drawable.green_boarder);
                btn_daily.setBackgroundResource(R.drawable.green_boarder);
                btn_month.setBackgroundResource(R.drawable.green_solid);
                break;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
          String  title = bundle.getString("title", "");
            toolbar_title.setText(title);
        }
    }

}
