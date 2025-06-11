package com.d4rk.androidtutorials.java.ui.screens.settings;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.d4rk.androidtutorials.java.ui.screens.settings.repository.SettingsRepository;


/**
 * ViewModel for the Settings screen. Delegates to SettingsRepository for
 * reading/writing preferences, applying theme, etc.
 */
public class SettingsViewModel extends AndroidViewModel {

    private final SettingsRepository settingsRepository;

    public SettingsViewModel(@NonNull Application application) {
        super(application);
        settingsRepository = new SettingsRepository(application);
    }

    /**
     * Called by the Activity or Fragment when a preference changes.
     * We do the logic in the repository, and if theme changed, we return true so the UI can recreate.
     */
    public boolean onPreferenceChanged(String key) {
        if (key == null) return false;
        settingsRepository.handlePreferenceChange(key);
        return settingsRepository.applyTheme();
    }

    public SharedPreferences getSharedPreferences() {
        return settingsRepository.getSharedPreferences();
    }

    public void applyConsent() {
        settingsRepository.applyConsent();
    }
}