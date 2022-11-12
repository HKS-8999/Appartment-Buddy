package com.example.apartmentbuddy.model

import android.net.Uri
import java.util.*

data class Apartment(
    val images: Uri,
    val noOfBedrooms: Number,
    val noOfBathrooms: Number,
    val unitNumber: Number,
    val description: String,
    val rent: Float,
    val startDate: Date
)
