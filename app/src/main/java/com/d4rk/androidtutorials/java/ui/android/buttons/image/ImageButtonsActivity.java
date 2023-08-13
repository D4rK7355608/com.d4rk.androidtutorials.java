package com.d4rk.androidtutorials.java.ui.android.buttons.image;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.d4rk.androidtutorials.java.R;
import com.d4rk.androidtutorials.java.databinding.ActivityImageButtonsBinding;
import com.google.android.material.snackbar.Snackbar;
import me.zhanghai.android.fastscroll.FastScrollerBuilder;
public class ImageButtonsActivity extends AppCompatActivity {
    private ActivityImageButtonsBinding binding;
    private final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityImageButtonsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        new FastScrollerBuilder(binding.scrollView).useMd2Style().build();
        binding.imageButton.setOnClickListener(v -> Snackbar.make(binding.getRoot(), R.string.snack_image_button, Snackbar.LENGTH_SHORT).show());
        binding.floatingButtonShowSyntax.setOnClickListener(v -> startActivity(new Intent(ImageButtonsActivity.this, ImageButtonsCodeActivity.class)));
        handler.postDelayed(() -> binding.floatingButtonShowSyntax.shrink(), 5000);
    }
}