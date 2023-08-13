package com.d4rk.androidtutorials.java.ui.android.textboxes.passwordbox;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import androidx.appcompat.app.AppCompatActivity;
import com.d4rk.androidtutorials.java.R;
import com.d4rk.androidtutorials.java.databinding.ActivityPasswordBoxBinding;
import com.google.android.material.snackbar.Snackbar;
public class PasswordBoxActivity extends AppCompatActivity {
    private ActivityPasswordBoxBinding binding;
    private final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPasswordBoxBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bindListeners();
        handler.postDelayed(() -> binding.floatingButtonShowSyntax.shrink(), 5000);
    }
    private void bindListeners() {
        binding.floatingButtonShowSyntax.setOnClickListener(v ->
                startActivity(new Intent(this, PasswordBoxCodeActivity.class)));
        binding.showPasswordButton.setOnClickListener(v ->
                togglePasswordVisibility());
        addKeyListener();
    }
    private void togglePasswordVisibility() {
        if (binding.showPasswordButton.getText().equals("Show")) {
            showPassword();
        } else {
            hidePassword();
        }
    }
    @SuppressLint("SetTextI18n")
    private void showPassword() {
        binding.editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        binding.showPasswordButton.setIconResource(R.drawable.ic_visible_off);
        binding.showPasswordButton.setText("Hide");
    }
    @SuppressLint("SetTextI18n")
    private void hidePassword() {
        binding.editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        binding.showPasswordButton.setIconResource(R.drawable.ic_visible);
        binding.showPasswordButton.setText("Show");
    }
    private void addKeyListener() {
        binding.buttonShowPassword.setOnClickListener(v ->
                Snackbar.make(binding.getRoot(), binding.editText.getText(), Snackbar.LENGTH_LONG).show());
    }
}