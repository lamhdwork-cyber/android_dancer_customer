package com.kantek.dancer.booking.domain.model.ui.booking

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.kantek.dancer.booking.domain.model.response.BookingDTO
import com.kantek.dancer.booking.domain.model.ui.user.ILawyer
import com.kantek.dancer.booking.presentation.theme.Colors

interface IBooking {
    val id get() = ""
    val status get() = ""
    val statusDisplay get() = ""
    val colorStatus get() = Colors.Blue227
    val description get() = ""
    val reason get() = ""
    val address get() = ""
    val datetime get() = ""
    val customerName get() = ""
    val customerNameDisplay get() = ""
    val bookingCodeDisplay get() = ""
    val roomName get() = ""
    val roomNameDisplay get() = ""
    val roomImageURL get() = ""
    val roomImagePlaceholder: ImageVector get() = Icons.Outlined.Home
    val totalAmount get() = ""
    val totalAmountDisplay get() = ""
    val dancers get() = listOf<String>()
    val dancersDisplay get() = ""
    val dancersDisplayOrFallback get() = "-"
    val dancersDisplayList get() = listOf<String>()
    val dancerAvatars get() = listOf<String>()
    val dancerAvatarsDisplay get() = listOf<String>()
    val numberOfGuests get() = 0
    val numberOfGuestsDisplay get() = "0"
    val numberOfSongs get() = 0
    val numberOfSongsDisplay get() = "0"
    val isNow get() = false
    val timeDisplay get() = ""
    val hasShowButtonCancel get() = true
    val hasCancel get() = false
    val hasNew get() = false
    val hasComplete get() = false
    val lawyer: ILawyer? get() = null
    val owner: BookingDTO? get() = null
}