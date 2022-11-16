package com.example.apartmentbuddy.fragments

import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.apartmentbuddy.R
import com.example.apartmentbuddy.model.Appointment


class NewAppointment : Fragment() {

    private val mYear = 0
    private  var mMonth:Int = 0
    private  var mDay:Int = 0
    private  var mHour:Int = 0
    private  var mMinute:Int = 0
    private lateinit var myToolbar : Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }



    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_new_appointment, container, false)

        val btnTimePicker : Button = view.findViewById(R.id.btn_time)
        val txtTime : EditText = view.findViewById(R.id.in_time)
        myToolbar = view.findViewById(R.id.toolbar)
        myToolbar.title = "New Appointment"

        btnTimePicker.setOnClickListener{
            val calendar: Calendar = Calendar.getInstance()
            mHour = calendar.get(Calendar.HOUR_OF_DAY);
            mMinute = calendar.get(Calendar.MINUTE);
            val appointment  = Appointment()
            val timePickerDialog = TimePickerDialog(view.context,
                // Reference : https://www.geeksforgeeks.org/timepicker-in-kotlin/
                { view, hourOfDay, minute ->
                        val msg = appointment.printValidTime(hourOfDay, minute)
                        txtTime.setText(msg)
                },
                mHour,
                mMinute,
                false
            )
            timePickerDialog.show()
        }

        val submit : Button = view.findViewById(R.id.new_appointment_submit)
//        val back :
//        submit.setOnClickListener {
//            view.findNavController().navigate(NewAppointmentDirections.actionNewAppointmentToAppointmentHome())
//        }
        return view
    }
}