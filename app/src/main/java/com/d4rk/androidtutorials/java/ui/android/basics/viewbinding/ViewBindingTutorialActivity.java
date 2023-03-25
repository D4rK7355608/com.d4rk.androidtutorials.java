package com.d4rk.androidtutorials.java.ui.android.basics.viewbinding;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.d4rk.androidtutorials.java.R;
import com.d4rk.androidtutorials.java.databinding.ActivityViewBindingTutorialBinding;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import me.zhanghai.android.fastscroll.FastScrollerBuilder;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
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
    }
    private String readTextFromInputStream(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining("\n"));
    }
}