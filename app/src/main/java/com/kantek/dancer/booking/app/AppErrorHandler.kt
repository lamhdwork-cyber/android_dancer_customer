package com.kantek.dancer.booking.app

import android.app.AlertDialog
import android.app.Service
import android.content.Context
import com.google.gson.JsonSyntaxException
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.domain.extension.ResourceException
import java.net.ConnectException
import java.net.UnknownHostException

interface AppErrorHandler {
    fun handle(activity: AppComponentAct, error: Throwable)
    fun handle(service: Service, error: Throwable)
}

class AppErrorHandlerImpl : AppErrorHandler {

    override fun handle(activity: AppComponentAct, error: Throwable) {
        handle(activity, activity.applicationContext, error)
    }

    override fun handle(service: Service, error: Throwable) {
        AlertDialog.Builder(service.applicationContext).setMessage(error.message)
            .create()
            .show()
    }

    private fun handle(
        activity: AppComponentAct,
        context: Context,
        error: Throwable
    ) {
        when (error) {
            is ResourceException -> activity.showNotification(error.resource)
            is UnknownHostException -> activity.showNotification(R.string.error_no_internet)
            is ConnectException,
            is JsonSyntaxException -> activity.showNotification(R.string.error_server)

            is ExpiredTokenException ->  activity.showExpiredTokenDialog(true)
            is InternalServerException -> activity.showNotification(R.string.error_internal_server)

            else -> {
                if (error.message == "Unauthorized !")
                    activity.showExpiredTokenDialog(true)
                else activity.showNotification(error.message ?: "error")
            }
        }
    }
}

