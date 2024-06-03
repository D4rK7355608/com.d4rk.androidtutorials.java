package com.d4rk.androidtutorials.java.ui.android.clocks.chronometer;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.d4rk.androidtutorials.java.databinding.ActivityChronometerBinding;
import me.zhanghai.android.fastscroll.FastScrollerBuilder;
public class ChronometerActivity extends AppCompatActivity {
    private ActivityChronometerBinding binding;
    private final Handler handler = new Handler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChronometerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        new FastScrollerBuilder(binding.scrollView).useMd2Style().build();
        binding.floatingButtonShowSyntax.setOnClickListener(v -> startActivity(new Intent(ChronometerActivity.this, ChronometerCodeActivity.class)));
        handler.postDelayed(() -> binding.floatingButtonShowSyntax.shrink(), 5000);
        binding.buttonStart.setOnClickListener(v -> binding.chronometer.start());
        binding.buttonStop.setOnClickListener(v -> binding.chronometer.stop());
        binding.buttonReset.setOnClickListener(v -> binding.chronometer.setBase(SystemClock.elapsedRealtime()));
    }
}