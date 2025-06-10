package com.d4rk.androidtutorials.java.ui.screens.home.repository;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.d4rk.androidtutorials.java.BuildConfig;
import com.d4rk.androidtutorials.java.R;

/**
 * Repository for Home screen data/logic.
 * Here you can manage fetching or storing any data needed on the HomeFragment.
 */
public class HomeRepository {

    private final Context context;

    public HomeRepository(Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * Returns an Intent that opens the Google Play Store page for your app.
     * You can change the package name or URL as needed.
     */
    public Intent getPlayStoreIntent() {
        String playStoreUrl = "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(playStoreUrl));
        intent.setPackage("com.android.vending");
        return intent;
    }

    /**
     * Returns a daily tip based on the current date.
     */
    public String getDailyTip() {
        String[] tips = context.getResources().getStringArray(R.array.daily_tips);
        long daysSinceEpoch = System.currentTimeMillis() / (24L * 60 * 60 * 1000);
        int index = (int) (daysSinceEpoch % tips.length);
        return tips[index];
    }
}