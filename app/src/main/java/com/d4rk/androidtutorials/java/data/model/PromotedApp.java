package com.d4rk.androidtutorials.java.data.model;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

public record PromotedApp(
        @DrawableRes int iconResId,
        @StringRes int nameResId,
        @StringRes int descriptionResId,
        String packageName
) {}
