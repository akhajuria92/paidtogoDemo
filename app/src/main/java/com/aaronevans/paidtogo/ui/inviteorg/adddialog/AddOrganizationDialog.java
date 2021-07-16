package com.aaronevans.paidtogo.ui.inviteorg.adddialog;

import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.ViewGroup;
import android.view.Window;

import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.databinding.FragmentInviteorgDialogBinding;

import org.androidannotations.annotations.BindingObject;
import org.androidannotations.annotations.DataBound;
import org.androidannotations.annotations.EFragment;

/**
 * Created by leandro on 4/12/17.
 */
@DataBound
@EFragment(R.layout.fragment_inviteorg_dialog)
public class AddOrganizationDialog extends DialogFragment {

    @BindingObject FragmentInviteorgDialogBinding mBinding;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return dialog;
    }


    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = getActivity().getWindow().getAttributes().width;
            dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}
