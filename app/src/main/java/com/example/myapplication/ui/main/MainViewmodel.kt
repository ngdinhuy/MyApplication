package com.example.myapplication.ui.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.Dish
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class MainViewmodel @Inject constructor(
) : ViewModel() {

    var listDisk = listOf<Dish>()
    var maxTab = 0
    private val _currentTab = MutableLiveData(0)
    val currentTab: LiveData<Int> = _currentTab

    var numberCustomer = 0
    var meal = ""
    var restaurant = ""

    fun clickNext() {
        if ((_currentTab.value ?: 0) < maxTab) {
            _currentTab.value = (_currentTab.value ?: 0) + 1
        }
    }

    fun clickPrevious() {
        if ((_currentTab.value ?: 0) > 0) {
            _currentTab.value = (_currentTab.value ?: 0) - 1
        }
    }
}