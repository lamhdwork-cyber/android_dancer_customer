package com.kantek.dancer.booking.domain.factory

import android.support.core.extensions.safe
import androidx.compose.ui.graphics.Color
import com.kantek.dancer.booking.domain.extension.Format
import com.kantek.dancer.booking.domain.extension.Format.FORMAT_DATE_TIME
import com.kantek.dancer.booking.domain.extension.formatWith
import com.kantek.dancer.booking.domain.extension.utcToDateLocal
import com.kantek.dancer.booking.domain.formatter.TextFormatter
import com.kantek.dancer.booking.domain.model.response.BookingDTO
import com.kantek.dancer.booking.domain.model.ui.booking.IBooking
import com.kantek.dancer.booking.domain.model.ui.booking.IBookingDetail
import androidx.compose.ui.graphics.vector.ImageVector
import com.kantek.dancer.booking.domain.model.ui.booking.IBookingScheduleDay
import com.kantek.dancer.booking.domain.model.ui.user.ILawyer
import com.kantek.dancer.booking.domain.model.ui.user.IUser
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class BookingFactory(
    private val textFormatter: TextFormatter,
    private val userFactory: UserFactory,
    private val roomFactory: RoomFactory
) {
    fun createList(its: List<BookingDTO>?): List<IBooking> {
        return its?.map(::create) ?: listOf()
    }

    private fun create(it: BookingDTO): IBooking {
        val bookingDancers = if (!it.dancers.isNullOrEmpty()) {
            it.dancers
        } else {
            listOfNotNull(it.dancer)
        }
        return object : IBooking {
            override val id: String
                get() = it.id.safe()
            override val status: String
                get() = it.status.safe()
            override val statusDisplay: String
                get() = status.replaceFirstChar { c -> c.uppercase() }
            override val colorStatus: Color
                get() = textFormatter.getColorWithStatus(status)
            override val description: String
                get() = it.notes.safe()
            override val reason: String
                get() = it.cancelReason.safe()
            override val address: String
                get() = it.club?.address.safe()
            override val datetime: String
                get() = it.createdAt.safe().utcToDateLocal().formatWith(FORMAT_DATE_TIME)
            override val customerName: String
                get() = it.user?.firstName.safe()
            override val customerNameDisplay: String
                get() = customerName.ifBlank { "Guest" }.uppercase()
            override val bookingCodeDisplay: String
                get() = "#${id.takeLast(6)}"
            override val roomName: String
                get() = it.room?.name.safe()
            override val roomNameDisplay: String
                get() = roomName.ifBlank { "-" }
            override val roomImageURL: String
                get() = it.room?.image.safe()
            override val roomImagePlaceholder: ImageVector
                get() = roomFactory.imagePlaceholderForType(it.room?.type)
            override val totalAmount: String
                get() = it.totalAmount.safe()
            override val totalAmountDisplay: String
                get() = if (totalAmount.isBlank()) "$0.00" else "$$totalAmount"
            override val dancers: List<String>
                get() = bookingDancers.mapNotNull { dancer ->
                    dancer.name?.takeIf { name -> name.isNotBlank() }
                }
            override val dancersDisplay: String
                get() = dancersDisplayList.joinToString(", ")
            override val dancersDisplayOrFallback: String
                get() = dancersDisplay.ifBlank { "-" }
            override val dancersDisplayList: List<String>
                get() = dancers.take(5)
            override val dancerAvatars: List<String>
                get() = bookingDancers.mapNotNull { dancer ->
                    dancer.avatar?.takeIf { avatar -> avatar.isNotBlank() }
                }
            override val dancerAvatarsDisplay: List<String>
                get() = dancerAvatars.take(5)
            override val numberOfGuests: Int
                get() = it.numberOfGuests.safe()
            override val numberOfGuestsDisplay: String
                get() = numberOfGuests.toString()
            override val numberOfSongs: Int
                get() = it.numberOfSongs.safe()
            override val numberOfSongsDisplay: String
                get() = numberOfSongs.toString()
            override val isNow: Boolean
                get() = it.bookingType.safe().equals("immediate", true)
            override val timeDisplay: String
                get() = if (isNow) "NOW" else datetime
            override val hasShowButtonCancel: Boolean
                get() = status.equals("pending", true) || status.equals("scheduled", true)
            override val hasCancel: Boolean
                get() = status.equals("cancelled", true)
            override val hasComplete: Boolean
                get() = status.equals("completed", true)
            override val hasNew: Boolean
                get() = status.equals("pending", true)
            override val lawyer: ILawyer?
                get() = null
            override val owner: BookingDTO
                get() = it
        }
    }

    fun createDetails(it: BookingDTO?): IBookingDetail? {
        if (it == null) return null
        val bookingDancers = if (!it.dancers.isNullOrEmpty()) {
            it.dancers
        } else {
            listOfNotNull(it.dancer)
        }
        return object : IBookingDetail, IBooking by create(it) {
            override val statusDisplay: String
                get() = when (it.status.safe().lowercase(Locale.getDefault())) {
                    "confirmed" -> "Accepted"
                    else -> it.status.safe().replaceFirstChar { c -> c.uppercase() }
                }
            override val language: String
                get() = it.bookingType.safe()
            override val user: IUser?
                get() = userFactory.create(it.user)
            override val hasReview: Boolean
                get() = it.isReview
            override val clubNameDisplay: String
                get() = it.club?.name.safe().ifBlank { "-" }
            override val clubCoverImage: String
                get() = it.club?.coverImage.safe()
            override val bookingDateShort: String
                get() = formatBookingDateShort(it.bookingDate)
            override val bookingTimeFormatted: String
                get() = formatBookingStartTime(it.startTime)
            override val dancerStyleLines: List<String>
                get() = bookingDancers.take(5).map { d ->
                    d.danceStyles?.filter { s -> !s.isNullOrBlank() }
                        ?.joinToString(", ")
                        ?.trim()
                        .orEmpty()
                }
            override val showVipGuestBadge: Boolean
                get() {
                    val roomName = it.room?.name.safe().lowercase(Locale.getDefault())
                    return roomName.contains("vip") || roomName.contains("suite")
                }
            override val roomType: String
                get() = it.room?.type.safe()
        }
    }

    fun createScheduleDays(): List<IBookingScheduleDay> {
        val calendar = Calendar.getInstance()
        val dayFormat = SimpleDateFormat("EEE", Locale.getDefault())
        val dateFormat = SimpleDateFormat("dd", Locale.getDefault())
        val valueFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return List(7) {
            val day = object : IBookingScheduleDay {
                override val label: String =
                    dayFormat.format(calendar.time).uppercase(Locale.getDefault())
                override val dayNumber: String = dateFormat.format(calendar.time)
                override val dateValue: String = valueFormat.format(calendar.time)
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

    private fun formatBookingDateShort(bookingDate: String?): String {
        val raw = bookingDate?.trim().orEmpty()
        if (raw.isEmpty()) return ""
        return try {
            val parsed = SimpleDateFormat(Format.FORMAT_DATE_API, Locale.getDefault()).parse(raw)
                ?: return ""
            SimpleDateFormat("MMM d", Locale.getDefault()).format(parsed)
        } catch (_: ParseException) {
            ""
        }
    }

    private fun formatBookingStartTime(startTime: String?): String {
        val raw = startTime?.trim().orEmpty()
        if (raw.isEmpty()) return ""
        val patterns = listOf("HH:mm:ss", "HH:mm", "hh:mm a")
        for (pattern in patterns) {
            try {
                val fmt = SimpleDateFormat(pattern, Locale.getDefault())
                fmt.timeZone = TimeZone.getDefault()
                val parsed: Date = fmt.parse(raw) ?: continue
                return SimpleDateFormat(Format.FORMAT_TIME, Locale.getDefault()).format(parsed)
            } catch (_: ParseException) {
                continue
            }
        }
        return raw
    }

}