package com.d4rk.androidtutorials.java.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;

import androidx.core.content.res.ResourcesCompat;

import com.d4rk.androidtutorials.java.R;

public class FontManager {

    public static Typeface getMonospaceFont(Context context, SharedPreferences prefs) {
        return switch (prefs.getString(context.getString(R.string.key_monospace_font), "0")) {
            case "1" -> ResourcesCompat.getFont(context, R.font.font_fira_code);
            case "2" -> ResourcesCompat.getFont(context, R.font.font_jetbrains_mono);
            case "3" -> ResourcesCompat.getFont(context, R.font.font_noto_sans_mono);
            case "4" -> ResourcesCompat.getFont(context, R.font.font_poppins);
            case "5" -> ResourcesCompat.getFont(context, R.font.font_roboto_mono);
            default -> ResourcesCompat.getFont(context, R.font.font_audiowide);
        };
    }
}
