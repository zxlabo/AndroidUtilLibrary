package com.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


/**
 * BaseDialogFragment
 */
public abstract class BaseDialogFragment extends DialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }


    public void show(FragmentManager fragmentManager, String tag) {
        if (isAdded()) {
            return;
        }
        try {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(this, tag);
            transaction.commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void show(Activity activity, FragmentManager fragmentManager, String tag) {
        if (activity == null || activity.isFinishing()) return;
        if (isAdded()) {
            return;
        }
        try {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(this, tag);
            transaction.commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dismiss() {
        dismissAllowingStateLoss();
    }

    public boolean isRemoved() {
        return getActivity() == null || isDetached() || !isAdded();
    }

}
