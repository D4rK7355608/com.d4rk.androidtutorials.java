package com.d4rk.androidtutorials.java.ui.android.textboxes.textbox.tabs;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.d4rk.androidtutorials.java.R;
import com.d4rk.androidtutorials.java.databinding.FragmentCodeBinding;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import me.zhanghai.android.fastscroll.FastScrollerBuilder;
public class TextboxTabCodeFragment extends Fragment {
    private FragmentCodeBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCodeBinding.inflate(inflater, container, false);
        new FastScrollerBuilder(binding.scrollView).useMd2Style().build();
        MobileAds.initialize(requireContext());
        binding.adView.loadAd(new AdRequest.Builder().build());
        StringBuilder builder = new StringBuilder();
        InputStream inputStream = getResources().openRawResource(R.raw.text_textbox_java);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append('\n');
            }
            binding.textView.setText(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(requireContext());
        boolean preferenceFont = preference.getBoolean(getString(R.string.key_monospace_font), false);
        if (preferenceFont) {
            Typeface monospaceFont = ResourcesCompat.getFont(requireContext(), R.font.font_roboto_mono);
            binding.textView.setTypeface(monospaceFont);
        }
    }
}