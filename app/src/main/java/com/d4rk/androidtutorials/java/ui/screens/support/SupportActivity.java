package com.d4rk.androidtutorials.java.ui.screens.support;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.android.billingclient.api.SkuDetails;
import com.d4rk.androidtutorials.java.databinding.ActivitySupportBinding;
import com.d4rk.androidtutorials.java.utils.EdgeToEdgeDelegate;

import java.util.List;

public class SupportActivity extends AppCompatActivity {

    private ActivitySupportBinding binding;
    private SupportViewModel supportViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySupportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        EdgeToEdgeDelegate edgeToEdgeDelegate = new EdgeToEdgeDelegate(this);
        edgeToEdgeDelegate.applyEdgeToEdge(binding.container);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        supportViewModel = new ViewModelProvider(this).get(SupportViewModel.class);

        supportViewModel.initMobileAds(binding);

        binding.buttonWebAd.setOnClickListener(v ->
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://bit.ly/3p8bpjj"))));

        supportViewModel.initBillingClient(this::querySkuDetails);

        binding.buttonLowDonation.setOnClickListener(v -> initiatePurchase("low_donation"));
        binding.buttonNormalDonation.setOnClickListener(v -> initiatePurchase("normal_donation"));
        binding.buttonHighDonation.setOnClickListener(v -> initiatePurchase("high_donation"));
        binding.buttonExtremeDonation.setOnClickListener(v -> initiatePurchase("extreme_donation"));
    }

    private void querySkuDetails() {
        List<String> skuList = List.of("low_donation", "normal_donation", "high_donation", "extreme_donation");
        supportViewModel.querySkuDetails(skuList, skuDetailsList -> {
            for (SkuDetails skuDetails : skuDetailsList) {
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
        });
    }

    private void initiatePurchase(String sku) {
        supportViewModel.initiatePurchase(this, sku);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}