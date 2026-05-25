package com.kantek.dancer.booking.domain.usecase

import com.kantek.dancer.booking.domain.repo.DestinationRepo

class GetStartDestinationCase(
    private val destinationRepo: DestinationRepo,
) {
    operator fun invoke(): String {
        return destinationRepo.getStartDestination()
    }
}