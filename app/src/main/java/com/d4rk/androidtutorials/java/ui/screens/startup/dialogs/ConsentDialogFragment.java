package com.d4rk.androidtutorials.java.ui.screens.startup.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.d4rk.androidtutorials.java.BuildConfig;
import com.d4rk.androidtutorials.java.R;
import com.d4rk.androidtutorials.java.databinding.DialogConsentBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class ConsentDialogFragment extends DialogFragment {

    public interface ConsentListener {
        void onConsentSet(boolean analytics, boolean adStorage, boolean adUserData, boolean adPersonalization);
    }

    private ConsentListener listener;

    public void setConsentListener(ConsentListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        DialogConsentBinding binding = DialogConsentBinding.inflate(LayoutInflater.from(requireContext()));

        boolean defaultChecked = !BuildConfig.DEBUG;
        binding.checkAnalyticsStorage.setChecked(defaultChecked);
        binding.checkAdStorage.setChecked(defaultChecked);
        binding.checkAdUserData.setChecked(defaultChecked);
        binding.checkAdPersonalization.setChecked(defaultChecked);

        setCancelable(false);

        return new MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.consent_dialog_title)
                .setView(binding.getRoot())
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    if (listener != null) {
                        listener.onConsentSet(
                                binding.checkAnalyticsStorage.isChecked(),
                                binding.checkAdStorage.isChecked(),
                                binding.checkAdUserData.isChecked(),
                                binding.checkAdPersonalization.isChecked()
                        );
                    }
                })
                .create();
    }
}
