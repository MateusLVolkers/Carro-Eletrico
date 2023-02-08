package com.example.carroeletriconovo.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.carroeletriconovo.ui.fragment.CarFragment
import com.example.carroeletriconovo.ui.fragment.FavoritesFragment
import com.example.carroeletriconovo.ui.fragment.FragmentRefresh

class TabAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private val fragmentList: List<FragmentRefresh> = listOf(CarFragment(), FavoritesFragment())

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position] as Fragment
    }

    fun refreshFragment(position: Int){
        fragmentList[position].refresh()
    }
}