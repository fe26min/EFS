package com.fe26min.efs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fe26min.efs.databinding.FragmentC25kBinding
import com.fe26min.efs.databinding.FragmentRecordBinding

class RecordFragment : Fragment() {
    private lateinit var binding : FragmentRecordBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecordBinding.inflate(inflater)
        return binding.root
    }

}