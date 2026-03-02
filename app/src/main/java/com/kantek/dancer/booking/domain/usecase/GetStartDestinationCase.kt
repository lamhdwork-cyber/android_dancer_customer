package com.kantek.dancer.booking.domain.usecase

import com.kantek.dancer.booking.data.repo.GetStartDestinationMainRepo

class GetStartDestinationCase(private val getStartDestinationMainRepo: GetStartDestinationMainRepo) {
    operator fun invoke(): String {
        return getStartDestinationMainRepo()
    }
}