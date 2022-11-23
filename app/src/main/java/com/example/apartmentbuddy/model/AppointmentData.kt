package com.example.apartmentbuddy.model

import com.google.firebase.firestore.DocumentId

data class AppointmentData(
    val name:String,
    val date: String,
    val time:String,
    val user_id: String,
    var location: String,
    val timestamp: String,
)