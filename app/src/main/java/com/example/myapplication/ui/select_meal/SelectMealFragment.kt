package com.example.myapplication.ui.select_meal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.example.myapplication.databinding.FragmentReviewBinding
import com.example.myapplication.databinding.FragmentSelectMealBinding
import com.example.myapplication.ui.review.ReviewViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@AndroidEntryPoint
class SelectMealFragment : Fragment() {
    val viewmodel by viewModels<SelectMealViewModel> ()
    lateinit var databinding : FragmentSelectMealBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databinding = FragmentSelectMealBinding.inflate(layoutInflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return databinding.root
    }
}