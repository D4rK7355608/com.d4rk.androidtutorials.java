package com.d4rk.androidtutorials.java.ui.screens.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.d4rk.androidtutorials.java.databinding.FragmentHomeBinding;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;

import me.zhanghai.android.fastscroll.FastScrollerBuilder;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        initializeAds();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getAnnouncementTitle().observe(getViewLifecycleOwner(), title -> binding.announcementTitle.setText(title));
        homeViewModel.getAnnouncementSubtitle().observe(getViewLifecycleOwner(), subtitle -> binding.announcementSubtitle.setText(subtitle));
        homeViewModel.getDailyTip().observe(getViewLifecycleOwner(), tip -> {
            binding.tipText.setText(tip);
            binding.shareTipButton.setOnClickListener(v -> shareTip(tip));
        });
        setupPromotions(LayoutInflater.from(requireContext()));
        new FastScrollerBuilder(binding.scrollView)
                .useMd2Style()
                .build();
        binding.btnGooglePlay.setOnClickListener(v -> startActivity(homeViewModel.getOpenPlayStoreIntent()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initializeAds() {
        MobileAds.initialize(requireContext());
        binding.smallBannerAd.loadAd(new AdRequest.Builder().build());
        binding.largeBannerAd.loadAd(new AdRequest.Builder().build());
    }

    private void shareTip(String tip) {
        android.content.Intent shareIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, tip);
        startActivity(android.content.Intent.createChooser(shareIntent, getString(com.d4rk.androidtutorials.java.R.string.share_using)));
    }

    private void setupPromotions(LayoutInflater inflater) {
        ViewGroup container = binding.promotedAppsContainer;
        for (com.d4rk.androidtutorials.java.data.model.PromotedApp app : homeViewModel.getPromotedApps()) {
            com.d4rk.androidtutorials.java.databinding.PromotedAppItemBinding itemBinding =
                    com.d4rk.androidtutorials.java.databinding.PromotedAppItemBinding.inflate(inflater, container, false);
            itemBinding.appIcon.setImageResource(app.iconResId());
            itemBinding.appName.setText(app.nameResId());
            itemBinding.appDescription.setText(app.descriptionResId());
            itemBinding.appButton.setOnClickListener(v -> startActivity(homeViewModel.getPromotedAppIntent(app.packageName())));
            container.addView(itemBinding.getRoot());
        }
    }
}