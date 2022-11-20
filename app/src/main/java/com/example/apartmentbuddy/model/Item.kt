package com.example.apartmentbuddy.model

import android.net.Uri
import java.util.ArrayList

class Item(
    uid: String,
    images: ArrayList<Uri>,
    description: String,
    type: String,
    contact: String,
    val title: String,
    val condition: String,
    val price: Float,
    val category: String,
    val address: String
) : Advertisement(uid, images, description, type, contact)
