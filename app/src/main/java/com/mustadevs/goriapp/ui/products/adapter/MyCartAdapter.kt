package com.mustadevs.goriapp.ui.products.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.mustadevs.goriapp.R
import com.mustadevs.goriapp.domain.model.CartModel
import com.mustadevs.goriapp.ui.products.eventbus.UpdateCartEvent
import org.greenrobot.eventbus.EventBus

class MyCartAdapter(
    private val context: Context,
    private val cartModelList: MutableList<CartModel> = mutableListOf()
): RecyclerView.Adapter<MyCartAdapter.MyCartViewHolder>() {
    class MyCartViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        var btnMinus:ImageView? = null
        var btnPlus:ImageView?=null
        var btnDelete:ImageView?=null
        var imageView:ImageView?=null
        var txtName:TextView?=null
        var txtPrice:TextView?=null
        var txtQuantity:TextView?=null


    init {
        btnMinus = itemView.findViewById(R.id.btnMinus) as ImageView
        btnPlus = itemView.findViewById(R.id.btnPlus) as ImageView
        btnDelete = itemView.findViewById(R.id.btnDelete) as ImageView
        imageView = itemView.findViewById(R.id.imageView) as ImageView
        txtName = itemView.findViewById(R.id.txtName) as TextView
        txtPrice = itemView.findViewById(R.id.txtPrice) as TextView
        txtQuantity = itemView.findViewById(R.id.txtQuantity) as TextView
    }}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCartViewHolder {
        return MyCartViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.layout_cart_item,parent,false))
    }

    override fun getItemCount(): Int {
        return cartModelList.size
    }

    override fun onBindViewHolder(holder: MyCartViewHolder, position: Int) {
        Glide.with(context)
            .load(cartModelList[position].image)
            .into(holder.imageView!!)
        holder.txtName!!.text = StringBuilder().append(cartModelList[position].name)
        holder.txtPrice!!.text = StringBuilder("$").append(cartModelList[position].price)
        holder.txtQuantity!!.text = StringBuilder("").append(cartModelList[position].quantity)

        //Event
        holder.btnMinus!!.setOnClickListener{_ -> minusCartItem(holder,cartModelList[position])}
        holder.btnPlus!!.setOnClickListener{_ -> plusCartItem(holder,cartModelList[position])}
        holder.btnDelete!!.setOnClickListener{_ ->
            val dialog = AlertDialog.Builder(context)
                .setTitle("Borrar item")
                .setMessage("¿Estás seguro de borrar este item?")
                .setNegativeButton("CANCELAR") {dialog,_ -> dialog.dismiss() }
                .setPositiveButton("BORRAR") {dialog,_ ->
                    val uniqueUserId = "UNIQUE_USER_ID"
                    val cartModel = cartModelList[position]

                    // Eliminar el producto de la base de datos
                    FirebaseDatabase.getInstance()
                        .getReference("Cart")
                        .child(uniqueUserId)
                        .child(cartModel.key!!)
                        .removeValue()
                        .addOnSuccessListener {
                            // Actualizar la interfaz gráfica después de la eliminación
                            cartModelList.removeAt(position)
                            notifyItemRemoved(position)
                            notifyItemRangeChanged(position, cartModelList.size)

                            // Notificar evento de actualización
                            EventBus.getDefault().postSticky(UpdateCartEvent())


                        }
                }
                .create()
            dialog.show()
        }
    }


    private fun plusCartItem(holder: MyCartAdapter.MyCartViewHolder, cartModel: CartModel) {
        cartModel.quantity += 1
        cartModel.totalPrice = (cartModel.quantity * cartModel.price!!.toFloat()).toInt()
        holder.txtQuantity?.text= StringBuilder("").append(cartModel.quantity)
        updateFirebase(cartModel)
        notifyItemChanged(holder.adapterPosition)
    }

    private fun minusCartItem(holder: MyCartAdapter.MyCartViewHolder, cartModel: CartModel) {
        if(cartModel.quantity > 1)
        {
            cartModel.quantity -= 1
            cartModel.totalPrice = (cartModel.quantity * cartModel.price!!.toFloat()).toInt()
            holder.txtQuantity?.text = StringBuilder("").append(cartModel.quantity)
            updateFirebase(cartModel)
            notifyItemChanged(holder.adapterPosition)
        }
    }

    private fun updateFirebase(cartModel: CartModel) {
        FirebaseDatabase.getInstance()
            .getReference("Cart")
            .child("UNIQUE_USER_ID")
            .child(cartModel.key!!)
            .setValue(cartModel)
            .addOnSuccessListener { EventBus.getDefault().postSticky(UpdateCartEvent()) } }

    fun getCartModelList(): MutableList<CartModel> {
        return cartModelList
    }
    }