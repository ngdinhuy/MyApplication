package com.example.myapplication.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.myapplication.R
import com.example.myapplication.data.Dish
import com.example.myapplication.databinding.FragmentMainBinding
import com.example.myapplication.service.MediaService
import com.example.myapplication.ui.review.ReviewFragment
import com.example.myapplication.ui.select_disk.SelectDishFragment
import com.example.myapplication.ui.select_meal.SelectMealFragment
import com.example.myapplication.ui.select_restaurant.SelectRestaurantFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    val viewmodel by viewModels<MainViewmodel>()
    lateinit var databinding: FragmentMainBinding
    val listFragment = mutableListOf<Fragment>()
    val adapter by lazy {
        PagerAdapter(listFragment, childFragmentManager, lifecycle)
    }

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
        convertDataJsonToDish()
        setUpViewPager()
        setUpEvent()
    }

    private fun setUpViewPager() {
        listFragment.add(SelectMealFragment())
        listFragment.add(SelectRestaurantFragment())
        listFragment.add(SelectDishFragment())
        listFragment.add(ReviewFragment())

        viewmodel.maxTab = listFragment.size
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
                viewmodel.updateCurrentTab(tab?.position?:0)

                if(tab?.position == 0) {
//                    databinding.tvPrevious.visibility = View.GONE
                } else if (tab?.position == listFragment.size - 1) {
                    databinding.tvNext.text = "Submit"
                } else {
                    databinding.tvPrevious.visibility = View.VISIBLE
                    databinding.tvNext.text = "Next"
                }
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

    private fun setUpEvent() {
        viewmodel.currentTab.observe(viewLifecycleOwner, Observer {
            databinding.viewpage2.setCurrentItem(it, true)
        })

        viewmodel.currentSong.observe(viewLifecycleOwner, Observer{
            Intent(requireContext(), MediaService::class.java).apply {
                val bundle = Bundle()
                bundle.putSerializable("NEW_SONG", viewmodel.listSong[it])
               putExtras(bundle)
                activity?.startService(this)
            }
        })
    }

    private fun convertDataJsonToDish() {
        try {
            activity?.let {
                val inputStream = it.assets.open("dishes.json")
                val size: Int = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()
                val myJson = String(buffer, charset("UTF-8"))
                viewmodel.listDisk =
                    Gson().fromJson(myJson, object : TypeToken<List<Dish>>() {}.type)
                Log.e("SIZEEEE", viewmodel.listDisk.size.toString())
            }
        } catch (e: Exception) {
            Log.e("Error", e.message.toString())
        }
    }

}