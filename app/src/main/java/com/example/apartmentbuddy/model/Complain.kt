package com.example.apartmentbuddy.model

import android.net.Uri
import java.util.ArrayList
class Complain(
    val userId:String ,
    val selectedImages: ArrayList<Uri>,
    val description:String,
    val subject:String,
    val date:String,
    val category:String,
    val unitnumber: String,
    val firstname: String,
    val status: String,
    val ticketid:String

)