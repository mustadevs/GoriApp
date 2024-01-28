package com.mustadevs.goriapp.ui.products.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mustadevs.goriapp.databinding.ItemProductsBinding
import com.mustadevs.goriapp.domain.model.ProductsInfo


class ProductsViewHolder(view: View):RecyclerView.ViewHolder(view) {

    private val binding = ItemProductsBinding.bind(view)

    fun render(productsInfo: ProductsInfo, onItemSelected: (ProductsInfo) -> Unit) {
        val context = binding.titleTv.context
        binding.ivProducts.setImageResource(productsInfo.img)
        binding.titleTv.text = context.getString(productsInfo.name)

        binding.parent.setOnClickListener { onItemSelected(productsInfo) }
    }
}