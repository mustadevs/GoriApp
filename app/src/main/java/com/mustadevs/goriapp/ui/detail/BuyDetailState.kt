package com.mustadevs.goriapp.ui.detail

sealed class BuyDetailState {
    data object Loading:BuyDetailState()
    data class Error(val error:String):BuyDetailState()
    data class Success(val sign:String):BuyDetailState()
}