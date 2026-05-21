package com.kantek.dancer.booking.domain.usecase

import android.support.core.extensions.withIO
import com.kantek.dancer.booking.domain.model.search.IDancerDetail
import com.kantek.dancer.booking.domain.repo.DancerRepo

class FetchDancerDetailCase(
    private val dancerRepo: DancerRepo,
) {
    suspend operator fun invoke(dancerId: String): IDancerDetail? {
        return withIO { dancerRepo.fetchDetail(dancerId) }
    }
}
