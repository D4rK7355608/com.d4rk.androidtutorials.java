package com.d4rk.androidtutorials.java.ui.settings;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.ListPreference;

import com.d4rk.androidtutorials.java.R;
import com.d4rk.androidtutorials.java.databinding.ActivitySettingsBinding;

/**
 * Settings screen that delegates preference change logic to a ViewModel/Repository.
 */
public class SettingsActivity extends AppCompatActivity
        implements SharedPreferences.OnSharedPreferenceChangeListener,
        androidx.preference.Preference.SummaryProvider<ListPreference> {

    private SettingsViewModel settingsViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setup view binding
        ActivitySettingsBinding binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain ViewModel
        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        // Insert the Fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();

        // If you have an ActionBar from the theme
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Register preference change listener
        SharedPreferences prefs = settingsViewModel.getSharedPreferences();
        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // We let the ViewModel handle the preference logic
        // If the theme changed, we recreate.
        boolean changedTheme = settingsViewModel.onPreferenceChanged(key);
        if (changedTheme) {
            recreate();
        }
    }

    /**
     * Provide summary for ListPreference if needed
     */
    @Override
    public CharSequence provideSummary(ListPreference preference) {
        String key = preference.getKey();
        if (key != null) {
            if (key.equals(getString(R.string.dark_mode))) {
                // Return the entry for the ListPreference
                return preference.getEntry();
            }
        }
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister the listener
        SharedPreferences prefs = settingsViewModel.getSharedPreferences();
        prefs.unregisterOnSharedPreferenceChangeListener(this);
    }
}