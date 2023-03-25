package com.d4rk.androidtutorials.java.ui.android.buttons.toggle;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
import com.d4rk.androidtutorials.java.R;
import com.d4rk.androidtutorials.java.databinding.ActivityToggleBinding;
import com.kieronquinn.monetcompat.app.MonetCompatActivity;
import me.zhanghai.android.fastscroll.FastScrollerBuilder;
public class ToggleActivity extends MonetCompatActivity {
    private ActivityToggleBinding binding;
    private final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityToggleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        new FastScrollerBuilder(binding.scrollView).useMd2Style().build();
        setOnClickListeners();
    }
    private void setOnClickListeners() {
        binding.switchMonet.setOnClickListener(v -> Toast.makeText(this, R.string.toast_monet_switch, Toast.LENGTH_SHORT).show());
        binding.buttonToggle.setOnClickListener(v -> Toast.makeText(this, R.string.toast_compat_toggle_button, Toast.LENGTH_SHORT).show());
        binding.switchMaterial.setOnClickListener(v -> Toast.makeText(this, R.string.toast_switch_material, Toast.LENGTH_SHORT).show());
        binding.materialSwitch.setOnClickListener(v -> Toast.makeText(this, R.string.toast_material_switch, Toast.LENGTH_SHORT).show());
        binding.floatingButtonShowSyntax.setOnClickListener(v -> startActivity(new Intent(this, ToggleCodeActivity.class)));
        handler.postDelayed(() -> binding.floatingButtonShowSyntax.shrink(), 5000);
    }
}