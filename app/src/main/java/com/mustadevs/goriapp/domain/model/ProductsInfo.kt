package com.mustadevs.goriapp.domain.model

import com.mustadevs.goriapp.R

sealed class ProductsInfo(val img:Int, val name:Int) {
    data object BuzoAmarillo:ProductsInfo(R.drawable.ic_buzo_amarillo, R.string.BuzoAmarillo)
    data object BuzoVerde:ProductsInfo(R.drawable.ic_buzo_verde, R.string.BuzoVerde)
    data object BuzoNaranja:ProductsInfo(R.drawable.ic_buzo_naranja, R.string.BuzoNaranja)
    data object RemeraBlanca:ProductsInfo(R.drawable.ic_remera_blanca, R.string.RemeraBlanca)
    data object RemeraNegra:ProductsInfo(R.drawable.ic_remera_negra, R.string.RemeraNegra)
    data object RemeraJero:ProductsInfo(R.drawable.ic_reme_jero, R.string.RemeraJero)
    data object RemeraDoblada:ProductsInfo(R.drawable.ic_ropa_doblada, R.string.RemeraDoblada)
    data object BuzoVerdeOscuro:ProductsInfo(R.drawable.ic_buzo_verde2, R.string.BuzoVerdeOscuro)
}