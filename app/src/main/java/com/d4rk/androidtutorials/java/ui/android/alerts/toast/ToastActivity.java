package com.d4rk.androidtutorials.java.ui.android.alerts.toast;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.d4rk.androidtutorials.java.R;
import com.d4rk.androidtutorials.java.databinding.ActivityToastBinding;
public class ToastActivity extends AppCompatActivity {
    private ActivityToastBinding binding;
    private final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityToastBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.button.setOnClickListener(v -> Toast.makeText(this, R.string.toast_this_is_a_toast, Toast.LENGTH_SHORT).show());
        binding.floatingButtonShowSyntax.setOnClickListener(v -> startActivity(new Intent(this, ToastCodeActivity.class)));
        handler.postDelayed(() -> binding.floatingButtonShowSyntax.shrink(), 5000);
    }
}