package com.example.apartmentbuddy.model

import android.net.Uri
import java.util.*

class Apartment(
    images: Uri,
    description: String,
    type: String,
    val noOfBedrooms: Number,
    val noOfBathrooms: Number,
    val unitNumber: Number,
    val rent: Float,
    val startDate: Date
) : Advertisement(images, description, type)
