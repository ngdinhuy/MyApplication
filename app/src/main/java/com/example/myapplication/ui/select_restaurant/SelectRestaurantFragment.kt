package com.example.myapplication.ui.select_restaurant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myapplication.databinding.FragmentSelectMealBinding
import com.example.myapplication.databinding.FragmentSelectRestaurentBinding
import com.example.myapplication.ui.select_meal.SelectMealViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectRestaurantFragment:Fragment() {
    val viewmodel by viewModels<SelectRestaurantViewModel> ()
    lateinit var databinding : FragmentSelectRestaurentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databinding = FragmentSelectRestaurentBinding.inflate(layoutInflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return databinding.root
    }
}