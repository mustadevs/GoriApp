package com.mustadevs.goriapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.navArgs
import com.mustadevs.goriapp.R
import com.mustadevs.goriapp.databinding.ActivityProductsDetailBinding
import com.mustadevs.goriapp.domain.model.ProductsModel
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DEPRECATION")
@AndroidEntryPoint
class ProductsDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductsDetailBinding
    private val productsDetailViewModel: ProductsDetailViewModel by viewModels()

    private val args: ProductsDetailActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //boton para ir para atras
        binding.ivBack.setOnClickListener { onBackPressed() }

        //Esta linea me trae el type (argumento que recibe en main_graph cuando navego al productsdetailactivity desde ProductsFragment
        var productType = args.type


        //De acuerdo al producto seleccioando, usando la misma vista del activity_products_detail cambia la imagen
        when (productType) {
            ProductsModel.BuzoAmarillo -> {
                binding.ivDetail.setImageResource(R.drawable.ic_buzo_amarillo)
                binding.tvTitle.setText(R.string.BuzoAmarillo)
            }

            ProductsModel.BuzoVerde -> {
                binding.ivDetail.setImageResource(R.drawable.ic_buzo_verde)
            }

            ProductsModel.BuzoNaranja -> {
                binding.ivDetail.setImageResource(R.drawable.ic_buzo_naranja)
            }

            ProductsModel.RemeraBlanca -> {
                binding.ivDetail.setImageResource(R.drawable.ic_remera_blanca)
            }

            ProductsModel.RemeraNegra -> {
                binding.ivDetail.setImageResource(R.drawable.ic_remera_blanca)
            }

            ProductsModel.RemeraJero -> {
                binding.ivDetail.setImageResource(R.drawable.ic_reme_jero)
            }

            ProductsModel.RemeraDoblada -> {
                binding.ivDetail.setImageResource(R.drawable.ic_ropa_doblada)
            }

            ProductsModel.BuzoVerdeOscuro -> {
                binding.ivDetail.setImageResource(R.drawable.ic_buzo_verde2)
            }
        }
    }


}