package com.mustadevs.goriapp.ui.detail

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.mustadevs.goriapp.data.listener.CartLoadListener
import com.mustadevs.goriapp.data.network.ApiService
import com.mustadevs.goriapp.data.network.response.ApiResponse
import com.mustadevs.goriapp.databinding.ActivityCartBinding
import com.mustadevs.goriapp.domain.model.CartModel
import com.mustadevs.goriapp.ui.products.adapter.MyCartAdapter
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CartActivity : AppCompatActivity(), CartLoadListener {
    private lateinit var binding: ActivityCartBinding
    private var cartLoadListener: CartLoadListener? = null
    private lateinit var mercadoPagoBaseUrl: String
    private lateinit var initPoint: String // Declarar aquí como variable miembro

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        loadCartFromFirebase()
    }

    private fun loadCartFromFirebase() {
        val cartModels: MutableList<CartModel> = ArrayList()
        FirebaseDatabase.getInstance()
            .getReference("Cart")
            .child("UNIQUE_USER_ID")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (cartSnapshot in snapshot.children) {
                        val cartModel = cartSnapshot.getValue(CartModel::class.java)
                        cartModel!!.key = cartSnapshot.key
                        cartModels.add(cartModel)
                    }
                    cartLoadListener?.onLoadCartSuccess(cartModels)
                    Log.d("ProductsFragment", "countCartFromFirebase: Success")

                    // Llamada a tu API para obtener la información necesaria
                    getMercadoPagoUrl()
                }

                override fun onCancelled(error: DatabaseError) {
                    cartLoadListener?.onLoadCartFailed(error.message)
                    Log.e("ProductsFragment", "countCartFromFirebase: Failed", error.toException())
                }
            })
    }

    private fun getMercadoPagoUrl() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.mercadopago.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        // Construye la cadena JSON directamente
        val jsonBody = """
        {
            "payer_email": "TESTUSER2051773604",
            "items": [
                {
                    "quantity": 2,
                    "unit_price": 15000
                }
            ],
            "back_urls": {
                "failure": "/failure",
                "pending": "/pending",
                "success": "/success"
            }
        }
    """.trimIndent()

        val requestBody = jsonBody.toRequestBody("application/json".toMediaType())

        val accessToken = "APP_USR-blabla"

        val call = apiService.getMercadoPagoUrl("Bearer $accessToken", requestBody)
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()

                    if (apiResponse != null) {
                        mercadoPagoBaseUrl = apiResponse.mercadopagoUrl ?: ""
                        initPoint = apiResponse.initPoint ?: ""

                        Log.d("MercadoPago", "Respuesta de la API: ${Gson().toJson(apiResponse)}")
                        Log.d("MercadoPago", "URL de MercadoPago obtenida: $mercadoPagoBaseUrl, init_point: $initPoint")

                        if (initPoint.isNotEmpty()) {
                            updateButtonClickListener()
                        } else {
                            showErrorMessage("initPoint en la respuesta de la API es nulo o vacío.")
                        }
                    } else {
                        showErrorMessage("Error en la respuesta de la API: Cuerpo de respuesta nulo.")
                    }
                } else {
                    val errorMessage = "Error en la respuesta de la API: ${response.code()} ${response.message()}"
                    Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                val errorMessage = "Error al realizar la llamada a la API: ${t.message}"
                t.printStackTrace()
                Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).show()
            }
        })
    }




    private fun updateButtonClickListener() {
        binding.btnPay.setOnClickListener {
            if (initPoint.isNotEmpty()) {
                launchMercadoPagoUrl()
            } else {
                showErrorMessage("Error al obtener la URL de MercadoPago")
            }
        }
    }

    private fun launchMercadoPagoUrl() {
        val intent = CustomTabsIntent.Builder().build()
        intent.launchUrl(this@CartActivity, Uri.parse(initPoint))
    }

    private fun showErrorMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun init() {
        cartLoadListener = this
        val layoutManager = LinearLayoutManager(this)

        binding.recyclerCart.layoutManager = layoutManager
        binding.recyclerCart.addItemDecoration(
            DividerItemDecoration(this, layoutManager.orientation)
        )

        binding.btnBack.setOnClickListener { finish() }

        // Verifica si mercadoPagoBaseUrl está inicializado antes de usarlo
        if (::mercadoPagoBaseUrl.isInitialized) {
            Snackbar.make(binding.root, "MercadoPago URL: $mercadoPagoBaseUrl", Snackbar.LENGTH_LONG).show()
        } else {
            Snackbar.make(binding.root, "MercadoPago URL no disponible", Snackbar.LENGTH_LONG).show()
        }
    }


    override fun onLoadCartSuccess(cartModelList: MutableList<CartModel>) {
        var sum = 0.0
        for (cartModel in cartModelList) {
            sum += cartModel.totalPrice
        }
        binding.txtTotal.text = StringBuilder("$").append(sum)
        val adapter = MyCartAdapter(this, cartModelList.toMutableList())
        binding.recyclerCart.adapter = adapter

    }

    override fun onLoadCartFailed(message: String?) {
        Snackbar.make(binding.productsLayout, message!!, Snackbar.LENGTH_LONG).show()
    }
}
