package com.example.apartmentbuddy.persistence

import android.net.Uri
import com.example.apartmentbuddy.model.Apartment
import java.io.File
import java.util.*

class ApartmentDataSource() {
    fun getApartmentList(): List<Apartment> {
        //Call Firestore to fetch the apartments here
        return listOf(
            Apartment(
                Uri.fromFile(File("gs://test_image_view/URIBrooksideHall2.jpg")),
                2,
                2,
                2,
                "A beautiful 2 bedroom apartment available at a beautiful location",
                "2351".toFloat(),
                Date(72818219)
            ),
            Apartment(
                Uri.fromFile(File("gs://test_image_view/URIBrooksideHall2.jpg")),
                4,
                2,
                2,
                "A beautiful 4 bedroom apartment available at a beautiful location",
                "2351".toFloat(),
                Date(72818219)
            ),
            Apartment(
                Uri.fromFile(File("gs://test_image_view/URIBrooksideHall2.jpg")),
                3,
                2,
                2,
                "A beautiful 3 bedroom apartment available at a beautiful location",
                "2351".toFloat(),
                Date(72818219)
            )
        )
    }
}