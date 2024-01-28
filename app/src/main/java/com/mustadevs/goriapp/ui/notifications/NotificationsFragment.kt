package com.mustadevs.goriapp.ui.notifications

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mustadevs.goriapp.R

class NotificationsFragment : Fragment(R.layout.fragment_notifications) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }
    fun irAActivityDestino() {
        findNavController().navigate(R.id.action_notificationsFragment_to_notificationsDetailActivity)
    }


}