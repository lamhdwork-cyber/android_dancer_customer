package com.kantek.dancer.booking.domain.repo

import com.kantek.dancer.booking.domain.model.notification.INotification

interface NotificationRepo {
    suspend fun fetchByPage(page: Int): List<INotification>
    suspend fun readById(id: String)
    suspend fun readAll()
}