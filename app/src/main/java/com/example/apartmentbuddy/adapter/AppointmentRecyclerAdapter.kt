package com.example.apartmentbuddy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apartmentbuddy.R
import com.example.apartmentbuddy.model.AppointmentData

class AppointmentRecyclerAdapter (private val appointment: List<AppointmentData>) : RecyclerView.Adapter<AppointmentRecyclerAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_view_each_appointment,parent,false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.appointmentDate.text = appointment[position].date
            holder.appointmentTime.text = appointment[position].time
            holder.appointmentLocation.text = appointment[position].location
        }

        override fun getItemCount(): Int {
            return appointment.size
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var appointmentDate : TextView
            var appointmentTime : TextView
            var appointmentLocation : TextView

            init {
                appointmentDate = itemView.findViewById(R.id.show_appointment_date)
                appointmentTime = itemView.findViewById(R.id.show_appointment_time)
                appointmentLocation = itemView.findViewById(R.id.show_appointment_location)
            }
        }
    }
}