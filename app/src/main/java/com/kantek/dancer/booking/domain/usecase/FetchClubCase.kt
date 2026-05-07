package com.kantek.dancer.booking.domain.usecase

import android.support.core.extensions.withIO
import com.kantek.dancer.booking.data.repo.ClubRepo
import com.kantek.dancer.booking.domain.factory.ClubFactory
import com.kantek.dancer.booking.domain.model.ui.search.IClub

class FetchClubCase(
    private val clubRepo: ClubRepo,
    private val clubFactory: ClubFactory
) {
    suspend operator fun invoke(page: Int): List<IClub> {
        return withIO { clubFactory.createList(clubRepo.fetchByPage(page)) }
    }
}
