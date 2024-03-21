package com.example.myapplication.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMainBinding
import com.example.myapplication.ui.review.ReviewFragment
import com.example.myapplication.ui.select_disk.SelectDishFragment
import com.example.myapplication.ui.select_meal.SelectMealFragment
import com.example.myapplication.ui.select_restaurant.SelectRestaurantFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    val viewmodel by viewModels<MainViewmodel>()
    lateinit var databinding: FragmentMainBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databinding = FragmentMainBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = this@MainFragment.viewmodel
        }
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewPager()
    }

    private fun setUpViewPager() {
        val listFragment = mutableListOf<Fragment>()
        listFragment.add(SelectMealFragment())
        listFragment.add(SelectRestaurantFragment())
        listFragment.add(SelectDishFragment())
        listFragment.add(ReviewFragment())
        val adapter = PagerAdapter(listFragment, childFragmentManager, lifecycle)

        databinding.viewpage2.adapter = adapter
        TabLayoutMediator(databinding.tablayout, databinding.viewpage2) { tab, pos ->
            tab.setCustomView(R.layout.custom_tablayout)
            val tvCounter = tab.customView?.findViewById<TextView>(R.id.tvCounter)
            val tvTitle = tab.customView?.findViewById<TextView>(R.id.tvTitle)
            tvCounter?.text = (pos + 1).toString()
            tvTitle?.text = if (pos != listFragment.size) "Step ${pos + 1}" else "Review"
        }.attach()

        databinding.tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val tvCounter = tab?.customView?.findViewById<TextView>(R.id.tvCounter)
                tvCounter?.isSelected = true
                tvCounter?.setTextColor(resources.getColor(R.color.white))
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val tvCounter = tab?.customView?.findViewById<TextView>(R.id.tvCounter)
                tvCounter?.isSelected = false
                tvCounter?.setTextColor(resources.getColor(R.color.grey))
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }
}