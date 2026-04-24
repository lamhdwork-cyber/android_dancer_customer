package com.kantek.dancer.booking.domain.factory

import com.kantek.dancer.booking.domain.model.ui.booking.IBookingPerformer
import com.kantek.dancer.booking.domain.model.ui.booking.IBookingScheduleDay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class BookingMockFactory {
    fun createPerformers(): List<IBookingPerformer> {
        return listOf(
            createPerformer(
                id = "1",
                name = "Luna",
                avatar = "https://dancer.kendemo.com/uploads/dancer-gallery/dancer-1-1.jpg"
            ),
            createPerformer(
                id = "2",
                name = "Jade",
                avatar = "https://dancer.kendemo.com/uploads/dancer-gallery/dancer-2-1.jpg"
            ),
            createPerformer(
                id = "3",
                name = "Amber",
                avatar = "https://dancer.kendemo.com/uploads/dancer-gallery/dancer-3-1.jpg"
            )
        )
    }

    fun createScheduleDays(): List<IBookingScheduleDay> {
        val calendar = Calendar.getInstance()
        val dayFormat = SimpleDateFormat("EEE", Locale.getDefault())
        val dateFormat = SimpleDateFormat("dd", Locale.getDefault())
        return List(7) {
            val day = object : IBookingScheduleDay {
                override val label: String = dayFormat.format(calendar.time).uppercase(Locale.getDefault())
                override val dayNumber: String = dateFormat.format(calendar.time)
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            day
        }
    }

    fun createScheduleTimes(): List<String> {
        val list = mutableListOf<String>()
        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val base = Calendar.getInstance()
        for (minutes in (19 * 60)..(25 * 60) step 30) {
            val calendar = base.clone() as Calendar
            calendar.set(Calendar.HOUR_OF_DAY, minutes / 60 % 24)
            calendar.set(Calendar.MINUTE, minutes % 60)
            if (minutes >= 24 * 60) {
                calendar.add(Calendar.DAY_OF_MONTH, 1)
            }
            list.add(timeFormat.format(calendar.time))
        }
        return list
    }

    private fun createPerformer(id: String, name: String, avatar: String): IBookingPerformer {
        return object : IBookingPerformer {
            override val id: String = id
            override val name: String = name
            override val avatar: String = avatar
        }
    }
}
