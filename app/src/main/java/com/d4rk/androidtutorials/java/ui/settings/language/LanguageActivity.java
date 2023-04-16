package com.d4rk.androidtutorials.java.ui.settings.language;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.LocaleManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import androidx.appcompat.app.AppCompatActivity;
import com.d4rk.androidtutorials.java.R;
import com.d4rk.androidtutorials.java.databinding.ActivityLanguageBinding;
import me.zhanghai.android.fastscroll.FastScrollerBuilder;
import java.util.Locale;
public class LanguageActivity extends AppCompatActivity {
    private ActivityLanguageBinding binding;
    private LocaleManager localeManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLanguageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        new FastScrollerBuilder(binding.scrollView).useMd2Style().build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            localeManager = (LocaleManager) getSystemService(Context.LOCALE_SERVICE);
        }
        binding.buttonDefault.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                localeManager.setApplicationLocales(LocaleList.getEmptyLocaleList());
            } else {
                setAppLocale(Locale.getDefault().getLanguage(), this);
            }
        });
        binding.buttonLanguageEn.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                localeManager.setApplicationLocales(new LocaleList(Locale.forLanguageTag("en")));
            } else {
                setAppLocale("en", this);
            }
            recreate();
        });
        binding.buttonLanguageRo.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                localeManager.setApplicationLocales(new LocaleList(Locale.forLanguageTag("ro")));
            } else {
                setAppLocale("ro", this);
            }
            recreate();
        });
        binding.buttonLanguageDe.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                localeManager.setApplicationLocales(new LocaleList(Locale.forLanguageTag("de")));
            } else {
                setAppLocale("de", this);
            }
        });
        binding.buttonLanguageEs.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                localeManager.setApplicationLocales(new LocaleList(Locale.forLanguageTag("es")));
            } else {
                setAppLocale("es", this);
            }
        });
        binding.buttonLanguageFr.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                localeManager.setApplicationLocales(new LocaleList(Locale.forLanguageTag("fr")));
            } else {
                setAppLocale("fr", this);
            }
        });
        binding.buttonLanguageHi.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                localeManager.setApplicationLocales(new LocaleList(Locale.forLanguageTag("hi")));
            } else {
                setAppLocale("hi", this);
            }
        });
        binding.buttonLanguageIn.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                localeManager.setApplicationLocales(new LocaleList(Locale.forLanguageTag("in")));
            } else {
                setAppLocale("in", this);
            }
        });
        binding.buttonLanguageIt.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                localeManager.setApplicationLocales(new LocaleList(Locale.forLanguageTag("it")));
            } else {
                setAppLocale("it", this);
            }
        });
        binding.buttonLanguageJa.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                localeManager.setApplicationLocales(new LocaleList(Locale.forLanguageTag("ja")));
            } else {
                setAppLocale("ja", this);
            }
        });
        binding.buttonLanguageRu.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                localeManager.setApplicationLocales(new LocaleList(Locale.forLanguageTag("ru")));
            } else {
                setAppLocale("ru", this);
            }
        });
        binding.buttonLanguageTr.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                localeManager.setApplicationLocales(new LocaleList(Locale.forLanguageTag("tr")));
            } else {
                setAppLocale("tr", this);
            }
        });
        binding.buttonLanguageBg.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                localeManager.setApplicationLocales(new LocaleList(Locale.forLanguageTag("bg")));
            } else {
                setAppLocale("bg", this);
            }
        });
        binding.buttonLanguagePl.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                localeManager.setApplicationLocales(new LocaleList(Locale.forLanguageTag("pl")));
            } else {
                setAppLocale("pl", this);
            }
        });
        binding.buttonLanguageUk.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                localeManager.setApplicationLocales(new LocaleList(Locale.forLanguageTag("uk")));
            } else {
                setAppLocale("uk", this);
            }
        });
    }
    @SuppressLint("AppBundleLocaleChanges")
    @SuppressWarnings("deprecation")
    private void setAppLocale(String language, Activity activity) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        activity.getBaseContext().getResources().updateConfiguration(config, activity.getBaseContext().getResources().getDisplayMetrics());
    }
    @Override
    protected void onResume() {
        super.onResume();
        String language;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            switch (localeManager.getApplicationLocales().toLanguageTags()) {
                case "en":
                    language = "English";
                    break;
                case "ro":
                    language = "Română";
                    break;
                case "de":
                    language = "Deutsch";
                    break;
                case "es":
                    language = "Español";
                    break;
                case "fr":
                    language = "Français";
                    break;
                case "hi":
                    language = "हिन्दी";
                    break;
                case "in":
                    language = "Indonesia";
                    break;
                case "it":
                    language = "Italiano";
                    break;
                case "ja":
                    language = "日本語";
                    break;
                case "ru":
                    language = "Русский";
                    break;
                case "tr":
                    language = "Türkçe";
                    break;
                case "sv":
                    language = "Svenska";
                    break;
                case "bg":
                    language = "български";
                    break;
                case "pl":
                    language = "Polski";
                    break;
                case "uk":
                    language = "Ukrainian";
                    break;
                default:
                    language = getResources().getString(R.string.system_default);
                    break;
            }
        } else {
            language = Locale.getDefault().getDisplayLanguage();
        }
        String currentLanguage = getResources().getString(R.string.current_language, language);
        binding.textViewCurrentLanguage.setText(currentLanguage);
    }
}