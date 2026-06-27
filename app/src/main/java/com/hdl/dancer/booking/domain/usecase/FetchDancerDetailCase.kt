package com.hdl.dancer.booking.domain.usecase

import android.support.core.extensions.withIO
import com.hdl.dancer.booking.domain.model.search.IDancerDetail
import com.hdl.dancer.booking.domain.repo.DancerRepo

class FetchDancerDetailCase(
    private val dancerRepo: DancerRepo,
) {
    suspend operator fun invoke(dancerId: String): IDancerDetail? {
        return withIO { dancerRepo.fetchDetail(dancerId) }
    }
}
