package com.d4rk.androidtutorials.java.ui.android.textboxes.textbox;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.d4rk.androidtutorials.java.databinding.ActivityTextboxBinding;
public class TextboxActivity extends AppCompatActivity {
    private ActivityTextboxBinding binding;
    private final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTextboxBinding.inflate(getLayoutInflater());
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
        binding.buttonPrintEdit.setOnClickListener(v -> Toast.makeText(TextboxActivity.this,
                binding.editText.getText(),
                Toast.LENGTH_LONG).show());
    }
}