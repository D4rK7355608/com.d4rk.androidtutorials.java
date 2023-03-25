package com.d4rk.androidtutorials.java.ui.android.clocks.clock.tabs;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.d4rk.androidtutorials.java.R;
import com.d4rk.androidtutorials.java.databinding.FragmentClockLayoutBinding;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import me.zhanghai.android.fastscroll.FastScrollerBuilder;
public class ClockTabLayoutFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        com.d4rk.androidtutorials.java.databinding.FragmentClockLayoutBinding binding = FragmentClockLayoutBinding.inflate(inflater, container, false);
        new FastScrollerBuilder(binding.scrollView).useMd2Style().build();
        MobileAds.initialize(requireContext());
        binding.adView.loadAd(new AdRequest.Builder().build());
        setTextView(binding.textViewDigitalClockXml,R.raw.text_clock_digital_xml);
        setTextView(binding.textViewTextClockXml,R.raw.text_clock_xml);
        setTextView(binding.textViewAnalogClockXml,R.raw.text_clock_analog_xml);
        return binding.getRoot();
    }
    private void setTextView(TextView textView, int rawResource) {
        try {
            InputStream inputStream = getResources().openRawResource(rawResource);
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            String text = result.toString(StandardCharsets.UTF_8.name());
            inputStream.close();
            textView.setText(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}