package com.example.myapplication.data

import java.io.Serializable

data class Song(
    val name: String? = null,
    val link: String? = null,
    val author: String? = null,
) : Serializable{
}