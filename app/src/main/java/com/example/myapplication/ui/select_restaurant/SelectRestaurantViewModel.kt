package com.example.myapplication.ui.select_restaurant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.Dish
import com.example.myapplication.ui.main.MainViewmodel
import com.example.myapplication.utils.Event
import com.example.myapplication.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectRestaurantViewModel @Inject constructor() : ViewModel() {
    var mainViewmodel: MainViewmodel? = null
    val restaurant = MutableLiveData<String>("---")
    private val _eventClickSelectrestaurant = MutableLiveData<Event<List<String>>>()
    val eventClickSelectrestaurant: LiveData<Event<List<String>>> = _eventClickSelectrestaurant
    fun clickSelectrestaurant() {
        val listDish = Utils.getListRestaurant(mainViewmodel?.listDisk ?: listOf(), mainViewmodel?.meal ?: "")
        _eventClickSelectrestaurant.value = Event(listDish)
    }

    fun updateRestaurant(restaurant: String) {
        this.restaurant.value = restaurant
        mainViewmodel?.restaurant = restaurant
    }
}