package com.hdl.dancer.booking.domain.usecase

import android.support.core.extensions.withIO
import com.hdl.dancer.booking.domain.model.search.IDancer
import com.hdl.dancer.booking.domain.repo.DancerRepo

class FetchClubDancersAdminPageCase(
    private val dancerRepo: DancerRepo,
) {
    suspend operator fun invoke(clubId: String, page: Int): Pair<List<IDancer>, Int?> = withIO {
        dancerRepo.fetchByPageWithTotal(clubId = clubId, page = page)
    }
}
