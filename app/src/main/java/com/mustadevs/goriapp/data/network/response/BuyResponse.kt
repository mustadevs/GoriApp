package com.mustadevs.goriapp.data.network.response

import com.google.gson.annotations.SerializedName
import com.mustadevs.goriapp.domain.model.BuyModel

data class BuyResponse (
    @SerializedName("sign") val sign: String,
    @SerializedName("horoscope") val horoscope: String,
){
    fun toDomain():BuyModel{
        return BuyModel(
            horoscope = horoscope,
            sign = sign
        )
    }
}