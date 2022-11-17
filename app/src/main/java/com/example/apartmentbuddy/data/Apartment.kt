package com.example.apartmentbuddy.data

import android.net.Uri
import java.util.ArrayList

data class Apartment(
    val uid: String,
    val photos: ArrayList<Uri>,
    val bedrooms: Float,
    val bathrooms: Float,
    val apartment: String,
    val description: String,
    val rent: Float,
    val availability: String,
    val contact: String,
)