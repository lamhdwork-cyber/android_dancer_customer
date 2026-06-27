package com.hdl.dancer.booking.data.repo

import com.hdl.dancer.booking.data.remote.api.NotificationApi
import com.hdl.dancer.booking.data.factory.NotificationFactory
import com.hdl.dancer.booking.domain.model.notification.INotification
import com.hdl.dancer.booking.domain.repo.NotificationRepo

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