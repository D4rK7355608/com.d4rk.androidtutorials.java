package com.d4rk.androidtutorials.java.ui.android.clocks.timepicker;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.d4rk.androidtutorials.java.databinding.ActivityTimePickerBinding;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
public class TimePickerActivity extends AppCompatActivity {
    private ActivityTimePickerBinding binding;
    private final Handler handler = new Handler();
    private final Calendar calendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTimePickerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        updateTimeInView();
        binding.changeTimeButton.setOnClickListener(v -> {
            TimePickerDialog.OnTimeSetListener timeSetListener = (view, hourOfDay, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                updateTimeInView();
            };
            new TimePickerDialog(TimePickerActivity.this,
                    timeSetListener,
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true).show();
        });
        binding.floatingButtonShowSyntax.setOnClickListener(v -> startActivity(new Intent(TimePickerActivity.this,
                TimePickerCodeActivity.class)));
        handler.postDelayed(() -> binding.floatingButtonShowSyntax.shrink(), 5000);
    }
    private void updateTimeInView() {
        String timeFormat = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat, Locale.getDefault());
        binding.timeTextView.setText(sdf.format(calendar.getTime()));
    }
}