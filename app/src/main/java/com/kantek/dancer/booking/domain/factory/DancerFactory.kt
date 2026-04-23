package com.kantek.dancer.booking.domain.factory

import android.support.core.extensions.safe
import com.kantek.dancer.booking.domain.model.response.dancer.DancerDTO
import com.kantek.dancer.booking.domain.model.ui.search.IDancer

class DancerFactory {

    fun createList(items: List<DancerDTO>?): List<IDancer> {
        return items?.map(::create) ?: emptyList()
    }

    private fun create(item: DancerDTO): IDancer {
        return object : IDancer {
            override val id: String
                get() = item.id.safe()
            override val name: String
                get() = item.name.safe()
            override val avatar: String
                get() = item.avatar.safe()
            override val danceStyle: String
                get() = item.danceStyles?.firstOrNull().safe()
            override val rating: String
                get() = item.rating.safe()
            override val bio: String
                get() = item.bio.safe()
            override val isAvailableNow: Boolean
                get() = item.isAvailableNow == true
        }
    }
}
