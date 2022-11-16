package com.example.apartmentbuddy.model

class Appointment (){
    private val date : Int = 0
    private val time : Int = 0

    fun getDate(): Int {
        return date
    }
    fun getTime(): Int {
        return time
    }
    fun setTime(hourOfDay: Int, minute: Int){

    }
    fun  printValidTime(hourOfDay: Int, minute : Int) : String {
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