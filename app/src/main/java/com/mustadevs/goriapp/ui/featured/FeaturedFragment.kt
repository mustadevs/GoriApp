package com.mustadevs.goriapp.ui.featured

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mustadevs.goriapp.R
import com.mustadevs.goriapp.databinding.FragmentDiscountBinding
import com.mustadevs.goriapp.databinding.FragmentFeaturedBinding
import com.mustadevs.goriapp.databinding.FragmentProductsBinding

class FeaturedFragment : Fragment() {

    private var _binding: FragmentFeaturedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeaturedBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}