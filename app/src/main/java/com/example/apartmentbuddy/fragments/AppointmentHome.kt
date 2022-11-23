package com.example.apartmentbuddy.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import com.example.apartmentbuddy.R
import com.example.apartmentbuddy.model.Appointment

class AppointmentHome : Fragment() {

    private val appointment  = Appointment()
    private val user_id : String = "test@dal.ca"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.O)
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
        back.setOnClickListener {
//            view.findNavController().navigate(AppointmentHomeDirections.actionAppointmentHomeToShowAppointment())
        }
        val new_appointment : CardView = view.findViewById(R.id.newAppointment)
        new_appointment.setOnClickListener {
            view.findNavController().navigate(AppointmentHomeDirections.actionAppointmentHomeToNewAppointment())
        }

        val view_appointment : CardView = view.findViewById(R.id.ViewAppointment)
        view_appointment.setOnClickListener {
            view.findNavController().navigate(AppointmentHomeDirections.actionAppointmentHomeToShowAppointment())
        }

        val cancel_appointment : CardView = view.findViewById(R.id.CancelAppointment)
        cancel_appointment.setOnClickListener {
            appointment.isPending(user_id, this.context)
            view.findNavController().navigate(AppointmentHomeDirections.actionAppointmentHomeToCancelAppointment())
        }
        return view
    }
}