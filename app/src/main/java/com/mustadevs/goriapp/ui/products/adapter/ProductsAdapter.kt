package com.mustadevs.goriapp.ui.products.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mustadevs.goriapp.R
import com.mustadevs.goriapp.domain.model.ProductsInfo

class ProductsAdapter(private var productsList:List<ProductsInfo> = emptyList(),
    private val onItemSelected:(ProductsInfo) -> Unit):
    RecyclerView.Adapter<ProductsViewHolder>() {

    fun updateList(list: List<ProductsInfo>){
        productsList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_products, parent, false)
        )
    }

    override fun getItemCount() = productsList.size

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.render(productsList[position], onItemSelected)
        }

}