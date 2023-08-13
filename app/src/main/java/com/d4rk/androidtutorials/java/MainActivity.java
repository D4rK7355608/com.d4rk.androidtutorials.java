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
import androidx.core.graphics.drawable.IconCompat;
import androidx.core.os.LocaleListCompat;
import androidx.core.splashscreen.SplashScreen;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;
import com.d4rk.androidtutorials.java.databinding.ActivityMainBinding;
import com.d4rk.androidtutorials.java.notifications.AppUpdateNotificationsManager;
import com.d4rk.androidtutorials.java.notifications.AppUsageNotificationsManager;
import com.d4rk.androidtutorials.java.ui.settings.SettingsActivity;
import com.d4rk.androidtutorials.java.ui.settings.support.SupportActivity;
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
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    NavController navController;
    private AppUpdateManager appUpdateManager;
    private final int requestUpdateCode = 1;
    private AppUpdateNotificationsManager appUpdateNotificationsManager;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final long snackbarInterval = 60L * 24 * 60 * 60 * 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen.installSplashScreen(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        launcherShortcuts();
        applySettings();
        setContentView(binding.getRoot());
        MobileAds.initialize(this);
        binding.adView.loadAd(new AdRequest.Builder().build());
        appUpdateManager = AppUpdateManagerFactory.create(this);
        appUpdateManager = AppUpdateManagerFactory.create(this);
        appUpdateNotificationsManager = new AppUpdateNotificationsManager(this);
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
    private void applySettings() {
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
        String labelKey = getString(R.string.key_bottom_navigation_bar_labels);
        String[] bottomNavigationBarLabelsValues = getResources().getStringArray(R.array.preference_bottom_navigation_bar_labels_values);
        String labelDefaultValue = getString(R.string.default_value_bottom_navigation_bar_labels);
        String labelVisibility = sharedPreferences.getString(labelKey, labelDefaultValue);
        int visibilityMode = NavigationBarView.LABEL_VISIBILITY_AUTO;
        if (labelVisibility.equals(bottomNavigationBarLabelsValues[0])) {
            visibilityMode = NavigationBarView.LABEL_VISIBILITY_LABELED;
        } else if (labelVisibility.equals(bottomNavigationBarLabelsValues[1])) {
            visibilityMode = NavigationBarView.LABEL_VISIBILITY_SELECTED;
        } else if (labelVisibility.equals(bottomNavigationBarLabelsValues[2])) {
            visibilityMode = NavigationBarView.LABEL_VISIBILITY_UNLABELED;
        }
        binding.navView.setLabelVisibilityMode(visibilityMode);
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
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_android_studio, R.id.navigation_about).build();
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        }
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_android_studio, R.id.navigation_about).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        String languageCode = sharedPreferences.getString(getString(R.string.key_language), getString(R.string.default_value_language));
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageCode));
    }
    private void showSnackbar() {
        Snackbar.make(binding.getRoot(), getString(R.string.snack_support), Snackbar.LENGTH_LONG).setAction(getString(android.R.string.ok), view -> startActivity(new Intent(view.getContext(), SupportActivity.class))).show();
        handler.postDelayed(this::showSnackbar, snackbarInterval);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
    @Deprecated
    @Override
    public void onBackPressed() {
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.alert_dialog_close)
                .setMessage(R.string.summary_alert_dialog_close)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    MainActivity.super.onBackPressed();
                    moveTaskToBack(true);
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }
    @SuppressWarnings("deprecation")
    @Override
    protected void onResume() {
        super.onResume();
        if (!PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getString(R.string.key_firebase), true)) {
            FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(false);
            FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(false);
        } else {
            FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(true);
            FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);
        }
        AppUsageNotificationsManager appUsageNotificationsManager = new AppUsageNotificationsManager(this);
        appUsageNotificationsManager.checkAndSendAppUsageNotification();
        appUpdateNotificationsManager.checkAndSendUpdateNotification();
        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                try {
                    appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.FLEXIBLE, this, requestUpdateCode);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            }
        });
        startupScreen();
    }
    private void startupScreen() {
        SharedPreferences startupPreference = getSharedPreferences("startup", MODE_PRIVATE);
        if (startupPreference.getBoolean("value", true)) {
            startupPreference.edit().putBoolean("value", false).apply();
            startActivity(new Intent(this, StartupActivity.class));
        }
    }
    @Deprecated
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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