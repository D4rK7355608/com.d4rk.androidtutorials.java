package com.d4rk.androidtutorials.java.ui.android.layouts.table.tabs;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.d4rk.androidtutorials.java.databinding.FragmentNoCodeBinding;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
public class TableLayoutTabCodeFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        com.d4rk.androidtutorials.java.databinding.FragmentNoCodeBinding binding = FragmentNoCodeBinding.inflate(inflater, container, false);
        MobileAds.initialize(requireContext());
        binding.adView.loadAd(new AdRequest.Builder().build());
        return binding.getRoot();
    }
}