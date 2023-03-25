package com.d4rk.androidtutorials.java.ui.feedback;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.d4rk.androidtutorials.java.R;
import com.d4rk.androidtutorials.java.databinding.ActivityFeedbackBinding;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import me.zhanghai.android.fastscroll.FastScrollerBuilder;
public class FeedbackActivity extends AppCompatActivity {
    private ReviewManager reviewManager;
    private ActivityFeedbackBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedbackBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        new FastScrollerBuilder(binding.scrollView).useMd2Style().build();
        init();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_feedback, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.dev_mail) {
            sendFeedbackEmail();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void sendFeedbackEmail() {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/email");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"d4rk7355608@gmail.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback for " + getString(R.string.app_name));
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Dear developer, ");
        startActivity(Intent.createChooser(emailIntent, "Send mail to Developer:"));
    }
    private void init() {
        reviewManager = ReviewManagerFactory.create(this);
        binding.buttonRateNow.setOnClickListener(view -> {
            showRateDialog();
            Toast.makeText(this, R.string.toast_feedback, Toast.LENGTH_SHORT).show();
        });
    }
    @SuppressWarnings("EmptyMethod")
    private void showRateDialog() {
        reviewManager.requestReviewFlow().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ReviewInfo reviewInfo = task.getResult();
                if (reviewInfo != null) {
                    reviewManager.launchReviewFlow(FeedbackActivity.this, reviewInfo);
                }
            }
        });
    }
}