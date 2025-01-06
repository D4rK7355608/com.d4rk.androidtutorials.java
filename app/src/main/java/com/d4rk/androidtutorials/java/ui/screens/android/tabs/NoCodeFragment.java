package com.d4rk.androidtutorials.java.ui.screens.android.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.d4rk.androidtutorials.java.R;
import com.d4rk.androidtutorials.java.databinding.FragmentNoCodeBinding;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;

public class NoCodeFragment extends Fragment {
    private static final String ARG_MESSAGE = "arg_message";

    public static NoCodeFragment newInstance(String message) {
        NoCodeFragment fragment = new NoCodeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentNoCodeBinding binding = FragmentNoCodeBinding.inflate(inflater, container, false);
        MobileAds.initialize(requireContext());
        binding.adView.loadAd(new AdRequest.Builder().build());

        String message = requireArguments().getString(ARG_MESSAGE, String.valueOf(R.string.no_java_code_needed));
        binding.textViewNoCodeMessage.setText(message);

        return binding.getRoot();
    }
}
