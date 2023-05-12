package com.d4rk.androidtutorials.java;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.os.LocaleListCompat;
import androidx.core.splashscreen.SplashScreen;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;
import com.d4rk.androidtutorials.java.databinding.ActivityMainBinding;
import com.d4rk.androidtutorials.java.ui.settings.SettingsActivity;
import com.d4rk.androidtutorials.java.ui.startup.StartupActivity;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.ActivityResult;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import java.util.concurrent.TimeUnit;
public class MainActivity extends AppCompatActivity {
    private AppUpdateManager appUpdateManager;
    private final int requestUpdateCode = 1;
    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen.installSplashScreen(this);
        com.d4rk.androidtutorials.java.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
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
        setContentView(binding.getRoot());
        appUpdateManager = AppUpdateManagerFactory.create(this);
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
        String languageCode = sharedPreferences.getString(getString(R.string.key_language), getString(R.string.default_value_language));
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageCode));
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
            navController.setGraph(navGraph, savedInstanceState);
            NavigationUI.setupWithNavController(binding.navView, navController);
        }
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_android_studio, R.id.navigation_about).build();
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        }
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_android_studio, R.id.navigation_about).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        SharedPreferences appUsagePrefs = getSharedPreferences("app_usage", MODE_PRIVATE);
        long lastUsedTimestamp = appUsagePrefs.getLong("last_used", 0);
        long currentTimestamp = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (currentTimestamp - lastUsedTimestamp > TimeUnit.DAYS.toMillis(3)) {
            String channelId = "app_usage_channel";
            NotificationChannel channel = new NotificationChannel(channelId, getString(R.string.app_usage_notifications), NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(R.drawable.ic_notification_important)
                    .setContentTitle(getString(R.string.notification_last_time_used_title))
                    .setContentText(getString(R.string.summary_notification_last_time_used))
                    .setAutoCancel(true);
            notificationManager.notify(0, builder.build());
        }
        appUsagePrefs.edit().putLong("last_used", currentTimestamp).apply();
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(this);
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                String updateChannelId = "update_channel";
                NotificationChannel updateChannel = new NotificationChannel(updateChannelId, getString(R.string.update_notifications), NotificationManager.IMPORTANCE_HIGH);
                notificationManager.createNotificationChannel(updateChannel);
                NotificationCompat.Builder updateBuilder = new NotificationCompat.Builder(this, updateChannelId)
                        .setSmallIcon(R.drawable.ic_notification_update)
                        .setContentTitle(getString(R.string.notification_update_title))
                        .setContentText(getString(R.string.summary_notification_update))
                        .setAutoCancel(true)
                        .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())), PendingIntent.FLAG_IMMUTABLE));
                notificationManager.notify(0, updateBuilder.build());
            }
        });
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
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean preferenceFirebase = prefs.getBoolean(getString(R.string.key_firebase), true);
        FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(preferenceFirebase);
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(preferenceFirebase);
        if (prefs.getBoolean("value", true)) {
            prefs.edit().putBoolean("value", false).apply();
            startActivity(new Intent(this, StartupActivity.class));
        }
        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                try {
                    appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.FLEXIBLE, this, requestUpdateCode);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            }
        });
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
    @Deprecated
    @Override
    public void onBackPressed() {
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.close)
                .setMessage(R.string.summary_close)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    MainActivity.super.onBackPressed();
                    moveTaskToBack(true);
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }
}