package com.example.apartmentbuddy.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.apartmentbuddy.AdvertisementActivity
import com.example.apartmentbuddy.R
import com.example.apartmentbuddy.databinding.FragmentHomeAdminBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeAdmin : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var auth: FirebaseAuth
    private var db=Firebase.firestore
    private  var _homeBinding: FragmentHomeAdminBinding?=null
    private val homeBinding get()=_homeBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): android.view.View? {
        auth= Firebase.auth
        _homeBinding= FragmentHomeAdminBinding.inflate(inflater,container,false)
        return homeBinding.root
    }
    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = Firebase.auth.currentUser
        user?.let {
            // Name, email address, and profile photo Url
            val name = user.displayName
            val email = user.email
            // Check if user's email is verified
            val emailVerified = user.isEmailVerified
            val user_id = user.uid
            homeBinding.txtViewUsername.setText(name)
            homeBinding.btnLogout.setOnClickListener {
                auth.signOut();
                findNavController().navigate(R.id.action_home2_to_login)
            }
            homeBinding.btnUpdateProfile.setOnClickListener{
                findNavController().navigate(R.id.action_home2_to_updateProfile)
            }

            homeBinding.btnViewAppointment.setOnClickListener {
                findNavController().navigate(R.id.action_homeAdmin2_to_appointment)
            }

            homeBinding.btnNavigateTicket.setOnClickListener {
                findNavController().navigate(R.id.action_homeAdmin3_to_ticket)
            }

            homeBinding.btnNavigateAdvertisement.setOnClickListener{
                val intent = Intent(activity, AdvertisementActivity::class.java)
                startActivity(intent)
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _homeBinding=null;
    }
}