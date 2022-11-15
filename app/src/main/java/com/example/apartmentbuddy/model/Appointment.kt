package com.example.apartmentbuddy.model

import com.google.type.Date
import com.google.type.TimeOfDay


class Appointment (){
    val date : Date
        get() {
            TODO()
        }
    val time : TimeOfDay
        get() {
            TODO()
        }

    fun  validDate(hourOfDay: Int, minute : Int) : String {
        var hour = hourOfDay
        var am_pm = ""
        // AM_PM decider logic
        when { hour == 0 -> { hour += 12
            am_pm = "AM"
        }
            hour == 12 -> am_pm = "PM"
            hour > 12 -> { hour -= 12
                am_pm = "PM"
            }
            else -> am_pm = "AM"
        }
        val hourDay = if (hour < 10) "0" + hour else hour
        val min = if (minute < 10) "0" + minute else minute
        // display format of time
        return "$hourDay : $min $am_pm"

    }
}