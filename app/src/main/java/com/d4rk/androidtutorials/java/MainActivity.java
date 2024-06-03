package com.d4rk.androidtutorials.java;

import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.content.pm.ShortcutManagerCompat;
import androidx.core.graphics.drawable.IconCompat;
import androidx.core.os.LocaleListCompat;
import androidx.core.splashscreen.SplashScreen;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
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
import com.d4rk.androidtutorials.java.ui.settings.help.HelpActivity;
import com.d4rk.androidtutorials.java.ui.startup.StartupActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.ActivityResult;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;

import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    NavController navController;
    private AppBarConfiguration appBarConfiguration;
    private AppUpdateNotificationsManager appUpdateNotificationsManager = new AppUpdateNotificationsManager(this);
    private AppUpdateManager appUpdateManager;
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen.installSplashScreen(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        safeDrawingPadding();
        launcherShortcuts();
        startupScreen();
        setupUpdateNotifications();
        applySettings();
        setupNavigationDrawer();
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
                .setIcon(IconCompat.createWithResource(this, R.mipmap.ic_shortcut_kotlin_edition))
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

    @Override
    public boolean onSupportNavigateUp() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        } else {
            return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
        }
    }

    private void setupNavigationDrawer() {
        DrawerLayout drawerLayout = binding.drawerLayout;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, binding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        NavigationView navView = binding.navDrawer;
        appBarConfiguration = new AppBarConfiguration.Builder(new HashSet<>())
                .setOpenableLayout(drawerLayout)
                .build();

        NavigationUI.setupWithNavController(navView, navController);
        navController.setGraph(R.navigation.mobile_navigation);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.END);
        navView.setNavigationItemSelectedListener(menuItem -> {
            int itemId = menuItem.getItemId();
            if (itemId == R.id.nav_settings) {
                Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
            } else if (itemId == R.id.nav_help) {
                Intent helpIntent = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(helpIntent);
            } else if (itemId == R.id.nav_updates) {
                String url = "https://www.example.com/updates";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            } else if (itemId == R.id.nav_share) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.summary_share_message, "https://play.google.com/store/apps/details?id=" + getPackageName()));
                shareIntent.setType("text/plain");
                startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_email_using)));
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void safeDrawingPadding() {
        EdgeToEdge.enable(this);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_VISIBLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        if ((getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
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
        int visibilityMode = getVisibilityMode(labelVisibility, bottomNavigationBarLabelsValues);
        binding.bottomNavView.setLabelVisibilityMode(visibilityMode);

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
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
            NavGraph navGraph = navController.getNavInflater().inflate(R.navigation.mobile_navigation);
            navGraph.setStartDestination(startFragmentId);
            navController.setGraph(navGraph);
            NavigationUI.setupWithNavController(binding.bottomNavView, navController);
        }

        // Language
        String languageCode = sharedPreferences.getString(getString(R.string.key_language), getString(R.string.default_value_language));
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageCode));
    }

    private static int getVisibilityMode(String labelVisibility, String[] bottomNavigationBarLabelsValues) {
        int visibilityMode = NavigationBarView.LABEL_VISIBILITY_AUTO; // FIXME: It's possible to extract method returning 'visibilityMode' from a long surrounding method
        if (labelVisibility.equals(bottomNavigationBarLabelsValues[0])) {
            visibilityMode = NavigationBarView.LABEL_VISIBILITY_LABELED;
        } else if (labelVisibility.equals(bottomNavigationBarLabelsValues[1])) {
            visibilityMode = NavigationBarView.LABEL_VISIBILITY_SELECTED;
        } else if (labelVisibility.equals(bottomNavigationBarLabelsValues[2])) {
            visibilityMode = NavigationBarView.LABEL_VISIBILITY_UNLABELED;
        }
        return visibilityMode;
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
     * @noinspection deprecation
     * @deprecated This method provides a custom back button behavior which is deprecated.
     * The current implementation shows a confirmation dialog before closing the
     * activity. This behavior might be removed or changed in the future.
     */
    @Deprecated
    @Override
    public void onBackPressed() {
        new MaterialAlertDialogBuilder(this)
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
                    if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                            && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                        Integer stalenessDays = appUpdateInfo.clientVersionStalenessDays();
                        if (stalenessDays != null && stalenessDays < 90) {
                            try {
                                //noinspection deprecation
                                appUpdateManager.startUpdateFlowForResult(
                                        appUpdateInfo,
                                        AppUpdateType.FLEXIBLE,
                                        this,
                                        1);
                            } catch (IntentSender.SendIntentException ignored) {
                            }
                        }
                    } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                        // If the update is already downloaded, show a notification
                        // or prompt the user to install it
                        Snackbar.make(findViewById(android.R.id.content),
                                        "An update is ready to install", Snackbar.LENGTH_INDEFINITE)
                                .setAction("INSTALL", view -> appUpdateManager.completeUpdate())
                                .show();
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