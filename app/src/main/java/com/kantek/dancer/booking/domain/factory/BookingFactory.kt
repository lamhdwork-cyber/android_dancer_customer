package com.kantek.dancer.booking.domain.factory

import android.support.core.extensions.safe
import androidx.compose.ui.graphics.Color
import com.kantek.dancer.booking.domain.extension.Format.FORMAT_DATE_TIME
import com.kantek.dancer.booking.domain.extension.formatWith
import com.kantek.dancer.booking.domain.extension.toObject
import com.kantek.dancer.booking.domain.extension.utcToDateLocal
import com.kantek.dancer.booking.domain.formatter.TextFormatter
import com.kantek.dancer.booking.domain.model.response.BookingDTO
import com.kantek.dancer.booking.domain.model.response.SpecialityDTO
import com.kantek.dancer.booking.domain.model.response.lawyer.LawyerDTO
import com.kantek.dancer.booking.domain.model.ui.booking.IBooking
import com.kantek.dancer.booking.domain.model.ui.booking.IBookingDetail
import com.kantek.dancer.booking.domain.model.ui.booking.IBookingScheduleDay
import com.kantek.dancer.booking.domain.model.ui.user.ILawyer
import com.kantek.dancer.booking.domain.model.ui.user.ILawyerDetail
import com.kantek.dancer.booking.domain.model.ui.user.IUser
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class BookingFactory(
    private val textFormatter: TextFormatter,
    private val userFactory: UserFactory
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

    private fun createLawyer(
        it: LawyerDTO?,
        typeService: List<SpecialityDTO>?
    ): ILawyer {
        return object : ILawyer {
            override val owner: LawyerDTO?
                get() = it
            override val id: Int
                get() = it?.id.safe()
            override val fullName: String
                get() = it?.name.safe()
            override val avatarURL: String
                get() = it?.avatar_url.safe()
            override val exp: String
                get() = it?.exp.toString()
            override val cases: String
                get() = it?.cases.toString()
            override val specialties: List<String>
                get() = typeService?.map { it.name }.safe()
        }
    }

    fun createDetails(it: BookingDTO?): IBookingDetail? {
        if (it == null) return null
        return object : IBookingDetail, IBooking by create(it) {
            override val language: String
                get() = it.bookingType.safe()
            override val user: IUser?
                get() = userFactory.create(it.user)
            override val hasReview: Boolean
                get() = it.isReview
        }
    }

    private fun createDetail(
        it: LawyerDTO?,
        specialities: List<SpecialityDTO>? = null
    ): ILawyerDetail {
        return object : ILawyerDetail,
            ILawyer by createLawyer(it, specialities) {

            override val education: String
                get() = it?.education.safe()
            override val achievements: String
                get() = it?.experience.safe()
            override val licenseURL: String
                get() = it?.license_url.safe()
            override val phoneNumber: String
                get() = it?.phone.safe()
            override val phoneDisplay: String
                get() = textFormatter.formatPhone(phoneNumber).safe()
            override val email: String
                get() = it?.email.safe()
            override val rating: Float
                get() = if (it?.rating_avg.safe() == 0f) 5f else it?.rating_avg.safe()
            override val reviewCount: Int
                get() = it?.total_reviews.safe()
        }
    }

    fun createLawyerDetail(it: String): ILawyerDetail? {
        return try {
            val bookingDTO = it.toObject<BookingDTO>()
            null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun createLawyerDetail(it: LawyerDTO): ILawyerDetail {
        return createDetail(it)
    }

    fun createScheduleDays(): List<IBookingScheduleDay> {
        val calendar = Calendar.getInstance()
        val dayFormat = SimpleDateFormat("EEE", Locale.getDefault())
        val dateFormat = SimpleDateFormat("dd", Locale.getDefault())
        val valueFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return List(7) {
            val day = object : IBookingScheduleDay {
                override val label: String = dayFormat.format(calendar.time).uppercase(Locale.getDefault())
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
}