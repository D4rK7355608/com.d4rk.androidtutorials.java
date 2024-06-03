package com.d4rk.androidtutorials.java.ui.android.notifications.simple.tabs;
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
import com.d4rk.androidtutorials.java.databinding.FragmentLinearLayoutLayoutBinding;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import me.zhanghai.android.fastscroll.FastScrollerBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class SimpleNotificationTabLayoutFragment extends Fragment {
    private FragmentLinearLayoutLayoutBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLinearLayoutLayoutBinding.inflate(inflater, container, false);
        new FastScrollerBuilder(binding.scrollView).useMd2Style().build();
        MobileAds.initialize(requireContext());
        binding.adView.loadAd(new AdRequest.Builder().build());
        StringBuilder verticalBuilder = new StringBuilder();
        try (BufferedReader readerVertical = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.text_center_button_xml)))) {
            String line;
            while ((line = readerVertical.readLine()) != null) {
                verticalBuilder.append(line).append('\n');
            }
            binding.textViewVerticalXml.setText(verticalBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder horizontalBuilder = new StringBuilder();
        try (BufferedReader readerHorizontal = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.text_linear_layout_horizontal_xml)))) {
            String line;
            while ((line = readerHorizontal.readLine()) != null) {
                horizontalBuilder.append(line).append('\n');
            }
            binding.textViewHorizontalXml.setText(horizontalBuilder.toString());
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
        binding.textViewVerticalXml.setTypeface(monospaceFont);
        binding.textViewHorizontalXml.setTypeface(monospaceFont);
    }
}