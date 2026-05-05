package com.kantek.dancer.booking.domain.factory

import android.support.core.extensions.safe
import androidx.compose.ui.graphics.Color
import com.kantek.dancer.booking.app.AppConfig
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
        return object : IBooking {
            override val id: Int
                get() = it.id.safe()
            override val status: Int
                get() = it.status
            override val statusDisplay: String
                get() = it.status_title
            override val colorStatus: Color
                get() = textFormatter.getColorWithStatus(status)
            override val description: String
                get() = it.description.safe()
            override val reason: String
                get() = it.reason_cancel.safe()
            override val address: String
                get() = it.address.safe()
            override val datetime: String
                get() = it.created_at.utcToDateLocal().formatWith(FORMAT_DATE_TIME)
            override val hasShowButtonCancel: Boolean
                get() = status == AppConfig.Booking.Status.NEW || status == AppConfig.Booking.Status.PENDING
            override val hasCancel: Boolean
                get() = status == AppConfig.Booking.Status.CANCELED
            override val hasComplete: Boolean
                get() = status == AppConfig.Booking.Status.COMPLETE
            override val hasNew: Boolean
                get() = status == AppConfig.Booking.Status.NEW
            override val lawyer: ILawyer?
                get() = if (it.partner_account != null) createLawyer(
                    it.partner_account,
                    it.type_service
                ) else null
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
                get() = textFormatter.getLanguage(it.languages)
            override val user: IUser?
                get() = userFactory.create(it.user)
            override val hasReview: Boolean
                get() = it.is_review
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
            createDetail(bookingDTO.partner_account, bookingDTO.type_service)
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