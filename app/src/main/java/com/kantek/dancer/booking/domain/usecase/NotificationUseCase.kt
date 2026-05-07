package com.kantek.dancer.booking.domain.usecase

import com.kantek.dancer.booking.data.repo.NotificationRepo
import com.kantek.dancer.booking.domain.factory.NotificationFactory
import com.kantek.dancer.booking.domain.model.ui.user.INotification

class NotificationUseCase(
    private val notificationRepo: NotificationRepo,
    private val notificationFactory: NotificationFactory
) {
    suspend operator fun invoke(page: Int): List<INotification> {
        return notificationFactory.createList(notificationRepo.fetchByPage(page))
    }

    suspend fun readAll(): Any {
        return notificationRepo.readAll()
    }

    suspend fun readById(id: String): Any {
        return notificationRepo.readById(id)
    }
}