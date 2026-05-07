package com.kantek.dancer.booking.domain.usecase

import android.support.core.extensions.withIO
import com.kantek.dancer.booking.data.repo.NotificationRepo
import com.kantek.dancer.booking.domain.factory.NotificationFactory
import com.kantek.dancer.booking.domain.model.ui.user.INotification

class NotificationUseCase(
    private val notificationRepo: NotificationRepo,
    private val notificationFactory: NotificationFactory
) {
    suspend operator fun invoke(page: Int): List<INotification> {
        return withIO { notificationFactory.createList(notificationRepo.fetchByPage(page)) }
    }

    suspend fun readAll(): Any {
        return withIO { notificationRepo.readAll() }
    }

    suspend fun readById(id: String): Any {
        return withIO { notificationRepo.readById(id) }
    }
}