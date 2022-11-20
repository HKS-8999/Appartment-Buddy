package com.example.apartmentbuddy.model

import android.net.Uri
import java.util.*

class Apartment(
    documentId: String,
    uid: String,
    images: ArrayList<Uri>,
    description: String,
    type: String,
    contact: String,
    val noOfBedrooms: Float,
    val noOfBathrooms: Float,
    val unitNumber: String,
    val rent: Float,
    val startDate: String,
    bookmarkUserList: MutableList<String>
) : Advertisement(documentId, uid, images, description, type, contact, bookmarkUserList)
