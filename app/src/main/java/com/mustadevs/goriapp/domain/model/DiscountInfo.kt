package com.mustadevs.goriapp.domain.model

import com.mustadevs.goriapp.R

sealed class DiscountInfo(val img:Int, val name:Int) {
    data object DiscountImageQR:DiscountInfo(R.drawable.ic_my_cupon_qr, R.string.DiscountImageQR)
}