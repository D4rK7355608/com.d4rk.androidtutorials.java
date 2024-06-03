package com.d4rk.androidtutorials.java.ui.android.views.grid;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.d4rk.androidtutorials.java.databinding.ActivityGridViewBinding;

public class GirdViewActivity extends AppCompatActivity {

    private final String[] numbers = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private final Handler handler = new Handler();
    private ActivityGridViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGridViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, numbers);
        binding.gridView.setAdapter(adapter);
        binding.gridView.setOnItemClickListener((adapterView, view, i, l) -> {
            final TextView textView = view.findViewById(android.R.id.text1);
            Toast.makeText(this, textView.getText(), Toast.LENGTH_SHORT).show();
        });
        binding.floatingButtonShowSyntax.setOnClickListener(v -> startActivity(new Intent(this, GirdViewCodeActivity.class)));
        handler.postDelayed(() -> binding.floatingButtonShowSyntax.shrink(), 5000);
    }
}
