package com.kantek.dancer.booking.domain.formatter

import com.kantek.dancer.booking.domain.extension.Format
import com.kantek.dancer.booking.domain.extension.formatWith
import com.kantek.dancer.booking.domain.extension.getCalendarCurrent
import com.kantek.dancer.booking.domain.extension.getTimeAgo
import com.kantek.dancer.booking.domain.extension.utcToCalendarLocal
import com.kantek.dancer.booking.domain.extension.utcToDateLocal
import java.util.Calendar
import java.util.Date

class TimeFormatter {

    fun convertToMessage(lastTime: String?, timeNext: String?): String {
        val date: Date? = lastTime?.utcToDateLocal()
        val calendarLast = lastTime.utcToCalendarLocal()!!
        val calendarNext = timeNext.utcToCalendarLocal() ?: getCalendarCurrent()
        return when {
            calendarNext.get(Calendar.YEAR) != calendarLast.get(Calendar.YEAR)
                -> date.formatWith(Format.Message.FORMAT_YEAR)

            calendarNext.get(Calendar.MONTH) != calendarLast.get(Calendar.MONTH)
                -> date.formatWith(Format.Message.FORMAT_MONTH)

            calendarLast.get(Calendar.DATE) == getCalendarCurrent().get(Calendar.DATE)
                -> date.formatWith(Format.Message.FORMAT_TIME)

            calendarNext.get(Calendar.DATE) != calendarLast.get(Calendar.DATE)
                -> date.formatWith(Format.Message.FORMAT_DAY)

            calendarNext.get(Calendar.DATE) == calendarLast.get(Calendar.DATE)
                -> date.formatWith(Format.Message.FORMAT_TIME)

            else -> date.formatWith(Format.Message.FORMAT_TIME)
        }
    }

    /**
     * @param lastTime~
     * @param timeNext~
     * @return boolean
     * If the interval is greater than 30min or day different then return true, else return false
     */
    fun checkShowTimeMessage(lastTime: String?, timeNext: String?): Boolean {
        if (lastTime.isNullOrEmpty() || timeNext.isNullOrEmpty()) return true
        val calendarLast = lastTime.utcToCalendarLocal()
        val calendarNext = timeNext.utcToCalendarLocal()
        return if (calendarLast!!.get(Calendar.DAY_OF_MONTH) != calendarNext!!.get(Calendar.DAY_OF_MONTH))
            true
        else
            calendarLast.timeInMillis - calendarNext.timeInMillis > 300000// 5min
    }

    fun timeAgo(datetime: String?): String {
        return datetime?.utcToDateLocal().getTimeAgo()
    }
}