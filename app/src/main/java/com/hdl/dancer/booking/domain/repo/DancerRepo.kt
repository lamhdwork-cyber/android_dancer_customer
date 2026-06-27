package com.hdl.dancer.booking.domain.repo

import com.hdl.dancer.booking.domain.model.search.IDancer
import com.hdl.dancer.booking.domain.model.search.IDancerDetail

interface DancerRepo {
    suspend fun fetchByPage(clubId: String, page: Int): List<IDancer>

    suspend fun availableNow(clubId: String, page: Int): List<IDancer>
    suspend fun fetchByPageWithTotal(clubId: String, page: Int): Pair<List<IDancer>, Int?>
    suspend fun fetchDetail(dancerId: String): IDancerDetail?
    suspend fun toggleAvailability(dancerId: String)
}
