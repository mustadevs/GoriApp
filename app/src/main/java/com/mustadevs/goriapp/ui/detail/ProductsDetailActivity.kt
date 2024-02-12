package com.mustadevs.goriapp.ui.detail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.navArgs
import com.mustadevs.goriapp.R
import com.mustadevs.goriapp.databinding.ActivityProductsDetailBinding
import com.mustadevs.goriapp.domain.model.ProductsModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

//@Suppress("DEPRECATION")
//@AndroidEntryPoint
//class ProductsDetailActivity : AppCompatActivity() {

  //  private lateinit var binding: ActivityProductsDetailBinding
    //private val productsDetailViewModel: ProductsDetailViewModel by viewModels()

    //private val args: ProductsDetailActivityArgs by navArgs()

    //override fun onCreate(savedInstanceState: Bundle?) {
      //  super.onCreate(savedInstanceState)
        //binding = ActivityProductsDetailBinding.inflate(layoutInflater)
        //setContentView(binding.root)
        //productsDetailViewModel.getBuy(args.type.name)
        //initUI()
        //boton para ir para atras
        //binding.ivBack.setOnClickListener { onBackPressed() }

        //Esta linea me trae el type (argumento que recibe en main_graph cuando navego al productsdetailactivity desde ProductsFragment
       // var productType = args.type


        //De acuerdo al producto seleccioando, usando la misma vista del activity_products_detail cambia la imagen
        //when (productType) {
         //   ProductsModel.gemini -> {
         //       binding.ivDetail.setImageResource(R.drawable.ic_buzo_amarillo)
        //        binding.tvTitle.setText(R.string.taurus)
//     }

//     ProductsModel.BuzoVerde -> {
//         binding.ivDetail.setImageResource(R.drawable.ic_buzo_verde)
//     }

//    ProductsModel.BuzoNaranja -> {
//        binding.ivDetail.setImageResource(R.drawable.ic_buzo_naranja)
//   }

//   ProductsModel.RemeraBlanca -> {
//        binding.ivDetail.setImageResource(R.drawable.ic_remera_blanca)
//    }

//     ProductsModel.RemeraNegra -> {
//         binding.ivDetail.setImageResource(R.drawable.ic_remera_blanca)
//    }

//  ProductsModel.RemeraJero -> {
//      binding.ivDetail.setImageResource(R.drawable.ic_reme_jero)
//  }

//   ProductsModel.RemeraDoblada -> {
//       binding.ivDetail.setImageResource(R.drawable.ic_ropa_doblada)
//      }

//      ProductsModel.BuzoVerdeOscuro -> {
    //          binding.ivDetail.setImageResource(R.drawable.ic_buzo_verde2)
//       }
//   }
// }

//    private fun initUI() {
//        initUIState()
//    }

//  private fun initUIState() {
//      lifecycleScope.launch {
    //          repeatOnLifecycle(Lifecycle.State.STARTED){
//            productsDetailViewModel.state.collect{
                        //                 when(it){
//                   BuyDetailState.Loading ->  loadingState()
//                   is BuyDetailState.Error ->  errorState()
//                   is BuyDetailState.Success -> successState(it)
//               }
//           }
//       }
//    }
//  }

//  private fun successState(state: BuyDetailState.Success) {
//     binding.progressBar.isVisible = false
//     binding.tvTitle.text = state.sign

            // Ir a seccion CONTACTO
//     binding?.btnMercadoPago?.setOnClickListener {
//         // Cuando se hace clic en Contacto, inicia el link de whatsapp
//         openUrl("https://www.mercadopago.com.ar/checkout/v1/payment/redirect/b05492ec-4355-42b1-a751-ac4cbb32fb84/payment-option-form/?preference-id=378603507-3c8ffe3c-7153-44c9-b2d5-64c0a17f89fd&sniffing-rollout=sniffing-api&router-request-id=1b2219c5-a3d9-4e66-8450-cc83f0669e76&p=682fb5a483acccda8e84a548094720b5#/")
//     }
//  }

//private fun openUrl(link: String) {
//  /*Funcion que abre link de whatsapp*/
//      val uri = Uri.parse(link)
//      val inte = Intent(Intent.ACTION_VIEW, uri)
//      startActivity(inte)
// }

// private fun errorState() {
//         binding.progressBar.isVisible = false
//    }

//    private fun loadingState() {
//        binding.progressBar.isVisible = true
//   }
//}