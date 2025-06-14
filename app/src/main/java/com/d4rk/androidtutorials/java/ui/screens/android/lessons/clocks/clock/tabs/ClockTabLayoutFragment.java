package com.d4rk.androidtutorials.java.ui.screens.android.lessons.clocks.clock.tabs;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.d4rk.androidtutorials.java.R;
import com.d4rk.androidtutorials.java.databinding.FragmentClockLayoutBinding;
import com.google.android.gms.ads.AdRequest;
import com.d4rk.androidtutorials.java.utils.FontManager;
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
        setTextView(binding.textViewDigitalClockXml, R.raw.text_clock_digital_xml);
        setTextView(binding.textViewTextClockXml, R.raw.text_clock_xml);
        setTextView(binding.textViewAnalogClockXml, R.raw.text_clock_analog_xml);
        return binding.getRoot();
    }

    private void setTextView(TextView textView, int rawResource) {
        try (InputStream inputStream = getResources().openRawResource(rawResource)) {
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            String text = result.toString();
            textView.setText(text);
        } catch (IOException e) {
            Log.e("ClockTab", "Error reading clock layout", e);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        Typeface monospaceFont = FontManager.getMonospaceFont(requireContext(), prefs);
        binding.textViewDigitalClockXml.setTypeface(monospaceFont);
        binding.textViewTextClockXml.setTypeface(monospaceFont);
        binding.textViewAnalogClockXml.setTypeface(monospaceFont);
    }
}