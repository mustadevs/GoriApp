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

    //1 agrego binding
    private lateinit var binding: ActivityDiscountDetailBinding
    private val discountDetailViewModel: DiscountDetailViewModel by viewModels()
    //anuncios
    private var interstitial: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //2 agrego binding
        binding = ActivityDiscountDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //google ad
        initLoadAds()
        MobileAds.initialize(this)
        setContentView(R.layout.activity_discount_detail)
        initInterstitialAds()



    }
    private fun showInterstitial() {
        if(interstitial!=null){
            interstitial!!.show(this)
            interstitial=null
        }else{

            initInterstitialAds()
            binding.root.isEnabled = false
        }
    }
    private fun initInterstitialAds() {
        var adRequest = com.google.android.gms.ads.AdRequest.Builder().build()

        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback(){
            override fun onAdLoaded(interstitialAd : InterstitialAd) {
                interstitial = interstitialAd
                Toast.makeText(this@DiscountDetailActivity, "Anuncio encontrado", Toast.LENGTH_SHORT).show()
                binding.root.isEnabled=true
                showInterstitial()
            }

            override fun onAdFailedToLoad(p0: LoadAdError) {
                interstitial=null
                Toast.makeText(this@DiscountDetailActivity, "Verifique su conexion", Toast.LENGTH_SHORT).show()
                binding.root.isEnabled=true
            }
        }

        )
    }

    private fun initLoadAds() {
        val adRequest = AdRequest.Builder().build()
        binding.banner.loadAd(adRequest)
    }

}