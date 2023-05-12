package com.d4rk.androidtutorials.java.ui.android.basics.viewbinding;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.preference.PreferenceManager;
import com.d4rk.androidtutorials.java.R;
import com.d4rk.androidtutorials.java.databinding.ActivityViewBindingTutorialBinding;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import me.zhanghai.android.fastscroll.FastScrollerBuilder;
public class ViewBindingTutorialActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.d4rk.androidtutorials.java.databinding.ActivityViewBindingTutorialBinding binding = ActivityViewBindingTutorialBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        new FastScrollerBuilder(binding.scrollView).useMd2Style().build();
        MobileAds.initialize(this);
        binding.adView.loadAd(new AdRequest.Builder().build());
        binding.moreAboutViewBindingButton.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://developer.android.com/topic/libraries/view-binding#java"))));
        InputStream bindingGradle = getResources().openRawResource(R.raw.text_binding_gradle);
        binding.bindingText.setText(readTextFromInputStream(bindingGradle));
        InputStream bindingActivity = getResources().openRawResource(R.raw.text_binding_activity);
        binding.bindingActivitiesText.setText(readTextFromInputStream(bindingActivity));
        InputStream bindingFragment = getResources().openRawResource(R.raw.text_binding_fragment);
        binding.bindingFragmentsText.setText(readTextFromInputStream(bindingFragment));
        SharedPreferences preferenceManager = PreferenceManager.getDefaultSharedPreferences(this);
        Typeface monospaceFont = switch (preferenceManager.getString(getString(R.string.key_monospace_font), "0")) {
            case "1" -> ResourcesCompat.getFont(this, R.font.font_fira_code);
            case "2" -> ResourcesCompat.getFont(this, R.font.font_jetbrains_mono);
            case "3" -> ResourcesCompat.getFont(this, R.font.font_noto_sans_mono);
            case "4" -> ResourcesCompat.getFont(this, R.font.font_poppins);
            case "5" -> ResourcesCompat.getFont(this, R.font.font_roboto_mono);
            default -> ResourcesCompat.getFont(this, R.font.font_audiowide);
        };
        binding.bindingText.setTypeface(monospaceFont);
        binding.bindingActivitiesText.setTypeface(monospaceFont);
        binding.bindingFragmentsText.setTypeface(monospaceFont);
    }
    private String readTextFromInputStream(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining("\n"));
    }
}