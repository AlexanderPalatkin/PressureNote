package com.example.pressurenote.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object Utils {
    fun getTime(): String {
        val calendar = Calendar.getInstance()
        val hours = calendar.get(Calendar.HOUR_OF_DAY)
        val minutes = calendar.get(Calendar.MINUTE)
        return String.format("%02d:%02d", hours, minutes)
    }

    fun getDate(): String {
        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd MMMM", Locale.US)
        return sdf.format(calendar.time)
    }
}