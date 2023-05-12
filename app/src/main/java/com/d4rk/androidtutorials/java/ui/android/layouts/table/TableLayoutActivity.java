package com.d4rk.androidtutorials.java.ui.android.layouts.table;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.d4rk.androidtutorials.java.databinding.ActivityTableLayoutBinding;
public class TableLayoutActivity extends AppCompatActivity {
    private ActivityTableLayoutBinding binding;
    private final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTableLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.floatingButtonShowSyntax.setOnClickListener(v -> startActivity(new Intent(TableLayoutActivity.this, TableLayoutCodeActivity.class)));
        handler.postDelayed(() -> binding.floatingButtonShowSyntax.shrink(), 5000);
    }
}
