package com.mustadevs.goriapp.domain.model

import java.io.Serializable

class ProductsModel: Serializable {
    var key:String?=null
    var name:String?=null
    var image:String?=null
    var price: Int? = null
    var stock: Int? = null
    var quantity: Int = 0 // Campo para la cantidad seleccionada
}
//enum class ProductsModel {
  //  gemini,
    //BuzoVerde,
    //BuzoNaranja,
    //RemeraBlanca,
    //RemeraNegra,
    //RemeraJero,
    //RemeraDoblada,
    //BuzoVerdeOscuro
//}