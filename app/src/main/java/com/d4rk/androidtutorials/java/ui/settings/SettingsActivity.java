package com.d4rk.androidtutorials.java.ui.settings;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import com.d4rk.androidtutorials.java.BuildConfig;
import com.d4rk.androidtutorials.java.R;
import com.d4rk.androidtutorials.java.databinding.ActivitySettingsBinding;
import com.d4rk.androidtutorials.java.ui.dialogs.RequireRestartDialog;
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;
import java.util.HashMap;
import java.util.Map;
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
            Preference moreApps = findPreference(getString(R.string.key_more_apps));
            if (moreApps != null) {
                moreApps.setOnPreferenceClickListener(preference -> {
                    View view = getLayoutInflater().inflate(R.layout.dialog_more_apps, null);
                    new MaterialAlertDialogBuilder(requireContext())
                            .setTitle(R.string.more_apps)
                            .setIcon(R.drawable.ic_shop)
                            .setNegativeButton(android.R.string.cancel, null)
                            .setView(view)
                            .create()
                            .show();
                    MaterialTextView textViewMusicSleepTimer = view.findViewById(R.id.text_view_music_sleep_timer);
                    MaterialTextView textViewEnglishWithLidia = view.findViewById(R.id.text_view_english_with_lidia);
                    MaterialTextView textViewQRCodeScanner = view.findViewById(R.id.text_view_qr_code_scanner);
                    MaterialTextView textViewLowBrightness = view.findViewById(R.id.text_view_low_brightness);
                    MaterialTextView textViewCleaner = view.findViewById(R.id.text_view_cleaner);
                    MaterialTextView textViewCartCalculator = view.findViewById(R.id.text_view_cart_calculator);
                    MaterialTextView textViewAndroidStudioTutorialsKotlin = view.findViewById(R.id.text_view_android_studio_tutorials_kotlin);
                    Map<MaterialTextView, String> urls = new HashMap<>();
                    urls.put(textViewAndroidStudioTutorialsKotlin, "https://play.google.com/store/apps/details?id=com.d4rk.androidtutorials");
                    urls.put(textViewCleaner, "https://play.google.com/store/apps/details?id=com.d4rk.cleaner.plus");
                    urls.put(textViewMusicSleepTimer, "https://play.google.com/store/apps/details?id=com.d4rk.musicsleeptimer.plus");
                    urls.put(textViewEnglishWithLidia, "https://play.google.com/store/apps/details?id=com.d4rk.englishwithlidia.plus");
                    urls.put(textViewQRCodeScanner, "https://play.google.com/store/apps/details?id=com.d4rk.qrcodescanner.plus");
                    urls.put(textViewLowBrightness, "https://play.google.com/store/apps/details?id=com.d4rk.lowbrightness");
                    urls.put(textViewCartCalculator, "https://play.google.com/store/apps/details?id=com.d4rk.cartcalculator");
                    for (Map.Entry<MaterialTextView, String> entry : urls.entrySet()) {
                        MaterialTextView textView = entry.getKey();
                        String url = entry.getValue();
                        textView.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url))));
                    }
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