package com.aaronevans.paidtogo.ui.main.settings;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;

import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.background_service.SensorService;
import com.aaronevans.paidtogo.components.GeneralInterface;
import com.aaronevans.paidtogo.data.local.SettingsPreferences;
import com.aaronevans.paidtogo.data.local.UserPreferences;
import com.aaronevans.paidtogo.data.remote.PaidToGoService;
import com.aaronevans.paidtogo.databinding.FragmentMainSettingsBinding;
import com.aaronevans.paidtogo.ui.BaseActivity;
import com.aaronevans.paidtogo.ui.BaseFragment;
import com.aaronevans.paidtogo.ui.main.settings.components.SettingsAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.BindingObject;
import org.androidannotations.annotations.DataBound;
import org.androidannotations.annotations.EFragment;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

import static com.aaronevans.paidtogo.ui.main.MainActivity.toolbar_image;
import static com.aaronevans.paidtogo.ui.main.MainActivity.toolbar_title;

/**
 * Created by leandro on 22/11/17.
 */

@DataBound
@EFragment(R.layout.fragment_main_settings)
public class SettingsFragment extends BaseFragment {
    private ProgressDialog mProgressDialog = null;
    @BindingObject
    FragmentMainSettingsBinding mBinding;

    @AfterViews
    public void setup() {

        BaseActivity baseActivity = (BaseActivity) getActivity();
        mBinding.mRecyclerView.setAdapter(new SettingsAdapter(baseActivity, new GeneralInterface.OnSettingsClickListener() {
            @Override
            public void onSettingsClick(SettingsAdapter.SETTINGS settingsType, boolean isChecked) {
                switch (settingsType)
                {
                    case AUTO_TRACKER:
                        SettingsPreferences.setAutoTracking(getContext(), isChecked);
                        if (isChecked) {
                            //baseActivity.startService(new Intent(baseActivity, SensorService.class).putExtra("title", "Paidtogo").putExtra("message", "Service is running in background"));
                           // baseActivity.mstartService();
                        } else {
                           // baseActivity.stopService(new Intent(baseActivity, SensorService.class));
                           // baseActivity.mstopService();
                        }
                        break;

                    case MILES_KM:
                        if(isChecked){
                            updateDistanceUnit(2);
                        }else{
                            updateDistanceUnit(1);
                        }

                        SettingsPreferences.setMilesKm(getContext(), isChecked);
                        break;
                }
            }
        }));
        mBinding.mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mBinding.mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbar_image.setVisibility(View.GONE);
        toolbar_title.setVisibility(View.VISIBLE);
        toolbar_title.setText("Settings");
    }




    void updateDistanceUnit(int type) {

        showProgressDialog();

        PaidToGoService
                .getServiceClient()
                .changeDistanceUnit(UserPreferences.getUser(getActivity()).getId(),type+"")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        hideProgressDialog();
                        showDialog("Unit of measurement successfully changed ");
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        hideProgressDialog();
                        onError(t);
                    }
                });
    }



    public void showProgressDialog() {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setTitle(getResources().getString(R.string.app_name));
        mProgressDialog.show();
    }


    public void hideProgressDialog() {
        if (mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }


    public void showErrorAlert(String msg) {
        new AlertDialog.Builder(getContext())
                .setTitle(getResources().getString(R.string.app_name))
                .setMessage(msg)
                .setPositiveButton(R.string.alert_accept, null)
                .create()
                .show();
    }

    public void onError(Throwable throwable) {
        if (throwable instanceof HttpException) {
            showErrorAlert(PaidToGoService.attendError((HttpException) throwable).getDetail());
        } else {
            showErrorAlert(getContext().getString(R.string.connection_problem));
        }
    }


}