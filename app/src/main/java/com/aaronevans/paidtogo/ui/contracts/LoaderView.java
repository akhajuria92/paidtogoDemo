package com.aaronevans.paidtogo.ui.contracts;

/**
 * Created by evaristo on 12/12/16.
 */

public interface LoaderView {
    void showProgressDialog();
    void hideProgressDialog();
    void showErrorAlert(String msg);
}
