package com.d4rk.androidtutorials.java.ui.android.textboxes.textbox;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.d4rk.androidtutorials.java.databinding.ActivityTextBoxBinding;
import com.google.android.material.snackbar.Snackbar;
public class TextboxActivity extends AppCompatActivity {
    private ActivityTextBoxBinding binding;
    private final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTextBoxBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bindListeners();
        handler.postDelayed(() -> binding.floatingButtonShowSyntax.shrink(), 5000);
    }
    private void bindListeners() {
        binding.floatingButtonShowSyntax.setOnClickListener(v -> startActivity(new Intent(TextboxActivity.this,
                TextboxCodeActivity.class)));
        addKeyListener();
    }
    private void addKeyListener() {
        binding.buttonPrintEdit.setOnClickListener(v -> {
            CharSequence text = binding.editText.getText();
            if (text != null) {
                Snackbar.make(binding.getRoot(), text, Snackbar.LENGTH_LONG).show();
            }
        });
    }
}