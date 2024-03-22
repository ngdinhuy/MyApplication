package com.example.myapplication.ui.select_disk

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemDishBinding

class DishAdapter(val context: Context) :
    ListAdapter<Pair<String, Int>, DishAdapter.ViewHolder>(DiffUtilCallback()) {


    class ViewHolder(val itemDishBinding: ItemDishBinding) :
        RecyclerView.ViewHolder(itemDishBinding.root) {
        fun bind(pair: Pair<String, Int>) {
            itemDishBinding.tvDish.text = pair.first
            itemDishBinding.tvNumber.text = pair.second.toString()

            itemDishBinding.flSelectDish.setOnClickListener {

            }

            itemDishBinding.ivUp.setOnClickListener {

            }

            itemDishBinding.ivDown.setOnClickListener {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val databinding = ItemDishBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(databinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class DiffUtilCallback : DiffUtil.ItemCallback<Pair<String, Int>>() {
    override fun areItemsTheSame(oldItem: Pair<String, Int>, newItem: Pair<String, Int>): Boolean {
        return oldItem.first == newItem.first
    }

    override fun areContentsTheSame(
        oldItem: Pair<String, Int>,
        newItem: Pair<String, Int>
    ): Boolean {
        return oldItem == newItem
    }
}

