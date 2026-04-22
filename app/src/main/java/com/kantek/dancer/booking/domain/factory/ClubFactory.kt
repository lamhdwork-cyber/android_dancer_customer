package com.kantek.dancer.booking.domain.factory

import android.support.core.extensions.safe
import com.kantek.dancer.booking.domain.model.response.club.ClubDTO
import com.kantek.dancer.booking.domain.model.ui.search.IClub

class ClubFactory {

    fun createList(items: List<ClubDTO>?): List<IClub> {
        return items?.map(::create) ?: emptyList()
    }

    private fun create(it: ClubDTO): IClub {
        return object : IClub {
            override val id: String
                get() = it.id.safe()
            override val name: String
                get() = it.name.safe()
            override val description: String
                get() = it.description.safe()
            override val fullAddress: String
                get() = listOf(it.address.safe(), it.district.safe(), it.city.safe())
                    .filter { txt -> txt.isNotBlank() }
                    .joinToString(", ")
            override val coverImage: String
                get() = it.coverImage.safe()
            // Temporary hardcoded UI fields until backend provides these values.
            override val distance: String
                get() = "0.5 miles"
            override val rating: String
                get() = "5.0"
            override val openTime: String
                get() = it.openTime.safe()
            override val closeTime: String
                get() = it.closeTime.safe()
            override val isOpen: Boolean
                get() = it.status.safe().equals("active", true)
        }
    }
}
