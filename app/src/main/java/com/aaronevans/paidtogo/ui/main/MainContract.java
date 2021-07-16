package com.aaronevans.paidtogo.ui.main;

import android.location.Location;
import com.aaronevans.paidtogo.data.entities.PoolType;
import com.aaronevans.paidtogo.ui.contracts.BasePresenter;
import com.aaronevans.paidtogo.ui.contracts.BaseView;
import com.aaronevans.paidtogo.ui.contracts.LoaderView;
import java.util.List;

/**
 * Created by evaristo on 26/12/16.
 */

public interface MainContract {

    interface View extends BaseView, LoaderView {
        void launchActivity(List<PoolType> poolTypes);

        void updateLocation(Location location);
    }

    interface Presenter extends BasePresenter<View, Presenter> {
        void getPoolTypes();

        void checkGps();

        void initLocation();

        void stopLocation();

        void logout();

    }
}
