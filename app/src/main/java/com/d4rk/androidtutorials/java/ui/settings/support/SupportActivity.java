package com.d4rk.androidtutorials.java.ui.settings.support;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.d4rk.androidtutorials.java.R;
import com.d4rk.androidtutorials.java.databinding.ActivitySupportBinding;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/** @noinspection deprecation*/
public class SupportActivity extends AppCompatActivity {
    private ActivitySupportBinding binding;
    private RewardedAd rewardedAd;
    private BillingClient billingClient;
    private final Map<String, SkuDetails> skuDetailsMap = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySupportBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_support);
        setContentView(binding.getRoot());
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        binding.buttonWebAd.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://bit.ly/3p8bpjj"))));
        MobileAds.initialize(this);
        loadRewardedAd();
        binding.buttonWatchAd.setOnClickListener(v -> showRewardedVideo());
        billingClient = BillingClient.newBuilder(this).setListener((billingResult, purchases) -> {
                }).enablePendingPurchases().build();
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    querySkuDetails();
                }
            }
            @Override
            public void onBillingServiceDisconnected() {
            }
        });
        binding.buttonLowDonation.setOnClickListener(v -> initiatePurchase("low_donation"));
        binding.buttonNormalDonation.setOnClickListener(v -> initiatePurchase("normal_donation"));
        binding.buttonHighDonation.setOnClickListener(v -> initiatePurchase("high_donation"));
        binding.buttonExtremeDonation.setOnClickListener(v -> initiatePurchase("extreme_donation"));
    }
    private void loadRewardedAd() {
        if (rewardedAd == null) {
            AdRequest adRequest = new AdRequest.Builder().build();
            String adUnitId = "ca-app-pub-5294151573817700/4542103671";
            RewardedAd.load(this, adUnitId, adRequest, new RewardedAdLoadCallback() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                    rewardedAd = null;
                }
                @Override
                public void onAdLoaded(@NonNull RewardedAd ad) {
                    rewardedAd = ad;
                }
            });

        }
    }
    private void showRewardedVideo() {
        if (rewardedAd == null) {
            return;
        }
        rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdShowedFullScreenContent() {
            }
            @Override
            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                rewardedAd = null;
            }
            @Override
            public void onAdDismissedFullScreenContent() {
                rewardedAd = null;
                loadRewardedAd();
            }
        });
        rewardedAd.show(this, rewardItem -> {
        });
    }
    private void querySkuDetails() {
        List<String> skuList = List.of("low_donation", "normal_donation", "high_donation", "extreme_donation");
        SkuDetailsParams params = SkuDetailsParams.newBuilder().setSkusList(skuList).setType(BillingClient.SkuType.INAPP).build();
        billingClient.querySkuDetailsAsync(params, (billingResult, skuDetailsList) -> {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && skuDetailsList != null) {
                for (SkuDetails skuDetails : skuDetailsList) {
                    skuDetailsMap.put(skuDetails.getSku(), skuDetails);
                    switch (skuDetails.getSku()) {
                        case "low_donation" ->
                                binding.buttonLowDonation.setText(skuDetails.getPrice());
                        case "normal_donation" ->
                                binding.buttonNormalDonation.setText(skuDetails.getPrice());
                        case "high_donation" ->
                                binding.buttonHighDonation.setText(skuDetails.getPrice());
                        case "extreme_donation" ->
                                binding.buttonExtremeDonation.setText(skuDetails.getPrice());
                    }
                }
            }
        });
    }
    private void initiatePurchase(String sku) {
        SkuDetails skuDetails = skuDetailsMap.get(sku);
        if (skuDetails != null) {
            BillingFlowParams flowParams = BillingFlowParams.newBuilder().setSkuDetails(skuDetails).build();
            billingClient.launchBillingFlow(this, flowParams);
        }
    }
}