package com.example.apartmentbuddy.model

object AppointmentList {
        private val appointmentList = mutableListOf<ShowAppointmentData>()

    // Method to add data in Appointment list
        fun add(appointment: ShowAppointmentData) {
            if(!appointmentList.contains(appointment)){
                appointmentList.add(appointment)
            }
        }

        fun remove(){
            appointmentList.clear()
        }

        // Method to fetch the data in Appointment List
        fun getAllAppointment(): List<ShowAppointmentData> {
            return appointmentList
        }
}

