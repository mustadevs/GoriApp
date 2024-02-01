package com.mustadevs.goriapp.ui.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mustadevs.goriapp.R
import com.mustadevs.goriapp.databinding.FragmentProductsBinding
import com.mustadevs.goriapp.domain.model.ProductsInfo
import com.mustadevs.goriapp.domain.model.ProductsInfo.*
import com.mustadevs.goriapp.domain.model.ProductsModel
import com.mustadevs.goriapp.ui.home.AndroidEntryPoint
import com.mustadevs.goriapp.ui.products.adapter.ProductsAdapter
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private val productsViewModel by viewModels<ProductsViewModel>()
    private lateinit var productsAdapter: ProductsAdapter

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()

    }

    private fun initUI() {
        initList()
        initSearch()
        initUIState()
    }

    private fun initSearch() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })
    }
    private fun filterList(query: String?) {
        if (query != null) {
            val filteredList = mutableListOf<ProductsInfo>()
            for (product in ProductsInfo::class.sealedSubclasses) {
                val productObject = product.objectInstance
                if (productObject is ProductsInfo && getString(productObject.name).toLowerCase(Locale.ROOT).contains(query)) {
                    filteredList.add(productObject)
                }
            }
            productsAdapter.updateList(filteredList)
        }
    }



    private fun initList() {
        productsAdapter = ProductsAdapter(onItemSelected = { selectedProduct ->
            val type = when (selectedProduct) {
                BuzoAmarillo -> ProductsModel.BuzoAmarillo
                BuzoNaranja -> ProductsModel.BuzoNaranja
                BuzoVerde -> ProductsModel.BuzoVerde
                BuzoVerdeOscuro -> ProductsModel.BuzoVerdeOscuro
                RemeraBlanca -> ProductsModel.RemeraBlanca
                RemeraDoblada -> ProductsModel.RemeraDoblada
                RemeraJero -> ProductsModel.RemeraJero
                RemeraNegra -> ProductsModel.RemeraNegra
            }

            findNavController().navigate(
                ProductsFragmentDirections.actionProductsFragmentToProductsDetailActivity(type)
            )
        })

        binding.rvProducts.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productsAdapter
        }
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                productsViewModel.products.collect {
                    // Actualiza la lista de productos en el adaptador
                    productsAdapter.updateList(it)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(layoutInflater, container, false)
        // Infla el diseño para este fragmento
        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

