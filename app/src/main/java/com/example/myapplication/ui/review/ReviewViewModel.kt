package com.example.myapplication.ui.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.Dish
import com.example.myapplication.ui.main.MainViewmodel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(): ViewModel() {
    var mainViewmodel : MainViewmodel? = null
    var numberCustomer = MutableLiveData<Int>(0)
    var meal = MutableLiveData<String>("")
    var restaurant = MutableLiveData<String>("")
    var listDish = MutableLiveData<List<String>>()
    fun setData(){
        mainViewmodel?.let { it ->
            numberCustomer.value = it.numberCustomer
            meal.value = it.meal
            restaurant.value = it.restaurant
            val list = mutableListOf<String>()
            it.listDish.forEach {dish ->
                if (dish.second != 0 && dish.first != "---"){
                    list.add("${dish.first} - ${dish.second}")
                }
            }
            listDish.value = list
        }
    }
}