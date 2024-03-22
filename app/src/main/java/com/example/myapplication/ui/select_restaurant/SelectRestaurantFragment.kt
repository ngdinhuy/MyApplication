package com.example.myapplication.ui.select_restaurant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myapplication.component.ClickButtonEvent
import com.example.myapplication.component.DialogPicker
import com.example.myapplication.databinding.FragmentSelectRestaurentBinding
import com.example.myapplication.ui.main.MainViewmodel
import com.example.myapplication.utils.EventObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectRestaurantFragment : Fragment() {
    val viewmodel by viewModels<SelectRestaurantViewModel>()
    lateinit var databinding: FragmentSelectRestaurentBinding
    val parentViewmodel by viewModels<MainViewmodel>(ownerProducer = { requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databinding =
            FragmentSelectRestaurentBinding.inflate(layoutInflater, container, false).apply {
                lifecycleOwner = viewLifecycleOwner
                viewmodel = this@SelectRestaurantFragment.viewmodel.apply {
                    mainViewmodel = parentViewmodel
                }
            }
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewmodel.eventClickSelectrestaurant.observe(viewLifecycleOwner, EventObserver {
            val dialog = DialogPicker().apply {
                listCategory = it
                clickButtonEvent = object : ClickButtonEvent {
                    override fun clickOk(text: String) {
                        viewmodel.updateRestaurant(text)
                    }

                }
            }.show(requireFragmentManager(), "")
        })
    }
}