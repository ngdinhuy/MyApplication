package com.example.myapplication.ui.select_disk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myapplication.databinding.FragmentReviewBinding
import com.example.myapplication.databinding.FragmentSelectDishBinding
import com.example.myapplication.ui.review.ReviewViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectDishFragment:Fragment() {
    val viewmodel by viewModels<SelectDishViewModel> ()
    lateinit var databinding : FragmentSelectDishBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databinding = FragmentSelectDishBinding.inflate(layoutInflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return databinding.root
    }
}