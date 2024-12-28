package com.d4rk.androidtutorials.java.ui.screens.about;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.d4rk.androidtutorials.java.ui.screens.about.repository.AboutRepository;


/**
 * ViewModel for the About screen. Delegates data/logic to AboutRepository.
 */
public class AboutViewModel extends AndroidViewModel {

    private final AboutRepository repository;

    public AboutViewModel(@NonNull Application application) {
        super(application);
        repository = new AboutRepository(application);
    }

    /**
     * Returns a formatted version string, e.g. "v1.2.3 (45)".
     */
    public String getVersionString() {
        return repository.getVersionString();
    }

    /**
     * Returns the current year, e.g. "2024".
     */
    public String getCurrentYear() {
        return repository.getCurrentYear();
    }
}
