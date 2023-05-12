package com.d4rk.androidtutorials.java.ui.android.layouts.linear;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.d4rk.androidtutorials.java.databinding.ActivityLinearLayoutBinding;
public class LinearLayoutActivity extends AppCompatActivity {
    private ActivityLinearLayoutBinding binding;
    private final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLinearLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.floatingButtonShowSyntax.setOnClickListener(v -> startActivity(new Intent(LinearLayoutActivity.this, LinearLayoutCodeActivity.class)));
        handler.postDelayed(() -> binding.floatingButtonShowSyntax.shrink(), 5000);
    }
}
