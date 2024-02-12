package com.mustadevs.goriapp.data.network

import com.mustadevs.goriapp.data.network.response.BuyResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface BuyApiService {


    //Debo obtener el link de pago, pasandole un body de payment services
    @GET("/{sign}") //pasa body buy
    suspend fun getBuyAmount(@Path("sign") sign:String): BuyResponse
    
}