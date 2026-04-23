package com.kantek.dancer.booking.domain.usecase

import com.kantek.dancer.booking.data.repo.DancerRepo
import com.kantek.dancer.booking.domain.factory.DancerFactory
import com.kantek.dancer.booking.domain.model.ui.search.IDancer

class FetchDancerByClubCase(
    private val dancerRepo: DancerRepo,
    private val dancerFactory: DancerFactory
) {
    suspend operator fun invoke(clubId: String, page: Int): List<IDancer> {
        return dancerFactory.createList(dancerRepo.fetchByPage(clubId = clubId, page = page))
    }
}
