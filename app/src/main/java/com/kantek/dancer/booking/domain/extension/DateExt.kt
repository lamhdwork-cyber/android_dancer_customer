package com.kantek.dancer.booking.domain.extension

import com.kantek.dancer.booking.domain.extension.Format.DATE_PICKER
import com.kantek.dancer.booking.domain.extension.Format.FORMAT_DATE_APP
import com.kantek.dancer.booking.domain.extension.Format.FORMAT_DATE_DISPLAY
import com.kantek.dancer.booking.domain.extension.Format.FORMAT_DATE_TIME_API_2
import com.kantek.dancer.booking.domain.extension.Format.FORMAT_DATE_TIME_REQUEST_FORM
import com.kantek.dancer.booking.domain.extension.Format.dateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object Format {
    const val FORMAT_DATE_API = "yyyy-MM-dd"
    const val FORMAT_DATE_DISPLAY = "MM/dd/yyyy"
    const val FORMAT_DATE_OF_BIRTH = "MM-dd-yyyy"

    const val DATE_PICKER = "dd-MM-yyyy"
    const val FORMAT_DATE_TIME_API = "yyyy-MM-dd HH:mm"
    const val FORMAT_DATE_TIME_API_2 = "yyyy-MM-dd HH:mm:ss"
    const val FORMAT_DATE_MONTH_TIME = "MMMM dd, yyyy hh:mm a"

    const val FORMAT_DATE_APP = "dd-MM-yyyy"
    const val FORMAT_DATE_APPOINTMENT = "EEE, MMMM dd, yyyy"
    const val FORMAT_DATE_BOOKING = "EEE, MMMM dd, yyyy"
    const val FORMAT_DATE_TIME_BOOKING = "EEE, MMMM dd, yyyy hh:mm a"
    const val FORMAT_TIME = "hh:mm a"
    const val FORMAT_TIME_API = "HH:mm"
    const val FORMAT_DATE_TIME = "hh:mm a - MMM dd yyyy"
    const val FORMAT_DATE_TIME_REQUEST_FORM = "MM/dd/yyyy HH:mm"
    const val FORMAT_DATE_TIME_REVIEW = "hh:mm a - MM/dd/yyyy"

    object Message {
        const val FORMAT_YEAR = "E, yyyy-MM-dd hh:mm a"
        const val FORMAT_MONTH = "E, MM-dd hh:mm a"
        const val FORMAT_DAY = "E, dd hh:mm a"
        const val FORMAT_TIME = "hh:mm a"
    }

    val dateFormat = listOf(
        "yyyy-MM-dd'T'HH:mm:ssZZZZZ",
        "yyyy-MM-dd'T'HH:mm:ss'Z'",
        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
        "yyyy-MM-dd HH:mm:ss",
        "yyyy-MM-dd hh:mm a",
        "yyyy-MM-dd HH:mm",
        "MM/dd/yyyy hh:mm a",
        "yyyy-MM-dd",
        "h:mm:ss a",
        "h:mm a",
        "MM/dd/yyyy",
        "MMMM d, yyyy",
        "MMMM d, yyyy LT",
        "EEEE, MMMM d, yyyy LT",
        "yyyyyy-MM-dd",
        "yyyy-MM-dd",
        "yyyy-'W'ww-E",
        "GGGG-'[W]'ww-E",
        "yyyy-'W'ww",
        "GGGG-'[W]'ww",
        "yyyy'W'ww",
        "yyyy-DDD",
        "HH:mm:ss.SSSS",
        "HH:mm:ss",
        "HH:mm",
        "HH"
    )

}

fun String.toDateWithFormat(
    formatInput: String = DATE_PICKER,
    formatOutput: String = DATE_PICKER
): Int {
    val inputFormat = SimpleDateFormat(formatInput, Locale("vi", "VN"))
    val outputFormat = SimpleDateFormat(formatOutput, Locale("vi", "VN"))
    return try {
        val date = inputFormat.parse(this)
        Integer.parseInt(outputFormat.format(date ?: 0))
    } catch (e: Exception) {
        0
    }
}

fun String?.formatDateWithFormat(
    formatInput: String = DATE_PICKER,
    formatOutput: String = DATE_PICKER
): String {
    if (this.isNullOrEmpty()) return ""
    val inputFormat = SimpleDateFormat(formatInput, Locale("vi", "VN"))
    inputFormat.timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh")
    val outputFormat = SimpleDateFormat(formatOutput, Locale("vi", "VN"))
    outputFormat.timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh")
    val date = inputFormat.parse(this)
    return outputFormat.format(date ?: 0)
}

fun String?.toDateLocal(formatInput: String = FORMAT_DATE_TIME_API_2): Date? {
    val dateFormat = SimpleDateFormat(formatInput, Locale.getDefault())
    dateFormat.timeZone = TimeZone.getTimeZone("UTC")
    return this?.let { dateFormat.parse(it) }
}

fun Date?.formatWith(formatter: String = FORMAT_DATE_APP): String {
    if (this == null) return ""
    val fm = SimpleDateFormat(formatter, Locale.getDefault())
    fm.timeZone = TimeZone.getDefault()
    return fm.format(this)
}

fun String.formatWithVN(formatter: String = FORMAT_DATE_APP): String {
    val date = this.toDateVN()
    return date.formatWith(formatter)
}

fun String.toDateVN(): Date {
    dateFormat.forEach {
        val dateFormat = SimpleDateFormat(it, Locale("vi", "VN"))
        dateFormat.timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh")
        var date: Date? = null
        try {
            date = dateFormat.parse(this)
        } catch (_: ParseException) {
        }
        if (date != null) return date
    }
    return Date()
}

fun Date?.getTimeAgo(): String {
    val calendar = Calendar.getInstance()
    if (this != null)
        calendar.time = this

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)
    val second = calendar.get(Calendar.SECOND)

    val currentCalendar = Calendar.getInstance()

    val currentYear = currentCalendar.get(Calendar.YEAR)
    val currentMonth = currentCalendar.get(Calendar.MONTH)
    val currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH)
    val currentHour = currentCalendar.get(Calendar.HOUR_OF_DAY)
    val currentMinute = currentCalendar.get(Calendar.MINUTE)
    val currentSecond = currentCalendar.get(Calendar.SECOND)

    return if (year < currentYear) {
        val interval = currentYear - year
        "$interval năm trước"
    } else if (month < currentMonth) {
        val interval = currentMonth - month
        "$interval tháng trước"
    } else if (day < currentDay) {
        val interval = currentDay - day
        "$interval ngày trước"
    } else if (hour < currentHour) {
        val interval = currentHour - hour
        "$interval giờ trước"
    } else if (minute < currentMinute) {
        val interval = currentMinute - minute
        "$interval phút trước"
    } else {
        val interval = currentSecond - second
        if (interval <= 0) "ngay bây giờ" else "$interval giây trước"
    }
}

fun String.utcToDateLocal(): Date {
    dateFormat.forEach {
        val dateFormat = SimpleDateFormat(it, Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        var date: Date? = null
        try {
            date = dateFormat.parse(this)
        } catch (_: ParseException) {
        }
        if (date != null) return date
    }
    return Date()
}

fun Long.withFormat(format: String = FORMAT_DATE_DISPLAY): String {
    val sdf = SimpleDateFormat(format, Locale("vi", "VN"))
    sdf.timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh")
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return sdf.format(calendar.time)
}

fun String.isFutureDateTime(format: String = FORMAT_DATE_TIME_REQUEST_FORM): Boolean {
    val formatter = SimpleDateFormat(format, Locale("vi", "VN"))
    formatter.timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh")
    return try {
        val inputDateTime = formatter.parse(this)
        val currentDateTime = Date()
        inputDateTime?.after(currentDateTime) ?: false
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

fun String.toDateUTC(
    formatInput: String = Format.FORMAT_DATE_TIME_API,
    formatOutput: String = Format.FORMAT_DATE_TIME_API
): String {
    val inputFormat = SimpleDateFormat(formatInput, Locale.getDefault())
    val outputFormat = SimpleDateFormat(formatOutput, Locale.getDefault())
    outputFormat.timeZone = TimeZone.getTimeZone("UTC")
    val dateInput = inputFormat.parse(this)
    return outputFormat.format(dateInput ?: getDateCurrent(formatOutput))
}


fun getDateCurrent(format: String = Format.FORMAT_DATE_TIME_API): String {
    val timeCurrent = Calendar.getInstance().time
    val outputFormat = SimpleDateFormat(format, Locale.getDefault())
    return outputFormat.format(timeCurrent)
}