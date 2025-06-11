package com.d4rk.androidtutorials.java.ui.screens.startup;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.d4rk.androidtutorials.java.databinding.ActivityStartupBinding;
import com.d4rk.androidtutorials.java.ui.screens.main.MainActivity;
import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.UserMessagingPlatform;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.d4rk.androidtutorials.java.ui.screens.startup.dialogs.ConsentDialogFragment;

import java.util.EnumMap;
import java.util.Map;

import me.zhanghai.android.fastscroll.FastScrollerBuilder;

public class StartupActivity extends AppCompatActivity {

    private StartupViewModel startupViewModel;
    private ConsentInformation consentInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.d4rk.androidtutorials.java.databinding.ActivityStartupBinding binding = ActivityStartupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        applyStoredConsent();

        startupViewModel = new ViewModelProvider(this).get(StartupViewModel.class);

        consentInformation = UserMessagingPlatform.getConsentInformation(this);
        ConsentRequestParameters params = new ConsentRequestParameters.Builder()
                .setTagForUnderAgeOfConsent(false)
                .build();

        startupViewModel.requestConsentInfoUpdate(
                this,
                params,
                () -> {
                    if (consentInformation.isConsentFormAvailable()) {
                        startupViewModel.loadConsentForm(
                                this,
                                formError -> updateFirebaseConsent(false, false, false, false)
                        );
                    } else if (consentInformation.getConsentStatus() == ConsentInformation.ConsentStatus.OBTAINED) {
                        applyStoredConsent();
                    }
                },
                formError -> {}
        );

        new FastScrollerBuilder(binding.scrollView)
                .useMd2Style()
                .build();

        binding.buttonBrowsePrivacyPolicyAndTermsOfService.setOnClickListener(v ->
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://d4rk7355608.github.io/profile/#privacy-policy-apps")))
        );

        binding.floatingButtonAgree.setOnClickListener(v -> {
            ConsentDialogFragment dialog = new ConsentDialogFragment();
            dialog.setConsentListener((analytics, adStorage, adUserData, adPersonalization) -> {
                updateFirebaseConsent(analytics, adStorage, adUserData, adPersonalization);
                proceedToMainActivity();
            });
            dialog.show(getSupportFragmentManager(), "consent_dialog");
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
        }
    }

    private void proceedToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void applyStoredConsent() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean analytics = prefs.getBoolean(getString(R.string.key_consent_analytics), true);
        boolean adStorage = prefs.getBoolean(getString(R.string.key_consent_ad_storage), true);
        boolean adUserData = prefs.getBoolean(getString(R.string.key_consent_ad_user_data), true);
        boolean adPersonalization = prefs.getBoolean(getString(R.string.key_consent_ad_personalization), true);
        updateFirebaseConsent(analytics, adStorage, adUserData, adPersonalization);
    }

    private void updateFirebaseConsent(boolean analytics,
                                       boolean adStorage,
                                       boolean adUserData,
                                       boolean adPersonalization) {
        Map<FirebaseAnalytics.ConsentType, FirebaseAnalytics.ConsentStatus> consentMap = new EnumMap<>(FirebaseAnalytics.ConsentType.class);
        consentMap.put(FirebaseAnalytics.ConsentType.ANALYTICS_STORAGE, analytics ? FirebaseAnalytics.ConsentStatus.GRANTED : FirebaseAnalytics.ConsentStatus.DENIED);
        consentMap.put(FirebaseAnalytics.ConsentType.AD_STORAGE, adStorage ? FirebaseAnalytics.ConsentStatus.GRANTED : FirebaseAnalytics.ConsentStatus.DENIED);
        consentMap.put(FirebaseAnalytics.ConsentType.AD_USER_DATA, adUserData ? FirebaseAnalytics.ConsentStatus.GRANTED : FirebaseAnalytics.ConsentStatus.DENIED);
        consentMap.put(FirebaseAnalytics.ConsentType.AD_PERSONALIZATION, adPersonalization ? FirebaseAnalytics.ConsentStatus.GRANTED : FirebaseAnalytics.ConsentStatus.DENIED);

        FirebaseAnalytics.getInstance(this).setConsent(consentMap);
    }
}