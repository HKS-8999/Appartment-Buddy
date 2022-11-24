package com.example.apartmentbuddy.model

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.FirebaseFirestore

class Complains {
    private val db = FirebaseFirestore.getInstance()
    private lateinit var id : String


    fun showComplain(user_id: String) {
        db.collection("complain").whereEqualTo("userId", user_id).get().addOnSuccessListener { documents ->
            for (document in documents) {
                val data = document.data.get("name")
                Log.d(ContentValues.TAG, "${data}")
//                    val appointments = document.toObject(AppointmentData::class.java)
//                    AppointmentList.add(appointments)
                var ticketid : String = document.data.get("ticketid").toString()
                var category : String = document.data.get("category").toString()
                var subject : String = document.data.get("subject").toString()
                var description : String = document.data.get("description").toString()
                var status : String = document.data.get("status").toString()
                println("THis is my $ticketid")
                println("tHIS IS THE cATEgory of the $category")
                println("hffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff")
                ComplainList.add(Complalistdata(ticketid, category, subject, description,status))
                println(ComplainList)
            }

        }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }
}