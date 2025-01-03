package com.d4rk.androidtutorials.java.ui.screens.help;


import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.d4rk.androidtutorials.java.ui.screens.help.repository.HelpRepository;
import com.google.android.play.core.review.ReviewInfo;

/**
 * ViewModel for the Help screen. Delegates to HelpRepository for
 * requesting or launching in-app reviews.
 */
public class HelpViewModel extends AndroidViewModel {

    public HelpViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Requests a review flow. On success, you get a ReviewInfo.
     * On failure, handle the exception (like fallback to the Play Store).
     */
    public void requestReviewFlow(Activity activity, HelpRepository.OnReviewInfoListener listener) {
        HelpRepository repository = new HelpRepository(activity);
        repository.requestReviewFlow(listener);
    }

    /**
     * Launches the in-app review flow with the given ReviewInfo.
     */
    public void launchReviewFlow(Activity activity, ReviewInfo info) {
        HelpRepository repository = new HelpRepository(activity);
        repository.launchReviewFlow(activity, info);
    }
}