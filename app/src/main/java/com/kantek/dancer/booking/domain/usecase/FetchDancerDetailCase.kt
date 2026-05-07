package com.kantek.dancer.booking.domain.usecase

import android.support.core.extensions.withIO
import com.kantek.dancer.booking.data.repo.DancerRepo
import com.kantek.dancer.booking.domain.factory.DancerFactory
import com.kantek.dancer.booking.domain.model.ui.search.IDancerDetail

class FetchDancerDetailCase(
    private val dancerRepo: DancerRepo,
    private val dancerFactory: DancerFactory
) {
    suspend operator fun invoke(dancerId: String): IDancerDetail? {
        return withIO { dancerFactory.createDetail(dancerRepo.fetchDetail(dancerId)) }
    }
}
