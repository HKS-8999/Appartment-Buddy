package com.example.apartmentbuddy.model

data class AppointmentData(
    val name:String,
    val date: String,
    val time:String,
    val user_id: String,
    var location: String,
    val timestamp: String
)