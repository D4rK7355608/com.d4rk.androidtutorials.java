package com.d4rk.androidtutorials.java.ui.screens.home;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.d4rk.androidtutorials.java.ui.screens.home.repository.HomeRepository;


/**
 * ViewModel for the Home screen. It interacts with HomeRepository
 * to retrieve data/logic and exposes it to the HomeFragment.
 */
public class HomeViewModel extends AndroidViewModel {

    private final HomeRepository homeRepository;

    private final MutableLiveData<String> announcementTitle = new MutableLiveData<>();
    private final MutableLiveData<String> announcementSubtitle = new MutableLiveData<>();

    public HomeViewModel(@NonNull Application application) {
        super(application);
        homeRepository = new HomeRepository(application);
        announcementTitle.setValue("Android Dev Tips!");
        announcementSubtitle.setValue("Learn Jetpack Compose, Material 3, and more.");
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
     * Returns an Intent that opens the Google Play Store page for your app.
     * The HomeFragment can startActivity(...) on it.
     */
    public Intent getOpenPlayStoreIntent() {
        return homeRepository.getPlayStoreIntent();
    }
}