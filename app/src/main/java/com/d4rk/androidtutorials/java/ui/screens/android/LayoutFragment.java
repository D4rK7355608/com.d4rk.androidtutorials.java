package com.d4rk.androidtutorials.java.ui.screens.android;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.d4rk.androidtutorials.java.R;
import com.d4rk.androidtutorials.java.databinding.FragmentLayoutBinding;
import com.d4rk.androidtutorials.java.utils.TypefaceUtils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import me.zhanghai.android.fastscroll.FastScrollerBuilder;

// LayoutFragment.java
public class LayoutFragment extends Fragment {
    private FragmentLayoutBinding binding;
    private int layoutResId;

    // Factory method to create a new instance with the layout resource ID
    public static LayoutFragment newInstance(int layoutResId) {
        LayoutFragment fragment = new LayoutFragment();
        Bundle args = new Bundle();
        args.putInt("layoutResId", layoutResId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            layoutResId = getArguments().getInt("layoutResId");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLayoutBinding.inflate(inflater, container, false);
        setupUI();
        loadLayout();
        return binding.getRoot();
    }

    private void setupUI() {
        new FastScrollerBuilder(binding.scrollView).useMd2Style().build();
        MobileAds.initialize(requireContext());
        binding.adView.loadAd(new AdRequest.Builder().build());

        // Set typeface based on user preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        Typeface monospaceFont = TypefaceUtils.getMonospaceFont(requireContext(), prefs);
        binding.textView.setTypeface(monospaceFont);
    }

    private void loadLayout() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(getResources().openRawResource(layoutResId)))) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append('\n');
            }
            binding.textView.setText(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
            binding.textView.setText(R.string.error_loading_layout);
        }
    }
}
