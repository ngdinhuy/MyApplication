package com.example.myapplication.main

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.data.Dish
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val viewmodel: MainViewModel by viewModels()
    lateinit var databinding: ActivityMainBinding
    var listDisk = listOf<Dish>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(databinding.root)
        convertDataJsonToDish()
    }

    fun convertDataJsonToDish() {
        try {
            val inputStream = this.assets.open("dishes.json")
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val myJson = String(buffer, charset("UTF-8"))
            listDisk = Gson().fromJson(myJson,object : TypeToken<List<Dish>>(){}.type)
        } catch (e: Exception) {
            Log.e("Error", e.message.toString())
        }
    }
}