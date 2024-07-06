package com.d4rk.androidtutorials.java;

import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.content.pm.ShortcutManagerCompat;
import androidx.core.os.LocaleListCompat;
import androidx.core.splashscreen.SplashScreen;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import com.android.volley.NoConnectionError;
import com.android.volley.TimeoutError;
import com.d4rk.androidtutorials.java.databinding.ActivityMainBinding;
import com.d4rk.androidtutorials.java.notifications.managers.AppUpdateNotificationsManager;
import com.d4rk.androidtutorials.java.notifications.managers.AppUsageNotificationsManager;
import com.d4rk.androidtutorials.java.ui.settings.SettingsActivity;
import com.d4rk.androidtutorials.java.ui.startup.StartupActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.ActivityResult;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    NavController navController;
    private AppUpdateNotificationsManager appUpdateNotificationsManager = new AppUpdateNotificationsManager(this);
    private AppUpdateManager appUpdateManager;
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen.installSplashScreen(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        launcherShortcuts();
        startupScreen();
        setupUpdateNotifications();
        applySettings();
        setContentView(binding.getRoot());
        MobileAds.initialize(this);
        binding.adView.loadAd(new AdRequest.Builder().build());
        long snackbarInterval = 60L * 24 * 60 * 60 * 1000;
        handler.postDelayed(this::showSnackbar, snackbarInterval);
    }

    private void launcherShortcuts() {
        ShortcutInfoCompat shortcut = new ShortcutInfoCompat.Builder(this, "shortcut_id")
                .setShortLabel(getString(R.string.shortcut_kotlin_edition_short))
                .setLongLabel(getString(R.string.shortcut_kotlin_edition_long))
                //.setIcon(IconCompat.createWithResource(this, R.mipmap.ic_shortcut_kotlin_edition))
                .setIntent(new Intent(Intent.ACTION_MAIN) {{
                    if (isAppInstalled(getPackageManager())) {
                        setClassName("com.d4rk.androidtutorials", "com.d4rk.androidtutorials.MainActivity");
                    } else {
                        setData(Uri.parse("https://play.google.com/store/apps/details?id=com.d4rk.androidtutorials"));
                    }
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }})
                .build();
        ShortcutManagerCompat.pushDynamicShortcut(this, shortcut);
    }

    private boolean isAppInstalled(PackageManager packageManager) {
        try {
            packageManager.getPackageInfo("com.d4rk.androidtutorials.java", 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private void applySettings() {

        // Theme
        String[] darkModeValues = getResources().getStringArray(R.array.preference_theme_values);
        String preference = PreferenceManager.getDefaultSharedPreferences(this).getString(getString(R.string.key_theme), getString(R.string.default_value_theme));
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
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Bottom Navigation Bar Label Visibility
        String labelKey = getString(R.string.key_bottom_navigation_bar_labels);
        String[] bottomNavigationBarLabelsValues = getResources().getStringArray(R.array.preference_bottom_navigation_bar_labels_values);
        String labelDefaultValue = getString(R.string.default_value_bottom_navigation_bar_labels);
        String labelVisibility = sharedPreferences.getString(labelKey, labelDefaultValue);
        int visibilityMode = NavigationBarView.LABEL_VISIBILITY_AUTO; // FIXME: It's possible to extract method returning 'visibilityMode' from a long surrounding method
        if (labelVisibility.equals(bottomNavigationBarLabelsValues[0])) {
            visibilityMode = NavigationBarView.LABEL_VISIBILITY_LABELED;
        } else if (labelVisibility.equals(bottomNavigationBarLabelsValues[1])) {
            visibilityMode = NavigationBarView.LABEL_VISIBILITY_SELECTED;
        } else if (labelVisibility.equals(bottomNavigationBarLabelsValues[2])) {
            visibilityMode = NavigationBarView.LABEL_VISIBILITY_UNLABELED;
        }
        binding.navView.setLabelVisibilityMode(visibilityMode);

        // Bottom Navigation Bar Default Tab
        String defaultTabKey = getString(R.string.key_default_tab);
        String defaultTabValue = getString(R.string.default_value_tab);
        String[] defaultTabValues = getResources().getStringArray(R.array.preference_default_tab_values);
        String startFragmentIdValue = sharedPreferences.getString(defaultTabKey, defaultTabValue);
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
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
            NavGraph navGraph = navController.getNavInflater().inflate(R.navigation.mobile_navigation);
            navGraph.setStartDestination(startFragmentId);
            navController.setGraph(navGraph);
            NavigationUI.setupWithNavController(binding.navView, navController);
        }

        // Toolbar
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_android_studio, R.id.navigation_about).build();
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        }
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_android_studio, R.id.navigation_about).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Language
        String languageCode = sharedPreferences.getString(getString(R.string.key_language), getString(R.string.default_value_language));
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageCode));
    }

    private void showSnackbar() {

    }

    /**
     * {@inheritDoc}
     * <p>
     * Inflates the options menu, adding items to the action bar if present.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Handles item selections in the options menu.
     * <p>
     * This implementation handles the "Settings" option by starting the
     * {@link SettingsActivity}.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @deprecated This method provides a custom back button behavior which is deprecated.
     * The current implementation shows a confirmation dialog before closing the
     * activity. This behavior might be removed or changed in the future.
     */
    @Deprecated
    @Override
    public void onBackPressed() {
        new MaterialAlertDialogBuilder(this) // FIXME: Overrides deprecated method in 'androidx.activity.ComponentActivity'
                .setTitle(R.string.alert_dialog_close)
                .setMessage(R.string.summary_alert_dialog_close)
                .setPositiveButton(android.R.string.yes,
                        (dialog, which) -> {
                            //noinspection deprecation
                            MainActivity.super.onBackPressed();
                            moveTaskToBack(true);
                        })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    /**
     * {@inheritDoc}
     * <p>
     * This method is called when the activity resumes from a paused state. It schedules
     * app usage checks, initiates an app update check from the Google Play Store,
     * and checks for flexible updates.
     */
    @Override
    protected void onResume() {
        super.onResume();
        AppUsageNotificationsManager appUsageNotificationsManager =
                new AppUsageNotificationsManager(this);
        appUsageNotificationsManager.scheduleAppUsageCheck();
        appUpdateNotificationsManager.checkAndSendUpdateNotification();
        checkForFlexibleUpdate();
    }

    /**
     * Checks for the availability of updates from the Google Play Store and triggers the
     * appropriate update flow if certain conditions are met.
     * <p>
     * This method uses the Google Play Core library to asynchronously check for available
     * updates. If an update is available and meets specific conditions based on the update
     * type (IMMEDIATE or FLEXIBLE) and the client version staleness, it initiates the
     * update flow.
     * <p>
     * The method ensures that no developer-triggered update is already in progress before
     * initiating a new update. If an error occurs during the update check, and the app
     * is not in debug mode, a snackbar message is displayed to inform the user.
     */
    private void checkForFlexibleUpdate() {
        appUpdateManager.getAppUpdateInfo()
                .addOnSuccessListener(appUpdateInfo -> {
                    if (appUpdateInfo.updateAvailability() ==
                            UpdateAvailability.UPDATE_AVAILABLE
                            && appUpdateInfo.updateAvailability() !=
                            UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {

                        if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                            Integer stalenessDays =
                                    appUpdateInfo.clientVersionStalenessDays();
                            if (stalenessDays != null && stalenessDays > 90) {
                                try {
                                    appUpdateManager.startUpdateFlowForResult( // FIXME; 'startUpdateFlowForResult(com.google.android.play.core.appupdate.AppUpdateInfo, int, android.app.Activity, int)' is deprecated
                                            appUpdateInfo, AppUpdateType.IMMEDIATE,
                                            this, 1
                                    );
                                } catch (IntentSender.SendIntentException e) {
                                    e.printStackTrace(); // FIXME: Call to 'printStackTrace()' should probably be replaced with more robust logging
                                }
                            }
                        }

                        if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                            Integer stalenessDays =
                                    appUpdateInfo.clientVersionStalenessDays();
                            if (stalenessDays != null && stalenessDays < 90) {
                                try {
                                    appUpdateManager.startUpdateFlowForResult( // FIXME: 'startUpdateFlowForResult(com.google.android.play.core.appupdate.AppUpdateInfo, int, android.app.Activity, int)' is deprecated
                                            appUpdateInfo, AppUpdateType.FLEXIBLE,
                                            this, 1
                                    );
                                } catch (IntentSender.SendIntentException e) {
                                    e.printStackTrace(); // FIXME: Call to 'printStackTrace()' should probably be replaced with more robust logging
                                }
                            }
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    if (!BuildConfig.DEBUG) {
                        String message;
                        if (e instanceof NoConnectionError || e instanceof TimeoutError) {
                            message = getString(R.string.snack_network_error);
                        } else {
                            message = getString(R.string.snack_general_error);
                        }
                        Snackbar.make(findViewById(android.R.id.content),
                                message, Snackbar.LENGTH_LONG).show();
                    }
                });
    }

    /**
     * Initializes components related to app update notifications from the Google Play Store.
     * <p>
     * This method initializes the {@link AppUpdateManager} for managing app updates
     * and the {@link AppUpdateNotificationsManager} for handling update notifications.
     */
    private void setupUpdateNotifications() {
        appUpdateManager = AppUpdateManagerFactory.create(this);
        appUpdateNotificationsManager = new AppUpdateNotificationsManager(this);
    }

    /**
     * Displays the startup screen if the app is being launched for the first time after
     * installation or a fresh install.
     * <p>
     * This method checks a shared preference to determine if the app has been launched
     * before. If not, it launches the {@link StartupActivity} and sets the preference
     * to prevent the startup screen from showing on subsequent launches.
     */
    private void startupScreen() {
        SharedPreferences startupPreference = getSharedPreferences("startup",
                MODE_PRIVATE);
        if (startupPreference.getBoolean("value", true)) {
            startupPreference.edit().putBoolean("value", false).apply();
            startActivity(new Intent(this, StartupActivity.class));
        }
    }

    /**
     * {@inheritDoc}
     *
     * @deprecated This method is deprecated and should no longer be used. It currently handles
     * result codes from in-app update requests, but this functionality is deprecated and
     * may be removed in the future.
     */
    @Deprecated
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int requestUpdateCode = 1;
        if (requestCode == requestUpdateCode) {
            switch (resultCode) {
                case RESULT_OK:
                case RESULT_CANCELED:
                case ActivityResult.RESULT_IN_APP_UPDATE_FAILED:
                    break;
            }
        }
    }
}