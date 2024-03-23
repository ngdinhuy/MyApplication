package com.example.myapplication.ui.select_disk

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.ui.main.MainViewmodel
import com.example.myapplication.utils.Utils.notifyObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectDishViewModel @Inject constructor() : ViewModel() {
    var mainViewmodel: MainViewmodel? = null
    var listItem = MutableLiveData<MutableList<Pair<String, Int>>>()

    init {
        listItem.value = mutableListOf(Pair("---", 0))
    }

    fun updateListDish(pos: Int, dish: String?, number: Int?) {
        listItem.value?.let {
            dish?.let { dish ->
                it[pos] = it[pos].copy(first = dish)

            }
            number?.let { number ->
                val newNumber = if (number > 0)
                    it[pos].second + 1
                else
                    if (it[pos].second > 0)
                        it[pos].second - 1
                    else
                        0
                it[pos] = it[pos].copy(second = newNumber)
            }
            mainViewmodel?.listDish = it
        }
        listItem.notifyObserver()
    }

    fun addDish(){
        listItem.value?.let {
            it.add(Pair("---",0))
            mainViewmodel?.listDish = it
        }
        listItem.notifyObserver()
    }

}