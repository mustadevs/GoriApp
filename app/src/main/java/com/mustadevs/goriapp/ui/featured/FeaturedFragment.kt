package com.mustadevs.goriapp.ui.featured
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mustadevs.goriapp.R
import com.mustadevs.goriapp.databinding.FragmentFeaturedBinding
import com.mustadevs.goriapp.ui.featured.adapter.FeaturedRecyclerViewAdapter


class FeaturedFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FeaturedRecyclerViewAdapter
    private var binding: FragmentFeaturedBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeaturedBinding.inflate(inflater, container, false)
        val view = binding?.root

        // Inicializa y configura el RecyclerView
        recyclerView = view?.findViewById(R.id.recyclerViewFeatured) ?: RecyclerView(requireContext())
        adapter = FeaturedRecyclerViewAdapter()

        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
