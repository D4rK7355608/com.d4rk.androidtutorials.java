package com.d4rk.androidtutorials.java.ui.settings;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import com.d4rk.androidtutorials.java.BuildConfig;
import com.d4rk.androidtutorials.java.R;
import com.d4rk.androidtutorials.java.databinding.ActivitySettingsBinding;
import com.d4rk.androidtutorials.java.ui.dialogs.RequireRestartDialog;
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
public class SettingsActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.SummaryProvider<androidx.preference.ListPreference> {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.d4rk.androidtutorials.java.databinding.ActivitySettingsBinding binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportFragmentManager().beginTransaction().replace(R.id.settings, new SettingsFragment()).commit();
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.key_theme))) {
            String[] darkModeValues = getResources().getStringArray(R.array.preference_theme_values);
            String preference = sharedPreferences.getString(key, getString(R.string.default_value_theme));
            int defaultNightMode = AppCompatDelegate.getDefaultNightMode();
            if (preference.equals(darkModeValues[0])) {
                if (defaultNightMode != AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                    recreate();
                }
            } else if (preference.equals(darkModeValues[1])) {
                if (defaultNightMode != AppCompatDelegate.MODE_NIGHT_NO) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    recreate();
                }
            } else if (preference.equals(darkModeValues[2])) {
                if (defaultNightMode != AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    recreate();
                }
            } else if (preference.equals(darkModeValues[3])) {
                if (defaultNightMode != AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
                    recreate();
                }
            }
            String languageCode = sharedPreferences.getString(getString(R.string.key_language), getString(R.string.default_value_language));
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageCode));
        }
    }
    public static class SettingsFragment extends PreferenceFragmentCompat {
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
                        Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                        intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
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
                        Toast.makeText(getContext(), R.string.copied_to_clipboard, Toast.LENGTH_SHORT).show();
                    }
                    return true;
                });
            }
        }
    }
    @Override
    public CharSequence provideSummary(ListPreference preference) {
        String key = preference.getKey();
        if (key != null)
            if (key.equals(getString(R.string.dark_mode)))
                return preference.getEntry();
        return null;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }
}