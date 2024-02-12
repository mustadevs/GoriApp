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
import com.mustadevs.goriapp.R
import com.mustadevs.goriapp.domain.model.ProductsModel
import com.mustadevs.goriapp.ui.products.ProductsFragment
import java.lang.StringBuilder

class MyProductsAdapter(
    private val context: Context,
    private var list:List<ProductsModel>
): RecyclerView.Adapter<MyProductsAdapter.MyProductsViewHolder>(){

    class MyProductsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
         var imageView: ImageView
         var txtName:TextView
         var txtPrice:TextView

        init {
            imageView = itemView.findViewById(R.id.imageView) as ImageView;
            txtName = itemView.findViewById(R.id.txtName) as TextView;
            txtPrice = itemView.findViewById(R.id.txtPrice) as TextView;
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
    }

    fun updateList(newList: List<ProductsModel>) {
        list = newList
        notifyDataSetChanged()
    }

}