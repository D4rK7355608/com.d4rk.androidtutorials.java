package com.d4rk.androidtutorials.java.ui.screens.android.lessons.navigation.drawer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;

import androidx.appcompat.app.AppCompatActivity;

import com.d4rk.androidtutorials.java.R;
import com.d4rk.androidtutorials.java.databinding.ActivityNavigationDrawerBinding;
import com.d4rk.androidtutorials.java.ui.screens.android.CodeActivity;
import com.d4rk.androidtutorials.java.utils.EdgeToEdgeDelegate;

public class NavigationDrawerActivity extends AppCompatActivity {
    private final Handler handler = new Handler();
    private ActivityNavigationDrawerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNavigationDrawerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EdgeToEdgeDelegate edgeToEdgeDelegate = new EdgeToEdgeDelegate(this);
        edgeToEdgeDelegate.applyEdgeToEdge(binding.container);

        binding.navigationView.setNavigationItemSelectedListener(item -> {
            binding.textView.setText(getString(R.string.selected) + " " + item.getTitle());
            binding.drawerLayout.closeDrawer(Gravity.START);
            return true;
        });

        binding.floatingButtonShowSyntax.setOnClickListener(v -> {
            Intent intent = new Intent(this, CodeActivity.class);
            intent.putExtra("lesson_name", "NavigationDrawer");
            startActivity(intent);
        });

        handler.postDelayed(() -> binding.floatingButtonShowSyntax.shrink(), 5000);
    }
}
