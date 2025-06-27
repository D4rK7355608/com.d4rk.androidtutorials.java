package com.d4rk.androidtutorials.java.ui.screens.android.lessons.clocks.clock.tabs;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.amrdeveloper.codeview.CodeView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.d4rk.androidtutorials.java.R;
import com.d4rk.androidtutorials.java.databinding.FragmentClockLayoutBinding;
import com.google.android.gms.ads.AdRequest;
import com.d4rk.androidtutorials.java.utils.FontManager;
import com.d4rk.androidtutorials.java.utils.CodeHighlighter;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import me.zhanghai.android.fastscroll.FastScrollerBuilder;

public class ClockTabLayoutFragment extends Fragment {
    private FragmentClockLayoutBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentClockLayoutBinding.inflate(inflater, container, false);
        new FastScrollerBuilder(binding.scrollView).useMd2Style().build();
        binding.adView.loadAd(new AdRequest.Builder().build());
        setCodeView(binding.codeViewDigitalClockXml, R.raw.text_clock_digital_xml);
        setCodeView(binding.codeViewTextClockXml, R.raw.text_clock_xml);
        setCodeView(binding.codeViewAnalogClockXml, R.raw.text_clock_analog_xml);
        return binding.getRoot();
    }

    private void setCodeView(CodeView codeView, int rawResource) {
        try (InputStream inputStream = getResources().openRawResource(rawResource)) {
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            String text = result.toString();
            codeView.setText(text);
            CodeHighlighter.applyXmlTheme(codeView);
        } catch (IOException e) {
            Log.e("ClockTab", "Error reading clock layout", e);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        Typeface monospaceFont = FontManager.getMonospaceFont(requireContext(), prefs);
        binding.codeViewDigitalClockXml.setTypeface(monospaceFont);
        binding.codeViewDigitalClockXml.setLineNumberTextSize(32f);
        binding.codeViewDigitalClockXml.setHorizontallyScrolling(false);
        binding.codeViewDigitalClockXml.setKeyListener(null);
        binding.codeViewDigitalClockXml.setCursorVisible(false);
        binding.codeViewDigitalClockXml.setTextIsSelectable(true);

        binding.codeViewTextClockXml.setTypeface(monospaceFont);
        binding.codeViewTextClockXml.setLineNumberTextSize(32f);
        binding.codeViewTextClockXml.setHorizontallyScrolling(false);
        binding.codeViewTextClockXml.setKeyListener(null);
        binding.codeViewTextClockXml.setCursorVisible(false);
        binding.codeViewTextClockXml.setTextIsSelectable(true);

        binding.codeViewAnalogClockXml.setTypeface(monospaceFont);
        binding.codeViewAnalogClockXml.setLineNumberTextSize(32f);
        binding.codeViewAnalogClockXml.setHorizontallyScrolling(false);
        binding.codeViewAnalogClockXml.setKeyListener(null);
        binding.codeViewAnalogClockXml.setCursorVisible(false);
        binding.codeViewAnalogClockXml.setTextIsSelectable(true);
    }
}