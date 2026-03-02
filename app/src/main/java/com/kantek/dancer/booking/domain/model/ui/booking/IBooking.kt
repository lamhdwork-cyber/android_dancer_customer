package com.kantek.dancer.booking.domain.model.ui.booking

import com.kantek.dancer.booking.domain.model.response.BookingDTO
import com.kantek.dancer.booking.domain.model.ui.user.ILawyer
import com.kantek.dancer.booking.presentation.theme.Colors

interface IBooking {
    val id get() = 0
    val status get() = 1
    val statusDisplay get() = ""
    val colorStatus get() = Colors.Blue227
    val description get() = ""
    val reason get() = ""
    val address get() = ""
    val datetime get() = ""
    val hasShowButtonCancel get() = true
    val hasCancel get() = false
    val hasNew get() = false
    val hasComplete get() = false
    val lawyer: ILawyer? get() = null
    val owner: BookingDTO? get() = null
}