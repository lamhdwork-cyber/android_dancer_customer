package com.hdl.dancer.booking.domain.usecase

import com.hdl.dancer.booking.domain.repo.DestinationRepo

class GetStartDestinationCase(
    private val destinationRepo: DestinationRepo,
) {
    operator fun invoke(): String {
        return destinationRepo.getStartDestination()
    }
}