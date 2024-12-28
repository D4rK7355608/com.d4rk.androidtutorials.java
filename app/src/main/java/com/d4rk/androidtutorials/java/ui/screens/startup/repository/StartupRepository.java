package com.d4rk.androidtutorials.java.ui.screens.startup.repository;

import android.app.Activity;

import com.google.android.ump.ConsentForm;
import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.FormError;
import com.google.android.ump.UserMessagingPlatform;

/**
 * Repository that handles consent logic for the startup screen.
 * It keeps references to ConsentInformation and ConsentForm.
 */
public class StartupRepository {

    private final ConsentInformation consentInformation;
    private ConsentForm consentForm;

    public StartupRepository(Activity activity) {
        consentInformation = UserMessagingPlatform.getConsentInformation(activity);
    }

    /**
     * Requests consent info update from the UMP SDK.
     *
     * @param activity  the current Activity
     * @param params    the ConsentRequestParameters
     * @param onSuccess callback invoked when request succeeds
     * @param onError   callback invoked on failure
     */
    public void requestConsentInfoUpdate(Activity activity,
                                         ConsentRequestParameters params,
                                         Runnable onSuccess,
                                         OnFormError onError) {
        consentInformation.requestConsentInfoUpdate(
                activity,
                params,
                () -> {
                    consentInformation.isConsentFormAvailable();
                    onSuccess.run();
                },
                formError -> {
                    if (onError != null) {
                        onError.onFormError(formError);
                    }
                }
        );
    }

    /**
     * Loads the consent form and shows it if required.
     *
     * @param activity the current Activity
     * @param onError  callback invoked if there's a problem loading or showing the form
     */
    public void loadConsentForm(Activity activity, OnFormError onError) {
        UserMessagingPlatform.loadConsentForm(
                activity,
                form -> {
                    this.consentForm = form;
                    if (consentInformation.getConsentStatus()
                            == ConsentInformation.ConsentStatus.REQUIRED) {
                        consentForm.show(
                                activity,
                                formError -> loadConsentForm(activity, onError)
                        );
                    }
                },
                formError -> {
                    if (onError != null) {
                        onError.onFormError(formError);
                    }
                }
        );
    }

    /**
     * Simple functional interface for delivering form errors back to the caller.
     */
    public interface OnFormError {
        void onFormError(FormError error);
    }
}