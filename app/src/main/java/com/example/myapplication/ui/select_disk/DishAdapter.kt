package com.example.myapplication.ui.select_disk

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.Dish
import com.example.myapplication.databinding.ItemDishBinding

class DishAdapter(val context: Context) :
    ListAdapter<Pair<String, Int>, DishAdapter.ViewHolder>(DiffUtilCallback()) {

    var onClickSelectDish: OnClickSelectDish? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val databinding = ItemDishBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(databinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }


    inner class ViewHolder(val itemDishBinding: ItemDishBinding) :
        RecyclerView.ViewHolder(itemDishBinding.root) {
        fun bind(pair: Pair<String, Int>, pos: Int) {
            itemDishBinding.tvDish.text = pair.first
            itemDishBinding.tvNumber.text = pair.second.toString()

            itemDishBinding.flSelectDish.setOnClickListener {
                onClickSelectDish?.clickSelectDish(pos)
            }

            itemDishBinding.ivUp.setOnClickListener {
                onClickSelectDish?.clickUpSize(pos)
            }

            itemDishBinding.ivDown.setOnClickListener {
                onClickSelectDish?.clickDownSize(pos)
            }
        }
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

interface OnClickSelectDish {
    fun clickSelectDish(pos: Int)

    fun clickUpSize(pos: Int)

    fun clickDownSize(pos: Int)
}

