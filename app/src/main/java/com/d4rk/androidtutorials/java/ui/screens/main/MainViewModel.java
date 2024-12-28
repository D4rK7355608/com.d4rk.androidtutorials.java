package com.d4rk.androidtutorials.java.ui.screens.main;

import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.d4rk.androidtutorials.java.R;
import com.d4rk.androidtutorials.java.ui.screens.main.repository.MainRepository;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.play.core.appupdate.AppUpdateManager;

/**
 * ViewModel for MainActivity. It interacts with MainRepository to retrieve or
 * update data, and exposes it to the UI.
 */
public class MainViewModel extends AndroidViewModel {

    private final MainRepository mainRepository;
    private final MutableLiveData<Integer> bottomNavLabelVisibility = new MutableLiveData<>();
    private final MutableLiveData<Integer> defaultNavDestination = new MutableLiveData<>();
    private final MutableLiveData<Boolean> themeChanged = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        mainRepository = new MainRepository(application);
    }

    private static int getVisibilityMode(String labelVisibilityStr, String[] bottomNavBarLabelsValues) {
        int visibilityMode = NavigationBarView.LABEL_VISIBILITY_AUTO;
        if (labelVisibilityStr.equals(bottomNavBarLabelsValues[0])) {
            visibilityMode = NavigationBarView.LABEL_VISIBILITY_LABELED;
        } else if (labelVisibilityStr.equals(bottomNavBarLabelsValues[1])) {
            visibilityMode = NavigationBarView.LABEL_VISIBILITY_SELECTED;
        } else if (labelVisibilityStr.equals(bottomNavBarLabelsValues[2])) {
            visibilityMode = NavigationBarView.LABEL_VISIBILITY_UNLABELED;
        }
        return visibilityMode;
    }

    /**
     * Loads and applies settings such as theme, bottom nav visibility, default tab, etc.
     * This can be called from onCreate() or similar lifecycle methods in MainActivity.
     */
    public void applySettings() {
        boolean changedTheme = mainRepository.applyThemeSettings(
                getApplication().getResources().getStringArray(R.array.preference_theme_values)
        );
        themeChanged.setValue(changedTheme);

        String labelKey = getApplication().getString(R.string.key_bottom_navigation_bar_labels);
        String labelDefaultValue = getApplication().getString(R.string.default_value_bottom_navigation_bar_labels);
        String[] bottomNavBarLabelsValues =
                getApplication().getResources().getStringArray(R.array.preference_bottom_navigation_bar_labels_values);

        String labelVisibilityStr = mainRepository.getBottomNavLabelVisibility(labelKey, labelDefaultValue);
        int visibilityMode = getVisibilityMode(labelVisibilityStr, bottomNavBarLabelsValues);
        bottomNavLabelVisibility.setValue(visibilityMode);

        String defaultTabKey = getApplication().getString(R.string.key_default_tab);
        String defaultTabValue = getApplication().getString(R.string.default_value_tab);
        String[] defaultTabValues = getApplication().getResources().getStringArray(R.array.preference_default_tab_values);

        String startFragmentIdValue = mainRepository.getDefaultTabPreference(defaultTabKey, defaultTabValue);
        int startFragmentId;
        if (startFragmentIdValue.equals(defaultTabValues[0])) {
            startFragmentId = R.id.navigation_home;
        } else if (startFragmentIdValue.equals(defaultTabValues[1])) {
            startFragmentId = R.id.navigation_android_studio;
        } else if (startFragmentIdValue.equals(defaultTabValues[2])) {
            startFragmentId = R.id.navigation_about;
        } else {
            startFragmentId = R.id.navigation_home;
        }
        defaultNavDestination.setValue(startFragmentId);
        mainRepository.applyLanguageSettings();
    }

    /**
     * Checks if we need to show the startup screen.
     */
    public boolean shouldShowStartupScreen() {
        return mainRepository.shouldShowStartupScreen();
    }

    /**
     * Mark startup screen as shown.
     */
    public void markStartupScreenShown() {
        mainRepository.markStartupScreenShown();
    }

    /**
     * Check if the “Android Tutorials” app is installed or not.
     */
    public boolean isAndroidTutorialsInstalled() {
        PackageManager pm = getApplication().getPackageManager();
        return mainRepository.isAppInstalled(pm, "com.d4rk.androidtutorials.java");
    }

    /**
     * Build the intent for the shortcut (opens app if installed, or fallback to the Play Store).
     */
    public Intent getShortcutIntent(boolean isInstalled) {
        return mainRepository.buildShortcutIntent(isInstalled);
    }

    /**
     * Expose the bottom nav visibility as LiveData, so MainActivity can observe it.
     */
    public LiveData<Integer> getBottomNavVisibility() {
        return bottomNavLabelVisibility;
    }

    /**
     * Expose the default nav destination as LiveData, so MainActivity can observe it.
     */
    public LiveData<Integer> getDefaultNavDestination() {
        return defaultNavDestination;
    }

    /**
     * This tells the UI whether the theme changed so it can decide to recreate if necessary.
     */
    public LiveData<Boolean> getThemeChanged() {
        return themeChanged;
    }

    /**
     * Expose the AppUpdateManager if the Activity wants to directly check for in-app updates.
     */
    public AppUpdateManager getAppUpdateManager() {
        return mainRepository.getAppUpdateManager();
    }
}