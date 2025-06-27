package com.d4rk.androidtutorials.java.ui.screens.android.lessons.buttons.buttons.tabs;

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
import com.d4rk.androidtutorials.java.databinding.FragmentButtonsLayoutBinding;
import com.google.android.gms.ads.AdRequest;
import com.d4rk.androidtutorials.java.utils.FontManager;
import com.d4rk.androidtutorials.java.utils.CodeHighlighter;
import com.amrdeveloper.codeview.CodeView;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import me.zhanghai.android.fastscroll.FastScrollerBuilder;

public class ButtonsTabLayoutFragment extends Fragment {
    private final Map<Integer, CodeView> buttonXMLResources = new HashMap<>();
    private FragmentButtonsLayoutBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentButtonsLayoutBinding.inflate(inflater, container, false);
        new FastScrollerBuilder(binding.scrollView).useMd2Style().build();
        binding.adView.loadAd(new AdRequest.Builder().build());
        buttonXMLResources.put(R.raw.text_button_normal_xml, binding.codeViewButtonNormalXml);
        buttonXMLResources.put(R.raw.text_button_outlined_xml, binding.codeViewButtonOutlinedXml);
        buttonXMLResources.put(R.raw.text_button_elevated_xml, binding.codeViewButtonElevatedXml);
        buttonXMLResources.put(R.raw.text_button_normal_icon_xml, binding.codeViewButtonNormalIconXml);
        buttonXMLResources.put(R.raw.text_button_outlined_icon_xml, binding.codeViewButtonOutlinedIconXml);
        buttonXMLResources.put(R.raw.text_button_elevated_icon_xml, binding.codeViewButtonElevatedIconXml);
        buttonXMLResources.put(R.raw.text_extended_floating_button_primary_xml, binding.codeViewExtendedFloatingButtonPrimaryXml);
        buttonXMLResources.put(R.raw.text_extended_floating_button_secondary_xml, binding.codeViewExtendedFloatingButtonSecondaryXml);
        buttonXMLResources.put(R.raw.text_extended_floating_button_surface_xml, binding.codeViewExtendedFloatingButtonSurfaceXml);
        buttonXMLResources.put(R.raw.text_extended_floating_button_tertiary_xml, binding.codeViewExtendedFloatingButtonTertiaryXml);
        buttonXMLResources.put(R.raw.text_extended_floating_button_primary_icon_xml, binding.codeViewExtendedFloatingButtonPrimaryIconXml);
        buttonXMLResources.put(R.raw.text_extended_floating_button_secondary_icon_xml, binding.codeViewExtendedFloatingButtonSecondaryIconXml);
        buttonXMLResources.put(R.raw.text_extended_floating_button_surface_icon_xml, binding.codeViewExtendedFloatingButtonSurfaceIconXml);
        buttonXMLResources.put(R.raw.text_extended_floating_button_tertiary_icon_xml, binding.codeViewExtendedFloatingButtonTertiaryIconXml);
        buttonXMLResources.put(R.raw.text_floating_button_primary_xml, binding.codeViewFloatingButtonPrimaryXml);
        buttonXMLResources.put(R.raw.text_floating_button_secondary_xml, binding.codeViewFloatingButtonSecondaryXml);
        buttonXMLResources.put(R.raw.text_floating_button_surface_xml, binding.codeViewFloatingButtonSurfaceXml);
        buttonXMLResources.put(R.raw.text_floating_button_tertiary_xml, binding.codeViewFloatingButtonTertiaryXml);
        for (Map.Entry<Integer, CodeView> entry : buttonXMLResources.entrySet()) {
            Integer resourceId = entry.getKey();
            CodeView codeView = entry.getValue();
            try (InputStream inputStream = getResources().openRawResource(resourceId)) {
                byte[] bytes = new byte[inputStream.available()];
                int result = inputStream.read(bytes);
                if (result != -1) {
                    String text = new String(bytes, StandardCharsets.UTF_8);
                    codeView.setText(text);
                    CodeHighlighter.applyXmlTheme(codeView);
                }
            } catch (IOException e) {
                Log.e("ButtonsTab", "Error reading button resource", e);
            }
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        Typeface monospaceFont = FontManager.getMonospaceFont(requireContext(), prefs);
        for (CodeView codeView : buttonXMLResources.values()) {
            codeView.setTypeface(monospaceFont);
            codeView.setLineNumberTextSize(32f);
            codeView.setHorizontallyScrolling(false);
            codeView.setKeyListener(null);
            codeView.setCursorVisible(false);
            codeView.setTextIsSelectable(true);
        }
    }
}