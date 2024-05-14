package com.mustadevs.goriapp.ui.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
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

class HomeFragment : Fragment() {

    interface NavigationBarListener {
        fun showBottomNavigationBar(show: Boolean)
    }

    private var navigationBarListener: NavigationBarListener? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HorizontalRecyclerViewAdapter
    private var interstitial: InterstitialAd? = null
    private var binding: FragmentHomeBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // Indica que este fragmento tiene un menú de opciones
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NavigationBarListener) {
            navigationBarListener = context
            navigationBarListener?.showBottomNavigationBar(true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding?.root

        // Inicializa y configura el RecyclerView
        recyclerView = view?.findViewById(R.id.recyclerViewHorizontal) ?: RecyclerView(requireContext())
        adapter = HorizontalRecyclerViewAdapter()

        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.bottom_nav_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Agrega un OnClickListener al CardView de DESCUENTOS
        binding?.viewDesc?.setOnClickListener {
            // Cuando se hace clic en Descuentos, inicia DiscountDetailActivity
            val intent = Intent(activity, DiscountDetailActivity::class.java)
            startActivity(intent)
            MobileAds.initialize(requireActivity())
            initInterstitialAds()
        }

        // Agrega un OnClickListener a viewDest para ir a PRODUCTOS
        binding?.viewDest?.setOnClickListener {
            // Utiliza la acción para navegar a ProductsFragment
            findNavController().navigate(R.id.action_HomeFragment_to_productsFragment)
        }

        // Agrega un OnClickListener al CardView de NUESTRA HISTORIA
        binding?.viewQuien?.setOnClickListener {
            // Utiliza la acción para navegar a ProductsFragment
            findNavController().navigate(R.id.action_HomeFragment_to_historyDetailActivity)
        }

        // Ir a seccion CONTACTO
        binding?.viewContacto?.setOnClickListener {
            // Cuando se hace clic en Contacto, inicia el link de whatsapp
            openUrl("https://bit.ly/WhatsappGORI")
        }

        // Ir a seccion DONDE ENCONTRARNOS
        binding?.viewUbicacion?.setOnClickListener {
            // Cuando se hace clic en Descuentos, inicia DiscountDetailActivity
            val intent = Intent(activity, WhereDetailActivity::class.java)
            startActivity(intent)
        }
    }

    // Función para mostrar el anuncio intersticial
    private fun showInterstitial() {
        if(interstitial!=null){
            interstitial!!.show(requireActivity())
            interstitial=null
        }else{
            initInterstitialAds()
        }
    }

    // Función para inicializar el anuncio intersticial
    private fun initInterstitialAds() {
        var adRequest = com.google.android.gms.ads.AdRequest.Builder().build()

        InterstitialAd.load(requireActivity(), "ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback(){
            override fun onAdLoaded(interstitialAd : InterstitialAd) {
                interstitial = interstitialAd
                Toast.makeText(requireActivity(), "Anuncio encontrado", Toast.LENGTH_SHORT).show()
                showInterstitial()
            }

            override fun onAdFailedToLoad(p0: LoadAdError) {
                interstitial=null
                Toast.makeText(requireActivity(), "Verifique su conexion", Toast.LENGTH_SHORT).show()
            }
        }
        )
    }

    // Función para abrir un enlace en WhatsApp
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
