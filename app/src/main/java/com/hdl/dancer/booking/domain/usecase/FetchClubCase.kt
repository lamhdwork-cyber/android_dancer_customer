package com.hdl.dancer.booking.domain.usecase

import android.support.core.extensions.withIO
import com.hdl.dancer.booking.domain.model.search.IClub
import com.hdl.dancer.booking.domain.repo.ClubRepo

class FetchClubCase(
    private val clubRepo: ClubRepo,
) {
    suspend operator fun invoke(page: Int): List<IClub> {
        return withIO { clubRepo.fetchByPage(page) }
    }
}
