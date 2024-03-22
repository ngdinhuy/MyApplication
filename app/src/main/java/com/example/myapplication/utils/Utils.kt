package com.example.myapplication.utils

import com.example.myapplication.data.Dish

object Utils {
    fun getListRestaurant(dishes: List<Dish>, meal: String) : List<String>{
        val listRestaurant = mutableListOf<String>()
        dishes.forEach{dish ->
            dish.availableMeals.forEach inner@{
                if (it == meal) {
                    listRestaurant.add(dish.restaurant)
                    return@inner
                }
            }
        }
        return listRestaurant.distinctBy { it }
    }
}