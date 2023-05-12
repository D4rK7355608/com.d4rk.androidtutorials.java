package com.d4rk.androidtutorials.java.ui.android.images.tabs;
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
import com.d4rk.androidtutorials.java.databinding.FragmentLayoutBinding;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import me.zhanghai.android.fastscroll.FastScrollerBuilder;
public class ImagesTabLayoutFragment extends Fragment {
    private FragmentLayoutBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLayoutBinding.inflate(inflater, container, false);
        new FastScrollerBuilder(binding.scrollView).useMd2Style().build();
        MobileAds.initialize(requireContext());
        binding.adView.loadAd(new AdRequest.Builder().build());
        StringBuilder builder = new StringBuilder();
        InputStream inputStream = getResources().openRawResource(R.raw.text_image_view_xml);
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
        SharedPreferences preferenceManager = PreferenceManager.getDefaultSharedPreferences(requireContext());
        Typeface monospaceFont = switch (preferenceManager.getString(getString(R.string.key_monospace_font), "0")) {
            case "1" -> ResourcesCompat.getFont(requireContext(), R.font.font_fira_code);
            case "2" -> ResourcesCompat.getFont(requireContext(), R.font.font_jetbrains_mono);
            case "3" -> ResourcesCompat.getFont(requireContext(), R.font.font_noto_sans_mono);
            case "4" -> ResourcesCompat.getFont(requireContext(), R.font.font_poppins);
            case "5" -> ResourcesCompat.getFont(requireContext(), R.font.font_roboto_mono);
            default -> ResourcesCompat.getFont(requireContext(), R.font.font_audiowide);
        };
        binding.textView.setTypeface(monospaceFont);
    }
}