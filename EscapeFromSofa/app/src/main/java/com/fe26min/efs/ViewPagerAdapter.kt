package com.fe26min.efs

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fe26min.efs.C25K.C25kFragment

class ViewPagerAdapter(private val mainActivity: MainActivity) : FragmentStateAdapter(mainActivity) {

    override fun getItemCount(): Int {
        return 1
    //        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
//            0 -> {
//                return C25kFragment()
//            }
//            1 -> {
//                return Zone2Fragment()
//            }
//            else -> {
//                return RecordFragment()
//            }
            else -> {
                return C25kFragment()
            }
        }
    }


}