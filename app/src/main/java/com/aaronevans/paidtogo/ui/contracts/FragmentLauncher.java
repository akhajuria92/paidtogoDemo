package com.aaronevans.paidtogo.ui.contracts;

/**
 * Created by evaristo on 12/12/16.
 */

public interface FragmentLauncher {

    interface Receiver {
        void loadFragment(String label);
    }
}
