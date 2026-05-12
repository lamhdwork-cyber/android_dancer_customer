package com.kantek.dancer.booking.domain.usecase

import android.support.core.extensions.withIO
import com.kantek.dancer.booking.data.repo.DancerRepo
import com.kantek.dancer.booking.domain.factory.DancerFactory
import com.kantek.dancer.booking.domain.model.ui.search.IDancer

class FetchClubDancersAdminPageCase(
    private val dancerRepo: DancerRepo,
    private val dancerFactory: DancerFactory,
) {
    suspend operator fun invoke(clubId: String, page: Int): Pair<List<IDancer>, Int?> = withIO {
        val body = dancerRepo.fetchByPageWithTotal(clubId = clubId, page = page)
        val list = dancerFactory.createList(body?.data)
        val total = body?.meta?.totalItems
        list to total
    }
}
