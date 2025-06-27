package com.d4rk.androidtutorials.java.ui.screens.android.lessons.layouts.linear.tabs;

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
import com.d4rk.androidtutorials.java.databinding.FragmentLinearLayoutLayoutBinding;
import com.google.android.gms.ads.AdRequest;
import com.d4rk.androidtutorials.java.utils.FontManager;
import com.d4rk.androidtutorials.java.utils.CodeHighlighter;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import me.zhanghai.android.fastscroll.FastScrollerBuilder;

public class LinearLayoutTabLayoutFragment extends Fragment {
    private FragmentLinearLayoutLayoutBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLinearLayoutLayoutBinding.inflate(inflater, container, false);
        new FastScrollerBuilder(binding.scrollView).useMd2Style().build();
        binding.adView.loadAd(new AdRequest.Builder().build());
        StringBuilder verticalBuilder = new StringBuilder();
        try (BufferedReader readerVertical = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.text_linear_layout_vertical_xml)))) {
            String line;
            while ((line = readerVertical.readLine()) != null) {
                verticalBuilder.append(line).append('\n');
            }
            binding.codeViewVerticalXml.setText(verticalBuilder.toString());
            CodeHighlighter.applyXmlTheme(binding.codeViewVerticalXml);
        } catch (IOException e) {
            Log.e("LinearLayoutTab", "Error reading vertical layout", e);
        }
        StringBuilder horizontalBuilder = new StringBuilder();
        try (BufferedReader readerHorizontal = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.text_linear_layout_horizontal_xml)))) {
            String line;
            while ((line = readerHorizontal.readLine()) != null) {
                horizontalBuilder.append(line).append('\n');
            }
            binding.codeViewHorizontalXml.setText(horizontalBuilder.toString());
            CodeHighlighter.applyXmlTheme(binding.codeViewHorizontalXml);
        } catch (IOException e) {
            Log.e("LinearLayoutTab", "Error reading horizontal layout", e);
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        Typeface monospaceFont = FontManager.getMonospaceFont(requireContext(), prefs);
        binding.codeViewVerticalXml.setTypeface(monospaceFont);
        binding.codeViewVerticalXml.setLineNumberTextSize(32f);
        binding.codeViewVerticalXml.setHorizontallyScrolling(false);
        binding.codeViewVerticalXml.setKeyListener(null);
        binding.codeViewVerticalXml.setCursorVisible(false);
        binding.codeViewVerticalXml.setTextIsSelectable(true);

        binding.codeViewHorizontalXml.setTypeface(monospaceFont);
        binding.codeViewHorizontalXml.setLineNumberTextSize(32f);
        binding.codeViewHorizontalXml.setHorizontallyScrolling(false);
        binding.codeViewHorizontalXml.setKeyListener(null);
        binding.codeViewHorizontalXml.setCursorVisible(false);
        binding.codeViewHorizontalXml.setTextIsSelectable(true);
    }
}