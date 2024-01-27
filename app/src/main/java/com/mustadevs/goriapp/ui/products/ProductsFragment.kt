package com.mustadevs.goriapp.ui.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mustadevs.goriapp.R
import com.mustadevs.goriapp.databinding.FragmentProductsBinding

import com.mustadevs.goriapp.domain.model.ProductsModel.*
import com.mustadevs.goriapp.ui.home.AndroidEntryPoint
import com.mustadevs.goriapp.ui.products.adapter.ProductsAdapter
import kotlinx.coroutines.launch

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
        initUIState()
    }

    private fun initList() {
        productsAdapter = ProductsAdapter(onItemSelected = {
            Toast.makeText(context, getString(it.name), Toast.LENGTH_SHORT).show()
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
                    //CAMBIOS EN LISTA DE PRODUCTOS
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
        // Inflate the layout for this fragment
        return binding.root
    }
}

