package com.mustadevs.goriapp.ui.home.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mustadevs.goriapp.databinding.ActivityDiscountDetailBinding
import com.mustadevs.goriapp.databinding.ItemProductsBinding
import com.mustadevs.goriapp.domain.model.DiscountInfo
import com.mustadevs.goriapp.domain.model.ProductsInfo

class HomeViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ActivityDiscountDetailBinding.bind(view)

    fun renderHome(discountInfo: DiscountInfo) {
        val context = binding.titleTv.context
        binding.ivDiscount.setImageResource(discountInfo.img)
        binding.titleTv.text = context.getString(discountInfo.name)

    }
}