// Generated by view binder compiler. Do not edit!
package com.d4rk.androidtutorials.java.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.d4rk.androidtutorials.java.R;
import com.google.android.gms.ads.AdView;
import com.google.android.material.card.MaterialCardView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import me.zhanghai.android.fastscroll.FastScrollScrollView;

public final class ActivityShortcutsCodeBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final AdView adView;

  @NonNull
  public final MaterialCardView cardViewAd;

  @NonNull
  public final FastScrollScrollView scrollView;

  @NonNull
  public final TableLayout tableLayout;

  private ActivityShortcutsCodeBinding(@NonNull ConstraintLayout rootView, @NonNull AdView adView,
      @NonNull MaterialCardView cardViewAd, @NonNull FastScrollScrollView scrollView,
      @NonNull TableLayout tableLayout) {
    this.rootView = rootView;
    this.adView = adView;
    this.cardViewAd = cardViewAd;
    this.scrollView = scrollView;
    this.tableLayout = tableLayout;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityShortcutsCodeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityShortcutsCodeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_shortcuts_code, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityShortcutsCodeBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.ad_view;
      AdView adView = ViewBindings.findChildViewById(rootView, id);
      if (adView == null) {
        break missingId;
      }

      id = R.id.card_view_ad;
      MaterialCardView cardViewAd = ViewBindings.findChildViewById(rootView, id);
      if (cardViewAd == null) {
        break missingId;
      }

      id = R.id.scroll_view;
      FastScrollScrollView scrollView = ViewBindings.findChildViewById(rootView, id);
      if (scrollView == null) {
        break missingId;
      }

      id = R.id.table_layout;
      TableLayout tableLayout = ViewBindings.findChildViewById(rootView, id);
      if (tableLayout == null) {
        break missingId;
      }

      return new ActivityShortcutsCodeBinding((ConstraintLayout) rootView, adView, cardViewAd,
          scrollView, tableLayout);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}