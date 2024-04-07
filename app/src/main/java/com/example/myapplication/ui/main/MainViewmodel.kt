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
    var posSong = -1

    init {
        listSong.add(
            Song(
                "Crimmal",
                "https://github.com/rafaelreis-hotmart/Audio-Sample-files/raw/master/sample.mp3",
                "Britney SPears"
            )
        )
        listSong.add(
            Song(
                "Đánh Cắp Mặt Trời",
                "https://storage.googleapis.com/exoplayer-test-media-0/play.mp3",
                "Nhiều ca sĩ"
            )
        )
        listSong.add(
            Song(
                "Đào Hoa Nặc",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
                "Đặng Tử Kỳ"
            )
        )
        listSong.add(
            Song(
                "Váy Cưới Của Em Giống Như Bông Tuyết",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
                "Lý Phát Phát "
            )
        )
    }

    fun updateCurrentTab(currentTab: Int) {
        tab = currentTab
    }

    fun clickNext() {
        if (posSong < listSong.size - 1) {
            currentSong.value = posSong + 1
            posSong++
        }
    }

    fun clickPrevious() {
        if (posSong > 0) {
            currentSong.value = posSong - 1
            posSong--
        }
    }
}