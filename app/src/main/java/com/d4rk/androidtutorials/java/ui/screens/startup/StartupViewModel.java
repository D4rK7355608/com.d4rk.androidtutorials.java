package com.d4rk.androidtutorials.java.ui.screens.startup;


import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.d4rk.androidtutorials.java.ui.screens.startup.repository.StartupRepository;
import com.google.android.ump.ConsentRequestParameters;

/**
 * ViewModel for the startup screen.
 * Handles consent logic by delegating to StartupRepository.
 */
public class StartupViewModel extends AndroidViewModel {

    public StartupViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Request the consent info update.
     */
    public void requestConsentInfoUpdate(Activity activity,
                                         ConsentRequestParameters params,
                                         Runnable onSuccess,
                                         StartupRepository.OnFormError onError) {
        StartupRepository localRepo = new StartupRepository(activity);
        localRepo.requestConsentInfoUpdate(
                activity,
                params,
                onSuccess,
                onError
        );
    }

    /**
     * Load the consent form (and show it if required).
     */
    public void loadConsentForm(Activity activity, StartupRepository.OnFormError onError) {
        StartupRepository localRepo = new StartupRepository(activity);
        localRepo.loadConsentForm(activity, onError);
    }
}