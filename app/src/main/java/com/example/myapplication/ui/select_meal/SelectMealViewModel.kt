package com.example.myapplication.ui.select_meal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.ui.main.MainViewmodel
import com.example.myapplication.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectMealViewModel @Inject constructor() : ViewModel() {
    var mainViewModel: MainViewmodel? = null
    var numberCustomer = MutableLiveData<Int>(0)
    var meal = MutableLiveData<String>("---")

    private val _eventClickSelectMeal = MutableLiveData<Event<Unit>>()
    val eventClickSelectMeal: LiveData<Event<Unit>> = _eventClickSelectMeal
    fun clickSelectMeal() {
        _eventClickSelectMeal.value = Event(Unit)
    }

    fun updateMeal(meal: String) {
        mainViewModel?.meal = meal
        this.meal.value = meal
    }

    fun clickUpButton(){
        numberCustomer.value = (numberCustomer.value?: 0) + 1
        mainViewModel?.numberCustomer = (numberCustomer.value?: 0) + 1
    }

    fun clickDownButton(){
        numberCustomer.value?.let {
            if (it > 0) {
                numberCustomer.value = it - 1
                mainViewModel?.numberCustomer = it - 1
            }
        }
    }
}