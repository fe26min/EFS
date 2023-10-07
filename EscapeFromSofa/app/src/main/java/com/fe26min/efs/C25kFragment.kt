package com.fe26min.efs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fe26min.efs.databinding.FragmentC25kBinding

class C25kFragment : Fragment() {

    private lateinit var binding : FragmentC25kBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentC25kBinding.inflate(inflater)
        return binding.root
    }

}