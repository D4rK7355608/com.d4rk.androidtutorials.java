package com.d4rk.androidtutorials.java.ui.startup;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.d4rk.androidtutorials.java.MainActivity;
import com.d4rk.androidtutorials.java.databinding.ActivityStartupBinding;
import com.google.android.ump.ConsentForm;
import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.FormError;
import com.google.android.ump.UserMessagingPlatform;

import me.zhanghai.android.fastscroll.FastScrollerBuilder;

public class StartupActivity extends AppCompatActivity {
    private ConsentInformation consentInformation;
    private ConsentForm consentForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.d4rk.androidtutorials.java.databinding.ActivityStartupBinding binding = ActivityStartupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ConsentRequestParameters params = new ConsentRequestParameters.Builder().setTagForUnderAgeOfConsent(false).build();
        consentInformation = UserMessagingPlatform.getConsentInformation(this);
        consentInformation.requestConsentInfoUpdate(this, params, () -> {
            if (consentInformation.isConsentFormAvailable()) {
                loadForm();
            }
        }, (formError) -> {
        });
        new FastScrollerBuilder(binding.scrollView).useMd2Style().build();
        binding.buttonBrowsePrivacyPolicyAndTermsOfService.setOnClickListener(v ->
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://sites.google.com/view/d4rk7355608/more/apps/privacy-policy"))));
        binding.floatingButtonAgree.setOnClickListener(v ->
                startActivity(new Intent(this, MainActivity.class)));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
        }
    }

    private void loadForm() {
        UserMessagingPlatform.loadConsentForm(this, form -> {
            this.consentForm = form;
            if (consentInformation.getConsentStatus() == ConsentInformation.ConsentStatus.REQUIRED) {
                consentForm.show(this, (FormError unused) -> loadForm());
            }
        }, (formError) -> {
        });
    }
}