package com.example.apartmentbuddy.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import com.example.apartmentbuddy.R

class AppointmentHome : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {




        val view = inflater.inflate(R.layout.fragment_appointment_home, container, false)

        val myToolbar: Toolbar = view.findViewById(R.id.toolbar) as Toolbar
        myToolbar.inflateMenu(R.menu.appointment_new)
        myToolbar.title = "Appointment Booking"
        myToolbar.setTitleTextAppearance(this.context,R.style.CustomActionBarStyle)
        myToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        myToolbar.setNavigationOnClickListener { view ->
            view.findNavController().navigate(NewAppointmentDirections.actionNewAppointmentToAppointmentHome())
        }
        myToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_home -> {
                    // TODO: Navigate to HOME PAGE
                    view.findNavController()
                        .navigate(NewAppointmentDirections.actionNewAppointmentToAppointmentHome())
                    true
                }
                else -> false
            }
        }


        // Accessing Save button from Fragment 2
        val back: Button = view.findViewById(R.id.appointment_back)
        val new_appointment: CardView = view.findViewById(R.id.newAppointment)

        new_appointment.setOnClickListener {
                view.findNavController().navigate(AppointmentHomeDirections.actionAppointmentHomeToNewAppointment())
        }
        return view
    }
}

