package com.example.myapplication.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(
    val listFragment: MutableList<Fragment>,
    val fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = listFragment.size


    override fun createFragment(position: Int): Fragment = listFragment[position]

    fun getFragmentByPosition(pos : Int) : Fragment?{
        return fragmentManager.findFragmentByTag("f$pos")
    }
}

class PagerAdapter1(
    val listFragment: List<Fragment>,
    fm: FragmentManager
) : FragmentStatePagerAdapter(fm){
    override fun getCount(): Int {
        return listFragment.size
    }

    override fun getItem(position: Int): Fragment {
        return listFragment[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return "Tab $position"
    }
}