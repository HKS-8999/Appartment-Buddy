package com.example.apartmentbuddy

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.apartmentbuddy.fragments.Appointment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.core.View
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Home : AppCompatActivity() {
    // TODO: Rename and change types of parameters

    private lateinit var auth: FirebaseAuth
    private var db=Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)
        val welcome_text: TextView = findViewById(R.id.txt_view_username)
        val user = Firebase.auth.currentUser
        user?.let {
            // Name, email address, and profile photo Url
            val name = user.displayName
            val email = user.email
            // Check if user's email is verified
            val emailVerified = user.isEmailVerified
            val user_id = user.uid
//            welcome_text.setText(user_id)
            val btn_logout = findViewById<Button>(R.id.btn_logout)
            val btnb_navigate_apartment = findViewById<Button>(R.id.btn_navigate_appointment)
            btn_logout.setOnClickListener {
                auth.signOut();
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
            }
            btnb_navigate_apartment.setOnClickListener {
                val intent = Intent(this, AdvertisementActivity::class.java)
                startActivity(intent)
            }
        }
    }



}