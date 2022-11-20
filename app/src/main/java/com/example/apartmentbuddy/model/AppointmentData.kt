package com.example.apartmentbuddy.model

import java.sql.Timestamp

data class AppointmentData(
    val name:String,
    val date: String,
    val time:String,
    val user_id: String,
    var location: String,
    val timestamp: String
)