package com.example.apartmentbuddy.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.apartmentbuddy.R
import com.example.apartmentbuddy.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Home : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var auth: FirebaseAuth
    private var db=Firebase.firestore
    private  var _homeBinding: FragmentHomeBinding?=null
    private val homeBinding get()=_homeBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): android.view.View? {
        auth= Firebase.auth
        _homeBinding= FragmentHomeBinding.inflate(inflater,container,false)
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
            homeBinding.btnNavigateAppointment.setOnClickListener {
                //  val intent = Intent(this, AdvertisementActivity::class.java)
                // startActivity(intent)
            }
            homeBinding.btnUpdateProfile.setOnClickListener{
                findNavController().navigate(R.id.action_home2_to_updateProfile)
            }

            homeBinding.btnNavigateAppointment.setOnClickListener{
                findNavController().navigate(R.id.action_home2_to_appointmentHome)
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _homeBinding=null;
    }
}