package com.example.myapplication.ui.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentReviewBinding
import com.example.myapplication.ui.FragmentLifecycle
import com.example.myapplication.ui.main.MainViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewFragment : Fragment(), FragmentLifecycle {
    val viewmodel by viewModels<ReviewViewModel> ()
    lateinit var databinding : FragmentReviewBinding
    val parentViewmodel by viewModels<MainViewmodel>(ownerProducer = { requireParentFragment() })
    val mAdapter : ReviewAdapter by lazy {
        ReviewAdapter(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databinding = FragmentReviewBinding.inflate(layoutInflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = this@ReviewFragment.viewmodel.apply {
                mainViewmodel = parentViewmodel
                setData()
            }
        }
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycleview()
        viewmodel.listDish.observe(viewLifecycleOwner, Observer {
            mAdapter.submitList(it)
            mAdapter.notifyDataSetChanged()
        })

    }

    override fun onResume() {
        super.onResume()
        viewmodel.setData()
        databinding.root.requestLayout()
    }

    fun setUpRecycleview(){
        databinding.rvDish.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onResumeFragment() {
//        databinding.rvDish.visibility = View.GONE
    }

    override fun onPauseFragment() {

    }
}