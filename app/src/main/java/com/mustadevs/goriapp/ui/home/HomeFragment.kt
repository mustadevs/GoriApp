package com.mustadevs.goriapp.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.mustadevs.goriapp.R
import com.mustadevs.goriapp.databinding.FragmentHomeBinding
import com.mustadevs.goriapp.ui.detail.DiscountDetailActivity
import com.mustadevs.goriapp.ui.detail.WhereDetailActivity
import com.mustadevs.goriapp.ui.home.adapter.HorizontalRecyclerViewAdapter
import com.mustadevs.goriapp.ui.products.ProductsFragment

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HorizontalRecyclerViewAdapter
    private var interstitial: InterstitialAd? = null
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
            MobileAds.initialize(requireActivity())
            initInterstitialAds()
        }
        // Agrega un OnClickListener a viewDest
        binding?.viewDest?.setOnClickListener {
            // Utiliza la acción para navegar a ProductsFragment
            findNavController().navigate(R.id.action_homeFragment_to_productsFragment)
        }

        // Agrega un OnClickListener al CardView de Descuentos
        binding?.viewQuien?.setOnClickListener {
            // Utiliza la acción para navegar a ProductsFragment
            findNavController().navigate(R.id.action_homeFragment_to_historyDetailActivity)
        }

        binding?.viewContacto?.setOnClickListener {
            // Cuando se hace clic en Contacto, inicia el link de whatsapp
            openUrl("https://bit.ly/WhatsappGORI")
        }

        //Boton donde encontrarnos
        binding?.viewUbicacion?.setOnClickListener {
            // Cuando se hace clic en Descuentos, inicia DiscountDetailActivity
            val intent = Intent(activity, WhereDetailActivity::class.java)
            startActivity(intent)
        }
        // Inicializa y configura el RecyclerView
        recyclerView = view?.findViewById(R.id.recyclerViewHorizontal) ?: RecyclerView(requireContext())
        adapter = HorizontalRecyclerViewAdapter()

        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter

        return view
    }



    //Siguientes 2 funciones para ads de video interstelar
    private fun showInterstitial() {
        if(interstitial!=null){
            interstitial!!.show(requireActivity())
            interstitial=null
        }else{

            initInterstitialAds()
            //binding.viewDesc.isEnabled = false
        }
    }
    private fun initInterstitialAds() {
        var adRequest = com.google.android.gms.ads.AdRequest.Builder().build()

        InterstitialAd.load(requireActivity(), "ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback(){
            override fun onAdLoaded(interstitialAd : InterstitialAd) {
                interstitial = interstitialAd
                Toast.makeText(requireActivity(), "Anuncio encontrado", Toast.LENGTH_SHORT).show()

                //binding.viewDesc.isEnabled=true
                showInterstitial()
            }

            override fun onAdFailedToLoad(p0: LoadAdError) {
                interstitial=null
                Toast.makeText(requireActivity(), "Verifique su conexion", Toast.LENGTH_SHORT).show()
                //binding.viewDesc.isEnabled=true
            }
        }

        )
    }

    /*Funcion que abre link de whatsapp*/
    private fun openUrl(link: String) {
        val uri = Uri.parse(link)
        val inte = Intent(Intent.ACTION_VIEW, uri)

        startActivity(inte)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null // Libera la referencia para evitar posibles fugas de memoria
    }


}