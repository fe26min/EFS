package com.fe26min.efs.Zone2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fe26min.efs.databinding.FragmentZone2Binding

class Zone2Fragment : Fragment() {

    private lateinit var binding : FragmentZone2Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentZone2Binding.inflate(inflater)
        return binding.root
    }

}