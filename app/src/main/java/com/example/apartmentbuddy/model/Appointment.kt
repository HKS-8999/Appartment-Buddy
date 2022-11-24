package com.example.apartmentbuddy.model

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.apartmentbuddy.adapter.AppointmentRecyclerAdapter
import com.example.apartmentbuddy.fragments.AppointmentHomeDirections
import com.example.apartmentbuddy.fragments.NewAppointmentDirections
import com.example.apartmentbuddy.fragments.ShowAppointmentDirections
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class Appointment (){

    private val db = FirebaseFirestore.getInstance()
    private lateinit var id : String



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
    fun buildTimeStamp() : String? {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
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

    fun showAppointment(user_id: String) {
        db.collection("appointment").whereEqualTo("user_id", user_id).get().addOnSuccessListener { documents ->
                for (document in documents) {
                    val data = document.data.get("name")
                    Log.d(TAG, "${data}")
//                    val appointments = document.toObject(AppointmentData::class.java)
//                    AppointmentList.add(appointments)
                    var name : String = document.data.get("name").toString()
                    var date : String = document.data.get("date").toString()
                    var time : String = document.data.get("time").toString()
                    var user_id : String = document.data.get("user_id").toString()
                    var location : String = document.data.get("location").toString()
                    var timestamp : String = document.data.get("timestamp").toString()
                    var appointment_id : String = document.id
                    AppointmentList.add(ShowAppointmentData(name, date, time, user_id, location, timestamp, appointment_id))
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    fun getAppointmentID(user_id: String, date: String, time: String): String {
        db.collection("appointment").whereEqualTo("user_id", user_id).whereEqualTo("date", date)
            .whereEqualTo("time", time)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    id = document.id
                    Log.e(TAG, "$id")
                }
            }
        Log.e(TAG, "$id")
        return id
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun isPending(user_id : String, context: Context?) : Boolean {
        val current_date = LocalDate.now()
        val formatter_date = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val formatted_date = current_date.format(formatter_date)
        db.collection("appointment").whereEqualTo("user_id", user_id)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val date = document.data.get("date")
                    if(date.toString() > formatted_date.toString()){
                        var name : String = document.data.get("name").toString()
                        var date : String = document.data.get("date").toString()
                        var time : String = document.data.get("time").toString()
                        var user_id : String = document.data.get("user_id").toString()
                        var location : String = document.data.get("location").toString()
                        var timestamp : String = document.data.get("timestamp").toString()
                        var appointment_id : String = document.id
                        PendingAppointmentList.add(ShowAppointmentData(name, date, time, user_id, location, timestamp, appointment_id))
                        Log.e(TAG, "Pending")
                    }

//                    else if(date.toString() == formatted_date.toString()){
//                        val builder = AlertDialog.Builder(context)
//                        //set title for alert dialog
//                        builder.setTitle("Alert")
//                        //set message for alert dialog
//                        builder.setMessage("Please contact management to cancel today's appointment")
//
//                        builder.setNeutralButton("Cancel"){dialogInterface , which ->
//                        }
//
//                        // Create the AlertDialog
//                        val alertDialog: AlertDialog = builder.create()
//                        // Set other dialog properties
//                        alertDialog.setCancelable(false)
//                        alertDialog.show()
//                    }
//                    else{
//                        val builder = AlertDialog.Builder(context)
//                        //set title for alert dialog
//                        builder.setTitle("Alert")
//                        //set message for alert dialog
//                        builder.setMessage("No Pending Appointment")
//                        builder.setNeutralButton("Cancel"){dialogInterface , which ->
//                        }
//                        // Create the AlertDialog
//                        val alertDialog: AlertDialog = builder.create()
//                        // Set other dialog properties
//                        alertDialog.setCancelable(false)
//                        alertDialog.show()
//                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }

//    fun isValidDate(date: String, context: Context?, view: View) : Boolean {
//        if(date.isEmpty() || date < )
//    }

        return true
    }

    fun cancelAppointment(appointmentId : String){
        db.collection("appointment").document(appointmentId)
            .delete()
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
    }
}