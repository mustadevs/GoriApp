package com.mustadevs.goriapp.ui.featured.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.mustadevs.goriapp.R
import com.mustadevs.goriapp.ui.home.adapter.HorizontalRecyclerViewAdapter

class FeaturedRecyclerViewAdapter: RecyclerView.Adapter<FeaturedRecyclerViewAdapter.MyViewHolder>() {
    private val imageList = listOf(
        R.drawable.ic_buzo_amarillo,
        R.drawable.ic_reme_jero,
        R.drawable.ic_remera_negra,
        R.drawable.ic_ropa_doblada,
        R.drawable.ic_remera_blanca,
        R.drawable.ic_buzo_naranja,
        R.drawable.ic_buzo_verde,
        R.drawable.ic_buzo_verde2
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.featured_recycler_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val imageResId = imageList[position]
        holder.imageView.setImageResource(imageResId)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageViewFeatured)
    }
}