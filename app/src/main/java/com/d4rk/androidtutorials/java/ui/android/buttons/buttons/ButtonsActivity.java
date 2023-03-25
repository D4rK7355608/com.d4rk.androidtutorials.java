package com.d4rk.androidtutorials.java.ui.android.buttons.buttons;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.d4rk.androidtutorials.java.R;
import com.d4rk.androidtutorials.java.databinding.ActivityButtonsBinding;
import me.zhanghai.android.fastscroll.FastScrollerBuilder;
public class ButtonsActivity extends AppCompatActivity {
    private ActivityButtonsBinding binding;
    private final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityButtonsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FastScrollerBuilder fastScrollerBuilder = new FastScrollerBuilder(binding.scrollView);
        fastScrollerBuilder.useMd2Style();
        fastScrollerBuilder.build();
        binding.buttonNormal.setOnClickListener(v -> Toast.makeText(ButtonsActivity.this, R.string.toast_button_normal, Toast.LENGTH_SHORT).show());
        binding.buttonOutlined.setOnClickListener(v -> Toast.makeText(ButtonsActivity.this, R.string.toast_button_outlined, Toast.LENGTH_SHORT).show());
        binding.buttonElevated.setOnClickListener(v -> Toast.makeText(ButtonsActivity.this, R.string.toast_button_elevated, Toast.LENGTH_SHORT).show());
        binding.buttonNormalIcon.setOnClickListener(v -> Toast.makeText(ButtonsActivity.this, R.string.toast_button_normal_icon, Toast.LENGTH_SHORT).show());
        binding.buttonOutlinedIcon.setOnClickListener(v -> Toast.makeText(ButtonsActivity.this, R.string.toast_button_outlined_icon, Toast.LENGTH_SHORT).show());
        binding.buttonElevatedIcon.setOnClickListener(v -> Toast.makeText(ButtonsActivity.this, R.string.toast_button_elevated_icon, Toast.LENGTH_SHORT).show());
        binding.extendedFloatingButtonPrimary.setOnClickListener(v -> Toast.makeText(ButtonsActivity.this, R.string.toast_extended_floating_button_primary, Toast.LENGTH_SHORT).show());
        binding.extendedFloatingButtonSecondary.setOnClickListener(v -> Toast.makeText(ButtonsActivity.this, R.string.toast_extended_floating_button_secondary, Toast.LENGTH_SHORT).show());
        binding.extendedFloatingButtonSurface.setOnClickListener(v -> Toast.makeText(ButtonsActivity.this, R.string.toast_extended_floating_button_surface, Toast.LENGTH_SHORT).show());
        binding.extendedFloatingButtonTertiary.setOnClickListener(v -> Toast.makeText(ButtonsActivity.this, R.string.toast_extended_floating_button_tertiary, Toast.LENGTH_SHORT).show());
        binding.extendedFloatingButtonPrimaryIcon.setOnClickListener(v -> Toast.makeText(ButtonsActivity.this, R.string.toast_extended_floating_button_primary_icon, Toast.LENGTH_SHORT).show());
        binding.extendedFloatingButtonSecondaryIcon.setOnClickListener(v -> Toast.makeText(ButtonsActivity.this, R.string.toast_extended_floating_button_secondary_icon, Toast.LENGTH_SHORT).show());
        binding.extendedFloatingButtonSurfaceIcon.setOnClickListener(v -> Toast.makeText(ButtonsActivity.this, R.string.toast_extended_floating_button_surface_icon, Toast.LENGTH_SHORT).show());
        binding.extendedFloatingButtonTertiaryIcon.setOnClickListener(v -> Toast.makeText(ButtonsActivity.this, R.string.toast_extended_floating_button_tertiary_icon, Toast.LENGTH_SHORT).show());
        binding.floatingButtonPrimary.setOnClickListener(v -> Toast.makeText(ButtonsActivity.this, R.string.toast_floating_button_primary, Toast.LENGTH_SHORT).show());
        binding.floatingButtonSecondary.setOnClickListener(v -> Toast.makeText(ButtonsActivity.this, R.string.toast_floating_button_secondary, Toast.LENGTH_SHORT).show());
        binding.floatingButtonSurface.setOnClickListener(v -> Toast.makeText(ButtonsActivity.this, R.string.toast_floating_button_surface, Toast.LENGTH_SHORT).show());
        binding.floatingButtonTertiary.setOnClickListener(v -> Toast.makeText(ButtonsActivity.this, R.string.toast_floating_button_tertiary, Toast.LENGTH_SHORT).show());
        binding.floatingButtonShowSyntax.setOnClickListener(v -> startActivity(new Intent(ButtonsActivity.this, ButtonsCodeActivity.class)));
        handler.postDelayed(() -> binding.floatingButtonShowSyntax.shrink(), 5000);
    }
}