package com.mustadevs.goriapp.ui.products

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mustadevs.goriapp.data.listener.ProductsLoadListener
import com.mustadevs.goriapp.databinding.FragmentProductsBinding
import com.mustadevs.goriapp.domain.model.ProductsModel
import com.mustadevs.goriapp.domain.utils.SpaceItemDecoration
import com.mustadevs.goriapp.ui.products.adapter.MyProductsAdapter
import java.util.Locale

//@AndroidEntryPoint
class ProductsFragment : Fragment(), ProductsLoadListener {

    lateinit var productsLoadListener: ProductsLoadListener
    //private val productsViewModel by viewModels<ProductsViewModel>()
    private lateinit var myProductsAdapter: MyProductsAdapter
    private lateinit var productsModels: MutableList<ProductsModel>
    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       super.onViewCreated(view, savedInstanceState)
       initUI()
    }

    private fun initUI() {
        productsModels = mutableListOf()
        initList()
        loadProductsFromFirebase()
        initSearch()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }
    private fun loadProductsFromFirebase() {
        productsModels.clear() // Clear existing data if any

        FirebaseDatabase.getInstance()
            .getReference("Drink")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (productsSnapshot in snapshot.children) {
                            val productsModel = productsSnapshot.getValue(ProductsModel::class.java)
                            productsModel!!.key = productsSnapshot.key
                            productsModels.add(productsModel)
                        }

                        // Log or print the retrieved data for debugging
                        Log.d("FirebaseData", "Data: $productsModels")

                        // Notify the listener that data has been loaded successfully
                        productsLoadListener.onProductsLoadSuccess(productsModels)
                    } else {
                        productsLoadListener.onProductsLoadFailed("El item seleccionado no existe")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    productsLoadListener.onProductsLoadFailed(error.message)
                }
            })
    }

    private fun initList() {
        productsLoadListener = this
        _binding?.let {
            val gridLayoutManager = GridLayoutManager(requireContext(), 2)
            binding.rvProducts.layoutManager = gridLayoutManager
            binding.rvProducts.addItemDecoration(SpaceItemDecoration())
        }

        // Initialize the adapter only once when the fragment is created
        myProductsAdapter = MyProductsAdapter(requireContext(), emptyList())
        binding.rvProducts.adapter = myProductsAdapter
    }

    override fun onProductsLoadSuccess(productsModelList: List<ProductsModel>?) {
        if (productsModelList.isNullOrEmpty()) {
            Snackbar.make(binding.productsLayout, "No products available", Snackbar.LENGTH_LONG).show()
        } else {
            // Assuming you have the correct instance of the adapter
            myProductsAdapter.updateList(productsModelList)
        }
    }

        override fun onProductsLoadFailed(message: String?) {
            Snackbar.make(binding.productsLayout,message!!,Snackbar.LENGTH_LONG).show()

        }


    private fun initSearch() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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
            val filteredList = mutableListOf<ProductsModel>()
            for (productModel in productsModels) {
                if (productModel.name?.toLowerCase(Locale.ROOT)?.contains(query.toLowerCase(Locale.ROOT)) == true) {
                    filteredList.add(productModel)
                }
            }
            myProductsAdapter.updateList(filteredList)
        }
    }
        //productsAdapter = ProductsAdapter(onItemSelected = { selectedProduct ->
        //    val type = when (selectedProduct) {
        //        taurus -> ProductsModel.gemini
        //        BuzoNaranja -> ProductsModel.BuzoNaranja
        //        BuzoVerde -> ProductsModel.BuzoVerde
        //        BuzoVerdeOscuro -> ProductsModel.BuzoVerdeOscuro
        //        RemeraBlanca -> ProductsModel.RemeraBlanca
        //        RemeraDoblada -> ProductsModel.RemeraDoblada
         //       RemeraJero -> ProductsModel.RemeraJero
         //       RemeraNegra -> ProductsModel.RemeraNegra
         //   }
//
           // findNavController().navigate(
           //     ProductsFragmentDirections.actionProductsFragmentToProductsDetailActivity(type)
          //  )
       // })

       // binding.rvProducts.apply {
        //    layoutManager = LinearLayoutManager(context)
        //    adapter = productsAdapter
        //}


    //private fun initUIState() {
    //    lifecycleScope.launch {
     //       repeatOnLifecycle(Lifecycle.State.STARTED){
    //            productsViewModel.products.collect {
     //               // Actualiza la lista de productos en el adaptador
     //               myProductsAdapter.updateList(it)
     //           }
     //       }
     //   }
   // }

   // override fun onCreateView(
    //    inflater: LayoutInflater, container: ViewGroup?,
     //   savedInstanceState: Bundle?
   // ): View {
    //    _binding = FragmentProductsBinding.inflate(layoutInflater, container, false)
        // Infla el diseño para este fragmento
    //    return binding.root
   // }






    //override fun onDestroy() {
    //    super.onDestroy()
    //    _binding = null
   // }
}

