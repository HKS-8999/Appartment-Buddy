package com.example.apartmentbuddy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.apartmentbuddy.R
import com.example.apartmentbuddy.fragments.CancelAppointmentDirections
import com.example.apartmentbuddy.model.ShowAppointmentData

class AppointmentRecyclerAdapter(private val appointment: List<ShowAppointmentData>) : RecyclerView.Adapter<AppointmentRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_view_each_appointment,parent,false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.appointmentId.text = appointment[position].appointment_id
            holder.appointmentDate.text = appointment[position].date
            holder.appointmentTime.text = appointment[position].time
            holder.appointmentLocation.text = appointment[position].location
            holder.appointment_name = appointment[position].name

        }

        override fun getItemCount(): Int {
            return appointment.size
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var appointmentDate : TextView
            var appointmentTime : TextView
            var appointmentLocation : TextView
            var appointmentId : TextView
            var thisAppointment : CardView
            lateinit var appointment_name : String

            init {
                appointmentId = itemView.findViewById(R.id.show_appointment_count)
                appointmentDate = itemView.findViewById(R.id.show_appointment_date)
                appointmentTime = itemView.findViewById(R.id.show_appointment_time)
                appointmentLocation = itemView.findViewById(R.id.show_appointment_location)
                thisAppointment = itemView.findViewById(R.id.each_appointment)
                thisAppointment.setOnClickListener {
                    itemView.findNavController().navigate(CancelAppointmentDirections.actionCancelAppointmentToAppointmentHome(
//                        appointmentId.toString(),
//                        appointmentDate.toString(),
//                        appointmentTime.toString(),
//                        appointmentLocation.toString()
                    ))
                }
            }
        }
    }
