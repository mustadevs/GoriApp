package com.mustadevs.goriapp.ui.detail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import com.google.android.gms.ads.AdRequest
import com.mustadevs.goriapp.R
import com.mustadevs.goriapp.databinding.ActivityHistoryDetailBinding

class HistoryDetailActivity : AppCompatActivity(){

    private lateinit var binding: ActivityHistoryDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHistoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLoadAds()
        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title

        val whatsIcon = findViewById<View>(R.id.whatsapp)
        whatsIcon.setOnClickListener { openUrl("https://bit.ly/WhatsappGORI") }

    }
    private fun initLoadAds() {
        val adRequest= AdRequest.Builder().build()
        binding.banner4.loadAd(adRequest)
    }


    private fun openUrl(link: String) {
        val uri = Uri.parse(link)
        val inte = Intent(Intent.ACTION_VIEW, uri)

        startActivity(inte)
    }
}