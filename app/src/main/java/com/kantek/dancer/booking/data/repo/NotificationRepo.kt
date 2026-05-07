package com.kantek.dancer.booking.data.repo

import com.kantek.dancer.booking.data.remote.api.NotificationApi
import com.kantek.dancer.booking.domain.model.response.NotificationDTO

interface NotificationRepo {
    suspend fun fetchByPage(page: Int): List<NotificationDTO>?
    suspend fun readById(id: String)
    suspend fun readAll()
}

class FetchNotificationByPageRepoImpl(
    private val notificationApi: NotificationApi
) : NotificationRepo {

    override suspend fun fetchByPage(page: Int): List<NotificationDTO>? {
        return notificationApi.fetchByPage(page).awaitNullable()?.data
    }

    override suspend fun readAll() {
        notificationApi.readAll().awaitNullable()
    }

    override suspend fun readById(id: String) {
        notificationApi.readById(id).awaitNullable()
    }
}