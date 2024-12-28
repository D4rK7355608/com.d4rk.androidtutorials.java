package com.d4rk.androidtutorials.java.ui.android.alerts.alertdialog;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.d4rk.androidtutorials.java.R;
import com.d4rk.androidtutorials.java.databinding.ActivityAlertDialogBinding;
import com.d4rk.androidtutorials.java.utils.EdgeToEdgeDelegate;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class AlertDialogActivity extends AppCompatActivity {
    private final Handler handler = new Handler();
    private ActivityAlertDialogBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAlertDialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EdgeToEdgeDelegate edgeToEdgeDelegate = new EdgeToEdgeDelegate(this);
        edgeToEdgeDelegate.applyEdgeToEdge(binding.container);

        MaterialAlertDialogBuilder alertDialog = createAlertDialog();
        binding.button.setOnClickListener(v -> alertDialog.show());
        binding.floatingButtonShowSyntax.setOnClickListener(v -> startActivity(new Intent(this, AlertDialogCodeActivity.class)));
        handler.postDelayed(() -> binding.floatingButtonShowSyntax.shrink(), 5000);
    }

    private MaterialAlertDialogBuilder createAlertDialog() {
        return new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.your_title)
                .setMessage(R.string.summary_alert_dialog_message)
                .setIcon(R.drawable.ic_shop)
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, null);
    }
}