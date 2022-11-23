package com.example.apartmentbuddy.model

data class AppointmentData(
    val name:String,
    val date: String,
    val time:String,
    val user_id: String,
    var location: String,
    val timestamp: String,
){

    constructor(name:String,
                date: String,
                time:String,
                user_id: String,
                location: String,
                timestamp: String,
                appointment_id : String) : this(name, date, time, user_id, location, timestamp)
}