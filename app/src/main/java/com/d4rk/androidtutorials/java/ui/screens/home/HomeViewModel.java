package com.d4rk.androidtutorials.java.ui.screens.home;

import android.app.Application;
import android.content.Intent;
import java.util.ArrayList;
import java.util.List;

import com.d4rk.androidtutorials.java.data.model.PromotedApp;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.d4rk.androidtutorials.java.R;
import com.d4rk.androidtutorials.java.ui.screens.home.repository.HomeRepository;


public class HomeViewModel extends AndroidViewModel {

    private final HomeRepository homeRepository;

    private final MutableLiveData<String> announcementTitle = new MutableLiveData<>();
    private final MutableLiveData<String> announcementSubtitle = new MutableLiveData<>();
    private final MutableLiveData<String> dailyTip = new MutableLiveData<>();
    private final List<PromotedApp> promotedApps = new ArrayList<>();

    public HomeViewModel(@NonNull Application application) {
        super(application);
        homeRepository = new HomeRepository(application);

        announcementTitle.setValue(application.getString(R.string.announcement_title));
        announcementSubtitle.setValue(application.getString(R.string.announcement_subtitle));
        dailyTip.setValue(homeRepository.getDailyTip());

        promotedApps.add(new PromotedApp(
                R.mipmap.ic_shortcut_kotlin_edition,
                R.string.kotlin_edition_name,
                R.string.kotlin_edition_description,
                application.getString(R.string.package_ast_kotlin)));
        promotedApps.add(new PromotedApp(
                R.drawable.ic_shop,
                R.string.cart_calculator_name,
                R.string.cart_calculator_description,
                application.getString(R.string.package_cart_calculator)));
        promotedApps.add(new PromotedApp(
                R.drawable.ic_safety_check_tinted,
                R.string.cleaner_android_name,
                R.string.cleaner_android_description,
                application.getString(R.string.package_cleaner_android)));
    }

    /**
     * Provides a LiveData for the announcement title.
     */
    public LiveData<String> getAnnouncementTitle() {
        return announcementTitle;
    }

    /**
     * Provides a LiveData for the announcement subtitle.
     */
    public LiveData<String> getAnnouncementSubtitle() {
        return announcementSubtitle;
    }

    /**
     * Provides a LiveData for the tip of the day text.
     */
    public LiveData<String> getDailyTip() {
        return dailyTip;
    }

    /**
     * Returns an Intent that opens the Google Play Store page for your app.
     * The HomeFragment can startActivity(...) on it.
     */
    public Intent getOpenPlayStoreIntent() {
        return homeRepository.getPlayStoreIntent();
    }

    /**
     * List of apps to promote on the Home screen.
     */
    public List<PromotedApp> getPromotedApps() {
        return promotedApps;
    }

    /**
     * Builds an intent to open the Google Play listing for the provided package.
     */
    public Intent getPromotedAppIntent(String packageName) {
        return homeRepository.getAppPlayStoreIntent(packageName);
    }
}