package com.kantek.dancer.booking.domain.factory

import android.support.core.extensions.safe
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bed
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MeetingRoom
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.ui.graphics.vector.ImageVector
import com.kantek.dancer.booking.domain.formatter.TextFormatter
import com.kantek.dancer.booking.domain.model.response.room.RoomDTO
import com.kantek.dancer.booking.domain.model.ui.booking.IRoom

class RoomFactory(private val textFormatter: TextFormatter) {

    fun createList(items: List<RoomDTO>?): List<IRoom> {
        return items?.map(::create) ?: listOf()
    }

    private fun create(it: RoomDTO): IRoom {
        val type = it.type.safe()
        val icon = when (type) {
            "vip_room" -> Icons.Outlined.Bed
            "private_suite" -> Icons.Outlined.MeetingRoom
            "deluxe_lounge" -> Icons.Outlined.Home
            "executive_suite" -> Icons.Outlined.StarBorder
            else -> Icons.Outlined.Home
        }
        return object : IRoom {
            override val id: String
                get() = it.id.safe()
            override val name: String
                get() = it.name.safe()
            override val services: String
                get() = it.services.safe().joinToString(", ")
            override val price: String
                get() = it.hourlyRate.safe()
            override val priceDisplay: String
                get() = textFormatter.formatBookingPrice(price)
            override val imageURL: String
                get() = it.image.safe()
            override val imagePlaceholder: ImageVector
                get() = icon
        }
    }

}
