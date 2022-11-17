package com.example.apartmentbuddy.data

data class Apartment(
    val uid: String,
    val bedrooms: Float,
    val bathrooms: Float,
    val apartment: String,
    val description: String,
    val rent: Float,
    val availability: String,
    val contact: String,
)