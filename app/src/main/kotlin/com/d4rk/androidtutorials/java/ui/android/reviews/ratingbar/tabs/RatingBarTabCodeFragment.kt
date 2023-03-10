package com.d4rk.androidtutorials.java.ui.android.reviews.ratingbar.tabs
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.d4rk.androidtutorials.java.R
import com.d4rk.androidtutorials.java.databinding.FragmentRatingBarCodeBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import me.zhanghai.android.fastscroll.FastScrollerBuilder
class RatingBarTabCodeFragment : Fragment() {
    private lateinit var binding: FragmentRatingBarCodeBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRatingBarCodeBinding.inflate(inflater, container, false)
        FastScrollerBuilder(binding.scrollView).useMd2Style().build()
        MobileAds.initialize(requireContext())
        binding.adView.loadAd(AdRequest.Builder().build())
        val inputStream = resources.openRawResource(R.raw.text_rating_bar_java)
        val  text = inputStream.readBytes().toString(Charsets.UTF_8)
        inputStream.close()
        binding.textView.text = text
        return binding.root
    }
}