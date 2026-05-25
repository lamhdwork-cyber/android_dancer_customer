package com.kantek.dancer.booking.data.repo

import com.kantek.dancer.booking.data.remote.api.NotificationApi
import com.kantek.dancer.booking.data.factory.NotificationFactory
import com.kantek.dancer.booking.domain.model.notification.INotification
import com.kantek.dancer.booking.domain.repo.NotificationRepo

class NotificationRepoImpl(
    private val notificationApi: NotificationApi,
    private val notificationFactory: NotificationFactory
) : NotificationRepo {

    override suspend fun fetchByPage(page: Int): List<INotification> {
        return notificationFactory.createList(
            notificationApi.fetchByPage(page).awaitNullable()?.data
        )
    }

    override suspend fun readAll() {
        notificationApi.readAll().awaitNullable()
    }

    override suspend fun readById(id: String) {
        notificationApi.readById(id).awaitNullable()
    }
}