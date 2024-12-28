package com.d4rk.androidtutorials.java.ui.settings;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.d4rk.androidtutorials.java.BuildConfig;
import com.d4rk.androidtutorials.java.R;
import com.d4rk.androidtutorials.java.ui.components.dialogs.RequireRestartDialog;
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences_settings, rootKey);
        ListPreference labelVisibilityMode = findPreference(getString(R.string.key_bottom_navigation_bar_labels));
        if (labelVisibilityMode != null) {
            labelVisibilityMode.setOnPreferenceChangeListener((preference, newValue) -> {
                RequireRestartDialog restartDialog = new RequireRestartDialog();
                restartDialog.show(getChildFragmentManager(), RequireRestartDialog.class.getName());
                return true;
            });
        }
        ListPreference defaultTab = findPreference(getString(R.string.key_default_tab));
        if (defaultTab != null) {
            defaultTab.setOnPreferenceChangeListener((preference, newValue) -> {
                RequireRestartDialog restartDialog = new RequireRestartDialog();
                restartDialog.show(getChildFragmentManager(), RequireRestartDialog.class.getName());
                return true;
            });
        }
        Preference changelogPreference = findPreference(getString(R.string.key_changelog));
        if (changelogPreference != null) {
            changelogPreference.setOnPreferenceClickListener(preference -> {
                new MaterialAlertDialogBuilder(requireContext())
                        .setTitle(requireContext().getString(R.string.changelog_title, BuildConfig.VERSION_NAME))
                        .setIcon(R.drawable.ic_changelog)
                        .setMessage(R.string.changes)
                        .setNegativeButton(android.R.string.cancel, null)
                        .show();
                return true;
            });
        }
        Preference ossPreference = findPreference(getString(R.string.key_open_source_licenses));
        if (ossPreference != null) {
            ossPreference.setOnPreferenceClickListener(preference -> {
                startActivity(new Intent(getActivity(), OssLicensesMenuActivity.class));
                return true;
            });
        }
        Preference notificationsSettings = findPreference(getString(R.string.key_notifications_settings));
        if (notificationsSettings != null) {
            notificationsSettings.setOnPreferenceClickListener(preference -> {
                Context context = getContext();
                if (context != null) {
                    Intent intent;

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                        intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
                    } else {
                        intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                        intent.setData(uri);
                    }
                    startActivity(intent);
                    return true;
                } else {
                    return false;
                }
            });
        }
        Preference sharePreference = findPreference(getString(R.string.key_share));
        if (sharePreference != null) {
            sharePreference.setOnPreferenceClickListener(preference -> {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.share_subject));
                startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_using)));
                return true;
            });
        }
        Preference deviceInfoPreference = findPreference(getString(R.string.key_device_info));
        if (deviceInfoPreference != null) {
            String version = String.format(getResources().getString(R.string.app_build), String.format("%s %s", getResources().getString(R.string.manufacturer), Build.MANUFACTURER), String.format("%s %s", getResources().getString(R.string.device_model), Build.MODEL), String.format("%s %s", getResources().getString(R.string.android_version), Build.VERSION.RELEASE), String.format("%s %s", getResources().getString(R.string.api_level), Build.VERSION.SDK_INT), String.format("%s %s", getResources().getString(R.string.arch), TextUtils.join(",", Build.SUPPORTED_ABIS)));
            deviceInfoPreference.setSummary(version);
            deviceInfoPreference.setOnPreferenceClickListener(preference -> {
                ClipboardManager clipboard = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("text", version);
                clipboard.setPrimaryClip(clip);
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
                    Toast.makeText(getContext(), R.string.snack_copied_to_clipboard, Toast.LENGTH_SHORT).show();
                }
                return true;
            });
        }
    }
}