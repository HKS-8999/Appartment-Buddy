package com.example.apartmentbuddy.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.cardview.widget.CardView
import com.example.apartmentbuddy.R

class AppointmentHome : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_appointment_home, container, false)
        // Accessing Save button from Fragment 2
        val back: Button = view.findViewById(R.id.appointment_back)
        val new_appointment: CardView = view.findViewById(R.id.newAppointment)

        new_appointment.setOnClickListener {
//                view.findNavController().navigate()
        }
        return inflater.inflate(R.layout.fragment_appointment_home, container, false)
    }
}

