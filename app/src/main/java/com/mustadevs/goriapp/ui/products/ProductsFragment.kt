package com.mustadevs.goriapp.ui.products

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mustadevs.goriapp.data.listener.CartLoadListener
import com.mustadevs.goriapp.data.listener.ProductsLoadListener
import com.mustadevs.goriapp.databinding.FragmentProductsBinding
import com.mustadevs.goriapp.domain.model.CartModel
import com.mustadevs.goriapp.domain.model.ProductsModel
import com.mustadevs.goriapp.domain.utils.SpaceItemDecoration
import com.mustadevs.goriapp.ui.detail.CartActivity
import com.mustadevs.goriapp.ui.products.adapter.MyProductsAdapter
import com.mustadevs.goriapp.ui.products.eventbus.UpdateCartEvent
import com.nex3z.notificationbadge.NotificationBadge
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.Locale

//@AndroidEntryPoint
class ProductsFragment : Fragment(), ProductsLoadListener, CartLoadListener {

    lateinit var productsLoadListener: ProductsLoadListener
    lateinit var cartLoadListener: CartLoadListener
    private lateinit var badge: NotificationBadge
    private lateinit var myProductsAdapter: MyProductsAdapter
    private lateinit var productsModels: MutableList<ProductsModel>
    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!
    private var userId: String? = null
    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        if(EventBus.getDefault().hasSubscriberForEvent(UpdateCartEvent::class.java))
            EventBus.getDefault().removeStickyEvent(UpdateCartEvent::class.java)
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode= ThreadMode.MAIN,sticky=true)
    public fun onUpdateCartEvent(event:UpdateCartEvent)
    {
        countCartFromFirebase()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       super.onViewCreated(view, savedInstanceState)
       initUI()
        badge = binding.badge
    }

    private fun initUI() {
        productsModels = mutableListOf()
        initList()
        loadProductsFromFirebase()

        userId = FirebaseAuth.getInstance().currentUser?.uid
        Log.d("ProductsFragment", "userId: $userId")

        countCartFromFirebase()
        initSearch()
    }

    private fun countCartFromFirebase() {
        Log.d("ProductsFragment", "countCartFromFirebase: Start")
        val cartModels : MutableList<CartModel> = ArrayList()
        Log.d("ProductsFragment", "countCartFromFirebase: userId=$userId")
        FirebaseDatabase.getInstance()
            .getReference("Cart")
            .child("UNIQUE_USER_ID")
            .addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(cartSnapshot in snapshot.children)
                    {
                        val cartModel = cartSnapshot.getValue(CartModel::class.java)
                        cartModel!!.key = cartSnapshot.key
                        cartModels.add(cartModel)
                    }
                    cartLoadListener.onLoadCartSuccess(cartModels)
                    Log.d("ProductsFragment", "countCartFromFirebase: Success")
                }
                override fun onCancelled(error: DatabaseError) {
                    cartLoadListener.onLoadCartFailed(error.message)
                    Log.e("ProductsFragment", "countCartFromFirebase: Failed", error.toException())
                }
            })
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
        val cartButton = binding.cartButton
        productsLoadListener = this
        cartLoadListener = this
        _binding?.let {
            val gridLayoutManager = GridLayoutManager(requireContext(), 2)
            binding.rvProducts.layoutManager = gridLayoutManager
            binding.rvProducts.addItemDecoration(SpaceItemDecoration())

            cartButton.setOnClickListener { startActivity(Intent(requireContext(), CartActivity::class.java))
            }
        }

        // Initialize the adapter only once when the fragment is created
        myProductsAdapter = MyProductsAdapter(requireContext(), emptyList(), cartLoadListener)
        binding.rvProducts.adapter = myProductsAdapter
    }

    override fun onProductsLoadSuccess(productsModelList: List<ProductsModel>?) {
        val adapter = MyProductsAdapter(requireContext(), productsModelList!!, cartLoadListener)
        binding.rvProducts.adapter = adapter
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

    override fun onLoadCartSuccess(cartModelList: MutableList<CartModel>) {
        var cartSum = 0
        for (cartModel in cartModelList!!) cartSum+= cartModel!!.quantity
        badge!!.setNumber(cartSum)
    }

    override fun onLoadCartFailed(message: String?) {
        Snackbar.make(binding.productsLayout,message!!,Snackbar.LENGTH_LONG).show()
    }
}