package com.mustadevs.goriapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.mustadevs.goriapp.R

class HorizontalRecyclerViewAdapter: RecyclerView.Adapter<HorizontalRecyclerViewAdapter.MyViewHolder>() {
    private val imageList = listOf(
        R.drawable.ic_banner1,
        R.drawable.ic_banner2,
        R.drawable.ic_banner3,
        R.drawable.ic_banner4
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
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
        val imageView: ImageView = itemView.findViewById(R.id.imageViewHorizontal)
    }
}