package com.kantek.dancer.booking.data.repo

import com.kantek.dancer.booking.data.remote.api.NotificationApi
import com.kantek.dancer.booking.domain.model.response.NotificationDTO

interface NotificationRepo {
    suspend fun fetchByPage(page: Int): List<NotificationDTO>?
}

class FetchNotificationByPageRepoImpl(
    private val notificationApi: NotificationApi
):NotificationRepo {

    override suspend fun fetchByPage(page: Int): List<NotificationDTO>? {
        return notificationApi.fetchByPage(page).awaitNullable()?.data
    }
}