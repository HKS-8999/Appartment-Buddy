package com.example.apartmentbuddy.persistence

import com.example.apartmentbuddy.model.Item

class ItemDataSource {
    fun getItemsList(): List<Item> {
        //Call Firestore to fetch the items here
        return listOf(
            Item(
                "Mini-Closet",
                "A mini closet for sale",
                "New",
                "150".toFloat(),
                "Furniture",
                "6299 South Park street, Dalhousie University",
                "contact@dal.ca"
            )
        )
    }
}