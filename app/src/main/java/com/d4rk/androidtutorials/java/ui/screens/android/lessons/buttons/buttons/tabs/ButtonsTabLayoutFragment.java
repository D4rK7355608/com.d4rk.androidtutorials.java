package com.d4rk.androidtutorials.java.ui.screens.android.lessons.buttons.buttons.tabs;

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
import com.d4rk.androidtutorials.java.databinding.FragmentButtonsLayoutBinding;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.textview.MaterialTextView;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import me.zhanghai.android.fastscroll.FastScrollerBuilder;

public class ButtonsTabLayoutFragment extends Fragment {
    private final Map<Integer, MaterialTextView> buttonXMLResources = new HashMap<>();
    private FragmentButtonsLayoutBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentButtonsLayoutBinding.inflate(inflater, container, false);
        new FastScrollerBuilder(binding.scrollView).useMd2Style().build();
        MobileAds.initialize(requireContext());
        binding.adView.loadAd(new AdRequest.Builder().build());
        buttonXMLResources.put(R.raw.text_button_normal_xml, binding.textViewButtonNormalXml);
        buttonXMLResources.put(R.raw.text_button_outlined_xml, binding.textViewButtonOutlinedXml);
        buttonXMLResources.put(R.raw.text_button_elevated_xml, binding.textViewButtonElevatedXml);
        buttonXMLResources.put(R.raw.text_button_normal_icon_xml, binding.textViewButtonNormalIconXml);
        buttonXMLResources.put(R.raw.text_button_outlined_icon_xml, binding.textViewButtonOutlinedIconXml);
        buttonXMLResources.put(R.raw.text_button_elevated_icon_xml, binding.textViewButtonElevatedIconXml);
        buttonXMLResources.put(R.raw.text_extended_floating_button_primary_xml, binding.textViewExtendedFloatingButtonPrimaryXml);
        buttonXMLResources.put(R.raw.text_extended_floating_button_secondary_xml, binding.textViewExtendedFloatingButtonSecondaryXml);
        buttonXMLResources.put(R.raw.text_extended_floating_button_surface_xml, binding.textViewExtendedFloatingButtonSurfaceXml);
        buttonXMLResources.put(R.raw.text_extended_floating_button_tertiary_xml, binding.textViewExtendedFloatingButtonTertiaryXml);
        buttonXMLResources.put(R.raw.text_extended_floating_button_primary_icon_xml, binding.textViewExtendedFloatingButtonPrimaryIconXml);
        buttonXMLResources.put(R.raw.text_extended_floating_button_secondary_icon_xml, binding.textViewExtendedFloatingButtonSecondaryIconXml);
        buttonXMLResources.put(R.raw.text_extended_floating_button_surface_icon_xml, binding.textViewExtendedFloatingButtonSurfaceIconXml);
        buttonXMLResources.put(R.raw.text_extended_floating_button_tertiary_icon_xml, binding.textViewExtendedFloatingButtonTertiaryIconXml);
        buttonXMLResources.put(R.raw.text_floating_button_primary_xml, binding.textViewFloatingButtonPrimaryXml);
        buttonXMLResources.put(R.raw.text_floating_button_secondary_xml, binding.textViewFloatingButtonSecondaryXml);
        buttonXMLResources.put(R.raw.text_floating_button_surface_xml, binding.textViewFloatingButtonSurfaceXml);
        buttonXMLResources.put(R.raw.text_floating_button_tertiary_xml, binding.textViewFloatingButtonTertiaryXml);
        for (Map.Entry<Integer, MaterialTextView> entry : buttonXMLResources.entrySet()) {
            Integer resourceId = entry.getKey();
            MaterialTextView textView = entry.getValue();
            try (InputStream inputStream = getResources().openRawResource(resourceId)) {
                byte[] bytes = new byte[inputStream.available()];
                int result = inputStream.read(bytes);
                if (result != -1) {
                    String text = new String(bytes, StandardCharsets.UTF_8);
                    textView.setText(text);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        binding.textViewButtonNormalXml.setTypeface(monospaceFont);
        binding.textViewButtonOutlinedXml.setTypeface(monospaceFont);
        binding.textViewButtonElevatedXml.setTypeface(monospaceFont);
        binding.textViewButtonNormalIconXml.setTypeface(monospaceFont);
        binding.textViewButtonOutlinedIconXml.setTypeface(monospaceFont);
        binding.textViewButtonElevatedIconXml.setTypeface(monospaceFont);
        binding.textViewExtendedFloatingButtonPrimaryXml.setTypeface(monospaceFont);
        binding.textViewExtendedFloatingButtonSecondaryXml.setTypeface(monospaceFont);
        binding.textViewExtendedFloatingButtonSurfaceXml.setTypeface(monospaceFont);
        binding.textViewExtendedFloatingButtonTertiaryXml.setTypeface(monospaceFont);
        binding.textViewExtendedFloatingButtonPrimaryIconXml.setTypeface(monospaceFont);
        binding.textViewExtendedFloatingButtonSecondaryIconXml.setTypeface(monospaceFont);
        binding.textViewExtendedFloatingButtonSurfaceIconXml.setTypeface(monospaceFont);
        binding.textViewExtendedFloatingButtonTertiaryIconXml.setTypeface(monospaceFont);
        binding.textViewFloatingButtonPrimaryXml.setTypeface(monospaceFont);
        binding.textViewFloatingButtonSecondaryXml.setTypeface(monospaceFont);
        binding.textViewFloatingButtonSurfaceXml.setTypeface(monospaceFont);
        binding.textViewFloatingButtonTertiaryXml.setTypeface(monospaceFont);
    }
}