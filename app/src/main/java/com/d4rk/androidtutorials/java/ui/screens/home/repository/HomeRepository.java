package com.d4rk.androidtutorials.java.ui.screens.home.repository;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.d4rk.androidtutorials.java.R;
import com.d4rk.androidtutorials.java.data.model.PromotedApp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Repository for Home screen data/logic.
 * Here you can manage fetching or storing any data needed on the HomeFragment.
 */
public class HomeRepository {

    private final Context context;
    private final RequestQueue requestQueue;
    private static final String PROMOTED_APPS_URL =
            "https://raw.githubusercontent.com/D4rK7355608/com.d4rk.apis/refs/heads/main/App%20Toolkit/release/en/home/api_android_apps.json";

    public HomeRepository(Context context) {
        this.context = context.getApplicationContext();
        this.requestQueue = Volley.newRequestQueue(this.context);
    }

    /**
     * Returns an Intent that opens the Google Play Store page for your app.
     * You can change the package name or URL as needed.
     */
    public Intent getPlayStoreIntent() {
        String playStoreUrl = "https://play.google.com/store/apps/details?id=com.d4rk.androidtutorials";
        return buildPlayStoreIntent(playStoreUrl);
    }

    /**
     * Returns an Intent that opens the Google Play Store page for the provided package.
     */
    public Intent getAppPlayStoreIntent(String packageName) {
        String url = "https://play.google.com/store/apps/details?id=" + packageName;
        return buildPlayStoreIntent(url);
    }

    /**
     * Builds an intent that opens a Play Store url if the Google Play app is
     * installed, otherwise falls back to a generic ACTION_VIEW intent.
     */
    private Intent buildPlayStoreIntent(String url) {
        Intent playStoreIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        playStoreIntent.setPackage("com.android.vending");
        if (playStoreIntent.resolveActivity(context.getPackageManager()) != null) {
            return playStoreIntent;
        }
        return new Intent(Intent.ACTION_VIEW, Uri.parse(url));
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

    /** Callback used for delivering promoted apps. */
    public interface PromotedAppsCallback {
        void onResult(List<PromotedApp> apps);
    }

    /**
     * Fetches the promoted apps list from the remote JSON API.
     */
    public void fetchPromotedApps(PromotedAppsCallback callback) {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                PROMOTED_APPS_URL,
                null,
                response -> {
                    List<PromotedApp> result = new ArrayList<>();
                    try {
                        JSONArray apps = response.getJSONObject("data").getJSONArray("apps");
                        for (int i = 0; i < apps.length(); i++) {
                            JSONObject obj = apps.getJSONObject(i);
                            String pkg = obj.getString("packageName");
                            if (pkg.contains("com.d4rk.androidtutorials")) {
                                continue;
                            }
                            result.add(new PromotedApp(
                                    obj.getString("name"),
                                    pkg,
                                    obj.getString("iconLogo")
                            ));
                        }
                    } catch (JSONException e) {
                        result = Collections.emptyList();
                    }
                    callback.onResult(result);
                },
                error -> callback.onResult(Collections.emptyList())
        );
        requestQueue.add(request);
    }
}