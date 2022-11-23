package com.example.apartmentbuddy.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object FirebaseAuthUser {
    private lateinit var auth: FirebaseAuth
    private var userId: String? = null

    /**
     * This method returns the userId of the logged in user
     */
    fun getUserId(): String? {
        auth = Firebase.auth
        Firebase.auth.currentUser?.let {
            userId = it.uid
        }
        return userId
    }
}