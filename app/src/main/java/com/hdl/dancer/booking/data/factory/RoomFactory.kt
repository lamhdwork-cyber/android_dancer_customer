package com.hdl.dancer.booking.data.factory

import android.support.core.extensions.safe
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bed
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MeetingRoom
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.ui.graphics.vector.ImageVector
import com.hdl.dancer.booking.data.formatter.TextFormatter
import com.hdl.dancer.booking.data.model.response.room.RoomDTO
import com.hdl.dancer.booking.domain.model.booking.IRoom
import java.util.Locale

class RoomFactory(private val textFormatter: TextFormatter) {

    fun imagePlaceholderForType(rawType: String?): ImageVector {
        return when (rawType.safe().lowercase(Locale.getDefault())) {
            "vip_room" -> Icons.Outlined.Bed
            "private_suite" -> Icons.Outlined.MeetingRoom
            "deluxe_lounge" -> Icons.Outlined.Home
            "executive_suite" -> Icons.Outlined.StarBorder
            else -> Icons.Outlined.Home
        }
    }

    fun createList(items: List<RoomDTO>?): List<IRoom> {
        return items?.map(::create) ?: listOf()
    }

    private fun create(it: RoomDTO): IRoom {
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
                get() = imagePlaceholderForType(it.type)
        }
    }
}
