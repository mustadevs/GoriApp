package com.mustadevs.goriapp.ui.discount

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mustadevs.goriapp.R
import com.mustadevs.goriapp.databinding.FragmentDiscountBinding
import com.mustadevs.goriapp.databinding.FragmentProductsBinding

class DiscountFragment : Fragment() {

    private var _binding: FragmentDiscountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiscountBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

}