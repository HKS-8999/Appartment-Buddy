package com.example.apartmentbuddy.model

import com.example.apartmentbuddy.fragments.ShowAppointment

object PendingAppointmentList {
    private val pendingAppointmentList = mutableListOf<ShowAppointmentData>()

    // Method to add data in Appointment list
    fun add(pendingAppointment: ShowAppointmentData) {
        if(!pendingAppointmentList.contains(pendingAppointment)){
            pendingAppointmentList.add(pendingAppointment)
        }
    }

    fun remove(){
        pendingAppointmentList.clear()
    }

    // Method to fetch the data in Appointment List
    fun getAllAppointment(): List<ShowAppointmentData> {
        return pendingAppointmentList
    }
}