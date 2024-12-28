package com.d4rk.androidtutorials.java.ui.screens.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.content.pm.ShortcutManagerCompat;
import androidx.core.graphics.drawable.IconCompat;
import androidx.core.splashscreen.SplashScreen;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.d4rk.androidtutorials.java.BuildConfig;
import com.d4rk.androidtutorials.java.R;
import com.d4rk.androidtutorials.java.databinding.ActivityMainBinding;
import com.d4rk.androidtutorials.java.notifications.managers.AppUpdateNotificationsManager;
import com.d4rk.androidtutorials.java.notifications.managers.AppUsageNotificationsManager;
import com.d4rk.androidtutorials.java.ui.components.navigation.BottomSheetMenuFragment;
import com.d4rk.androidtutorials.java.ui.screens.startup.StartupActivity;
import com.d4rk.androidtutorials.java.ui.screens.support.SupportActivity;
import com.d4rk.androidtutorials.java.utils.EdgeToEdgeDelegate;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;

public class MainActivity extends AppCompatActivity {

    private final Handler handler = new Handler(Looper.getMainLooper());
    private ActivityMainBinding mBinding;
    private MainViewModel mainViewModel;
    private NavController navController;
    private AppUpdateNotificationsManager appUpdateNotificationsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        EdgeToEdgeDelegate edgeToEdgeDelegate = new EdgeToEdgeDelegate(this);
        edgeToEdgeDelegate.applyEdgeToEdge(mBinding.container);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        setupActionBar();
        observeViewModel();

        long snackbarInterval = 60L * 24 * 60 * 60 * 1000;
        handler.postDelayed(this::showSnackbar, snackbarInterval);
        setupUpdateNotifications();
        mainViewModel.applySettings();
        if (mainViewModel.shouldShowStartupScreen()) {
            mainViewModel.markStartupScreenShown();
            startActivity(new Intent(this, StartupActivity.class));
        }

        launcherShortcuts();
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_home,
                    R.id.navigation_android_studio,
                    R.id.navigation_about
            ).build();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        }

    }

    private void launcherShortcuts() {
        boolean isInstalled = mainViewModel.isAndroidTutorialsInstalled();

        ShortcutInfoCompat shortcut = new ShortcutInfoCompat.Builder(this, "shortcut_id")
                .setShortLabel(getString(R.string.shortcut_kotlin_edition_short))
                .setLongLabel(getString(R.string.shortcut_kotlin_edition_long))
                .setIcon(IconCompat.createWithResource(this, R.mipmap.ic_shortcut_kotlin_edition))
                .setIntent(mainViewModel.getShortcutIntent(isInstalled))
                .build();

        ShortcutManagerCompat.pushDynamicShortcut(this, shortcut);
    }

    private void observeViewModel() {
        mainViewModel.getBottomNavVisibility().observe(this, visibilityMode -> mBinding.navView.setLabelVisibilityMode(visibilityMode));
        mainViewModel.getDefaultNavDestination().observe(this, startFragmentId -> {
            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.nav_host_fragment_activity_main);
            if (navHostFragment != null) {
                navController = navHostFragment.getNavController();
                NavGraph navGraph = navController.getNavInflater().inflate(R.navigation.mobile_navigation);
                navGraph.setStartDestination(R.id.navigation_home);
                navController.setGraph(navGraph);

                NavigationUI.setupWithNavController(mBinding.navView, navController);
                setSupportActionBar(mBinding.toolbar);

                mBinding.toolbar.setNavigationOnClickListener(v -> {
                    BottomSheetMenuFragment bottomSheet = new BottomSheetMenuFragment();
                    bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());
                });
            }
        });
        mainViewModel.getThemeChanged().observe(this, changed -> {
            if (Boolean.TRUE.equals(changed)) {
                recreate();
            }
        });
    }

    private void setupUpdateNotifications() {
        appUpdateNotificationsManager = new AppUpdateNotificationsManager(this);
    }

    private void showSnackbar() {
        Snackbar.make(mBinding.getRoot(), "Hello after 1 day!", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if (item.getItemId() == R.id.support) {
            startActivity(new Intent(this, SupportActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("DeprecatedApi")
    @Deprecated
    @Override
    public void onBackPressed() {
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.alert_dialog_close)
                .setMessage(R.string.summary_alert_dialog_close)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    super.onBackPressed();
                    moveTaskToBack(true);
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppUsageNotificationsManager appUsageNotificationsManager =
                new AppUsageNotificationsManager(this);
        appUsageNotificationsManager.scheduleAppUsageCheck();
        appUpdateNotificationsManager.checkAndSendUpdateNotification();
        checkForFlexibleUpdate();
    }

    private void checkForFlexibleUpdate() {
        mainViewModel.getAppUpdateManager()
                .getAppUpdateInfo()
                .addOnSuccessListener(appUpdateInfo -> {
                    if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && appUpdateInfo.updatePriority() >= 3) {

                        if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                            try {
                                mainViewModel.getAppUpdateManager().startUpdateFlowForResult(appUpdateInfo, AppUpdateType.IMMEDIATE, this, 1);
                            } catch (Exception e) {
                                // handle error
                            }
                        } else if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                            try {
                                mainViewModel.getAppUpdateManager().startUpdateFlowForResult(appUpdateInfo, AppUpdateType.FLEXIBLE, this, 1);
                            } catch (Exception ignored) {
                            }
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    if (!BuildConfig.DEBUG) {
                        Snackbar.make(
                                findViewById(android.R.id.content),
                                getString(R.string.snack_general_error),
                                Snackbar.LENGTH_LONG
                        ).show();
                    }
                });
    }
}