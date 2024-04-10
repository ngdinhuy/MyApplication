package com.example.myapplication.ui.select_meal

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.example.myapplication.component.ClickButtonEvent
import com.example.myapplication.component.DialogPicker
import com.example.myapplication.databinding.FragmentReviewBinding
import com.example.myapplication.databinding.FragmentSelectMealBinding
import com.example.myapplication.ui.FragmentLifecycle
import com.example.myapplication.ui.main.MainViewmodel
import com.example.myapplication.ui.review.ReviewViewModel
import com.example.myapplication.utils.Define
import com.example.myapplication.utils.EventObserver
import com.google.android.gms.common.api.internal.LifecycleFragment
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay

@AndroidEntryPoint
class SelectMealFragment : Fragment(), FragmentLifecycle {
    val viewmodel by viewModels<SelectMealViewModel> ()
    lateinit var databinding : FragmentSelectMealBinding
    val parentViewmodel by viewModels<MainViewmodel>(ownerProducer = { requireParentFragment()})

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databinding = FragmentSelectMealBinding.inflate(layoutInflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = this@SelectMealFragment.viewmodel.apply {
                mainViewModel = parentViewmodel
            }
        }
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel.eventClickSelectMeal.observe(viewLifecycleOwner, EventObserver{
            val dialog = DialogPicker().apply {
                listCategory = Define.listMeal
                clickButtonEvent = object : ClickButtonEvent{
                    override fun clickOk(text: String) {
                        viewmodel.updateMeal(text)
                    }

                }
            }.show(requireFragmentManager(), "")
        })
    }

    override fun onResume() {
        super.onResume()
        databinding.root.requestLayout()
    }

    override fun onResumeFragment() {
    }

    override fun onPauseFragment() {
    }
}