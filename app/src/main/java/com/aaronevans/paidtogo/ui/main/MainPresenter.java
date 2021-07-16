package com.aaronevans.paidtogo.ui.main;

import android.Manifest;
import android.app.Activity;
import android.location.Location;
import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.entities.PoolType;
import com.aaronevans.paidtogo.data.local.UserPreferences;
import com.aaronevans.paidtogo.data.remote.PaidToGoService;
import com.tbruyelle.rxpermissions2.RxPermissions;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider;

/**
 * Created by evaristo on 01/02/17.
 */

public class MainPresenter implements MainContract.Presenter {
    final static int REQUEST_CHECK_SETTINGS = 0;

    MainContract.View mView;
    ReactiveLocationProvider locationProvider;
    Observable<Location> lastKnownLocationObservable;
    Observable<Location> locationUpdatesObservable;
    private Disposable lastKnownLocationSubscription;
    private Disposable updatableLocationSubscription;

    @Override
    public void checkGps() {
        new RxPermissions((Activity) mView.getContext())
                .request(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACTIVITY_RECOGNITION
                )
                .subscribeOn(Schedulers.trampoline())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        granted -> {
                            if (granted) onLocationPermissionGranted();
                            else mView.showErrorAlert(mView.getContext()
                                    .getString(R.string.missing_permissions));
                        }
                );
    }

    private void onLocationPermissionGranted() {
        lastKnownLocationSubscription = lastKnownLocationObservable
                .subscribe(
                        this::updateLocation,
                        this::locationError
                );

        updatableLocationSubscription = locationUpdatesObservable
                .subscribe(
                        this::updateLocation,
                        this::locationError
                );
    }

    private void updateLocation(Location location) {
        mView.updateLocation(location);
    }

    private void locationError(Throwable throwable) {
        mView.showErrorAlert(throwable.getMessage());
    }

    @Override
    public void initLocation() {
        /*locationProvider = new ReactiveLocationProvider(mView.getContext());
        lastKnownLocationObservable = locationProvider.getLastKnownLocation();

        final LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(30000);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationUpdatesObservable = locationProvider
                .checkLocationSettings(
                        new LocationSettingsRequest.Builder()
                                .addLocationRequest(locationRequest)
                                .setAlwaysShow(true)
                                .build()
                )
                .doOnNext(locationSettingsResult -> {
                    Status status = locationSettingsResult.getStatus();
                    if (status.getStatusCode() == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                        try {
                            status.startResolutionForResult((Activity) mView.getContext(), REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException th) {
                            Log.e("MainActivity", "Error opening settings activity.", th);
                        }
                    }
                })
                .flatMap(locationSettingsResult -> locationProvider.getUpdatedLocation(locationRequest));*/
    }

    @Override
    public void stopLocation() {
        if (lastKnownLocationSubscription != null)
            lastKnownLocationSubscription.dispose();
        if (updatableLocationSubscription != null)
            updatableLocationSubscription.dispose();
    }

    @Override
    public void logout() {
        UserPreferences.removeUser(mView.getContext());


    }

    @Override
    public void getPoolTypes() {
        mView.showProgressDialog();

        PaidToGoService.getServiceClient()
                .getPoolTypes()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::onSuccess,
                        this::onError
                );
    }

    private void onError(Throwable throwable) {
        mView.hideProgressDialog();
    }

    private void onSuccess(List<PoolType> types) {
        mView.hideProgressDialog();
        mView.launchActivity(types);
    }

    @Override
    public MainContract.Presenter start(MainContract.View view) {
        mView = view;
        return this;
    }

    @Override
    public void destroy() {

    }
}
