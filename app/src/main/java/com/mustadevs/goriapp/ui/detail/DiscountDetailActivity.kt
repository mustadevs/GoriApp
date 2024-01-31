package com.mustadevs.goriapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.mustadevs.goriapp.R
import com.mustadevs.goriapp.databinding.ActivityDiscountDetailBinding

class DiscountDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDiscountDetailBinding
    private val discountDetailViewModel: DiscountDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDiscountDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //google ad
        initLoadAds()
        MobileAds.initialize(this)
        setContentView(R.layout.activity_discount_detail)
    }

    private fun initLoadAds() {
        val adRequest = AdRequest.Builder().build()
        binding.googleAd1.loadAd(adRequest)
    }

}