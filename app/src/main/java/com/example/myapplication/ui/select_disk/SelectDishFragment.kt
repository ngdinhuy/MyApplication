package com.example.myapplication.ui.select_disk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.component.ClickButtonEvent
import com.example.myapplication.component.DialogPicker
import com.example.myapplication.databinding.FragmentReviewBinding
import com.example.myapplication.databinding.FragmentSelectDishBinding
import com.example.myapplication.ui.main.MainViewmodel
import com.example.myapplication.ui.review.ReviewViewModel
import com.example.myapplication.utils.Define
import com.example.myapplication.utils.EventObserver
import com.example.myapplication.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectDishFragment : Fragment() {
    val viewmodel by viewModels<SelectDishViewModel>()
    lateinit var databinding: FragmentSelectDishBinding
    val parentViewmodel by viewModels<MainViewmodel>(ownerProducer = { requireParentFragment() })
    val adapter: DishAdapter by lazy {
        DishAdapter(requireContext())
    }
    var dialogPicker: DialogPicker? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databinding = FragmentSelectDishBinding.inflate(layoutInflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = this@SelectDishFragment.viewmodel.apply {
                mainViewmodel = parentViewmodel
            }
        }
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecycleview()

        viewmodel.listItem.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        })
    }

    override fun onResume() {
        super.onResume()
        databinding.root.requestLayout()
    }

    private fun setUpRecycleview() {
        adapter.onClickSelectDish = object : OnClickSelectDish {
            override fun clickSelectDish(pos: Int) {
                dialogPicker = DialogPicker().apply {
                    listCategory = Utils.getListDish(
                        parentViewmodel.listDisk,
                        parentViewmodel.meal,
                        parentViewmodel.restaurant
                    )
                    clickButtonEvent = object : ClickButtonEvent {
                        override fun clickOk(text: String) {
                            viewmodel.updateListDish(pos, text, null)
                        }

                    }
                }
                dialogPicker?.show(requireFragmentManager(), "")
            }

            override fun clickUpSize(pos: Int) {
                viewmodel.updateListDish(pos, null, 1)
            }

            override fun clickDownSize(pos: Int) {
                viewmodel.updateListDish(pos, null, -1)
            }
        }
        databinding.rvDish.apply {
            adapter = this@SelectDishFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}