package com.d4rk.androidtutorials.java.ui.android.buttons.radio;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.RadioButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.d4rk.androidtutorials.java.databinding.ActivityRadioButtonsBinding;
public class RadioButtonsActivity extends AppCompatActivity {
    private ActivityRadioButtonsBinding binding;
    private final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRadioButtonsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.floatingButtonShowSyntax.setOnClickListener(v -> startActivity(new Intent(this, RadioButtonsCodeActivity.class)));
        binding.buttonDisplay.setOnClickListener(v -> {
            int selectedId = binding.radioGroup.getCheckedRadioButtonId();
            RadioButton radioButton = findViewById(selectedId);
            Toast.makeText(this, radioButton.getText(), Toast.LENGTH_SHORT).show();
        });
        handler.postDelayed(() -> binding.floatingButtonShowSyntax.shrink(), 5000);
    }
}