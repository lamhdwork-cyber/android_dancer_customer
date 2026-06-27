package com.hdl.dancer.booking.domain.usecase

import android.support.core.extensions.withIO
import com.hdl.dancer.booking.domain.repo.NotificationRepo
import com.hdl.dancer.booking.domain.model.notification.INotification

class NotificationUseCase(
    private val notificationRepo: NotificationRepo
) {
    suspend operator fun invoke(page: Int): List<INotification> {
        return withIO { notificationRepo.fetchByPage(page) }
    }

    suspend fun readAll(): Any {
        return withIO { notificationRepo.readAll() }
    }

    suspend fun readById(id: String): Any {
        return withIO { notificationRepo.readById(id) }
    }
}