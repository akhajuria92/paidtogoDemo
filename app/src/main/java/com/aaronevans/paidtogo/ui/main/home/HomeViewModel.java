package com.aaronevans.paidtogo.ui.main.home;

import android.content.Context;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableFloat;

import com.aaronevans.paidtogo.data.remote.response.PoolResponse;
import com.aaronevans.paidtogo.ui.inviteorg.InviteOrganizationActivity_;

import org.androidannotations.api.builder.IntentBuilder;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import io.reactivex.subjects.PublishSubject;

/**
 * Created by leandro on 2/11/17.
 */

public class HomeViewModel implements HomeContract.ViewModel {

    private final ObservableBoolean mIsAddGone = new ObservableBoolean(false);
    private final ObservableBoolean mAreStatsGone = new ObservableBoolean(false);
    private final ObservableArrayList<PoolResponse> listActivities = new ObservableArrayList<>();
    private final ObservableFloat mShowAddProgress = new ObservableFloat(0);
    private final PublishSubject<IntentBuilder> mBuilders = PublishSubject.create();
    WeakReference<Context> contextWeakReference;

    public HomeViewModel(ArrayList<PoolResponse> listActivities) {
        contextWeakReference = new WeakReference<>(null);
        this.listActivities.clear();
        this.listActivities.addAll(listActivities);
    }

    @Override
    public void setContext(Context context) {
        contextWeakReference = new WeakReference<>(context);
    }

    @Override
    public void onAddClick() {
        if (contextWeakReference.get() != null) {
            mBuilders.onNext(InviteOrganizationActivity_.intent(contextWeakReference.get()));
        }
    }

    @Override
    public io.reactivex.Observable<IntentBuilder> getBuilders() {
        return mBuilders;
    }

    @Override
    public ObservableBoolean getIsAddGone() {
        return mIsAddGone;
    }

    @Override
    public ObservableBoolean getAreStatsGone() {
        return mAreStatsGone;
    }

    @Override
    public ObservableArrayList<PoolResponse> getExercisesVM() {
        return listActivities;
    }

    @Override
    public ObservableFloat getShowAddProgress() {
        return mShowAddProgress;
    }

    public void setShowAddProgress(int showAddProgress) {
        this.mShowAddProgress.set(showAddProgress);
    }

    @Override
    public HomeContract.View.OnPageScrollListener getScrollListener() {
        return new HomeContract.View.OnPageScrollListener() {
            @Override
            public void onNewScroll(int position, float offset) {
                if (position == listActivities.size() - 1) {
                    mShowAddProgress.set(offset);
                }
            }

            @Override
            public void onShowingItem(int position) {
                if (position == listActivities.size()) {
                    mAreStatsGone.set(false);
                    mIsAddGone.set(false);
                } else {
                    mAreStatsGone.set(false);
                    mIsAddGone.set(false);
                }
            }
        };
    }
}
