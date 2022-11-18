package com.example.apartmentbuddy.persistence

import android.net.Uri
import com.example.apartmentbuddy.model.Item
import java.io.File

class ItemDataSource {
    fun getAllItemsList(): List<Item> {
        //Call Firestore to fetch the items here
        return listOf(

        )
    }

    fun getMyItemsList(): List<Item> {
        //Call Firestore to fetch the items here
        return listOf(

        )
    }

    fun getBookmarkedItemsList(): List<Item> {
        //Call Firestore to fetch the items here
        return listOf(

        )
    }
}