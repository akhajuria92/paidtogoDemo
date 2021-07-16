package com.aaronevans.paidtogo.ui.main.home;

import android.content.Context;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableFloat;

import com.aaronevans.paidtogo.data.entities.Activity;
import com.aaronevans.paidtogo.data.remote.request.RegisterActivity;
import com.aaronevans.paidtogo.data.remote.response.PoolResponse;
import com.aaronevans.paidtogo.ui.contracts.BasePresenter;
import com.aaronevans.paidtogo.ui.contracts.BaseView;
import com.aaronevans.paidtogo.ui.contracts.LoaderView;

import org.androidannotations.api.builder.IntentBuilder;

import java.util.ArrayList;

/**
 * Created by leandro on 2/11/17.
 */

public interface HomeContract {

    interface Presenter extends BasePresenter<View, Presenter> {
        void loadActivities(String userId);

        void registerActivity(RegisterActivity activity);
    }

    interface ViewModel {
        void setContext(Context context);

        void onAddClick();

        io.reactivex.Observable<IntentBuilder> getBuilders();

        ObservableBoolean getIsAddGone();

        ObservableBoolean getAreStatsGone();

        ObservableArrayList<PoolResponse> getExercisesVM();

        ObservableFloat getShowAddProgress();

        View.OnPageScrollListener getScrollListener();

    }

    interface View extends BaseView, LoaderView {
        void onActivitiesResponse(ArrayList<PoolResponse> listActivities);

        void onRegisterActivity(Activity activity);

        interface OnPageScrollListener {
            void onNewScroll(int position, float offset);

            void onShowingItem(int position);
        }

    }

}
