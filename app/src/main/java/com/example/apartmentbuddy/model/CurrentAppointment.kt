package com.example.apartmentbuddy.model

object CurrentAppointment {
    private val appointmentList = HashMap<String, String>()

    // Method to add data in Appointment list
    fun add( key : String, value: String) {
        appointmentList.put(key,value)
    }
    fun getItemFromAppointment(key: String):String{
        return appointmentList.get(key).toString()
    }
}