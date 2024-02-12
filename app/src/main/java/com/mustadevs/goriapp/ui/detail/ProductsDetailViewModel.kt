package com.mustadevs.goriapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustadevs.goriapp.domain.usecase.GetBuyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProductsDetailViewModel @Inject constructor(private val getBuyUseCase: GetBuyUseCase):ViewModel(){

    private var _state = MutableStateFlow<BuyDetailState>(BuyDetailState.Loading)
    val state: StateFlow<BuyDetailState> = _state

    fun getBuy(sign:String) {
        viewModelScope.launch {
            _state.value = BuyDetailState.Loading
            val result = withContext(Dispatchers.IO) { getBuyUseCase(sign) }
            if(result!=null){
                _state.value = BuyDetailState.Success(result.sign) //aca ver!!!
            }else{
                _state.value = BuyDetailState.Error("Ha ocurrido un error, intentelo de nuevo mas tarde")
            }
        }
    }
}