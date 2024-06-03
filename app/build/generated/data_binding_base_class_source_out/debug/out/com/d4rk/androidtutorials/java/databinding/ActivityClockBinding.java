// Generated by view binder compiler. Do not edit!
package com.d4rk.androidtutorials.java.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AnalogClock;
import android.widget.DigitalClock;
import android.widget.TextClock;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.d4rk.androidtutorials.java.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textview.MaterialTextView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import me.zhanghai.android.fastscroll.FastScrollScrollView;

public final class ActivityClockBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final DigitalClock digitalClock;

  @NonNull
  public final ExtendedFloatingActionButton floatingButtonShowSyntax;

  @NonNull
  public final FastScrollScrollView scrollView;

  @NonNull
  public final AnalogClock simpleAnalogClock;

  @NonNull
  public final MaterialTextView simpleAnalogClockText;

  @NonNull
  public final TextClock simpleTextClock;

  @NonNull
  public final MaterialTextView simpleTextClockText;

  private ActivityClockBinding(@NonNull ConstraintLayout rootView,
      @NonNull DigitalClock digitalClock,
      @NonNull ExtendedFloatingActionButton floatingButtonShowSyntax,
      @NonNull FastScrollScrollView scrollView, @NonNull AnalogClock simpleAnalogClock,
      @NonNull MaterialTextView simpleAnalogClockText, @NonNull TextClock simpleTextClock,
      @NonNull MaterialTextView simpleTextClockText) {
    this.rootView = rootView;
    this.digitalClock = digitalClock;
    this.floatingButtonShowSyntax = floatingButtonShowSyntax;
    this.scrollView = scrollView;
    this.simpleAnalogClock = simpleAnalogClock;
    this.simpleAnalogClockText = simpleAnalogClockText;
    this.simpleTextClock = simpleTextClock;
    this.simpleTextClockText = simpleTextClockText;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityClockBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityClockBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_clock, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityClockBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.digital_clock;
      DigitalClock digitalClock = ViewBindings.findChildViewById(rootView, id);
      if (digitalClock == null) {
        break missingId;
      }

      id = R.id.floating_button_show_syntax;
      ExtendedFloatingActionButton floatingButtonShowSyntax = ViewBindings.findChildViewById(rootView, id);
      if (floatingButtonShowSyntax == null) {
        break missingId;
      }

      id = R.id.scroll_view;
      FastScrollScrollView scrollView = ViewBindings.findChildViewById(rootView, id);
      if (scrollView == null) {
        break missingId;
      }

      id = R.id.simpleAnalogClock;
      AnalogClock simpleAnalogClock = ViewBindings.findChildViewById(rootView, id);
      if (simpleAnalogClock == null) {
        break missingId;
      }

      id = R.id.simpleAnalogClockText;
      MaterialTextView simpleAnalogClockText = ViewBindings.findChildViewById(rootView, id);
      if (simpleAnalogClockText == null) {
        break missingId;
      }

      id = R.id.simpleTextClock;
      TextClock simpleTextClock = ViewBindings.findChildViewById(rootView, id);
      if (simpleTextClock == null) {
        break missingId;
      }

      id = R.id.simpleTextClockText;
      MaterialTextView simpleTextClockText = ViewBindings.findChildViewById(rootView, id);
      if (simpleTextClockText == null) {
        break missingId;
      }

      return new ActivityClockBinding((ConstraintLayout) rootView, digitalClock,
          floatingButtonShowSyntax, scrollView, simpleAnalogClock, simpleAnalogClockText,
          simpleTextClock, simpleTextClockText);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}