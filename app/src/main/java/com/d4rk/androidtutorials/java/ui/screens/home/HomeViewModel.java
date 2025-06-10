package com.d4rk.androidtutorials.java.ui.screens.home;

import android.app.Application;
import android.content.Intent;

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

    public HomeViewModel(@NonNull Application application) {
        super(application);
        homeRepository = new HomeRepository(application);

        announcementTitle.setValue(application.getString(R.string.announcement_title));
        announcementSubtitle.setValue(application.getString(R.string.announcement_subtitle));
        dailyTip.setValue(homeRepository.getDailyTip());
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
}