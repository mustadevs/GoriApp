package com.mustadevs.goriapp.domain

import com.mustadevs.goriapp.domain.model.BuyModel

interface Repository {
    suspend fun getBuy(sign:String): BuyModel?
}