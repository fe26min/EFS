package com.fe26min.efs

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
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
import java.util.stream.Collectors

class C25kFragment : Fragment(), View.OnClickListener {

    private lateinit var binding : FragmentC25kBinding

//    private lateinit var weekAdapter: WeeksAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val sharedPref = activity?.getSharedPreferences(C25K_HISTORY, Context.MODE_PRIVATE)
        val checkW1D1 = sharedPref?.getString("11", "NOT FOUND")

        sharedPref?.contains("11")
        Log.e("check w1b1", "Key : 11, Value : $checkW1D1")


        binding = FragmentC25kBinding.inflate(inflater)

        // 버튼 등록 -> 커스텀 버튼 클래스로 코드 줄일 수 있는지 확인

        if(sharedPref?.contains("11") == true)
            binding.w1d1.setBackgroundColor(Color.LTGRAY)
        if(sharedPref?.contains("12") == true)
            binding.w1d2.setBackgroundColor(Color.LTGRAY)
        if(sharedPref?.contains("13") == true)
            binding.w1d3.setBackgroundColor(Color.LTGRAY)

        if(sharedPref?.contains("21") == true)
            binding.w2d1.setBackgroundColor(Color.LTGRAY)
        if(sharedPref?.contains("22") == true)
            binding.w2d2.setBackgroundColor(Color.LTGRAY)
        if(sharedPref?.contains("23") == true)
            binding.w2d3.setBackgroundColor(Color.LTGRAY)

        if(sharedPref?.contains("31") == true)
            binding.w3d1.setBackgroundColor(Color.LTGRAY)
        if(sharedPref?.contains("32") == true)
            binding.w3d2.setBackgroundColor(Color.LTGRAY)
        if(sharedPref?.contains("33") == true)
            binding.w3d3.setBackgroundColor(Color.LTGRAY)

        if(sharedPref?.contains("41") == true)
            binding.w4d1.setBackgroundColor(Color.LTGRAY)
        if(sharedPref?.contains("42") == true)
            binding.w4d2.setBackgroundColor(Color.LTGRAY)
        if(sharedPref?.contains("43") == true)
            binding.w4d3.setBackgroundColor(Color.LTGRAY)

        if(sharedPref?.contains("51") == true)
            binding.w5d1.setBackgroundColor(Color.LTGRAY)
        if(sharedPref?.contains("52") == true)
            binding.w5d2.setBackgroundColor(Color.LTGRAY)
        if(sharedPref?.contains("53") == true)
            binding.w5d3.setBackgroundColor(Color.LTGRAY)

        if(sharedPref?.contains("61") == true)
            binding.w6d1.setBackgroundColor(Color.LTGRAY)
        if(sharedPref?.contains("62") == true)
            binding.w6d2.setBackgroundColor(Color.LTGRAY)
        if(sharedPref?.contains("63") == true)
            binding.w6d3.setBackgroundColor(Color.LTGRAY)

        if(sharedPref?.contains("71") == true)
            binding.w7d1.setBackgroundColor(Color.LTGRAY)
        if(sharedPref?.contains("72") == true)
            binding.w7d2.setBackgroundColor(Color.LTGRAY)
        if(sharedPref?.contains("73") == true)
            binding.w7d3.setBackgroundColor(Color.LTGRAY)

        if(sharedPref?.contains("81") == true)
            binding.w8d1.setBackgroundColor(Color.LTGRAY)
        if(sharedPref?.contains("82") == true)
            binding.w8d2.setBackgroundColor(Color.LTGRAY)
        if(sharedPref?.contains("83") == true)
            binding.w8d3.setBackgroundColor(Color.LTGRAY)

        binding.w1d1.setOnClickListener(this)
        binding.w1d2.setOnClickListener(this)
        binding.w1d3.setOnClickListener(this)

        binding.w2d1.setOnClickListener(this)
        binding.w2d2.setOnClickListener(this)
        binding.w2d3.setOnClickListener(this)

        binding.w3d1.setOnClickListener(this)
        binding.w3d2.setOnClickListener(this)
        binding.w3d3.setOnClickListener(this)

        binding.w4d1.setOnClickListener(this)
        binding.w4d2.setOnClickListener(this)
        binding.w4d3.setOnClickListener(this)

        binding.w5d1.setOnClickListener(this)
        binding.w5d2.setOnClickListener(this)
        binding.w5d3.setOnClickListener(this)

        binding.w6d1.setOnClickListener(this)
        binding.w6d2.setOnClickListener(this)
        binding.w6d3.setOnClickListener(this)

        binding.w7d1.setOnClickListener(this)
        binding.w7d2.setOnClickListener(this)
        binding.w7d3.setOnClickListener(this)

        binding.w8d1.setOnClickListener(this)
        binding.w8d2.setOnClickListener(this)
        binding.w8d3.setOnClickListener(this)


//        binding.w1d1.setOnClickListener {it ->
//            Log.e("check", it.toString())
//            Log.e("check", binding.w1d1.text.toString())
//            Log.e("check", "week : ${binding.w1d1.text.toString()[1]}, day : ${binding.w1d1.text.toString()[3]}")
//
//            val intent = Intent(activity, C25KActivity::class.java)
//            intent.putExtra("week", 1)
//            intent.putExtra("day", 1)
//            startActivity(intent)
//        }

        return binding.root
    }

    override fun onClick(v: View) {
//        Log.e("btn id", v?.id.toString())
//        Log.e("btn text", (v as TextView).text.toString() )

        val btnText = (v as TextView).text.toString()

        val stream = btnText.chars()
        val intStr = stream.filter { ch: Int -> ch in 48..57 }
            .mapToObj {ch: Int -> ch.toChar()}
            .map { obj: Char -> obj.toString() }
            .collect(Collectors.joining())

        val intent = Intent(activity, C25KActivity::class.java)
//        var week = btnText[1] - '0'
//        var day = btnText[3] - '0'


        val week = intStr[0] - '0'
        val day = intStr[1] - '0'

//        Log.e("week day", "$week $day")

//        when(v?.id){
//            binding.w1d1.id -> {
//                week = 1
//                day = 1
//            }
//            binding.w1d2.id -> {
//                week = 1
//                day = 2
//            }
//            binding.w1d3.id -> {
//                week = 1
//                day = 3
//            }
//        }
        intent.putExtra("week", week)
        intent.putExtra("day", day)
        startActivity(intent)
    }


}