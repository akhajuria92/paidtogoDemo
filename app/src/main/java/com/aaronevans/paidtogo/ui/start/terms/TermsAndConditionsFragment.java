package com.aaronevans.paidtogo.ui.start.terms;


import android.app.ProgressDialog;
import android.graphics.Color;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.text.method.ScrollingMovementMethod;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.data.entities.Pool;
import com.aaronevans.paidtogo.data.entities.PoolType;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_terms_and_conditions)
public class TermsAndConditionsFragment extends Fragment implements TermsAndConditionsContract.View {

    TermsAndConditionsContract.Presenter mPresenter;

    @ViewById
    FrameLayout mFrameLayoutActionBar;
    @ViewById
    TextView mTitle;
    @ViewById
    TextView mText;

    @FragmentArg
    Pool mPool;
    @FragmentArg
    PoolType mPoolType;

    ProgressDialog mProgressDialog;

    String id = "0";

    @Click(R.id.mBack)
    void back() {
        getActivity().onBackPressed();
    }

    @AfterViews
    void setTitle() {
        mTitle.setText(getString(R.string.terms_and_conditions));
    }

    @Override
    public void loadText(String terms) {
        mText.setText(terms);
    }

    @Override
    @AfterViews
    public void startPresenter() {
        mPresenter = new TermsAndConditionsPresenter()
                .start(this);
        if (mPool == null)
            mPresenter.getTerms(id);
        else {
            mFrameLayoutActionBar.setBackgroundColor(Color.parseColor(mPoolType.getColor()));
            loadText(mPool.getTermsAndConditions());
        }
        mText.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public void showProgressDialog() {
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showErrorAlert(String msg) {
        new AlertDialog.Builder(getContext())
                .setMessage(msg)
                .setPositiveButton(R.string.alert_accept, null)
                .create()
                .show();
    }
}