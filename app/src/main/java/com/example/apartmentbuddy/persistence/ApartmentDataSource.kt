package com.example.apartmentbuddy.persistence

import android.net.Uri
import android.util.Log
import com.example.apartmentbuddy.model.Apartment
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ApartmentDataSource {
    private val db = FirebaseFirestore.getInstance()
    private val apartmentCollection = db.collection("apartments")

    fun getAllApartmentList(): List<Apartment> {
        val apartmentList = mutableListOf<Apartment>()
        GlobalScope.launch(Dispatchers.IO) {
            apartmentCollection.get().await().documents.forEach { document ->
                val images: ArrayList<Uri> =
                    document.get("photos").toString().split(",").map {
                        Uri.parse(it)
                    } as ArrayList<Uri>

                apartmentList.add(
                    Apartment(
                        document.data?.get("uid").toString(),
                        images,
                        document.data?.get("description").toString(),
                        document.data?.get("type").toString(),
                        document.data?.get("contact").toString(),
                        document.data?.get("bathrooms").toString().toFloat(),
                        document.data?.get("bedrooms").toString().toFloat(),
                        document.data?.get("apartment").toString(),
                        document.data?.get("rent").toString().toFloat(),
                        document.data?.get("availability").toString(),
                    )
                )
            }
            Log.e("SIZE", apartmentList.size.toString())
        }
        Log.e("SIZE", apartmentList.size.toString())
        return apartmentList
    }

    fun getMyApartmentsAdvertisement(): List<Apartment> {
        //Call Firestore to fetch the apartments here
        return listOf(

        )
    }

    fun getBookmarkedAdvertisement(): List<Apartment> {
        //Call Firestore to fetch the apartments here
        return listOf(

        )
    }
}