package com.example.apartmentbuddy.fragments

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.*
import android.widget.CalendarView.OnDateChangeListener
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.apartmentbuddy.R
import com.example.apartmentbuddy.model.Appointment
import com.google.firebase.firestore.FirebaseFirestore


class NewAppointment : Fragment() {

    private val mYear = 0
    private  var mMonth:Int = 0
    private  var mDay:Int = 0
    private  var mHour:Int = 0
    private  var mMinute:Int = 0
    private var selected_date : String = ""
    private  var selected_time : String = ""
    private val appointment  = Appointment()
    private var user_id : String = "test@dal.ca"
    private var user_name : String = "Harsh"


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_new_appointment, container, false)

        val btnTimePicker : Button = view.findViewById(R.id.btn_time)
        val txtTime : EditText = view.findViewById(R.id.appointment_time)

        val myToolbar: Toolbar = view.findViewById(R.id.toolbar) as Toolbar
        myToolbar.inflateMenu(R.menu.appointment_new)
        myToolbar.title = "New Appointment"
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

        val calendarView : CalendarView = view.findViewById(R.id.appointment_date)
        calendarView.setOnDateChangeListener( { view, year, month, dayOfMonth ->
            val month = month + 1
            selected_date = "$dayOfMonth/$month/$year"
        })


        btnTimePicker.setOnClickListener{
            val calendar: Calendar = Calendar.getInstance()
            mHour = calendar.get(Calendar.HOUR_OF_DAY);
            mMinute = calendar.get(Calendar.MINUTE);
            val timePickerDialog = TimePickerDialog(view.context,
                // Reference : https://www.geeksforgeeks.org/timepicker-in-kotlin/
                { view, hourOfDay, minute ->
                        selected_time = appointment.printValidTime(hourOfDay, minute)
                        txtTime.setText(selected_time)
                },
                mHour,
                mMinute,
                false
            )
            timePickerDialog.show()
        }



        val submit : Button = view.findViewById(R.id.new_appointment_submit)

        submit.setOnClickListener {
            this.view?.let { it1 ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    appointment.confirmAppointment(selected_date, selected_time, context,
                            it1, user_id, user_name )
                }
            }
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                val builder = AlertDialog.Builder(context)
//                //set title for alert dialog
//                builder.setTitle("Confirm")
//                //set message for alert dialog
//                builder.setMessage("Do you want proceed with this booking on $selected_date at $selected_time?")
//
//                //performing positive action
//                builder.setPositiveButton("Yes"){dialogInterface, which ->
//                    this.view?.findNavController()?.navigate(NewAppointmentDirections.actionNewAppointmentToAppointmentHome())
//                    appointment.addNewAppointment(selected_date, selected_time, user_id, user_name, context)
//                }
//                //performing cancel action
//                builder.setNeutralButton("Cancel"){dialogInterface , which ->
//                    Toast.makeText(context,"Appointment cancelled",Toast.LENGTH_LONG).show()
//                    this.view?.findNavController()?.navigate(NewAppointmentDirections.actionNewAppointmentToAppointmentHome())
//                }
//                //performing negative action
//                builder.setNegativeButton("No"){dialogInterface, which ->
//                    Toast.makeText(context,"Please select Date and Time",Toast.LENGTH_LONG).show()
//                }
//                // Create the AlertDialog
//                val alertDialog: AlertDialog = builder.create()
//                // Set other dialog properties
//                alertDialog.setCancelable(false)
//                alertDialog.show()
//            }
//            view.findNavController().navigate(NewAppointmentDirections.actionNewAppointmentToAppointmentHome())
        }
        return view
    }
}
