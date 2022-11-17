package com.example.apartmentbuddy.data

import android.net.Uri
import java.util.ArrayList

data class Item(
    val uid: String,
    val photos: ArrayList<Uri>,
    val title: String,
    val description: String,
    val condition: String,
    val price: Float,
    val category: String,
    val address: String,
    val contact: String,
)