package com.example.apartmentbuddy.model

import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import com.example.apartmentbuddy.fragments.NewAppointmentDirections
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Appointment (){

    private val db = FirebaseFirestore.getInstance()

    fun  printValidTime(hourOfDay: Int, minute : Int) : String {
        var hour = hourOfDay
        var am_pm = ""
        // AM_PM decider logic
        when { hour == 0 -> { hour += 12
            am_pm = "AM"
        }
            hour == 12 -> am_pm = "PM"
            hour > 12 -> { hour -= 12
                am_pm = "PM"
            }
            else -> am_pm = "AM"
        }
        val hourDay = if (hour < 10) "0" + hour else hour
        val min = if (minute < 10) "0" + minute else minute
        // display format of time
        return "$hourDay : $min $am_pm"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    // Reference : https://www.programiz.com/kotlin-programming/examples/current-date-time
    // Format the current Date and time in "yyyy-MM-dd HH:mm:ss"
    fun buildTimeStamp() : String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val formatted = current.format(formatter)
        return formatted
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun confirmAppointment(date: String, time: String, context: Context?, view: View, user_id: String, user_name: String,){
        val builder = AlertDialog.Builder(context)
        //set title for alert dialog
        builder.setTitle("Confirm")
        //set message for alert dialog
        builder.setMessage("Do you want proceed with this booking on $date at $time?")

        //performing positive action
        builder.setPositiveButton("Yes"){dialogInterface, which ->
            view.findNavController().navigate(NewAppointmentDirections.actionNewAppointmentToAppointmentHome())
            addNewAppointment(date, time, user_id, user_name, context)
        }
        //performing cancel action
        builder.setNeutralButton("Cancel"){dialogInterface , which ->
            Toast.makeText(context,"Appointment cancelled",Toast.LENGTH_LONG).show()
            view.findNavController().navigate(NewAppointmentDirections.actionNewAppointmentToAppointmentHome())
        }
        //performing negative action
        builder.setNegativeButton("No"){dialogInterface, which ->
            Toast.makeText(context,"Please select Date and Time",Toast.LENGTH_LONG).show()
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    // Populates the database with new appointment details
    @RequiresApi(Build.VERSION_CODES.O)
    fun addNewAppointment(date: String, time: String, user_id: String, user_name: String, context: Context?) : Boolean{
        val appointmentData = AppointmentData(user_name, date, time, user_id, location = "Office 2", timestamp = buildTimeStamp())
        db.collection("appointment").add(appointmentData)
            .addOnSuccessListener {
                Toast.makeText(context, "Appointment Booked", Toast.LENGTH_SHORT)
                    .show()
            }
            .addOnFailureListener { ex ->
                Toast.makeText(
                    context, "Host down due to" + ex.message, Toast.LENGTH_LONG
                ).show()
            }
        return true
    }
}