package com.d4rk.androidtutorials.java.ui.screens.android;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.d4rk.androidtutorials.java.R;

public class AndroidStudioFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences_android_studio, rootKey);
    }
}