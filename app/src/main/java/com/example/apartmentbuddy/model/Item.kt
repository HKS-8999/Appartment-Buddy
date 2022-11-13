package com.example.apartmentbuddy.model

import android.net.Uri

class Item(
    images : Uri,
    description: String,
    type: String,
    val title: String,
    val condition: String,
    val price: Float,
    val category: String,
    val address: String,
    val contact: String
) : Advertisement(images, description, type)
