package com.mustadevs.goriapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mustadevs.goriapp.R

class HorizontalRecyclerViewAdapter: RecyclerView.Adapter<HorizontalRecyclerViewAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 25
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }
}