package com.d4rk.androidtutorials.java.ui.android.buttons.image;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.d4rk.androidtutorials.java.R;
import com.d4rk.androidtutorials.java.databinding.ActivityImageButtonsBinding;
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
        binding.imageButton.setOnClickListener(v -> Toast.makeText(ImageButtonsActivity.this, R.string.toast_image_button, Toast.LENGTH_SHORT).show());
        binding.floatingButtonShowSyntax.setOnClickListener(v -> startActivity(new Intent(ImageButtonsActivity.this, ImageButtonsCodeActivity.class)));
        handler.postDelayed(() -> binding.floatingButtonShowSyntax.shrink(), 5000);
    }
}