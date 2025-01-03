package com.d4rk.androidtutorials.java.ui.screens.settings;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.ListPreference;

import com.d4rk.androidtutorials.java.R;
import com.d4rk.androidtutorials.java.databinding.ActivitySettingsBinding;
import com.d4rk.androidtutorials.java.utils.EdgeToEdgeDelegate;

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
        ActivitySettingsBinding binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EdgeToEdgeDelegate edgeToEdgeDelegate = new EdgeToEdgeDelegate(this);
        edgeToEdgeDelegate.applyEdgeToEdge(binding.container);

        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        SharedPreferences prefs = settingsViewModel.getSharedPreferences();
        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
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
                return preference.getEntry();
            }
        }
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences prefs = settingsViewModel.getSharedPreferences();
        prefs.unregisterOnSharedPreferenceChangeListener(this);
    }
}