package com.d4rk.androidtutorials.java.ui.about;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.d4rk.androidtutorials.java.BuildConfig;
import com.d4rk.androidtutorials.java.R;
import com.d4rk.androidtutorials.java.databinding.FragmentAboutBinding;
import com.d4rk.androidtutorials.java.ui.viewmodel.FragmentViewModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import me.zhanghai.android.fastscroll.FastScrollerBuilder;
public class AboutFragment extends Fragment {
    private FragmentAboutBinding binding;
    private final Calendar calendar = Calendar.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        new ViewModelProvider(this).get(FragmentViewModel.class);
        binding = FragmentAboutBinding.inflate(inflater, container, false);
        new FastScrollerBuilder(binding.scrollView).useMd2Style().build();
        MobileAds.initialize(requireContext());
        binding.adView.loadAd(new AdRequest.Builder().build());
        String version = String.format(getResources().getString(R.string.app_version), BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE);
        binding.textViewAppVersion.setText(version);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        String dateText = simpleDateFormat.format(calendar.getTime());
        String copyright = requireContext().getString(R.string.copyright, dateText);
        binding.textViewCopyright.setText(copyright);
        binding.textViewAppVersion.setOnLongClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setPrimaryClip(ClipData.newPlainText("Label", binding.textViewAppVersion.getText()));
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
                Toast.makeText(getContext(), R.string.copied_to_clipboard, Toast.LENGTH_SHORT).show();
            }
            return true;
        });
        binding.imageViewAppIcon.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://sites.google.com/view/d4rk7355608"))));
        binding.chipGoogleDev.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://g.dev/D4rK7355608"))));
        binding.chipYoutube.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/c/D4rK7355608"))));
        binding.chipGithub.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/D4rK7355608/" + BuildConfig.APPLICATION_ID))));
        binding.chipTwitter.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/D4rK7355608"))));
        binding.chipXda.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://forum.xda-developers.com/m/d4rk7355608.10095012"))));
        binding.chipMusic.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://sites.google.com/view/d4rk7355608/tracks"))));
        return binding.getRoot();
    }
}
