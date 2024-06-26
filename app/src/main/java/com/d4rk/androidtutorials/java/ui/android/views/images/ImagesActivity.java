package com.d4rk.androidtutorials.java.ui.android.views.images;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.d4rk.androidtutorials.java.databinding.ActivityImagesBinding;

import me.zhanghai.android.fastscroll.FastScrollerBuilder;

public class ImagesActivity extends AppCompatActivity {
    private final Handler handler = new Handler();
    private ActivityImagesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityImagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        new FastScrollerBuilder(binding.scrollView).useMd2Style().build();
        binding.floatingButtonShowSyntax.setOnClickListener(v -> startActivity(new Intent(ImagesActivity.this, ImagesCodeActivity.class)));
        handler.postDelayed(() -> binding.floatingButtonShowSyntax.shrink(), 5000);
    }
}