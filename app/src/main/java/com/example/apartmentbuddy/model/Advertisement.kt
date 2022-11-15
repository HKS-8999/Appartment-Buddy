package com.example.apartmentbuddy.model

import android.net.Uri

open class Advertisement(
    val images: Uri,
    val description: String,
    val type: String
)