package com.example.firebasepushnotifications

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.firebasepushnotifications.databinding.FragmentMainBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging


class MainFragment : Fragment(R.layout.fragment_main) {
    private var _binding : FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}