package com.aaronevans.paidtogo.ui.contracts;

/**
 * Created by evaristo on 12/12/16.
 */

public interface ToolbarRelated {

    interface ToolbarCreator {
        void setupToolbar();
    }

    interface UpNavigator {
        boolean onSupportNavigateUp();
    }
}
