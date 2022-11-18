package com.example.apartmentbuddy.model

import android.net.Uri
import java.util.ArrayList

open class Advertisement(
    val uid: String,
    val images: ArrayList<Uri>,
    val description: String,
    val type: String,
    val contact: String
)