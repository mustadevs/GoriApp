package com.mustadevs.goriapp.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mustadevs.goriapp.data.listener.CartLoadListener
import com.mustadevs.goriapp.databinding.ActivityCartBinding
import com.mustadevs.goriapp.domain.model.CartModel
import com.mustadevs.goriapp.ui.products.adapter.MyCartAdapter

class CartActivity : AppCompatActivity(), CartLoadListener {
    private lateinit var binding: ActivityCartBinding
    private var cartLoadListener: CartLoadListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        loadCartFromFirebase()


    }

    private fun loadCartFromFirebase() {
        val cartModels: MutableList<CartModel> = ArrayList()
        FirebaseDatabase.getInstance()
            .getReference("Cart")
            .child("UNIQUE_USER_ID")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (cartSnapshot in snapshot.children) {
                        val cartModel = cartSnapshot.getValue(CartModel::class.java)
                        cartModel!!.key = cartSnapshot.key
                        cartModels.add(cartModel)
                    }
                    cartLoadListener?.onLoadCartSuccess(cartModels)
                    Log.d("ProductsFragment", "countCartFromFirebase: Success")
                }

                override fun onCancelled(error: DatabaseError) {
                    cartLoadListener?.onLoadCartFailed(error.message)
                    Log.e("ProductsFragment", "countCartFromFirebase: Failed", error.toException())
                }
            })
    }

    private fun init() {
        cartLoadListener = this
        val layoutManager = LinearLayoutManager(this)

        binding.recyclerCart.layoutManager = layoutManager
        binding.recyclerCart.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))

        binding.btnBack.setOnClickListener { finish() }
    }

    override fun onLoadCartSuccess(cartModelList: MutableList<CartModel>) {
        var sum = 0.0
        for (cartModel in cartModelList) {
            sum += cartModel.totalPrice
        }
        binding.txtTotal.text = StringBuilder("$").append(sum)
        val adapter = MyCartAdapter(this, cartModelList.toMutableList())
        binding.recyclerCart.adapter = adapter

    }

    override fun onLoadCartFailed(message: String?) {
        Snackbar.make(binding.productsLayout, message!!, Snackbar.LENGTH_LONG).show()
    }
}
