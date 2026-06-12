package com.kantek.dancer.booking.domain.usecase

import android.support.core.extensions.withIO
import com.kantek.dancer.booking.domain.model.search.IDancer
import com.kantek.dancer.booking.domain.repo.DancerRepo

class FetchDancerByClubCase(
    private val dancerRepo: DancerRepo,
) {
    suspend operator fun invoke(clubId: String, page: Int): List<IDancer> {
        return withIO { dancerRepo.fetchByPage(clubId = clubId, page = page) }
    }

    suspend fun availableNow(clubId: String, page: Int): List<IDancer> {
        return withIO { dancerRepo.availableNow(clubId = clubId, page = page) }
    }
}
