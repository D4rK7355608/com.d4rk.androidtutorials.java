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
import com.google.android.ump.ConsentRequestParameters;

import me.zhanghai.android.fastscroll.FastScrollerBuilder;

public class StartupActivity extends AppCompatActivity {

    private StartupViewModel startupViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.d4rk.androidtutorials.java.databinding.ActivityStartupBinding binding = ActivityStartupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        startupViewModel = new ViewModelProvider(this).get(StartupViewModel.class);
        ConsentRequestParameters params = new ConsentRequestParameters
                .Builder()
                .setTagForUnderAgeOfConsent(false)
                .build();

        startupViewModel.requestConsentInfoUpdate(
                this,
                params,
                () -> startupViewModel.loadConsentForm(
                        this,
                        formError -> {
                        }
                ),
                formError -> {
                }
        );

        new FastScrollerBuilder(binding.scrollView)
                .useMd2Style()
                .build();

        binding.buttonBrowsePrivacyPolicyAndTermsOfService.setOnClickListener(v ->
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://sites.google.com/view/d4rk7355608/more/apps/privacy-policy")))
        );

        binding.floatingButtonAgree.setOnClickListener(v ->
                startActivity(new Intent(this, MainActivity.class)));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
        }
    }
}