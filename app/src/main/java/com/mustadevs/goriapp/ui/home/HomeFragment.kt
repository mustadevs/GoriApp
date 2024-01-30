package com.mustadevs.goriapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.mustadevs.goriapp.databinding.FragmentHomeBinding
import com.mustadevs.goriapp.ui.detail.DiscountDetailActivity


class HomeFragment : Fragment() {



    private var binding: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding?.root

        // Agrega un OnClickListener al CardView de Descuentos
        binding?.viewDesc?.setOnClickListener {
            // Cuando se hace clic en Descuentos, inicia DiscountDetailActivity
            val intent = Intent(activity, DiscountDetailActivity::class.java)
            startActivity(intent)

        }

        return view
    }




    override fun onDestroyView() {
        super.onDestroyView()
        binding = null // Libera la referencia para evitar posibles fugas de memoria
    }


}