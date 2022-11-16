package com.example.apartmentbuddy.data

data class Item(
    val uid: String,
    val title: String,
    val description: String,
    val condition: String,
    val price: Float,
    val category: String,
    val address: String,
    val contact: String,
)