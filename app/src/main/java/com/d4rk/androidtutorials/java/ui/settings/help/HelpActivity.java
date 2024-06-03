package com.d4rk.androidtutorials.java.ui.settings.help;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import com.d4rk.androidtutorials.java.R;
import com.d4rk.androidtutorials.java.databinding.ActivityHelpBinding;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.material.snackbar.Snackbar;
public class HelpActivity extends AppCompatActivity {
    private ReviewManager reviewManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.d4rk.androidtutorials.java.databinding.ActivityHelpBinding binding = ActivityHelpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_faq, new FaqFragment()).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_feedback, new FeedbackFragment()).commit();
        reviewManager = ReviewManagerFactory.create(this);
    }
    public static class FaqFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.preferences_faq, rootKey);
        }
    }
    public class FeedbackFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.preferences_feedback, rootKey);
            Preference feedbackPreference = findPreference(getString(R.string.key_feedback));
            if (feedbackPreference != null) {
                feedbackPreference.setOnPreferenceClickListener(preference -> {
                    reviewManager.requestReviewFlow().addOnSuccessListener(reviewInfo -> {
                        reviewManager.launchReviewFlow(HelpActivity.this, reviewInfo);
                        launchGooglePlayReviews();
                    }).addOnFailureListener(exception -> launchGooglePlayReviews());

                    return true;
                });
            }
        }
        private void launchGooglePlayReviews() {
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName() + "&showAllReviews=true");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Snackbar.make(requireView(), R.string.snack_unable_to_open_google_play_store, Snackbar.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_feedback, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.dev_mail) {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("text/email");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"d4rk7355608@gmail.com"});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.feedback_for) + getString(R.string.app_name));
            emailIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.dear_developer));
            startActivity(Intent.createChooser(emailIntent, getString(R.string.send_email_using)));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}