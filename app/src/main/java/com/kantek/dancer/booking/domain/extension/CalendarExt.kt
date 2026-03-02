package com.kantek.dancer.booking.domain.extension

import java.util.*

fun getCalendar(year: Int, month: Int, dayOfMonth: Int, hourOfDay: Int, minute: Int): Calendar {
    val calendar = Calendar.getInstance()
    calendar[year, month, dayOfMonth, hourOfDay] = minute
    return calendar
}

fun getCalendarCurrent(): Calendar {
    val cal = Calendar.getInstance()
    cal.timeZone = TimeZone.getDefault()
    return cal
}

fun String?.utcToCalendarLocal(): Calendar? {
    if (this.isNullOrEmpty()) return null
    val date = this.utcToDateLocal()
    val cal = Calendar.getInstance()
    cal.timeZone = TimeZone.getDefault()
    cal.time = date
    return cal
}