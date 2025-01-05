package com.d4rk.androidtutorials.java.ui.screens.android.lessons.basics.history;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.d4rk.androidtutorials.java.databinding.ActivityAndroidHistoryBinding;
import com.d4rk.androidtutorials.java.utils.EdgeToEdgeDelegate;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;

import me.zhanghai.android.fastscroll.FastScrollerBuilder;

public class AndroidHistory extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.d4rk.androidtutorials.java.databinding.ActivityAndroidHistoryBinding binding = ActivityAndroidHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        MobileAds.initialize(this);

        EdgeToEdgeDelegate edgeToEdgeDelegate = new EdgeToEdgeDelegate(this);
        edgeToEdgeDelegate.applyEdgeToEdge(binding.scrollView);

        binding.adView.loadAd(new AdRequest.Builder().build());
        binding.adViewBottom.loadAd(new AdRequest.Builder().build());
        new FastScrollerBuilder(binding.scrollView).useMd2Style().build();
    }
}