package com.mustadevs.goriapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.navArgs
import com.mustadevs.goriapp.R
import com.mustadevs.goriapp.databinding.ActivityProductsDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductsDetailBinding
    private val productsDetailViewModel:ProductsDetailViewModel by viewModels()

    private val args:ProductsDetailActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        args.type
    }
}