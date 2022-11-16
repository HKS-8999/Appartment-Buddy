package com.example.apartmentbuddy.persistence

import android.net.Uri
import com.example.apartmentbuddy.model.Item
import java.io.File

class ItemDataSource {
    fun getAllItemsList(): List<Item> {
        //Call Firestore to fetch the items here
        return listOf(
            Item(
                Uri.fromFile(File("gs://test_image_view/URIBrooksideHall2.jpg")),
                "A mini closet for sale",
                "Item",
                "contact@dal.ca",
                "Mini-Closet",
                "New",
                "150".toFloat(),
                "Furniture",
                "6299 South Park street, Dalhousie University"
            )
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
            Item(
                Uri.fromFile(File("gs://test_image_view/URIBrooksideHall2.jpg")),
                "A mini closet for sale",
                "Item",
                "contact@dal.ca",
                "Mini-Closet",
                "New",
                "150".toFloat(),
                "Furniture",
                "6299 South Park street, Dalhousie University"
            )
        )
    }
}