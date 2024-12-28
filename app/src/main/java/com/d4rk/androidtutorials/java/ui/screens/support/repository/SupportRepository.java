package com.d4rk.androidtutorials.java.ui.screens.support.repository;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupportRepository {

    private final Context context;
    private BillingClient billingClient;
    private RewardedAd rewardedAd;
    private final Map<String, SkuDetails> skuDetailsMap = new HashMap<>();

    public SupportRepository(Context context) {
        this.context = context.getApplicationContext();
    }

    // --------------------------------------
    // 1) In-App Billing Logic
    // --------------------------------------

    /**
     * Initialize the billing client and start the connection.
     * @param activity The current activity (required for launching flows).
     * @param onConnected Callback once the billing service is connected.
     */
    public void initBillingClient(Activity activity, Runnable onConnected) {
        billingClient = BillingClient.newBuilder(context)
                .setListener((billingResult, purchases) -> {
                    // If you need to handle updates to purchases, do it here.
                })
                .enablePendingPurchases()
                .build();

        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    // Billing service connected
                    if (onConnected != null) {
                        onConnected.run();
                    }
                }
            }
            @Override
            public void onBillingServiceDisconnected() {
                // Attempt reconnection or handle gracefully
            }
        });
    }

    /**
     * Query your SKU details for in-app items.
     * Typically called after billing client is connected.
     */
    public void querySkuDetails(List<String> skuList, OnSkuDetailsListener listener) {
        if (billingClient == null || !billingClient.isReady()) {
            return;
        }
        SkuDetailsParams params = SkuDetailsParams.newBuilder()
                .setSkusList(skuList)
                .setType(BillingClient.SkuType.INAPP)
                .build();

        billingClient.querySkuDetailsAsync(params, (billingResult, skuDetailsList) -> {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK
                    && skuDetailsList != null) {
                for (SkuDetails skuDetails : skuDetailsList) {
                    skuDetailsMap.put(skuDetails.getSku(), skuDetails);
                }
                // Notify the caller of the updated SKU details
                if (listener != null) {
                    listener.onSkuDetailsRetrieved(skuDetailsList);
                }
            }
        });
    }

    /**
     * Launch the billing flow for a particular SKU.
     */
    public void initiatePurchase(Activity activity, String sku) {
        if (skuDetailsMap.containsKey(sku)) {
            SkuDetails skuDetails = skuDetailsMap.get(sku);
            if (skuDetails != null) {
                BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                        .setSkuDetails(skuDetails)
                        .build();
                billingClient.launchBillingFlow(activity, flowParams);
            }
        }
    }

    // --------------------------------------
    // 2) Rewarded Ads Logic
    // --------------------------------------

    /**
     * Initialize Mobile Ads (usually done once in your app, but
     * can be done here if needed for the support screen).
     */
    public void initMobileAds() {
        MobileAds.initialize(context);
    }

    /**
     * Loads a rewarded ad.
     * @param adUnitId The ad unit ID for rewarded ads.
     * @param onAdLoaded Callback invoked when the ad is loaded
     * @param onAdFailed Callback invoked if ad fails to load
     */
    public void loadRewardedAd(String adUnitId, OnRewardedAdListener onAdLoaded,
                               Runnable onAdFailed) {
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(context, adUnitId, adRequest,
                new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull com.google.android.gms.ads.LoadAdError adError) {
                        rewardedAd = null;
                        if (onAdFailed != null) onAdFailed.run();
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd ad) {
                        rewardedAd = ad;
                        if (onAdLoaded != null) {
                            onAdLoaded.onRewardedAdLoaded(ad);
                        }
                    }
                });
    }

    /**
     * Shows the rewarded ad if available.
     * @param activity The current Activity needed to show the ad.
     * @param onAdDismiss Callback after the ad is dismissed (reload, etc.).
     */
    public void showRewardedAd(Activity activity, Runnable onAdDismiss) {
        if (rewardedAd == null) {
            return;
        }
        rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdDismissedFullScreenContent() {
                rewardedAd = null;
                if (onAdDismiss != null) onAdDismiss.run();
            }
            @Override
            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                rewardedAd = null;
                if (onAdDismiss != null) onAdDismiss.run();
            }
        });
        rewardedAd.show(activity, rewardItem -> {
            // You can reward the user here, if you want
        });
    }

    // --------------------------------------
    // 3) Listener Interfaces
    // --------------------------------------

    /**
     * Callback interface for when SKU details are fetched.
     */
    public interface OnSkuDetailsListener {
        void onSkuDetailsRetrieved(List<SkuDetails> skuDetailsList);
    }

    /**
     * Callback interface for rewarded ads loading.
     */
    public interface OnRewardedAdListener {
        void onRewardedAdLoaded(RewardedAd ad);
    }
}