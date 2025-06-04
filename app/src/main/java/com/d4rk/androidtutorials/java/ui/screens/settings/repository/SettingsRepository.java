package com.d4rk.androidtutorials.java.ui.screens.settings.repository;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;
import androidx.preference.PreferenceManager;

import com.d4rk.androidtutorials.java.R;

/**
 * Repository that handles reading/writing preferences (e.g., theme, language)
 * and applies the changes (e.g., calls setDefaultNightMode).
 */
public class SettingsRepository {

    private final Context context;
    private final SharedPreferences sharedPreferences;

    public SettingsRepository(Context context) {
        this.context = context.getApplicationContext();
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.context);
    }

    private static int getNewNightMode(int currentNightMode, String preference, String[] darkModeValues) {
        int newNightMode = currentNightMode;
        if (preference.equals(darkModeValues[0])) {
            newNightMode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
        } else if (preference.equals(darkModeValues[1])) {
            newNightMode = AppCompatDelegate.MODE_NIGHT_NO;
        } else if (preference.equals(darkModeValues[2])) {
            newNightMode = AppCompatDelegate.MODE_NIGHT_YES;
        } else if (preference.equals(darkModeValues[3])) {
            newNightMode = AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY;
        }
        return newNightMode;
    }

    /**
     * Applies the theme based on the user's preference. Returns true if the theme actually changed.
     */
    public boolean applyTheme() {
        String[] darkModeValues = context.getResources().getStringArray(R.array.preference_theme_values);
        String preferenceKey = context.getString(R.string.key_theme);
        String defaultThemeValue = context.getString(R.string.default_value_theme);

        String preference = sharedPreferences.getString(preferenceKey, defaultThemeValue);
        int currentNightMode = AppCompatDelegate.getDefaultNightMode();
        int newNightMode = getNewNightMode(currentNightMode, preference, darkModeValues);
        if (newNightMode != currentNightMode) {
            AppCompatDelegate.setDefaultNightMode(newNightMode);
            return true;
        }
        return false;
    }

    /**
     * Applies the language from prefs. If the user changes language, this is how you’d do it.
     */
    public void applyLanguage() {
        String languageKey = context.getString(R.string.key_language);
        String defaultLanguageValue = context.getString(R.string.default_value_language);

        String languageCode = sharedPreferences.getString(languageKey, defaultLanguageValue);
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageCode));
    }

    /**
     * Handle a preference change for a given key. For example, theme or language.
     */
    public void handlePreferenceChange(String key) {
        if (key == null) return;

        if (key.equals(context.getString(R.string.key_theme))) {
            applyTheme();
        } else if (key.equals(context.getString(R.string.key_language))) {
            applyLanguage();
        }
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}