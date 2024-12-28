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
import com.d4rk.androidtutorials.java.databinding.ActivitySupportBinding;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** @noinspection deprecation*/
public class SupportRepository {

    private final Context context;
    private BillingClient billingClient;
    private final Map<String, SkuDetails> skuDetailsMap = new HashMap<>();

    public SupportRepository(Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * Initialize the billing client and start the connection.
     * @param onConnected Callback once the billing service is connected.
     */
    public void initBillingClient(Runnable onConnected) {
        billingClient = BillingClient.newBuilder(context)
                .setListener((billingResult, purchases) -> {
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

    /**
     * Initialize Mobile Ads (usually done once in your app, but
     * can be done here if needed for the support screen).
     */
    public void initMobileAds(ActivitySupportBinding binding) {
        MobileAds.initialize(context);
        binding.largeBannerAd.loadAd(new AdRequest.Builder().build());
    }

    /**
     * Callback interface for when SKU details are fetched.
     */
    public interface OnSkuDetailsListener {
        void onSkuDetailsRetrieved(List<SkuDetails> skuDetailsList);
    }

}