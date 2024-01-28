package com.mustadevs.goriapp.data.providers

import com.mustadevs.goriapp.domain.model.ProductsInfo
import com.mustadevs.goriapp.domain.model.ProductsInfo.*
import javax.inject.Inject

class ProductsProvider @Inject constructor() {
    fun getProducts(): List<ProductsInfo> {
        return listOf(
            BuzoAmarillo,
            BuzoAmarillo,
            BuzoVerde,
            BuzoNaranja,
            RemeraBlanca,
            RemeraNegra,
            RemeraJero,
            RemeraDoblada,
            BuzoVerdeOscuro
        )
    }
}