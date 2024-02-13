package com.mustadevs.goriapp.data.listener

import android.view.View

interface RecyclerClickListener {
    fun onItemClickListener(view: View?, position:Int)
}