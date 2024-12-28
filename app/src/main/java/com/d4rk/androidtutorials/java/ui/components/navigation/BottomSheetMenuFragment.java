package com.d4rk.androidtutorials.java.ui.components.navigation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.d4rk.androidtutorials.java.BuildConfig;
import com.d4rk.androidtutorials.java.R;
import com.d4rk.androidtutorials.java.databinding.BottomSheetMenuBinding;
import com.d4rk.androidtutorials.java.ui.settings.SettingsActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetMenuFragment extends BottomSheetDialogFragment {

    private BottomSheetMenuBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = BottomSheetMenuBinding.inflate(inflater, container, false);

        // 1) Settings
        binding.menuSettings.setOnClickListener(v -> {
            // Launch the SettingsActivity
            Intent intent = new Intent(requireContext(), SettingsActivity.class);
            startActivity(intent);
            dismiss();
        });

        // 2) Help & Feedback
        binding.menuHelpFeedback.setOnClickListener(v -> {
            // This used to be in a Preference with <intent ... >
            // Now let's manually launch the same activity
            Intent helpIntent = new Intent();
            // The activity's package and class must match your help Activity
            helpIntent.setClassName(
                    "com.d4rk.androidtutorials.java",
                    "com.d4rk.androidtutorials.java.ui.screens.help.HelpActivity"
            );
            // Check if something can handle it
            if (helpIntent.resolveActivity(requireContext().getPackageManager()) != null) {
                startActivity(helpIntent);
            }
            dismiss();
        });

        // 3) Updates
        binding.menuUpdates.setOnClickListener(v -> {
            // Open a link in the browser (could be your changelog or update page)
            Uri changelogUri = Uri.parse("https://github.com/D4rK7355608/com.d4rk.androidtutorials.java/blob/main/CHANGELOG.md");
            Intent openChangelog = new Intent(Intent.ACTION_VIEW, changelogUri);
            if (openChangelog.resolveActivity(requireContext().getPackageManager()) != null) {
                startActivity(openChangelog);
            }
            dismiss();
        });

        // 4) Share
        binding.menuShare.setOnClickListener(v -> {
            // The "share" logic from your old preference
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");

            // Example Play Store link, using your BuildConfig.APPLICATION_ID
            String shareLink = "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;

            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareLink);
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_subject));

            startActivity(
                    Intent.createChooser(sharingIntent, getString(R.string.share_using))
            );
            dismiss();
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}