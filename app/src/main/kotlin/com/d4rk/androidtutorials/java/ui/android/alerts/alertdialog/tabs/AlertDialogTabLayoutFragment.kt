package com.d4rk.androidtutorials.java.ui.android.alerts.alertdialog.tabs
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.d4rk.androidtutorials.java.R
import com.d4rk.androidtutorials.java.databinding.FragmentAlertDialogLayoutBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import me.zhanghai.android.fastscroll.FastScrollerBuilder
import java.io.InputStream
class AlertDialogTabLayoutFragment : Fragment() {
    private lateinit var binding: FragmentAlertDialogLayoutBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAlertDialogLayoutBinding.inflate(inflater, container, false)
        FastScrollerBuilder(binding.scrollView).useMd2Style().build()
        MobileAds.initialize(requireContext())
        binding.adView.loadAd(AdRequest.Builder().build())
        val inputStream: InputStream = resources.openRawResource(R.raw.text_center_button_xml)
        val xmlText = inputStream.readBytes().toString(Charsets.UTF_8)
        binding.textView.text = xmlText
        return binding.root
    }
}