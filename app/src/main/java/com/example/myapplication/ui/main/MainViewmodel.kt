package com.example.myapplication.ui.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.Dish
import com.example.myapplication.data.Song
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class MainViewmodel @Inject constructor(
//    @ApplicationContext val context: Context
) : ViewModel() {

    var listDisk = listOf<Dish>()
    var maxTab = 0
    var tab = 0
    private val _currentTab = MutableLiveData(tab)
    val currentTab: LiveData<Int> = _currentTab

    var numberCustomer = 0
    var meal = ""
    var restaurant = ""
    var listDish = mutableListOf<Pair<String, Int>>()

    val listSong = mutableListOf<Song>()
    val currentSong = MutableLiveData<Int>()

    init {
        listSong.add(Song("Crimmal","https://www.youtube.com/results?search_query=crimmal", "Britney SPears"))
        listSong.add(Song("Đánh Cắp Mặt Trời","https://www.youtube.com/watch?v=yz7dyDJdmJk", "Nhiều ca sĩ"))
        listSong.add(Song("Đào Hoa Nặc","https://www.youtube.com/watch?v=V26j5u15Dk0&list=RDV26j5u15Dk0&start_radio=1", "Đặng Tử Kỳ"))
        listSong.add(Song("Váy Cưới Của Em Giống Như Bông Tuyết","https://www.youtube.com/watch?v=Sz_Ms84YzTU&list=RDV26j5u15Dk0&index=2", "Lý Phát Phát "))
    }

    fun updateCurrentTab(currentTab: Int){
        tab = currentTab
    }

    fun clickNext() {
        if ((currentSong.value ?: 0) < listSong.size - 1) {
            currentSong.value = tab + 1
        }
    }

    fun clickPrevious() {
        if ((currentSong.value ?: 0) > 0) {
            currentSong.value = tab - 1
        }
    }
}