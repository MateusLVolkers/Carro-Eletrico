package com.example.carroeletriconovo.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.carroeletriconovo.ui.CarFragment
import com.example.carroeletriconovo.ui.FavoritesFragment

class TabAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    //só tem 2 tabs, logo, retorna 2
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0-> CarFragment()
            1-> FavoritesFragment()
            else -> CarFragment()
        }
    }


}