package com.d4rk.androidtutorials.java.ui.screens.home.repository;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Repository for Home screen data/logic.
 * Here you can manage fetching or storing any data needed on the HomeFragment.
 */
public class HomeRepository {

    public HomeRepository(Context context) {
        context.getApplicationContext();
    }

    /**
     * Returns an Intent that opens the Google Play Store page for your app.
     * You can change the package name or URL as needed.
     */
    public Intent getPlayStoreIntent() {
        String playStoreUrl = "https://play.google.com/store/apps/details?id=com.d4rk.androidtutorials";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(playStoreUrl));
        intent.setPackage("com.android.vending");
        return intent;
    }
}