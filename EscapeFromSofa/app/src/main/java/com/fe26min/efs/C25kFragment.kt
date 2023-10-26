package com.fe26min.efs

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.fe26min.efs.databinding.FragmentC25kBinding
import org.w3c.dom.Text

class C25kFragment : Fragment() {

    private lateinit var binding : FragmentC25kBinding
//    private lateinit var weekAdapter: WeeksAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentC25kBinding.inflate(inflater)

        binding.w1d1.setOnClickListener {it ->
            Log.e("check", it.toString())
            Log.e("check", binding.w1d1.text.toString())
            Log.e("check", "week : ${binding.w1d1.text.toString()[1]}, day : ${binding.w1d1.text.toString()[3]}")

            val intent = Intent(activity, C25KActivity::class.java)
            intent.putExtra("week", 1)
            intent.putExtra("day", 1)
            startActivity(intent)
        }

        return binding.root
    }


}