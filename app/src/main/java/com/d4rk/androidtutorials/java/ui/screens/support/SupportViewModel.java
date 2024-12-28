package com.d4rk.androidtutorials.java.ui.screens.support;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.d4rk.androidtutorials.java.ui.screens.support.repository.SupportRepository;

import java.util.List;

public class SupportViewModel extends AndroidViewModel {

    private final SupportRepository repository;

    public SupportViewModel(@NonNull Application application) {
        super(application);
        repository = new SupportRepository(application);
    }

    // 1) Billing logic

    public void initBillingClient(Activity activity, Runnable onConnected) {
        repository.initBillingClient(activity, onConnected);
    }

    public void querySkuDetails(List<String> skuList,
                                SupportRepository.OnSkuDetailsListener listener) {
        repository.querySkuDetails(skuList, listener);
    }

    public void initiatePurchase(Activity activity, String sku) {
        repository.initiatePurchase(activity, sku);
    }

    // 2) Rewarded Ads logic

    public void initMobileAds() {
        repository.initMobileAds();
    }

    public void loadRewardedAd(String adUnitId,
                               SupportRepository.OnRewardedAdListener onLoaded,
                               Runnable onFailed) {
        repository.loadRewardedAd(adUnitId, onLoaded, onFailed);
    }

    public void showRewardedAd(Activity activity, Runnable onAdDismiss) {
        repository.showRewardedAd(activity, onAdDismiss);
    }
}