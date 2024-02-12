package com.mustadevs.goriapp.ui.products

import androidx.lifecycle.ViewModel
import com.mustadevs.goriapp.data.providers.ProductsProvider
import com.mustadevs.goriapp.domain.model.ProductsInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import com.mustadevs.goriapp.domain.model.ProductsInfo.*

@HiltViewModel
class ProductsViewModel @Inject constructor() :
    ViewModel(){

    private var _products = MutableStateFlow<List<ProductsInfo>>(emptyList())
    val products:StateFlow<List<ProductsInfo>> = _products

    init {
        _products.value = listOf(
            taurus, BuzoVerde, BuzoNaranja, RemeraBlanca, RemeraNegra, RemeraJero, RemeraDoblada, BuzoVerdeOscuro
        )
    }
}