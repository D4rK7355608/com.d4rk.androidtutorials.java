package com.d4rk.androidtutorials.java.ui.android.views.web;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.d4rk.androidtutorials.java.databinding.ActivityWebviewBinding;

import me.zhanghai.android.fastscroll.FastScrollerBuilder;

public class WebViewActivity extends AppCompatActivity {
    private final Handler handler = new Handler();
    private ActivityWebviewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWebviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        new FastScrollerBuilder(binding.webView).useMd2Style().build();
        setupWebView();
        setupFloatingButton();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupWebView() {
        WebView webView = binding.webView;
        webView.loadUrl("https://sites.google.com/view/d4rk7355608/home");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    private void setupFloatingButton() {
        binding.floatingButtonShowSyntax.setOnClickListener(v -> startActivity(new Intent(WebViewActivity.this, WebViewCodeActivity.class)));
        handler.postDelayed(() -> binding.floatingButtonShowSyntax.shrink(), 5000);
    }
}