package com.aaronevans.paidtogo.ui.contracts;

/**
 * Created by evaristo on 12/12/16.
 */

public interface BasePresenter<T extends BaseView, ORIGIN> {
    ORIGIN start(T view);

    void destroy();
}
