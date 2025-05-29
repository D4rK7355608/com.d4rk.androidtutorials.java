package com.d4rk.androidtutorials.java.ui.screens.startup;

import android.Manifest;
import android.content.Intent;
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

        startupViewModel = new ViewModelProvider(this).get(StartupViewModel.class);

        consentInformation = UserMessagingPlatform.getConsentInformation(this);
        ConsentRequestParameters params = new ConsentRequestParameters.Builder()
                .setTagForUnderAgeOfConsent(false)
                .build();

        consentInformation.requestConsentInfoUpdate(
                this,
                params,
                () -> {
                    if (consentInformation.isConsentFormAvailable()) {
                        startupViewModel.loadConsentForm(
                                this,
                                formError -> {
                                    updateFirebaseConsent(false);
                                    proceedToMainActivity();
                                }
                        );
                    } else {
                        if (consentInformation.getConsentStatus() == ConsentInformation.ConsentStatus.OBTAINED) {
                            updateFirebaseConsent(true);
                        }
                        proceedToMainActivity();
                    }
                },
                formError -> proceedToMainActivity()
        );

        new FastScrollerBuilder(binding.scrollView)
                .useMd2Style()
                .build();

        binding.buttonBrowsePrivacyPolicyAndTermsOfService.setOnClickListener(v ->
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://sites.google.com/view/d4rk7355608/more/apps/privacy-policy")))
        );

        binding.floatingButtonAgree.setOnClickListener(v -> proceedToMainActivity());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
        }
    }

    private void proceedToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void updateFirebaseConsent(boolean granted) {
        Map<FirebaseAnalytics.ConsentType, FirebaseAnalytics.ConsentStatus> consentMap = new EnumMap<>(FirebaseAnalytics.ConsentType.class);
        consentMap.put(FirebaseAnalytics.ConsentType.ANALYTICS_STORAGE, granted ? FirebaseAnalytics.ConsentStatus.GRANTED : FirebaseAnalytics.ConsentStatus.DENIED);
        consentMap.put(FirebaseAnalytics.ConsentType.AD_STORAGE, granted ? FirebaseAnalytics.ConsentStatus.GRANTED : FirebaseAnalytics.ConsentStatus.DENIED);
        consentMap.put(FirebaseAnalytics.ConsentType.AD_USER_DATA, granted ? FirebaseAnalytics.ConsentStatus.GRANTED : FirebaseAnalytics.ConsentStatus.DENIED);
        consentMap.put(FirebaseAnalytics.ConsentType.AD_PERSONALIZATION, granted ? FirebaseAnalytics.ConsentStatus.GRANTED : FirebaseAnalytics.ConsentStatus.DENIED);

        FirebaseAnalytics.getInstance(this).setConsent(consentMap);
    }
}