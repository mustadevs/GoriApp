package com.mustadevs.goriapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mustadevs.goriapp.R
import com.mustadevs.goriapp.databinding.FragmentHomeBinding
import com.mustadevs.goriapp.ui.detail.DiscountDetailActivity
import com.mustadevs.goriapp.ui.home.adapter.HorizontalRecyclerViewAdapter

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HorizontalRecyclerViewAdapter

    private var binding: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding?.root

        // Agrega un OnClickListener al CardView de Descuentos
        binding?.viewDesc?.setOnClickListener {
            // Cuando se hace clic en Descuentos, inicia DiscountDetailActivity
            val intent = Intent(activity, DiscountDetailActivity::class.java)
            startActivity(intent)

        }
        // Inicializa y configura el RecyclerView
        recyclerView = view?.findViewById(R.id.recyclerViewHorizontal) ?: RecyclerView(requireContext())
        adapter = HorizontalRecyclerViewAdapter()

        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter

        return view
    }




    override fun onDestroyView() {
        super.onDestroyView()
        binding = null // Libera la referencia para evitar posibles fugas de memoria
    }


}