package com.mustadevs.goriapp.data.listener

import com.mustadevs.goriapp.domain.model.ProductsModel

interface ProductsLoadListener {

    fun onProductsLoadSuccess(productsModelList:List<ProductsModel>?)
    fun onProductsLoadFailed(message:String?)
}