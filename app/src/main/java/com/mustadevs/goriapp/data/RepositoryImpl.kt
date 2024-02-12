package com.mustadevs.goriapp.data

import android.util.Log
import com.mustadevs.goriapp.data.network.BuyApiService
import com.mustadevs.goriapp.domain.Repository
import com.mustadevs.goriapp.domain.model.BuyModel
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val apiService:BuyApiService) : Repository {
    override suspend fun getBuy(sign: String): BuyModel? {
        runCatching {
            apiService.getBuyAmount(sign)}
            .onSuccess { return it.toDomain() }
            .onFailure { Log.i("gori", "Ha ocurrido un error ${it.message}") }

        return null
    }
}