package com.d4rk.androidtutorials.java.ui.android.layouts.relative;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.d4rk.androidtutorials.java.databinding.ActivityRelativeLayoutBinding;
public class RelativeLayoutActivity extends AppCompatActivity {
    private ActivityRelativeLayoutBinding binding;
    private final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRelativeLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.floatingButtonShowSyntax.setOnClickListener(v -> startActivity(new Intent(RelativeLayoutActivity.this, RelativeLayoutCodeActivity.class)));
        handler.postDelayed(() -> binding.floatingButtonShowSyntax.shrink(), 5000);
    }
}
