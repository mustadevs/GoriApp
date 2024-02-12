package com.mustadevs.goriapp.domain.usecase

import com.mustadevs.goriapp.domain.Repository
import javax.inject.Inject

class GetBuyUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(sign:String) = repository.getBuy(sign)

}