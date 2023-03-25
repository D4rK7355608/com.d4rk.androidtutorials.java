package com.d4rk.androidtutorials.java.ui.android.reviews.ratingbar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.d4rk.androidtutorials.java.R;
import com.d4rk.androidtutorials.java.databinding.ActivityRatingBarBinding;
public class RatingBarActivity extends AppCompatActivity {
    private ActivityRatingBarBinding binding;
    private float rating = 0f;
    private String formattedString;
    private final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRatingBarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        formattedString = String.format(getString(R.string.stars), rating);
        binding.textViewRatingValue.setText(formattedString);
        binding.ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            this.rating = rating;
            updateRatingText();
        });
        binding.button.setOnClickListener(v -> showRatingToast());
        binding.floatingButtonShowSyntax.setOnClickListener(v -> startActivity(new Intent(RatingBarActivity.this,
                RatingBarCodeActivity.class)));
        handler.postDelayed(() -> binding.floatingButtonShowSyntax.shrink(), 5000);
    }
    private void updateRatingText() {
        formattedString = String.format(getString(R.string.stars), rating);
        binding.textViewRatingValue.setText(formattedString);
    }
    private void showRatingToast() {
        formattedString = String.format(getString(R.string.rating), rating);
        Toast.makeText(this, formattedString, Toast.LENGTH_SHORT).show();
    }
}