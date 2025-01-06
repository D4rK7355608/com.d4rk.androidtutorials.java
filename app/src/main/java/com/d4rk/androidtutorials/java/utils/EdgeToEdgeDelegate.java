package com.d4rk.androidtutorials.java.utils;

import android.app.Activity;
import android.view.View;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

public class EdgeToEdgeDelegate {

    private final Activity activity;

    public EdgeToEdgeDelegate(Activity activity) {
        this.activity = activity;
    }

    public void applyEdgeToEdge(View container) {
        WindowCompat.setDecorFitsSystemWindows(activity.getWindow(), false);

        ViewCompat.setOnApplyWindowInsetsListener(container, (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });
    }

    public void applyEdgeToEdgeBottomBar(View container, View bottomNavigationView) {
        WindowCompat.setDecorFitsSystemWindows(activity.getWindow(), false);

        ViewCompat.setOnApplyWindowInsetsListener(container, (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            v.setPadding(bars.left, bars.top, bars.right, 0);

            if (bottomNavigationView != null) {
                bottomNavigationView.setPadding(0, 0, 0, bars.bottom);
            }
            return WindowInsetsCompat.CONSUMED;
        });
    }
}