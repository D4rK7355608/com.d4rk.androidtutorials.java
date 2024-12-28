package com.d4rk.androidtutorials.java.ui.components.dialogs;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Process;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.d4rk.androidtutorials.java.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class RequireRestartDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.alert_dialog_require_restart)
                .setMessage(R.string.summary_alert_dialog_require_restart)
                .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> {
                    if (getActivity() != null) {
                        getActivity().recreate();
                    }
                    NotificationManager nManager = (NotificationManager) requireContext().getSystemService(Context.NOTIFICATION_SERVICE);
                    nManager.cancelAll();
                    PackageManager pm = requireContext().getPackageManager();
                    Intent intent = pm.getLaunchIntentForPackage(requireContext().getPackageName());
                    if (intent != null) {
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        requireContext().startActivity(intent);
                    }
                    Process.killProcess(Process.myPid());
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }
}