package com.mustadevs.goriapp.ui.products.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mustadevs.goriapp.R
import com.mustadevs.goriapp.data.listener.CartLoadListener
import com.mustadevs.goriapp.data.listener.RecyclerClickListener
import com.mustadevs.goriapp.domain.model.CartModel
import com.mustadevs.goriapp.domain.model.ProductsModel
import com.mustadevs.goriapp.ui.products.eventbus.UpdateCartEvent
import org.greenrobot.eventbus.EventBus

class MyProductsAdapter(
    private val context: Context,
    private var list:List<ProductsModel>,
    private val cartListener:CartLoadListener
): RecyclerView.Adapter<MyProductsAdapter.MyProductsViewHolder>(){

    class MyProductsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
         var imageView: ImageView
         var txtName:TextView
         var txtPrice:TextView


         private var clickListener:RecyclerClickListener? = null
        fun setClickListener(clickListener: RecyclerClickListener)
        {
            this.clickListener = clickListener;
        }

        init {
            imageView = itemView.findViewById(R.id.imageView) as ImageView;
            txtName = itemView.findViewById(R.id.txtName) as TextView;
            txtPrice = itemView.findViewById(R.id.txtPrice) as TextView;

            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            clickListener!!.onItemClickListener(v,adapterPosition)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyProductsViewHolder {
        return MyProductsViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.layout_products_item,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyProductsViewHolder, position: Int) {
        Log.d("MyProductsAdapter", "Loading image: ${list[position].image}")
        Glide.with(context)
            .load(list[position].image)
            .into(holder.imageView!!)
        holder.txtName!!.text = StringBuilder().append(list[position].name)
        holder.txtPrice!!.text = StringBuilder("$").append(list[position].price)

        holder.setClickListener(object:RecyclerClickListener{
            override fun onItemClickListener(view: View?, position: Int) {
                addToCart(list[position])
            }
        })
    }

    private fun addToCart(productsModel: ProductsModel) {
        val userCart = FirebaseDatabase.getInstance()
            .getReference("Cart")
            .child("UNIQUE_USER_ID") // Puedes usar el UID del usuario de Firebase Auth

        userCart.child(productsModel.key!!)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val stock = productsModel.stock ?: 0 // Si stock es nulo, asigna 0

                    if (stock > 0) {
                        // Si hay suficiente stock, realiza la lógica para agregar al carrito
                        if (snapshot.exists()) {
                            // Si el producto ya está en el carrito, solo actualiza la cantidad
                            val cartModel = snapshot.getValue(CartModel::class.java)
                            val updateData: MutableMap<String, Any> = HashMap()
                            cartModel!!.quantity = cartModel!!.quantity + 1

                            updateData["quantity"] = cartModel!!.quantity
                            updateData["totalPrice"] = cartModel!!.quantity * cartModel.price!!.toFloat()

                            userCart.child(productsModel.key!!)
                                .updateChildren(updateData)
                                .addOnSuccessListener {
                                    // Notificar al EventBus sobre la actualización del carrito
                                    EventBus.getDefault().postSticky(UpdateCartEvent())
                                    cartListener.onLoadCartFailed("Agregaste este producto al carro correctamente")
                                }
                                .addOnFailureListener { e ->
                                    cartListener.onLoadCartFailed(e.message)
                                    Log.d("MyProductsAdapter", "Failed to add product")
                                }
                        } else {
                            // Si el producto no está en el carrito, agrégalo como nuevo
                            val cartModel = CartModel()
                            cartModel.key = productsModel.key
                            cartModel.name = productsModel.name
                            cartModel.image = productsModel.image
                            cartModel.price = productsModel.price
                            cartModel.quantity = 1
                            cartModel.totalPrice = productsModel.price!!.toFloat().toInt()

                            userCart.child(productsModel.key!!)
                                .setValue(cartModel)
                                .addOnSuccessListener {
                                    // Notificar al EventBus sobre la actualización del carrito
                                    EventBus.getDefault().postSticky(UpdateCartEvent())
                                    cartListener.onLoadCartFailed("Agregaste este producto al carro correctamente")
                                }
                                .addOnFailureListener { e ->
                                    cartListener.onLoadCartFailed(e.message)
                                }
                        }

                        // Reducir el stock en la base de datos
                        val updatedStock = stock - productsModel.quantity
                        FirebaseDatabase.getInstance()
                            .getReference("Ropa")
                            .child(productsModel.key!!)
                            .child("stock")
                            .setValue(updatedStock)
                    } else {
                        // Si no hay suficiente stock, muestra un mensaje al usuario
                        cartListener.onLoadCartFailed("No hay mas stock. Lamentamos las molestias")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Manejar el error si es necesario
                    cartListener.onLoadCartFailed(error.message)
                }
            })
    }




    fun updateList(newList: List<ProductsModel>) {
        list = newList
        notifyDataSetChanged()
    }

}