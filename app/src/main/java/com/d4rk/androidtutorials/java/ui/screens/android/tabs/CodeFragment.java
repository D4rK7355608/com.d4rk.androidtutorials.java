package com.d4rk.androidtutorials.java.ui.screens.android.tabs;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.d4rk.androidtutorials.java.R;
import com.d4rk.androidtutorials.java.databinding.FragmentCodeBinding;
import com.d4rk.androidtutorials.java.utils.TypefaceUtils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import me.zhanghai.android.fastscroll.FastScrollerBuilder;

public class CodeFragment extends Fragment {
    private FragmentCodeBinding binding;
    private int codeResId;

    public static CodeFragment newInstance(int codeResId) {
        CodeFragment fragment = new CodeFragment();
        Bundle args = new Bundle();
        args.putInt("codeResId", codeResId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            codeResId = getArguments().getInt("codeResId");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCodeBinding.inflate(inflater, container, false);
        setupUI();
        loadCode();
        return binding.getRoot();
    }

    private void setupUI() {
        new FastScrollerBuilder(binding.scrollView).useMd2Style().build();
        MobileAds.initialize(requireContext());
        binding.adView.loadAd(new AdRequest.Builder().build());

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        Typeface monospaceFont = TypefaceUtils.getMonospaceFont(requireContext(), prefs);
        binding.textView.setTypeface(monospaceFont);
    }

    private void loadCode() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(getResources().openRawResource(codeResId)))) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append('\n');
            }
            binding.textView.setText(builder.toString());
        } catch (IOException e) {
            Log.e("Android Code Fragment", "Error loading code from resource ID: " + codeResId, e);
            binding.textView.setText(R.string.error_loading_code);
        }
    }
}