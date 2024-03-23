package com.example.myapplication.utils

import androidx.lifecycle.MutableLiveData
import com.example.myapplication.data.Dish

object Utils {
    fun getListRestaurant(dishes: List<Dish>, meal: String): List<String> {
        val listRestaurant = mutableListOf<String>()
        dishes.forEach { dish ->
            dish.availableMeals.forEach inner@{
                if (it == meal) {
                    listRestaurant.add(dish.restaurant)
                    return@inner
                }
            }
        }
        return listRestaurant.distinctBy { it }
    }

    fun getListDish(dishes: List<Dish>, meal: String, restaurant: String): List<String> {
        val listDish = mutableListOf<String>()
        dishes.forEach { dish ->
            if (dish.restaurant != restaurant) {
                return@forEach
            }
            dish.availableMeals.forEach inner@{
                if (it == meal) {
                    listDish.add(dish.name)
                    return@inner
                }
            }
        }
        return listDish
    }

    fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }
}