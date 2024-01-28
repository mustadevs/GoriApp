package com.mustadevs.goriapp.ui.detail

sealed class ProductsDetailState {
    data object Loading:ProductsDetailState()
    data class Error(val error:String):ProductsDetailState()
    data class Success(val data:String):ProductsDetailState()
}