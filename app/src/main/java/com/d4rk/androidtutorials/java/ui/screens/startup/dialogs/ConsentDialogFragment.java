package com.d4rk.androidtutorials.java.ui.screens.startup.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;

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

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        boolean defaultChecked = !BuildConfig.DEBUG;
        boolean analytics = prefs.getBoolean(getString(R.string.key_consent_analytics), defaultChecked);
        boolean adStorage = prefs.getBoolean(getString(R.string.key_consent_ad_storage), defaultChecked);
        boolean adUserData = prefs.getBoolean(getString(R.string.key_consent_ad_user_data), defaultChecked);
        boolean adPersonalization = prefs.getBoolean(getString(R.string.key_consent_ad_personalization), defaultChecked);

        binding.checkAnalyticsStorage.setChecked(analytics);
        binding.checkAdStorage.setChecked(adStorage);
        binding.checkAdUserData.setChecked(adUserData);
        binding.checkAdPersonalization.setChecked(adPersonalization);

        setCancelable(false);

        return new MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.consent_dialog_title)
                .setView(binding.getRoot())
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    boolean a = binding.checkAnalyticsStorage.isChecked();
                    boolean b = binding.checkAdStorage.isChecked();
                    boolean c = binding.checkAdUserData.isChecked();
                    boolean d = binding.checkAdPersonalization.isChecked();

                    prefs.edit()
                            .putBoolean(getString(R.string.key_consent_analytics), a)
                            .putBoolean(getString(R.string.key_consent_ad_storage), b)
                            .putBoolean(getString(R.string.key_consent_ad_user_data), c)
                            .putBoolean(getString(R.string.key_consent_ad_personalization), d)
                            .apply();

                    if (listener != null) {
                        listener.onConsentSet(a, b, c, d);
                    }
                })
                .create();
    }
}
