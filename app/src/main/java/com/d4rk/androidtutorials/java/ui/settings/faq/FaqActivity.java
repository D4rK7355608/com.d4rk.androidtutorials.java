package com.d4rk.androidtutorials.java.ui.settings.faq;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import com.d4rk.androidtutorials.java.R;
import com.d4rk.androidtutorials.java.databinding.ActivityFaqBinding;
public class FaqActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.d4rk.androidtutorials.java.databinding.ActivityFaqBinding binding = ActivityFaqBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_faq, new SettingsFragment()).commit();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.preferences_faq, rootKey);
        }
    }
}