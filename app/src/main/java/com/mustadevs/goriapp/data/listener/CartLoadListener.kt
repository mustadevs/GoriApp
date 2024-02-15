package com.mustadevs.goriapp.data.listener

import com.mustadevs.goriapp.domain.model.CartModel

interface CartLoadListener {
    fun onLoadCartSuccess(cartModelList: MutableList<CartModel>)
    fun onLoadCartFailed(message:String?)
}