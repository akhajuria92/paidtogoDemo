package com.aaronevans.paidtogo.ui.main.stats;

import android.app.ProgressDialog;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.aaronevans.paidtogo.data.local.SettingsPreferences;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.entities.MyChartData;
import com.aaronevans.paidtogo.data.local.UserPreferences;
import com.aaronevans.paidtogo.data.mHandler;
import com.aaronevans.paidtogo.data.remote.PaidToGoService;
import com.aaronevans.paidtogo.data.remote.response.StatsResponse;
import com.aaronevans.paidtogo.ui.BaseFragment;
import com.aaronevans.paidtogo.ui.ComplaintContactActivity.MyProgressBar;
import com.aaronevans.paidtogo.ui.main.MainActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

import static com.aaronevans.paidtogo.ui.main.MainActivity.toolbar_image;
import static com.aaronevans.paidtogo.ui.main.MainActivity.toolbar_image_new;
import static com.aaronevans.paidtogo.ui.main.MainActivity.layout_status;
import static com.aaronevans.paidtogo.ui.main.MainActivity.toolbar_title;

public class UserStatsFragment extends BaseFragment {
    private ProgressDialog mProgressDialog = null;
    LineChart chart;
    Button b1, b2, b3, b4, b5;
    TextView xText, yText;

    UserStatsContract.Presenter mPresenter;
    ArrayList<StatsResponse> stats;
    boolean check = false;
    String span;
    double pool_id;

    RecyclerView mRecyclerView;
    MyProgressBar myProgressBar = null;

    LinearLayout ll_graph;
    private String filterType;

    public  String layout_status = "list";

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (myProgressBar != null) {
            myProgressBar.dismiss();
            myProgressBar = null;
        }
    }

    protected void progressBar_status() {
        if (myProgressBar != null) {
            myProgressBar.dismiss();
            myProgressBar = null;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootview = inflater.inflate(R.layout.fragment_main_stats, container, false);
        if (mHandler.reg_pools != null && mHandler.reg_pools.size() > 0) {
            pool_id = mHandler.reg_pools.get(0).getId();
            getData("daily");
        }

    /*    ((BaseActivity)getActivity()).setOnBackPressedListener(new BaseBackPressedListener(getActivity()){ public void doBack() {
          //  getActivity().finish();
            getActivity().getSupportFragmentManager().popBackStack();
        }});*/

        Spinner sp1 = (Spinner) rootview.findViewById(R.id.sp1);
        Spinner sp2 = (Spinner) rootview.findViewById(R.id.sp2);

        ll_graph=  rootview.findViewById(R.id.ll_graph);

        xText = (TextView) rootview.findViewById(R.id.Xtext);
        yText = (TextView) rootview.findViewById(R.id.Ytext);
        // Spinner Drop down elements
        List<String> items1 = new ArrayList<String>();
        items1.add("Daily");
        items1.add("Weekly");
        items1.add("Monthly");

        List<String> items2 = new ArrayList<String>();

        if (mHandler.reg_pools!=null&&mHandler.reg_pools.size()>0)
        {
            for (int i = 0; i < mHandler.reg_pools.size(); i++) {
                items2.add(mHandler.reg_pools.get(i).getName());
            }
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

        chart = (LineChart) rootview.findViewById(R.id.chart);
        b1 = (Button) rootview.findViewById(R.id.b1);
        b2 = (Button) rootview.findViewById(R.id.b2);
        b3 = (Button) rootview.findViewById(R.id.b3);
        b4 = (Button) rootview.findViewById(R.id.b4);
        b5 = (Button) rootview.findViewById(R.id.b5);

        mRecyclerView =  rootview.findViewById(R.id.mRecyclerView);


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
        //  RecyclerView mRecyclerView = (RecyclerView) rootview.findViewById(R.id.re);
        //  UserActivityContract.Presenter mPresenter;

        // UserStatsContract.ViewModel mViewModel = new UserActivityViewModel();

        ((MainActivity)getActivity()).updateApi(new MainActivity.UpdateFrag() {
            @Override
            public void updatefrag(String status) {
                switch (status)
                {
                    case "list" :
                        ll_graph.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        break;

                    case "graph":
                        ll_graph.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.GONE);
                        break;
                }
            }
        });
        return rootview;
    }

    public void loadNestedActivity(ArrayList<StatsResponse> userResponse)
    {
        hideProgressDialog();
        stats = new ArrayList<>();
        stats.addAll(userResponse);


        if (check == false) {
            b1.performClick();
        }

        mRecyclerView.setAdapter(new StatsAdapter(getActivity(),userResponse,filterType));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        progressBar_status();

    }

    public void onError(Throwable throwable) {
          hideProgressDialog();
        if (throwable instanceof HttpException) {
            //  mView.showErrorAlert(PaidToGoService.attendError((HttpException) throwable).getDetail());
        } else {
            // mView.showErrorAlert(mView.getContext().getString(R.string.connection_problem));
        }
    }

    public void loadchart(int type, String label) {
        if (stats != null) {
            ArrayList<StatsResponse> statsTemp=new ArrayList<>();
            statsTemp.addAll(stats);
            Collections.reverse(statsTemp);
            List<MyChartData> dataObjects = new ArrayList<>();
            if (type == 0) {
                for (int i = 0; i < statsTemp.size(); i++) {
                    dataObjects.add(new MyChartData(i, statsTemp.get(i).getEarnedPoints()));
                }
            } else if (type == 1) {
                for (int i = 0; i < statsTemp.size(); i++) {
                    dataObjects.add(new MyChartData(i, statsTemp.get(i).getMilesTraveled()));
                }
            } else if (type == 2) {
                for (int i = 0; i < statsTemp.size(); i++) {
                    dataObjects.add(new MyChartData(i, statsTemp.get(i).getSavedCalories()));
                }
            } else if (type == 3) {
                for (int i = 0; i < statsTemp.size(); i++) {
                    dataObjects.add(new MyChartData(i, statsTemp.get(i).getSavedCo2()));
                }
            } else if (type == 4) {
                for (int i = 0; i < statsTemp.size(); i++) {
                    dataObjects.add(new MyChartData(i, statsTemp.get(i).getTotalSteps()));
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

                    final Calendar cal = Calendar.getInstance();

                    cal.add(Calendar.DATE, -q-1);

                    DateFormat dateFormat = new SimpleDateFormat("dd-MMM");
                    String date = dateFormat.format(cal.getTime());

                    values[7 - (q + 1)] = date;
                }
                // values = new String[]{"18 Oct", "19Oct", "20Oct", "21Oct", "22Oct", "23Oct", "24Oct"};
            } else if (stats.size() == 4) {
                values = new String[4];
                int lcheck = 0;


                for (int q = stats.size() - 1; q >= 0; q--) {

                    final Calendar cal = Calendar.getInstance();

                    cal.add(Calendar.DATE, -q*7);

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
            xAxis.setAxisMinimum(0.0f);
            xAxis.setGranularity(1f);
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
            LineData lineData = new LineData(dataSet);

            chart.setDescription(null);
            chart.setData(lineData);
            chart.invalidate();
        }
    }

    public void showProgressDialog() {
        if(mProgressDialog==null)
        {
            mProgressDialog = new ProgressDialog(getActivity());
        }
        mProgressDialog.setTitle(getResources().getString(R.string.app_name));
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    public void getData(String type)
    {

        filterType=type;

        showProgressDialog();

        if (type.equalsIgnoreCase("daily"))
        {

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

    private void  showLoading()
    {

    }

    @Override
    public void onResume() {
        super.onResume();
        toolbar_image_new.setVisibility(View.VISIBLE);
        toolbar_image.setVisibility(View.GONE);
        toolbar_title.setVisibility(View.VISIBLE);
        toolbar_title.setText("Stats");



        toolbar_image_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(layout_status=="list"){
                    toolbar_image_new.setImageResource(R.drawable.ic_list);
                    layout_status = "graph";
                    ll_graph.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);

                }else{
                    toolbar_image_new.setImageResource(R.drawable.activity_graph);
                    layout_status = "list";

                    ll_graph.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                }

            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        toolbar_image_new.setVisibility(View.GONE);
        if (myProgressBar != null) {
            myProgressBar.dismiss();
            myProgressBar = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        layout_status="list";
    }


}