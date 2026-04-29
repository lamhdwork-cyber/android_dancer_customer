package com.kantek.dancer.booking.domain.factory

import android.support.core.extensions.safe
import com.kantek.dancer.booking.domain.model.response.dancer.DancerDTO
import com.kantek.dancer.booking.domain.model.ui.search.IDancer
import com.kantek.dancer.booking.domain.model.ui.search.IDancerDetail

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

    fun createDetail(item: DancerDTO?): IDancerDetail? {
        if (item == null) return null
        return object : IDancerDetail, IDancer by create(item) {
            override val clubId: String
                get() = item.clubId.safe().ifBlank { item.club?.id.safe() }
            override val age: Int
                get() = item.age ?: 0
            override val dancerCode: String
                get() = item.id.safe().takeLast(4)
            override val danceStyles: List<String>
                get() = item.danceStyles ?: emptyList()
            override val gallery: List<String>
                get() = item.gallery ?: emptyList()
            override val experience: Int
                get() = item.experience ?: 0
            override val hourlyRate: String
                get() = item.hourlyRate.safe()
            override val totalReviews: Int
                get() = item.totalReviews ?: 0
        }
    }
}
