package com.d4rk.androidtutorials.java.ui.android.alerts.toast.tabs
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.d4rk.androidtutorials.java.R
import com.d4rk.androidtutorials.java.databinding.FragmentToastCodeBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import me.zhanghai.android.fastscroll.FastScrollerBuilder
class ToastTabCodeFragment : Fragment() {
    private lateinit var binding: FragmentToastCodeBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentToastCodeBinding.inflate(inflater, container, false)
        FastScrollerBuilder(binding.scrollView).useMd2Style().build()
        MobileAds.initialize(requireContext())
        binding.adView.loadAd(AdRequest.Builder().build())
        val inputStream = resources.openRawResource(R.raw.text_toast_java)
        val xmlText = inputStream.readBytes().toString(Charsets.UTF_8)
        inputStream.close()
        binding.textView.text = xmlText
        return binding.root
    }
}